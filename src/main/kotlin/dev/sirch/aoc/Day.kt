package dev.sirch.aoc

import java.io.File
import java.io.FileNotFoundException

abstract class Day(year: Int, val day: Int, testing: Boolean = false) {
    val dayAsString = getDayWithPrefixIfOneDigit()
    val mainPath = "src/main"
    val testPath = "src/test"
    val inputPath = if (!testing) mainPath else testPath
    val fullPath = "$inputPath/resources/inputs/y$year/Day$dayAsString.txt"

    val inputLines by lazy {
        kotlin.runCatching {
            File(fullPath).readLines()
        }.onFailure {
            when (it) {
                is FileNotFoundException -> {
                    println("Could not find input file for puzzle, please make it")
                    throw it
                }

                else -> throw it
            }
        }.getOrThrow()
    }

    abstract fun part1(): Any
    abstract fun part2(): Any
    fun getDayWithPrefixIfOneDigit(): String {
        val dayAsString = day.toString()
        return if (dayAsString.length == 1) {
            "0$dayAsString"
        } else {
            dayAsString
        }
    }
}