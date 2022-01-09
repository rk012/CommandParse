package io.github.rk012.commandparse.commands

import io.github.rk012.commandparse.exceptions.parsing.CommandArgLengthException
import io.github.rk012.commandparse.exceptions.parsing.CommandArgTypeException
import io.github.rk012.commandparse.typing.ArgType

import java.lang.NumberFormatException

class Command (private val command: (List<Any>) -> Unit, private val argTypes: List<ArgType>?) {
    private lateinit var inputArgs: MutableList<Any>

    internal fun setArgs(args: String) {
        val rawArgs = mutableListOf<String>()

        var currentWord = ""

        args.trim().split(" ").forEach {
            if (currentWord.isEmpty()) {
                if (it.startsWith("\"")) currentWord += " $it" else rawArgs.add(it)
            } else {
                if (it.endsWith("\"") && it[it.length-2] != '\\') {
                    rawArgs.add("$currentWord $it")
                    currentWord = ""
                } else currentWord += " $it"
            }
        }

        if (argTypes == null) {
            inputArgs = rawArgs.toMutableList()
            return
        }

        if (rawArgs.size == 1 && rawArgs[0] == "") rawArgs.removeAt(0)

        if (rawArgs.size != argTypes.size) throw CommandArgLengthException(argTypes.size, rawArgs.size)

        inputArgs = rawArgs.toMutableList()

        argTypes.forEachIndexed { i, argType ->
            when(argType) {
                ArgType.INT -> inputArgs[i] = try {rawArgs[i].toInt()} catch (e: NumberFormatException) {throw CommandArgTypeException(
                    ArgType.INT, rawArgs[i])
                }
                ArgType.DOUBLE -> inputArgs[i] = rawArgs[i].toDouble()
                else -> {}
            }
        }
    }

    internal fun run() = command(inputArgs)
}