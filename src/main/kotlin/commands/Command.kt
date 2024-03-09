package commands

import interfaces.Course

interface Command {

    fun execute()

    fun getName() : String

    fun getPrice() : Int
}