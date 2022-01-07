package io.github.rk012.commandparse

import io.github.rk012.commandparse.commands.Command
import io.github.rk012.commandparse.commands.CommandGroup

class Parser {
    private val baseGroup: CommandGroup = CommandGroup()

    fun addGroup(name: String, group: CommandGroup) = baseGroup.addGroup(name, group)
    fun addCommand(name: String, command: Command) = baseGroup.addCommand(name, command)

    fun addFullCommand(name: String, command: Command) {
        val names = name.trim().split(" ")

        var currentGroup = baseGroup
        var nextGroup: CommandGroup

        names.subList(0, names.size-1).forEach {
            if (currentGroup.groupExists(it)) {
                nextGroup = currentGroup.getGroup(it)
            } else {
                nextGroup = CommandGroup()
                currentGroup.addGroup(it, nextGroup)
            }

            currentGroup = nextGroup
        }

        currentGroup.addCommand(names[names.size-1], command)
    }

    fun runCommand(instruction: String) {
        val command = baseGroup.getCommand(instruction.trim())

        command.run()
    }
}