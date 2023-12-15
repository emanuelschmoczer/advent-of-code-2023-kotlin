package day04

import currentPackage
import isNumber
import println
import readInput
import kotlin.math.pow

fun main() {
    fun extractCardNumbers(line: String): Pair<List<Int>, List<Int>> {
        val cardNumbers = line.split(": ").last().split(" | ")
        val winningNumbers = cardNumbers.first().split(" ").filter { it.isNumber() }.map { it.toInt() }
        val numbersWeHave = cardNumbers.last().split(" ").filter { it.isNumber() }.map { it.toInt() }
        return Pair(winningNumbers, numbersWeHave)
    }

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val (winningNumbers, numbersWeHave) = extractCardNumbers(line)
        numbersWeHave.count { winningNumbers.contains(it) }
            .let { 2.toDouble().pow(it - 1).toInt() }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { line ->
            val (winningNumbers, numbersWeHave) = extractCardNumbers(line)
            val count = numbersWeHave.count { winningNumbers.contains(it) }
            Card(count)
        }
        return cards.withIndex().reversed().map { card ->
            val additional = cards.subList(card.index + 1, card.index + 1 + card.value.winning)
            card.value.cards.addAll(additional)
            card
        }.sumOf { card ->
            card.value.sum()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${object {}.currentPackage()}/test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("${object {}.currentPackage()}/input")
    part1(input).println()
    part2(input).println()
}

private class Card(
    val winning: Int,
    val cards: MutableList<Card> = mutableListOf(),
) {
    fun sum(): Int {
        if (cards.isEmpty()) {
            return 1
        }
        return 1 + cards.sumOf { it.sum() }
    }
}
