package day03

import currentPackage
import isNotDigit
import isNumber
import println
import readInput

fun main() {
    fun findLeftNumber(line: String, index: Int): Int? {
        var startIndex = index
        while (startIndex > 0) {
            if (line[startIndex - 1].isDigit()) {
                startIndex -= 1
            } else {
                break
            }
        }
        return line.substring(startIndex, index).takeIf { it.isNumber() }?.toInt()
    }

    fun findRightNumber(line: String, index: Int): Int? {
        var endIndex = index
        while (endIndex < line.lastIndex) {
            if (line[endIndex + 1].isDigit()) {
                endIndex += 1
            } else {
                break
            }
        }
        return line.substring(index + 1, endIndex + 1).takeIf { it.isNumber() }?.toInt()
    }

    fun findHorizontalNumbers(line: String, index: Int): List<Int> {
        return listOf(findLeftNumber(line, index), findRightNumber(line, index)).mapNotNull { it }
    }

    fun findVerticalNumbers(line: String, index: Int): List<Int> {
        if (line[index].isDigit()) {
            var endIndex = index
            while (endIndex < line.lastIndex) {
                if (line[endIndex + 1].isDigit()) {
                    endIndex += 1
                } else {
                    break
                }
            }
            return listOf(findLeftNumber(line, endIndex + 1)).mapNotNull { it }
        }
        return findHorizontalNumbers(line, index)
    }

    fun findNumbersAround(x: Int, y: Int, input: List<String>, precondition: (Char) -> Boolean): List<Int> {
        if (!input[y][x].let(precondition)) {
            return emptyList()
        }
        val partNumbers = mutableListOf<Int>()
        partNumbers.addAll(findHorizontalNumbers(input[y], x))
        if (y > 0) {
            partNumbers.addAll(findVerticalNumbers(input[y - 1], x))
        }
        if (y < input.lastIndex) {
            partNumbers.addAll(findVerticalNumbers(input[y + 1], x))
        }
        return partNumbers
    }

    fun part1(input: List<String>): Int {
        return input.indices.sumOf { line ->
            input[line].indices.sumOf { column ->
                findNumbersAround(column, line, input, Char::isSymbol).sum()
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.indices.sumOf { line ->
            input[line].indices.sumOf { column ->
                val gearNumbers = findNumbersAround(column, line, input, Char::isGear)
                    .takeIf { it.size == 2 } ?: listOf(0)
                gearNumbers.reduce(Int::times)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${object {}.currentPackage()}/test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("${object {}.currentPackage()}/input")
    part1(input).println()
    part2(input).println()
}

private fun Char.isSymbol() = this.isNotDigit() && this != '.'

private fun Char.isGear() = this == '*'
