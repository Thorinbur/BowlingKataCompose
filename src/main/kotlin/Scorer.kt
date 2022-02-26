data class Frame(
    val firstRoll: Int,
    val secondRoll: Int?,
) {
    val isStrike = firstRoll == 10
    val isFinished = isStrike || secondRoll != null
    val isSpare = !isStrike && firstRoll + (secondRoll ?: 0) == 10
}

data class Game(
    val frames: List<Frame>,
    val bonusRoll1: Int? = null,
    val bonusRoll2: Int? = null
) {
    private fun rollsFollowing(frameNumber: Int): List<Int> {
        val remainingRolls = frames.drop(frameNumber + 1).flatMap { listOf(it.firstRoll, it.secondRoll) }
        return (remainingRolls + bonusRoll1 + bonusRoll2).filterNotNull()
    }

    fun getBonusRolls(frameNumber: Int): Pair<Int?, Int?> {
        val frame = frames[frameNumber]
        val rollsFollowing = rollsFollowing(frameNumber)
        val nextRoll = rollsFollowing.getOrNull(0)
        val secondNextRoll = rollsFollowing.getOrNull(1)
        return Pair(
            nextRoll.takeIf { frame.let { it.isSpare || it.isStrike } },
            secondNextRoll?.takeIf { frame.isStrike }
        )
    }
}

class Scorer {
    fun scoreFrame(frame: Frame, bonusRoll1: Int? = null, bonusRoll2: Int? = null) =
        frame.firstRoll + (frame.secondRoll ?: 0) + (bonusRoll1 ?: 0) + (bonusRoll2 ?: 0)

    fun scoreGame(game: Game): Int {
        return List(game.frames.size) { i ->
            scoreFrame(game, i)!!
        }.takeIf { it.isNotEmpty() }?.reduce { acc, i -> acc + i } ?: 0
    }

    fun scoreFrame(game: Game, frameNumber: Int): Int? {
        if (game.frames.getOrNull(frameNumber) == null) return null
        val (bonusRoll1, bonusRoll2) = game.getBonusRolls(frameNumber)
        return scoreFrame(
            game.frames[frameNumber],
            bonusRoll1,
            bonusRoll2,
        )
    }
}