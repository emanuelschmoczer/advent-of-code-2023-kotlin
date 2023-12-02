package day02

import java.security.InvalidParameterException

class Game private constructor(
    val id: Int,
    private val rounds: List<Round>,
) {
    companion object {
        fun toGame(line: String): Game {
            val gameParts = line.split(":")
            val gameNumber = gameParts.first().split(" ").last().toInt()
            val rounds = gameParts.last()
                .split(";")
                .map { round ->
                    var red: Red? = null
                    var green: Green? = null
                    var blue: Blue? = null
                    round.split(",")
                        .forEach { cube ->
                            val currentCube = cube.trim().split(" ")
                            val cubeCount = currentCube.first().toInt()
                            when (currentCube.last()) {
                                "red" -> red = Red(cubeCount)
                                "green" -> green = Green(cubeCount)
                                "blue" -> blue = Blue(cubeCount)
                                else -> throw InvalidParameterException("invalid color")
                            }
                        }
                    Round(red, green, blue)
                }
            return Game(gameNumber, rounds)
        }
    }

    fun isPossible() = rounds.all { it.isPossible() }

    fun minCubesRed() = rounds.mapNotNull { it.red?.cubes }.maxOrNull() ?: 1

    fun minCubesGreen() = rounds.mapNotNull { it.green?.cubes }.maxOrNull() ?: 1

    fun minCubesBlue() = rounds.mapNotNull { it.blue?.cubes }.maxOrNull() ?: 1
}

private data class Round(
    val red: Red?,
    val green: Green?,
    val blue: Blue?,
) {
    fun isPossible(): Boolean {
        return (red == null || red.cubes <= red.maxAllowed) &&
            (green == null || green.cubes <= green.maxAllowed) &&
            (blue == null || blue.cubes <= blue.maxAllowed)
    }
}

private data class Red(
    val cubes: Int,
    val maxAllowed: Int = 12,
)

private data class Green(
    val cubes: Int,
    val maxAllowed: Int = 13,
)

private data class Blue(
    val cubes: Int,
    val maxAllowed: Int = 14,
)
