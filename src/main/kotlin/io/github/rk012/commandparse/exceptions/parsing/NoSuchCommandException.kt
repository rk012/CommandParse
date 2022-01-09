package io.github.rk012.commandparse.exceptions.parsing

class NoSuchCommandException internal constructor(name: String): ParseException() {
    override val message = "No such command \"$name\""
}