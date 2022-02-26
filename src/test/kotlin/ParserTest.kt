import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ParserTest(){
    @Test
    fun `get representation for a simple frame`(){
        val parser = Parser()
        val frame = Frame(2,4)

        val result = parser.getRepresentation(frame)

        assertEquals("24", result)
    }

    @Test
    fun `get representation for spare`(){
        val parser = Parser()
        val frame = Frame(2,8)

        val result = parser.getRepresentation(frame)

        assertEquals("2/", result)
    }

    @Test
    fun `get representation for strike`(){
        val parser = Parser()
        val frame = Frame(10,null)

        val result = parser.getRepresentation(frame)

        assertEquals("x", result)
    }

    @Test
    fun `get representation for partial`(){
        val parser = Parser()
        val frame = Frame(2,null)

        val result = parser.getRepresentation(frame)

        assertEquals("2", result)
    }

    @Test
    fun `get representation for null`(){
        val parser = Parser()

        val result = parser.getRepresentation(null)

        assertEquals("", result)
    }

    @Test
    fun `representation for last frame contains bonuses`(){
        val parser = Parser()
        val frame = Frame(10,null)
        val bonus1 = 10
        val bonus2 = 10

        val result = parser.getRepresentation(frame, bonus1, bonus2)

        assertEquals("xxx", result)
    }

}