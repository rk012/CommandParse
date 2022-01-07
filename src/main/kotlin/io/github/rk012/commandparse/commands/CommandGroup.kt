package io.github.rk012.commandparse.commands

import io.github.rk012.commandparse.exceptions.api.InvalidCommandNameException
import io.github.rk012.commandparse.exceptions.parsing.NoSuchCommandException

class CommandGroup {
    private val commands = mutableMapOf<String, Command>()
    private val groups = mutableMapOf<String, CommandGroup>()

    fun addGroup(name: String, group: CommandGroup) {
        if (name.trim().contains(" ")) throw InvalidCommandNameException(name)
        groups[name] = group
    }

    fun addCommand(name: String, command: Command) {
        if (name.trim().contains(" ")) throw InvalidCommandNameException(name)
        commands[name] = command
    }

    internal fun getCommand(command: String): Command {
        val name = command.trim().split(" ")[0]

        if (name in groups) return groups[name]!!.getCommand(command.subSequence(name.length+1, command.length) as String)
        if (name in commands) {
            commands[name]!!.setArgs(command.subSequence(name.length, command.length) as String)
            return commands[name]!!
        }

        throw NoSuchCommandException(name)
    }

    internal fun groupExists(group: String) = groups.containsKey(group)
    internal fun getGroup(group: String) = groups[group]!!
}