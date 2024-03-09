package interfaces

interface Builder {

    fun reset()

    fun setName()

    fun setPrice()

    fun setTime()

    fun setAmount()

    fun getResult() : Course
}