package dev.sirch.aoc.y2022.days

import assertk.assertThat
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Test

class Day01Test{

  @Test
  fun `should return something for part1 with testInput`() {
    val day = Day01(testing = true)
    val part1 = day.part1()
    println("Part 1 testOutput: $part1")

    assertThat(part1).isNotEqualTo(0)
  }

  @Test
  fun `should return something for part2 with testInput`() {
    val day = Day01(testing = true)
    val part2 = day.part2()
    println("Part 2 testOutput: $part2")

    assertThat(part2).isNotEqualTo(0)
  }

  @Test
  fun `should return something for part1`() {
    val day = Day01()
    val part1 = day.part1()
    println("Part 1 output: $part1")

    assertThat(part1).isNotEqualTo(0)
  }

  @Test
  fun `should return something for part2`() {
    val day = Day01()
    val part2 = day.part2()
    println("Part 2 output: $part2")

    assertThat(part2).isNotEqualTo(0)
  }
}
