package interfaces

interface User {

    fun getLogin() : String

    fun getPassword() : String

    fun isAdmin() : Boolean

    fun dispatch()

    fun getExitStatus() : Boolean
}