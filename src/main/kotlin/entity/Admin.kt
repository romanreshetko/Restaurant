package entity

import dispatchers.AdminDispatcher
import interfaces.User

class Admin(private val login: String, private val password: String) : User {

    override fun getLogin(): String {
        return login
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAdmin(): Boolean {
        return true
    }

    override fun dispatch() {
        AdminDispatcher.dispatchAdmin()
    }

    override fun getExitStatus(): Boolean {
        return AdminDispatcher.exitStatus
    }
}