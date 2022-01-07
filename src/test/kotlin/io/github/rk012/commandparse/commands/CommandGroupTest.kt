package io.github.rk012.commandparse.commands

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals

class CommandGroupTest {
    private lateinit var group: CommandGroup
    private lateinit var command: Command

    @BeforeEach
    fun commandGroupSetup() {
        group = CommandGroup()
        command = Command({}, listOf())
    }

    @Test
    fun getCommandTest() {
        group.addCommand("foo", command)

        assertEquals(group.getCommand("foo"), command)
    }

    @Test
    fun getSubcommandTest() {
        val anotherGroup = CommandGroup()
        group.addGroup("foo", anotherGroup)
        anotherGroup.addCommand("bar", command)

        assertEquals(group.getCommand("foo bar"), command)
    }
}