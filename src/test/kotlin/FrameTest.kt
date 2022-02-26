import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FrameTest{
    @Test
    fun `check spare`(){
        val frame = Frame(2,8)

        assertTrue(frame.isSpare)
        assertFalse(frame.isStrike)
    }

    @Test
    fun `check strike`(){
        val frame = Frame(10,null)

        assertFalse(frame.isSpare)
        assertTrue(frame.isStrike)
    }
}