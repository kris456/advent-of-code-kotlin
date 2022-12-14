package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

class Stack {

    private val stack = mutableListOf<Char>()

    fun peek(): Char {
        return stack.last()
    }

    fun pop(amount: Int = 1): List<Char> {
        return (0 until amount).map {
            stack.removeLast()
        }
    }

    fun push(crates: List<Char>) {
        crates.forEach {
            stack.add(it)
        }
    }
}

class CrateStacks(val cratesRaw: List<String>, val numberingWithIndexForCrates: List<Pair<Int, Int>>) {
    private val stacks = initializeStacks()

    fun initializeStacks(): Map<Int, Stack> {
        return numberingWithIndexForCrates.associate { (numbering, crateIndexInRawString) ->
            numbering to initializeStack(crateIndexInRawString)
        }
    }

    fun initializeStack(indexForStack: Int): Stack {
        val stack = Stack()
        cratesRaw.map {
            it.getOrElse(indexForStack) { ' ' }
        }.filter {
            it != ' '
        }.reversed()
            .forEach {
                stack.push(listOf(it))
            }

        return stack
    }

    fun getAllCharOnTopOfStacks(): String {
        return stacks.map {
            it.value.peek()
        }.joinToString("")
    }

    fun moveCratesBetweenStack(move: Move, multipleMoves: Boolean = false) {
        val fromStack = stacks[move.from]!!
        val toStack = stacks[move.to]!!

        if (multipleMoves) {
            val crates = fromStack.pop(move.amount).reversed()
            toStack.push(crates)
        } else {
            repeat(move.amount) {
                val removedCrate = fromStack.pop()
                toStack.push(removedCrate)
            }
        }
    }
}

data class Move(
    val amount: Int,
    val from: Int,
    val to: Int
)

fun parseMoves(movesRaw: List<String>): List<Move> {
    val commandKeywords = Regex("move|from|to")
    return movesRaw.map { moveRaw ->
        val (amount, from, to) = commandKeywords.split(moveRaw).map { it.trim() }.filter { it.isNotBlank() }
        Move(amount.toInt(), from.toInt(), to.toInt())
    }
}

fun getIndexesForCrates(numberingForCrates: String): List<Pair<Int, Int>> {
    val numbers = numberingForCrates.trim().split("   ")

    return numbers.map {
        it.toInt() to numberingForCrates.indexOf(it)
    }
}

fun performMovesAndGetTopCrates(input: List<String>, multipleMove: Boolean = false): String {
    val indexofsplit = input.indexOf("")
    val crates = input.subList(0, indexofsplit - 1)
    val numberingforcrates = input[indexofsplit - 1]
    val indexesForCrates = getIndexesForCrates(numberingforcrates)
    val stacks = CrateStacks(crates, indexesForCrates)

    val moves = input.subList(indexofsplit + 1, input.size)
    val parsedmoves = parseMoves(moves)
    parsedmoves.forEach {
        stacks.moveCratesBetweenStack(it, multipleMove)
    }


    return stacks.getAllCharOnTopOfStacks()
}
class Day05(testing: Boolean = false): Day(2022, 5, testing) {
  override fun part1(): String {
      return performMovesAndGetTopCrates(inputLines)
  }

  override fun part2(): String {
      return performMovesAndGetTopCrates(inputLines, multipleMove = true)
  }
}
