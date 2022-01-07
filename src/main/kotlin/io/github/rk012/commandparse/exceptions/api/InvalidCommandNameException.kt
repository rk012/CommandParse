package io.github.rk012.commandparse.exceptions.api

class InvalidCommandNameException(private val name: String) : Exception() {
    override val message: String
        get() = "Invalid command name: $name"
}