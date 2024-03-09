package entity

import dispatchers.ClientDispatcher
import interfaces.User

class Client(private val login: String, private val password: String) : User {
    override fun getLogin(): String {
        return login
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAdmin(): Boolean {
        return false
    }

    override fun dispatch() {
        ClientDispatcher.dispatchClient()
    }

    override fun getExitStatus(): Boolean {
        return ClientDispatcher.exitStatus
    }
}