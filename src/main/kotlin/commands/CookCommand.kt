package commands

import interfaces.Course

class CookCommand(val course: Course) : Command {

    override fun execute() {
        Thread.sleep((course.getTime() * 1000).toLong())
    }

    override fun getName(): String {
        return course.getName()
    }

    override fun getPrice(): Int {
        return course.getPrice()
    }
}