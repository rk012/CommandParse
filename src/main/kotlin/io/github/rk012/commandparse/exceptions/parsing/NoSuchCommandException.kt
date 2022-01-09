package io.github.rk012.commandparse.exceptions.parsing

class NoSuchCommandException internal constructor(private val name: String) : ParseException() {
    override val message: String
        get() = "No such command \"$name\""
}