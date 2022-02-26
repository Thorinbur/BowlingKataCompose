import androidx.compose.runtime.withFrameMillis
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SimulatorTest {
    @Test
    fun `new game has no frames`() {
        val simulator = Simulator()

        assertEquals(0, simulator.game.frames.count())
    }

    @Test
    fun `nextRoll is added to the game`() {
        val simulator = Simulator()

        simulator.addRoll(5)

        assertEquals(1, simulator.game.frames.count())
        assertEquals(Frame(5, null), simulator.game.frames[0])
    }


    @Test
    fun `second roll in a frame is added to that frame`() {
        val simulator = Simulator()

        simulator.addRoll(5)
        simulator.addRoll(2)

        assertEquals(1, simulator.game.frames.count())
        assertEquals(Frame(5, 2), simulator.game.frames[0])
    }

    @Test
    fun `3 strikes in a row`() {
        val simulator = Simulator()

        simulator.addRoll(10)
        simulator.addRoll(10)
        simulator.addRoll(10)

        assertEquals(3, simulator.game.frames.count())
        assertEquals(Frame(10, null), simulator.game.frames[0])
        assertEquals(Frame(10, null), simulator.game.frames[1])
        assertEquals(Frame(10, null), simulator.game.frames[2])
    }

    @Test
    fun `test bonus roll`() {
        val simulator = Simulator(2)

        simulator.addRoll(10)
        simulator.addRoll(10)
        simulator.addRoll(2)
        simulator.addRoll(4)

        assertEquals(2, simulator.game.frames.count())
        assertEquals(Frame(10, null), simulator.game.frames[0])
        assertEquals(Frame(10, null), simulator.game.frames[1])
        assertEquals(2, simulator.game.bonusRoll1)
        assertEquals(4, simulator.game.bonusRoll2)
    }

    @Test
    fun `check hasRoll no bonus`(){
        val simulator = Simulator(1)

        simulator.addRoll(2)
        assertTrue(simulator.hasRoll())
        simulator.addRoll(5)
        assertFalse(simulator.hasRoll())
    }


    @Test
    fun `check hasRoll with spare bonus`(){
        val simulator = Simulator(1)

        simulator.addRoll(2)
        assertTrue(simulator.hasRoll())
        simulator.addRoll(8)
        assertTrue(simulator.hasRoll())
        simulator.addRoll(8)
        assertFalse(simulator.hasRoll())
    }

    @Test
    fun `check hasRoll with strike bonus`(){
        val simulator = Simulator(1)

        simulator.addRoll(10)
        assertTrue(simulator.hasRoll())
        simulator.addRoll(2)
        assertTrue(simulator.hasRoll())
        simulator.addRoll(4)
        assertFalse(simulator.hasRoll())
    }

    @Test
    fun `pins remaining is 10 on new frame`(){
        val simulator = Simulator(1)

        assertEquals(10, simulator.pinsRemaining)
    }

    @Test
    fun `pins remaining is 10 minus toppled on second roll in a frame`(){
        val simulator = Simulator(1)

        simulator.addRoll(2)

        assertEquals(8, simulator.pinsRemaining)
    }

    @Test
    fun `pins remaining is 10 after strike`(){
        val simulator = Simulator(1)

        simulator.addRoll(10)

        assertEquals(10, simulator.pinsRemaining)
    }

    @Test
    fun `pins remaining is 10 after first bonus strike`(){
        val simulator = Simulator(1)

        simulator.addRoll(10)
        simulator.addRoll(10)

        assertEquals(10, simulator.pinsRemaining)
    }

    @Test
    fun `pins stay after first bonus roll`(){
        val simulator = Simulator(1)

        simulator.addRoll(10)
        simulator.addRoll(2)

        assertEquals(8, simulator.pinsRemaining)
    }
}