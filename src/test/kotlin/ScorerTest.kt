import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ScorerTest {
    @Test
    fun `score single roll`() {
        val scorer = Scorer()
        val frame = Frame(5, null)

        val result = scorer.scoreFrame(frame)

        assertEquals(5, result)
    }

    @Test
    fun `score simple frame with 2 rolls`() {
        val scorer = Scorer()
        val frame = Frame(5, 2)

        val result = scorer.scoreFrame(frame)

        assertEquals(7, result)
    }

    @Test
    fun `score frame with spare`() {
        val scorer = Scorer()
        val frame = Frame(5, 5)
        val bonusRoll = 7

        val result = scorer.scoreFrame(frame, bonusRoll)

        assertEquals(17, result)
    }


    @Test
    fun `score frame with strike`() {
        val scorer = Scorer()
        val frame = Frame(10, null)
        val bonusRoll1 = 2
        val bonusRoll2 = 2

        val result = scorer.scoreFrame(frame, bonusRoll1, bonusRoll2)

        assertEquals(14, result)
    }

    @Test
    fun `score empty game`() {
        val scorer = Scorer()
        val game = Game(
            listOf()
        )

        val result = scorer.scoreGame(game)

        assertEquals(0, result)
    }

    @Test
    fun `score simple game`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(1, 1),
                Frame(1, 1),
                Frame(1, 1),
            )
        )

        val result = scorer.scoreGame(game)

        assertEquals(6, result)
    }

    @Test
    fun `score game with spare`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(1, 9),
                Frame(1, 1)
            )
        )

        val result = scorer.scoreGame(game)

        assertEquals(13, result)
    }

    @Test
    fun `score game with strike`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(10, null),
                Frame(1, 1)
            )
        )

        val result = scorer.scoreGame(game)

        assertEquals(14, result)
    }

    @Test
    fun `score game with 2 strike in a row`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(10, null),
                Frame(10, null),
                Frame(1, 1),
            )
        )

        val result = scorer.scoreGame(game)

        assertEquals(35, result)
    }

    @Test
    fun `game ending with spare`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(0, 0),
                Frame(2, 8)
            ),
            3
        )

        val result = scorer.scoreGame(game)

        assertEquals(13, result)
    }

    @Test
    fun `game ending with strike`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(0, 0),
                Frame(10, null)
            ),
            3,
            3
        )

        val result = scorer.scoreGame(game)

        assertEquals(16, result)
    }

    @Test
    fun `score frame by number`() {
        val scorer = Scorer()
        val game = Game(
            listOf(
                Frame(10, null),
                Frame(10, null),
                Frame(10, null),
            ),
            3,
            3
        )

        val result = scorer.scoreFrame(game, 1)

        assertEquals(23, result)
    }
}