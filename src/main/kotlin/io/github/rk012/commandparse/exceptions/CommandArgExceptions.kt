package io.github.rk012.commandparse.exceptions

import io.github.rk012.commandparse.typing.ArgType

abstract class CommandArgException : ParseException()
class CommandArgTypeException(private val expected: ArgType, private val actual: String) : CommandArgException() {
    override val message: String
        get() = "Expected type ${expected.arg_name}, got value \"$actual\"."
}
class CommandArgLengthException(private val expected: Int, private val actual: Int) : CommandArgException() {
    override val message: String
        get() = "Expected $expected args, received $actual instead."
}