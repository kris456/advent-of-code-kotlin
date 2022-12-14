package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

class Day01(testing: Boolean = false): Day(2022, 1, testing) {
  override fun part1(): Int {
      val allElves = parseElves(inputLines)

      return allElves.maxBy { it.totalCalories }.totalCalories
  }

  override fun part2(): Int {
      val allElves = parseElves(inputLines)

      return allElves.sortedByDescending { it.totalCalories }.take(3).sumOf { it.totalCalories }
  }

    fun parseElves(input: List<String>): List<Elf> {
        return input.joinToString(",") {
            if (it == "") "|" else it
        }.split("|")
            .mapIndexed { elfNumber, elfCalories ->
                Elf(elfNumber, elfCalories)
            }
    }
}

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
