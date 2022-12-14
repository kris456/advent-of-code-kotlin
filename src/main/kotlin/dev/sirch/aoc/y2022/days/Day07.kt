package dev.sirch.aoc.y2022.days

import dev.sirch.aoc.Day

class Day07(testing: Boolean = false) : Day(2022, 7, testing) {
    override fun part1(): Int {
        val tree = constructTree(inputLines)
        return tree.getAllDirsWithLessSizeThanUpperLimit(100000)!!
    }

    override fun part2(): Int {
        val tree = constructTree(inputLines)
        val maximumSize = 70000000
        val unusedSpace = maximumSize - tree.size
        val updateRequiresSize = 30000000
        val needsToFreeSize = updateRequiresSize - unusedSpace

        return tree.getDirSizeClosestToLowerLimit(needsToFreeSize)!!
    }
}

data class Node(
    val name: String,
    var size: Int = 0,
    val parent: Node? = null,
    val children: MutableMap<String, Node> = mutableMapOf()
) {
    fun addChild(name: String, size: Int = 0) {
        val newNode = Node(name = name, parent = this, size = size)
        children[name] = newNode
    }

    fun getAllDirsWithLessSizeThanUpperLimit(upperLimit: Int): Int? {
        val isDir = children.isNotEmpty()
        if (!isDir) {
            return null
        }

        var totalSizeOfDirsWithLessThanUpperLimit = if (size <= upperLimit) size else 0
        children.forEach { (name, node) ->
            val childSizes = node.getAllDirsWithLessSizeThanUpperLimit(upperLimit)
            if (childSizes != null) {
                totalSizeOfDirsWithLessThanUpperLimit += childSizes
            }

        }
        return totalSizeOfDirsWithLessThanUpperLimit
    }

    fun getDirSizeClosestToLowerLimit(lowerLimit: Int): Int? {
        if (children.isEmpty()) {
            return null
        }

        var sizeClosestToLowerLimit = size
        children.forEach { (name, node) ->
            val childDirClosestToLowerLimit = node.getDirSizeClosestToLowerLimit(lowerLimit)
            if (childDirClosestToLowerLimit != null) {
                if (childDirClosestToLowerLimit in (lowerLimit until sizeClosestToLowerLimit)) {
                    sizeClosestToLowerLimit = childDirClosestToLowerLimit
                }
            }

        }

        return sizeClosestToLowerLimit
    }

    fun calculateDirSizes(): Int {
        return if (size != 0) {
            size
        } else {
            val sizeOfDirs = children.map { (key, node) ->
                node.calculateDirSizes()
            }.sum()
            size = sizeOfDirs

            size
        }
    }

    fun getChild(name: String): Node? {
        return children.get(name)
    }
}

fun constructTree(input: List<String>): Node {
    val rootNode = Node(name = "root")
    var currentNode = rootNode
    input.forEach {
        if (it.startsWith("$")) {
            val operators = it.split(" ")
            if (operators[1] == "cd") {
                val dir = operators[2]
                currentNode = if (dir == "..") {
                    currentNode.parent ?: throw IllegalStateException("Root node does not have a parent")
                } else {
                    currentNode.getChild(dir) ?: throw IllegalStateException("Could not find node iwth name=$dir")
                }
            }
        } else {
            val (prefix, name) = it.split(" ")
            if (prefix == "dir") {
                currentNode.addChild(name)
            } else {
                currentNode.addChild(name, size = prefix.toInt())
            }
        }
    }
    rootNode.calculateDirSizes()
    return rootNode
}

