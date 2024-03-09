package builders

import entity.Dish
import interfaces.Builder
import interfaces.Course

class DishBuilder : Builder {
    private var dish = Dish()

    override fun reset() {
        dish = Dish()
    }

    override fun setName() {
        print("Enter name of the dish: ")
        val name = readln()
        dish.setName(name)
    }

    override fun setPrice() {
        print("Enter price of dish (integer): ")
        var price = readln().toIntOrNull()
        while (price == null || price < 0) {
            println("Incorrect price")
            price = readln().toIntOrNull()
        }
        dish.setPrice(price)
    }

    override fun setTime() {
        print("Enter time of cooking (minutes): ")
        var time = readln().toIntOrNull()
        while (time == null || time < 0) {
            println("Incorrect time")
            time = readln().toIntOrNull()
        }
        dish.setTime(time)
    }

    override fun setAmount() {
        print("Enter amount of dish: ")
        var amount = readln().toIntOrNull()
        while (amount == null || amount < 0) {
            println("Incorrect time")
            amount = readln().toIntOrNull()
        }
        dish.setAmount(amount)
    }

    override fun getResult(): Course {
        return dish
    }


}