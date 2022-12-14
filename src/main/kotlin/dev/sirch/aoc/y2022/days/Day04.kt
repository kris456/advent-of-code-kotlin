package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

fun parseRange(rangeString: String): IntRange {
    val (start, end) = rangeString.split("-")
    return start.toInt() .. end.toInt()
}
class Day04(testing: Boolean = false): Day(2022, 4, testing) {
  override fun part1(): Int {
      return inputLines.count {
          val (elfOne, elfTwo) = it.split(",")

          val elfOneRange = parseRange(elfOne).toList()
          val elfTwoRange = parseRange(elfTwo).toList()
          elfOneRange.containsAll(elfTwoRange) || elfTwoRange.containsAll(elfOneRange)
      }
  }

  override fun part2(): Int {
      return inputLines.count {
          val (elfOne, elfTwo) = it.split(",")

          val elfOneRange = parseRange(elfOne).toList()
          val elfTwoRange = parseRange(elfTwo).toList()

          elfOneRange.any {
              it in elfTwoRange
          }

      }
  }
}
