package builders

import entity.Dish
import interfaces.Course

class DishDirector(private val builder: DishBuilder) {

    fun buildDish() : Course {
        builder.reset()
        builder.setName()
        builder.setPrice()
        builder.setTime()
        builder.setAmount()
        return builder.getResult()
    }
}