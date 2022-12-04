
val characters = (('a'.. 'z') + ('A' .. 'Z')).mapIndexed { index,char ->char to index + 1  }.toMap()

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf{
            val (firstCompartment, secondCompartment) = it.chunked(it.length / 2)
            val commonCharacter = firstCompartment.toSet().intersect(secondCompartment.toSet()).first()
            characters[commonCharacter]!!
        }

    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf {group ->
                val (firstElf, secondElf, thirdElf) = group.map { it.toSet() }
                 val badgeItemInGroup = firstElf.first { charItem ->
                    secondElf.contains(charItem) && thirdElf.contains(charItem)
                }
                characters[badgeItemInGroup]!!
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testOutput = part1(testInput)
    val testOutput2 = part2(testInput)
    println("----SIMPLE TESTING----")
    println(testOutput)
    println(testOutput2)

    println("----PART1----")
    val input = readInput("Day03")
    val part1Output = part1(input)

    println("Total priority for is $part1Output")

    println()
    println("----PART2----")
    val part2Output = part2(input)
    println("Total priority for all groups is $part2Output")
}
