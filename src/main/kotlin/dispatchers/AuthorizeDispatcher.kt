package dispatchers

import db.UserDB
import entity.Admin
import entity.Client
import interfaces.User

object AuthorizeDispatcher {

    private const val ADMIN_CODE: String = "superadmin"

    fun authorize() : User? {
        Menus.showOptions()
        var option = readln()
        while (option != "1" && option != "2") {
            println("Incorrect input")
            option = readln()
        }
        if (option == "1") {
            return logIn()
        } else {
            signUp()
            return null
        }
    }

    private fun logIn() : User? {
        print("Enter login: ")
        val login = readln()
        print("Enter password: ")
        val password = readln()
        if (UserDB.findUser(login, password)) {
            return if (UserDB.isUserAdmin(login, password)) {
                Admin(login, password)
            } else {
                Client(login, password)
            }
        } else {
            println("Incorrect login or password")
            return null
        }
    }

    private fun signUp() {
        print("Enter login: ")
        var login = readln()
        while (UserDB.findUserByLogin(login)) {
            println("This login is already taken")
            print("Enter login: ")
            login = readln()
        }
        print("Enter password: ")
        val password = readln()
        print("Enter admin code or press Enter for client registration: ")
        val code = readln()
        if (code == ADMIN_CODE) {
            UserDB.insertUser(Admin(login, password))
        } else {
            UserDB.insertUser(Client(login, password))
        }
    }
}