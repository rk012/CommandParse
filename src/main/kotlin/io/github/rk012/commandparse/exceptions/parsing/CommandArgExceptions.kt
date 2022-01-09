package io.github.rk012.commandparse.exceptions.parsing

import io.github.rk012.commandparse.typing.ArgType

abstract class CommandArgException : ParseException()
class CommandArgTypeException internal constructor(private val expected: ArgType, private val actual: String) : CommandArgException() {
    override val message: String
        get() = "Expected type ${expected.arg_name}, got value \"$actual\"."
}
class CommandArgLengthException internal constructor(private val expected: Int, private val actual: Int) : CommandArgException() {
    override val message: String
        get() = "Expected $expected args, received $actual instead."
}