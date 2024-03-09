package interfaces

interface Course {
    fun getName() : String

    fun getPrice() : Int

    fun getTime() : Int

    fun getAmount() : Int

    fun setName(name: String)

    fun setPrice(price: Int)

    fun setTime(time: Int)

    fun setAmount(amount: Int)
}