package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

class Day06(testing: Boolean = false) : Day(2022, 6, testing) {
    override fun part1(): Int {
        val datastream = inputLines.first()

        return getIndexAfterConsecutiveUniqueCharacters(datastream, amountOfUniqueChars = 4)
    }

    override fun part2(): Int {
        val datastream = inputLines.first()

        return getIndexAfterConsecutiveUniqueCharacters(datastream, amountOfUniqueChars = 14)
    }
}

fun getIndexAfterConsecutiveUniqueCharacters(datastream: String, amountOfUniqueChars: Int): Int {
    val uniqueCharacters: MutableList<Char> = mutableListOf()

    datastream.forEachIndexed { index, char ->
        if (uniqueCharacters.size == amountOfUniqueChars) {
            return index
        }
        if (uniqueCharacters.contains(char)) {
            val indexOfReplicatedChar = uniqueCharacters.indexOf(char) + 1
            val charsToRemove = uniqueCharacters.subList(0, indexOfReplicatedChar)
            uniqueCharacters.removeAll(charsToRemove)
            uniqueCharacters.add(char)
        } else {
            uniqueCharacters.add(char)
        }

    }

    return -1
}
