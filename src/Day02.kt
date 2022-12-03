import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import PlayOutcome.*

enum class PlayOutcome(val codeLetter: Char, val scoreValue: Int) {
    Loose('X', 0),
    Draw('Y', 3),
    Win('Z', 6);

    companion object {
        fun from(codeLetter: Char): PlayOutcome {
            return values().first { it.codeLetter == codeLetter }
        }
    }
}

enum class Shape(val scoreValue: Int, val codeLetter: Char) {
    Rock(scoreValue = 1, codeLetter = 'A') {
        override val beatsShape: Shape
            get() = Scissor
    },
    Paper(scoreValue = 2, codeLetter = 'B') {
        override val beatsShape: Shape
            get() = Rock
    },
    Scissor(scoreValue = 3, codeLetter = 'C') {
        override val beatsShape: Shape
            get() = Paper
    };

    abstract val beatsShape: Shape

    fun playAgainst(other: Shape): PlayOutcome {
        return when {
            this.beatsShape == other -> Win
            this == other -> Draw
            other.beatsShape == this -> Loose
            else -> throw IllegalStateException("Outcome not supported")
        }
    }

    companion object {
        fun from(codeLetter: Char): Shape {
            return Shape.values().first { it.codeLetter == codeLetter }
        }

        fun fromMyCode(codeLetter: Char): Shape {
            return when (codeLetter) {
                'X' -> Rock
                'Y' -> Paper
                'Z' -> Scissor
                else -> throw IllegalArgumentException("Invalid code letter $codeLetter")
            }
        }

        fun fromMyWinningStrategy(strategy: PlayOutcome, foeShape: Shape): Shape {
            return when (strategy) {
                Win -> Shape.values().first { it.beatsShape == foeShape }
                Loose -> foeShape.beatsShape
                Draw -> foeShape
            }
        }
    }
}


fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val foe = Shape.from(it.first())
            val me = Shape.fromMyCode(it.last())

            val matchScore = me.playAgainst(foe).scoreValue

            return@sumOf me.scoreValue + matchScore
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val foe = Shape.from(it.first())
            val strategy = PlayOutcome.from(it.last())
            val me = Shape.fromMyWinningStrategy(strategy, foe)

            val matchScore = me.playAgainst(foe).scoreValue

            return@sumOf me.scoreValue + matchScore
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testOutput = part1(testInput)
    println("----SIMPLE TESTING----")
    println(testOutput)
    check(testOutput == 15)


    println()

    val input = readInput("Day02")

    println("----PART1----")
    val part1Output = part1(input)

    println("Total score for me is $part1Output")

    val testOutput2 = part2(testInput)
    check(testOutput2 == 12)

    println()
    println("----PART2----")

    val part2Output = part2(input)
    println("Total amount of score for Rock Paper Scissor is $part2Output")
}
