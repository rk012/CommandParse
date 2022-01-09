package io.github.rk012.commandparse.exceptions.api

class InvalidCommandNameException internal constructor(name: String) : Exception() {
    override val message = "Invalid command name: $name"
}