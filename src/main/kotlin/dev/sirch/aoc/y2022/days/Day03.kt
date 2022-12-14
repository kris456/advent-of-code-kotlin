package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

val characters = (('a'..'z') + ('A'..'Z')).mapIndexed { index, char -> char to index + 1 }.toMap()

class Day03(testing: Boolean = false) : Day(2022, 3, testing) {
    override fun part1(): Int {
        return inputLines.sumOf {
            val (firstCompartment, secondCompartment) = it.chunked(it.length / 2)
            val commonCharacter = firstCompartment.toSet().intersect(secondCompartment.toSet()).first()
            characters[commonCharacter]!!
        }
    }

    override fun part2(): Int {
        return inputLines.chunked(3)
            .sumOf { group ->
                val (firstElf, secondElf, thirdElf) = group.map { it.toSet() }
                val badgeItemInGroup = firstElf.first { charItem ->
                    secondElf.contains(charItem) && thirdElf.contains(charItem)
                }
                characters[badgeItemInGroup]!!
            }
    }
}
