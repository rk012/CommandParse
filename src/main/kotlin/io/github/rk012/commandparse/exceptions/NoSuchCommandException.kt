package io.github.rk012.commandparse.exceptions

class NoSuchCommandException(private val name: String) : ParseException() {
    override val message: String
        get() = "No such command \"$name\""
}