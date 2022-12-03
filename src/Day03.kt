import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import PlayOutcome.*

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testOutput = part1(testInput)
    println("----SIMPLE TESTING----")
    println(testOutput)
}
