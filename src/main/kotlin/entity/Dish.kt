package entity

import interfaces.Course

class Dish : Course {
    private var name: String = ""
    private var price: Int = 0
    private var time: Int = 0
    private var amount: Int = 0
    override fun getName(): String {
        return name
    }

    override fun getPrice(): Int {
        return price
    }

    override fun getTime(): Int {
        return time
    }

    override fun getAmount(): Int {
        return amount
    }

    override fun setName(name: String) {
        this.name = name
    }

    override fun setPrice(price: Int) {
        this.price = price
    }

    override fun setTime(time: Int) {
        this.time = time
    }

    override fun setAmount(amount: Int) {
        this.amount = amount
    }

    override fun toString(): String {
        return "Name: ${getName()}; price: ${getPrice()}; time: ${getTime()}; amount: ${getAmount()}"
    }
}