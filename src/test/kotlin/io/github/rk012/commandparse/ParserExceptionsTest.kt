package io.github.rk012.commandparse

import io.github.rk012.commandparse.commands.Command
import io.github.rk012.commandparse.commands.CommandGroup
import io.github.rk012.commandparse.exceptions.CommandArgLengthException
import io.github.rk012.commandparse.exceptions.CommandArgTypeException
import io.github.rk012.commandparse.exceptions.NoSuchCommandException
import io.github.rk012.commandparse.typing.ArgType

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserExceptionsTest {
    private fun add(a: Int, b: Int) = a + b
    private fun subtract(a: Int, b: Int) = a - b
    private fun mult(a: Int, b: Int) = a * b
    private fun divide(a: Int, b: Int) = a / b

    private val parser = Parser()

    @BeforeAll
    fun parserSetup() {
        parser.addCommand(
            "add", Command(
                {
                    add(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        val testGroup = CommandGroup()
        parser.addGroup("foo", testGroup)

        testGroup.addCommand(
            "subtract", Command(
                {
                    subtract(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.addFullCommand(
            "bar div", Command(
                {
                    divide(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )

        parser.addFullCommand(
            "foo multiply", Command(
                {
                    mult(it[0] as Int, it[1] as Int)
                },
                listOf(ArgType.INT, ArgType.INT)
            )
        )
    }

    @Test
    fun noSuchCommandTest() {
        assertThrows(NoSuchCommandException::class.java) {parser.runCommand("nonexistent")}

        try {
            parser.runCommand("nonexistent")
        } catch (e: NoSuchCommandException) {
            assertEquals(e.message, "No such command \"nonexistent\"")
        }
    }

    @Test
    fun noSuchCommandInGroupTest() {
        assertThrows(NoSuchCommandException::class.java) {parser.runCommand("foo nonexistent")}

        try {
            parser.runCommand("foo nonexistent")
        } catch (e: NoSuchCommandException) {
            assertEquals(e.message, "No such command \"nonexistent\"")
        }
    }

    @Test
    fun commandArgLengthTest() {
        assertThrows(CommandArgLengthException::class.java) {parser.runCommand("foo subtract 3")}

        try {
            parser.runCommand("foo subtract 3")
        } catch (e: CommandArgLengthException) {
            assertEquals(e.message, "Expected 2 args, received 1 instead.")
        }

        try {
            parser.runCommand("foo subtract")
        } catch (e: CommandArgLengthException) {
            assertEquals(e.message, "Expected 2 args, received 0 instead.")
        }

        try {
            parser.runCommand("foo subtract 3 4 5")
        } catch (e: CommandArgLengthException) {
            assertEquals(e.message, "Expected 2 args, received 3 instead.")
        }
    }

    @Test
    fun commandArgTypeTest() {
        assertThrows(CommandArgTypeException::class.java) {parser.runCommand("add 6 h")}

        try {
            parser.runCommand("add 6 h")
        } catch (e: CommandArgTypeException) {
            assertEquals(e.message, "Expected type Integer, got value \"h\".")
        }
    }
}