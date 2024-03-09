package commands

import db.MoneyDB

class Order {

    var status: String = "Creating"

    var list = mutableListOf<Command>()

    fun isEmpty() : Boolean {
        return list.isEmpty()
    }

    fun add(command: Command) {
        list.add(command)
    }

    fun accepted() {
        status = "Accepted"
    }

    fun startCooking() {
        status = "Cooking"
    }

    fun finishCooking() {
        if (status != "Canceled") {
            status = "Ready"
            var price = 0
            for (command in list) {
                price += command.getPrice()
            }
            val sum = MoneyDB.getMoney() + price
            MoneyDB.updateMoney(sum)
        }
    }
}