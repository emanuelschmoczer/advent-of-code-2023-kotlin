package day01

import currentPackage
import println
import readInput

fun main() {
    val numbers = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            var mutableLine = line
            numbers.indices.forEach {
                mutableLine = mutableLine.replace(it.toString(), numbers[it])
            }
            mutableLine
        }.sumOf { line ->
            val firstDigit = line.findAnyOf(numbers)?.second ?: ""
            val lastDigit = line.findLastAnyOf(numbers)?.second ?: ""
            val realFirstDigit = numbers.indexOf(firstDigit).toString()
            val realLastDigit = numbers.indexOf(lastDigit).toString()
            "$realFirstDigit$realLastDigit".toIntOrNull() ?: 0
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${object{}.currentPackage()}/test")
    check(part1(testInput) == 142)

    val input = readInput("${object{}.currentPackage()}/input")
    part1(input).println()

    val testInput2 = readInput("${object{}.currentPackage()}/test2")
    check(part2(testInput2) == 281)
    part2(input).println()
}
