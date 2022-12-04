

fun parseRange(rangeString: String): IntRange {
    val (start, end) = rangeString.split("-")
    return start.toInt() .. end.toInt()
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.count {
            val (elfOne, elfTwo) = it.split(",")

            val elfOneRange = parseRange(elfOne).toList()
            val elfTwoRange = parseRange(elfTwo).toList()
            elfOneRange.containsAll(elfTwoRange) || elfTwoRange.containsAll(elfOneRange)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val (elfOne, elfTwo) = it.split(",")

            val elfOneRange = parseRange(elfOne).toList()
            val elfTwoRange = parseRange(elfTwo).toList()

            elfOneRange.any {
                it in elfTwoRange
            }

        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testOutput = part1(testInput)
    val testOutput2 = part2(testInput)


    println("----SIMPLE TESTING----")
    println(testOutput)
    println(testOutput2)

    println("----PART1----")
    val input = readInput("Day04")
    val part1Output = part1(input)

    println("Totally contained ranges are $part1Output")


    println()
    println("----PART2----")
    val part2Output = part2(input)
    println("Totally ranges that overlap are $part2Output")

}
