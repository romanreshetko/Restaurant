import db.MenuDB
import db.MoneyDB
import db.ReviewDB
import db.UserDB
import dispatchers.AdminDispatcher
import dispatchers.AuthorizeDispatcher
import interfaces.User


fun main() {
    try {
        UserDB.createUsersTable()
        MenuDB.createMenuTable()
        MoneyDB.createMoneyRecord()
        ReviewDB.createReviewTable()
    } catch (e: Exception) {
        println("Trouble with connecting DB")
        return
    }

    var user: User? = AuthorizeDispatcher.authorize()
    while (user == null) {
        user = AuthorizeDispatcher.authorize()
    }

    while (!user.getExitStatus()) {
        try {
            user.dispatch()
        } catch (e: Exception) {
            println("Something went wrong")
            println(e.message)
        }
    }

    println("Thank you!")
}