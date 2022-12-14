import com.github.mustachejava.DefaultMustacheFactory
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.StringWriter
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import io.github.furstenheim.CopyDown;

const val mainPath = "src/main"
const val testPath = "src/test"
const val resourcesPath = "$mainPath/resources"
const val testResourcesPath = "$testPath/resources"
const val templatePath = "$resourcesPath/templates"
const val year = "2022"
const val baseInputPath = "$resourcesPath/inputs/y$year"
const val baseAssignmentPath = "$resourcesPath/assignments/y$year"
const val baseTestInputPath = "$testResourcesPath/inputs/y$year"
const val aocBaseURL = "https://adventofcode.com"

abstract class NewDayTask : DefaultTask() {
    @TaskAction
    fun newDay() {
        val aocPackagePath = getPackagePathForAoc()
        val nextDayNumber = getNextDayNumber(aocPackagePath.daysPath)
        val dayConfig = DayConfig(
            year = year.toInt(),
            dayNr = nextDayNumber.toInt(),
            day = nextDayNumber,
            packageName = aocPackagePath.packageName
        )
        createNewFileFromTemplate(dayConfig, aocPackagePath, isTest = false)
        createNewFileFromTemplate(dayConfig, aocPackagePath, isTest = true)
        createInputFiles(dayConfig)
        getAssignment(dayConfig)
    }

    private fun getDayInputContent(year: Int, dayNr: Int): String? {
        val sessionCookie = System.getenv().get("AOC_SESSION")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$aocBaseURL/$year/day/$dayNr/input"))
            .header("Cookie", "session=$sessionCookie")
            .GET()
            .build()

        val client = HttpClient.newHttpClient()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode().toString().startsWith("2")) {
            return response.body()
        } else {
            return null
        }
    }

    private fun getAssignment(dayConfig: DayConfig) {
        val sessionCookie = System.getenv().get("AOC_SESSION")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$aocBaseURL/$year/day/${dayConfig.dayNr}"))
            .header("Cookie", "session=$sessionCookie")
            .GET()
            .build()

        val client = HttpClient.newHttpClient()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode().toString().startsWith("2")) {
            val content = response.body().substringAfter("<main>").substringBefore("</main>")
            File("$baseAssignmentPath/Day${dayConfig.day}.md").also {
                it.parentFile.mkdirs()
                val markdown = CopyDown().convert(content)
                it.writeText(markdown)
            }
        }


}
    private fun createInputFiles(dayConfig: DayConfig) {

        val inputContent = getDayInputContent(dayConfig.year, dayConfig.dayNr)
        File("$baseInputPath/Day${dayConfig.day}.txt").also{
            it.parentFile.mkdirs()
            if(inputContent != null) {
                it.writeText(inputContent)
            } else {
                it.createNewFile()
            }
        }

        File("$baseTestInputPath/Day${dayConfig.day}.txt").also {
            it.parentFile.mkdirs()
            it.createNewFile()
        }
    }

    private fun createNewFileFromTemplate(dayConfig: DayConfig, aocPackagePath: ProjectPath, isTest: Boolean) {
        val templateName = if (isTest) "DayTestTemplate.mustache" else "DayTemplate.mustache"
        val newDayContent = createNewDayFromTemplate(dayConfig, templateName)

        val outputFileName = if (isTest) "${aocPackagePath.testDaysPath}/Day${dayConfig.day}Test.kt" else "${aocPackagePath.daysPath}/Day${dayConfig.day}.kt"
        File(outputFileName).writeText(newDayContent)
    }

    private fun getPackagePathForAoc(): ProjectPath {
        val groupIdAsPackageName = this.project.group.toString().replace(".", "/")

        val name = this.project.name
        val rootProjectDir = this.project.projectDir.absolutePath
        val aocPackagePath = "$mainPath/kotlin/$groupIdAsPackageName/$name"
        val testPath = "$testPath/kotlin/$groupIdAsPackageName/$name"
        return ProjectPath(aocPackagePath, testPath, rootProjectDir)
    }

    fun createNewDayFromTemplate(dayConfig: DayConfig, templateName: String): String {
        val myNewFile = DefaultMustacheFactory()
            .compile("$templatePath/$templateName")
            .execute(StringWriter(), dayConfig)
            .toString()

        return myNewFile
    }

    private fun getNextDayNumber(pathToDays: String): String {
        val regexDay = Regex("Day\\d+.kt")
        val existingDays = File(pathToDays)
            .listFiles()
            ?.map {
                it.name
            }
            ?.filter {
                regexDay.matches(it)
            }?.mapNotNull {
                Regex("\\d+").find(it)?.value?.toInt()
            }?.sorted()
        val newDayNumber = existingDays?.maxOrNull()?.let{
            it + 1
        }  ?: 1
        val newDayString = newDayNumber.toString().let {
            if (it.length == 1) "0$it" else it
        }
        return newDayString
    }
}

data class DayConfig(
    val year: Int,
    val dayNr: Int,
    val day: String,
    val packageName: String
)

data class ProjectPath(
    val path: String,
    val testPath: String,
    private val projectRootDir: String
) {
    val absolutePath: String = "$projectRootDir/$path"
    val daysPath = "$path/y$year/days"
    val testDaysPath = "$testPath/y$year/days"
    val packageName = daysPath.replace("/", ".")
        .replace("src.main.kotlin.", "")
}