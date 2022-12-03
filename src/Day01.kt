data class Elf(
    val elfNumber: Int,
    val calories: List<Int> = emptyList()
) {
    constructor(elfIndex: Int, caloriesString: String) : this(
        elfNumber = elfIndex + 1,
        calories = caloriesString.split(",")
            .filter { it != "" }
            .map { it.toInt() })

    val totalCalories = calories.sum()
}

fun main() {
    fun parseElves(input: List<String>): List<Elf> {
        return input.joinToString(",") {
            if (it == "") "|" else it
        }.split("|")
            .mapIndexed { elfNumber, elfCalories ->
                Elf(elfNumber, elfCalories)
            }
    }

    fun part1(input: List<String>): Elf {
        val allElves = parseElves(input)

        return allElves.maxBy { it.totalCalories }
    }

    fun part2(input: List<String>): Int {
        val allElves = parseElves(input)

        return allElves.sortedByDescending { it.totalCalories }.take(3).sumOf { it.totalCalories }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testOutput = part1(testInput)
    println("----SIMPLE TESTING----")
    println("Elf with top total calories is $testOutput")
    check(testOutput.elfNumber == 4)

    val testOutput2 = part2(testInput)
    println("Total amount of calories for 3 top elves is $testOutput2")
    check(testOutput2 == 45000)

    println()

    val input = readInput("Day01")
    val part1Output = part1(input)

    println("----PART1----")

    check(part1Output.elfNumber == 61)
    println("Elf with top total calories is $part1Output")
    val part2Output = part2(input)

    println()
    println("----PART2----")

    println("Total amount of calories for 3 top elves is $part2Output")
}
