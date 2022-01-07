package io.github.rk012.commandparse

import io.github.rk012.commandparse.commands.*
import io.github.rk012.commandparse.typing.ArgType

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals

class ParserTest {
    private fun add(a: Int, b: Int) = a + b
    private fun subtract(a: Int, b: Int) = a - b
    private fun mult(a: Int, b: Int) = a * b
    private fun divide(a: Int, b: Int) = a / b

    private lateinit var parser: Parser
    private var output: Int = -1

    @BeforeEach
    fun resetOutput() {
        parser = Parser()
        output = -1
    }

    @Test
    fun simpleCommandTest() {
        parser.addCommand(
            "add", Command(
                {
                    output = add(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.runCommand("add 6 7")
        assertEquals(output, 13)
    }

    @Test
    fun manualGroupCommandTest() {
        val testGroup = CommandGroup()
        parser.addGroup("foo", testGroup)

        testGroup.addCommand(
            "subtract", Command(
                {
                    output = subtract(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.runCommand("foo subtract 7 3")
        assertEquals(output, 4)
    }

    @Test
    fun newBulkCommandGroupTest() {
        parser.addFullCommand(
            "bar div", Command(
                {
                    output = divide(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.runCommand("bar div 6 3")
        assertEquals(output, 2)
    }

    @Test
    fun existingBulkCommandGroupTest() {
        val testGroup = CommandGroup()
        parser.addGroup("foo", testGroup)

        parser.addFullCommand(
            "foo multiply", Command(
                {
                    output = mult(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.runCommand("foo multiply 2 4")
        assertEquals(output, 8)
    }
}