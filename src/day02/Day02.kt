package day02

import currentPackage
import println
import readInput

fun main() {
    fun part1(input: List<String>) = input.map(Game::toGame)
        .filter { it.isPossible() }
        .sumOf { it.id }

    fun part2(input: List<String>): Int {
        return input.map(Game::toGame)
            .sumOf { game ->
                game.minCubesRed() * game.minCubesGreen() * game.minCubesBlue()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${object {}.currentPackage()}/test")
    check(part1(testInput) == 8)

    val input = readInput("${object {}.currentPackage()}/input")
    part1(input).println()
    part2(input).println()
}
