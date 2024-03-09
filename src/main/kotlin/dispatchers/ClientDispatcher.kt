package dispatchers

import commands.Command
import commands.CookCommand
import commands.Order
import db.MenuDB
import db.ReviewDB
import entity.Dish
import entity.Review
import kitchen.Kitchen

object ClientDispatcher {

    var exitStatus: Boolean = false

    var orders = mutableListOf<Order>()

    fun dispatchClient() {
        Menus.showClientMenu()
        var option = readln().toIntOrNull()
        while (option == null || (option < 1 || option > 6)) {
            println("Incorrect input")
            option = readln().toIntOrNull()
        }
        when (option) {
            1 -> showMenu()
            2 -> createOrder()
            3 -> changeOrder()
            4 -> getOrdersStatistics()
            5 -> giveReview()
            6 -> exitStatus = true
        }
    }

    private fun showMenu(): MutableList<Dish> {
        val list = MenuDB.getMenu()
        var i: Int = 1
        for (course in list) {
            println("$i. $course")
            ++i
        }
        return list
    }

    private fun createOrder() {
        val order = Order()
        Menus.createOrderMenu()
        var option : Int?
        do {
            option = readln().toIntOrNull()
            while (option == null || option != 1 && option != 2) {
                println("Incorrect input")
                option = readln().toIntOrNull()
            }
            if (option == 1) {
                println("Choose number of course from menu: ")
                val list = showMenu()
                var courseNumber = readln().toIntOrNull()
                while (courseNumber == null || courseNumber < 1 || courseNumber > list.size) {
                    println("Incorrect input")
                    courseNumber = readln().toIntOrNull()
                }
                val course = list[courseNumber - 1]
                if (course.getAmount() > 0) {
                    order.add(CookCommand(course))
                    MenuDB.decreaseAmount(course.getName())
                } else {
                    println("This course is finished")
                }
                Menus.createOrderMenu()
            }
        } while (option != 2)
        if (order.isEmpty()) {
            println("Order is empty")
        } else {
            orders.add(order)
            Kitchen.prepareOrder(order)
        }
    }

    private fun getOrdersStatistics() {
        var i = 1
        for (order in orders) {
            println("$i. ${order.status}")
            ++i
        }
    }

    private fun changeOrder() {
        if (orders.isEmpty()) {
            println("You have no orders to change")
            return
        }
        Menus.changeOrderMenu()
        var option = readln().toIntOrNull()
        while (option == null || option != 1 && option != 2) {
            println("Incorrect input")
            option = readln().toIntOrNull()
        }
        getOrdersStatistics()
        if (option == 1) {
            println("Choose order to add course")
            var orderNum = readln().toIntOrNull()
            while (orderNum == null || orderNum < 1 || orderNum > orders.size) {
                println("Incorrect input")
                orderNum = readln().toIntOrNull()
            }
            if (orders[orderNum - 1].status == "Ready" || orders[orderNum - 1].status == "Canceled") {
                println("This order cannot be changed")
                return
            }
            print("Choose number of course from menu: ")
            val list = showMenu()
            var courseNumber = readln().toIntOrNull()
            while (courseNumber == null || courseNumber < 1 || courseNumber > list.size) {
                println("Incorrect input")
                courseNumber = readln().toIntOrNull()
            }
            val course = list[courseNumber - 1]
            if (orders[orderNum - 1].status == "Ready") {
                println("Sorry, but order is already ready")
                return
            }
            orders[orderNum - 1].add(CookCommand(course))
        } else {
            println("Choose order to cancel")
            var orderNum = readln().toIntOrNull()
            while (orderNum == null || orderNum < 1 || orderNum > orders.size) {
                println("Incorrect input")
                orderNum = readln().toIntOrNull()
            }
            if (orders[orderNum - 1].status == "Ready" || orders[orderNum - 1].status == "Canceled") {
                println("This order cannot be canceled")
                return
            }
            orders[orderNum - 1].status = "Canceled"
        }
    }

    private fun giveReview() {
        if (orders.isEmpty()) {
            println("You have no orders to review")
            return
        }
        getOrdersStatistics()
        println("Choose order: ")
        var orderNum = readln().toIntOrNull()
        while (orderNum == null || orderNum < 1 || orderNum > orders.size) {
            println("Incorrect input")
            orderNum = readln().toIntOrNull()
        }
        if (orders[orderNum - 1].status != "Ready") {
            println("You have not received this order")
            return
        }
        var i = 1
        for (command in orders[orderNum - 1].list) {
            println("$i. ${command.getName()}")
            markCourse(command)
            ++i
        }
    }

    private fun markCourse(command: Command) {
        print("Enter mark from 1 to 5: ")
        var mark = readln().toIntOrNull()
        while (mark == null || mark < 1 || mark > 5) {
            println("Incorrect input")
            mark = readln().toIntOrNull()
        }
        print("Enter comment: ")
        var comment = readln()
        if (comment.length > 255) {
            comment = comment.substring(0, 255)
        }
        val review = Review(command.getName(), mark, comment)
        ReviewDB.insertReview(review)
    }
}