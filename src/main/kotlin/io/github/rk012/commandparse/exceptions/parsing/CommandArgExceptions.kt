package io.github.rk012.commandparse.exceptions.parsing

import io.github.rk012.commandparse.typing.ArgType

abstract class CommandArgException: ParseException()
class CommandArgTypeException internal constructor(expected: ArgType, actual: String): CommandArgException() {
    override val message = "Expected type ${expected.arg_name}, got value \"$actual\""
}
class CommandArgLengthException internal constructor(expected: Int, actual: Int): CommandArgException() {
    override val message = "Expected $expected args, received $actual instead"
}