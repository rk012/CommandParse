package io.github.rk012.commandparse.exceptions.api

class InvalidCommandNameException internal constructor(private val name: String) : Exception() {
    override val message: String
        get() = "Invalid command name: $name"
}