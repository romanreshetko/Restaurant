package db

import interfaces.User
import java.sql.DriverManager

object UserDB {

    fun createUsersTable() {
        val createTableSQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login VARCHAR(255),
                    password VARCHAR(255),
                    isAdmin BOOLEAN DEFAULT FALSE
                )
            """.trimIndent()

        DriverManager.getConnection("jdbc:sqlite:test.db").use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL)
            }
        }
    }

    fun insertUser(user: User) {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { connection ->
            connection.prepareStatement("INSERT INTO users (login, password, isAdmin) " +
                    "VALUES (?, ?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, user.getLogin())
                preparedStatement.setString(2, user.getPassword())
                preparedStatement.setBoolean(3, user.isAdmin())
                preparedStatement.executeUpdate()
            }
        }
    }

    fun findUser(login: String, password: String) : Boolean {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { connection ->
            connection.prepareStatement("SELECT * FROM users " +
                    "WHERE login= ? AND password= ?").use { preparedStatement ->
                preparedStatement.setString(1, login)
                preparedStatement.setString(2, password)
                val result = preparedStatement.executeQuery()
                return result.next()
            }
        }
    }

    fun isUserAdmin(login: String, password: String) : Boolean {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { connection ->
            connection.prepareStatement("SELECT isAdmin FROM users " +
                    "WHERE login= ? AND password= ? AND isAdmin= ?").use { preparedStatement ->
                preparedStatement.setString(1, login)
                preparedStatement.setString(2, password)
                preparedStatement.setString(3, "true")
                val result = preparedStatement.executeQuery()
                return result.next()
            }
        }
    }

    fun findUserByLogin(login: String) : Boolean {
        DriverManager.getConnection("jdbc:sqlite:test.db").use { connection ->
            connection.prepareStatement("SELECT * FROM users WHERE login = ?").use { preparedStatement ->
                preparedStatement.setString(1, login)
                val result = preparedStatement.executeQuery()
                return result.next()
            }
        }
    }
}