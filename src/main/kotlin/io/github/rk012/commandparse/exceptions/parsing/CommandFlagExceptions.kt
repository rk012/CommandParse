package io.github.rk012.commandparse.exceptions.parsing

import io.github.rk012.commandparse.typing.ArgType

abstract class CommandFlagException: ParseException()
class CommandFlagTypeException internal constructor(expected: ArgType?, actual: String?): CommandFlagException() {
    override val message = "Expected type ${expected?.arg_name ?: "Boolean"}, got ${if (actual != null) "value \"$actual\"." else "type Boolean"}"
}
open class NoSuchCommandFlagException internal constructor(flagName: String): CommandFlagException() {
    override val message = "Unknown flag \"$flagName\""
}
class NoSuchCommandFlagAliasException internal constructor(aliasName: String): NoSuchCommandFlagException(aliasName) {
    override val message = "Unknown flag alias \"$aliasName\""
}
class MissingCommandFlagException internal constructor(flagName: String): CommandFlagException() {
    override val message = "Missing flag \"$flagName\""
}
