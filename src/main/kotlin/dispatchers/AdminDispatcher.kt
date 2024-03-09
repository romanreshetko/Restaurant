package dispatchers

import builders.DishBuilder
import builders.DishDirector
import db.MenuDB
import db.MoneyDB
import db.ReviewDB
import entity.Dish

object AdminDispatcher {

    var exitStatus: Boolean = false

    fun dispatchAdmin() {
        Menus.showAdminMenu()
        var option = readln().toIntOrNull()
        while (option == null || (option < 1 || option > 6)) {
            println("Incorrect input")
            option = readln().toIntOrNull()
        }
        when (option) {
            1 -> addCourse()
            2 -> deleteCourse()
            3 -> showMenu()
            4 -> getStatistics()
            5 -> addPortions()
            6 -> exitStatus = true
        }
    }

    private fun addCourse() {
        val director = DishDirector(DishBuilder())
        val course = director.buildDish()
        MenuDB.insertCourse(course)
    }

    private fun showMenu() {
        val list = MenuDB.getMenu()
        var i: Int = 1
        for (course in list) {
            println("$i. $course")
            ++i
        }
    }

    private fun deleteCourse() {
        print("Enter name of dish: ")
        val name = readln()
        MenuDB.deleteCourse(name)
    }

    private fun getStatistics() {
        Menus.statisticsMenu()
        var option = readln().toIntOrNull()
        while (option == null || (option < 1 || option > 3)) {
            println("Incorrect input")
            option = readln().toIntOrNull()
        }
        when (option) {
            1 -> println("Money earned: ${MoneyDB.getMoney()}")
            2 -> getMarkByDish()
            3 -> getReviewsByDish()
        }

    }

    private fun getMarkByDish() {
        print("Enter name of the dish: ")
        val name = readln()
        val list = ReviewDB.markByName(name)
        if (list.isEmpty()) {
            println("No reviews on such dish")
            return
        }
        println("Average mark of $name is ${list.average()}")
    }

    private fun getReviewsByDish() {
        print("Enter name of the dish: ")
        val name = readln()
        val list = ReviewDB.commentByName(name)
        if (list.isEmpty()) {
            println("No reviews on such dish")
            return
        }
        for (comment in list) {
            println(comment)
        }
    }

    private fun addPortions() {
        print("Enter name of the dish: ")
        val name = readln()
        print("Enter number of portions: ")
        var num = readln().toIntOrNull()
        while (num == null || num < 1) {
            println("Incorrect input")
            num = readln().toIntOrNull()
        }
        MenuDB.increaseAmount(name, num)
    }
}