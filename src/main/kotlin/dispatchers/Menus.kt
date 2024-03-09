package dispatchers

object Menus {
    fun showOptions() {
        println("Welcome to Restaurant")
        println("-------------------------------------")
        println("Choose option:")
        println("1. Log in")
        println("2. Sign up")
    }

    fun showAdminMenu() {
        println("Choose option:")
        println("1. Add course to menu")
        println("2. Delete course from menu")
        println("3. Show the menu")
        println("4. Show statistics")
        println("5. Add portions")
        println("6. Exit")
    }

    fun showClientMenu() {
        println("Choose option:")
        println("1. Show menu")
        println("2. Create order")
        println("3. Change my order")
        println("4. Check my order")
        println("5. Give review")
        println("6. Exit")
    }

    fun createOrderMenu() {
        println("Choose option: ")
        println("1. Add course")
        println("2. Send order")
    }

    fun changeOrderMenu() {
        println("Choose option:")
        println("1. Add course to order")
        println("2. Cancel order")
    }

    fun statisticsMenu() {
        println("Choose option:")
        println("1. See money earned")
        println("2. Check average mark of dish")
        println("3. See reviews for the dish")
    }
}