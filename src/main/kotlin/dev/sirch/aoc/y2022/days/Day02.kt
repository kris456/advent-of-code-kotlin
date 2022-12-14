package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class Day02(testing: Boolean = false): Day(2022, 2, testing) {
  override fun part1(): Int {
      return inputLines.sumOf {
          val foe = Shape.from(it.first())
          val me = Shape.fromMyCode(it.last())

          val matchScore = me.playAgainst(foe).scoreValue

          return@sumOf me.scoreValue + matchScore
      }
  }

  override fun part2(): Int {
      return inputLines.sumOf {
          val foe = Shape.from(it.first())
          val strategy = PlayOutcome.from(it.last())
          val me = Shape.fromMyWinningStrategy(strategy, foe)

          val matchScore = me.playAgainst(foe).scoreValue

          return@sumOf me.scoreValue + matchScore
      }
  }
}

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
            this.beatsShape == other -> PlayOutcome.Win
            this == other -> PlayOutcome.Draw
            other.beatsShape == this -> PlayOutcome.Loose
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
                PlayOutcome.Win -> Shape.values().first { it.beatsShape == foeShape }
                PlayOutcome.Loose -> foeShape.beatsShape
                PlayOutcome.Draw -> foeShape
            }
        }
    }
}
