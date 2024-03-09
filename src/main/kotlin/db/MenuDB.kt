package db

import entity.Dish
import interfaces.Course
import interfaces.User
import java.sql.DriverManager

object MenuDB {

    fun createMenuTable() {
        val createTableSQL = """
                CREATE TABLE IF NOT EXISTS menu (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    price INTEGER,
                    time INTEGER,
                    amount INTEGER
                )
            """.trimIndent()

        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL)
            }
        }
    }

    fun insertCourse(course: Course) {
        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.prepareStatement("INSERT INTO menu (name, price, time, amount) " +
                    "VALUES (?, ?, ?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, course.getName())
                preparedStatement.setInt(2, course.getPrice())
                preparedStatement.setInt(3, course.getTime())
                preparedStatement.setInt(4, course.getAmount())
                preparedStatement.executeUpdate()
            }
        }
    }

    fun getMenu() : MutableList<Dish> {
        val courseList = mutableListOf<Dish>()
        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.prepareStatement("SELECT * FROM menu").use { preparedStatement ->
                val result = preparedStatement.executeQuery()
                while (result.next()) {
                    val course = Dish()
                    course.setName(result.getString("name"))
                    course.setPrice(result.getInt("price"))
                    course.setTime(result.getInt("time"))
                    course.setAmount(result.getInt("amount"))
                    courseList.add(course)
                }
            }
        }
        return courseList
    }

    fun deleteCourse(name: String) {
        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.prepareStatement("DELETE FROM menu " +
                    "WHERE name= ?").use { preparedStatement ->
                preparedStatement.setString(1, name)
                preparedStatement.executeUpdate()
            }
        }
    }

    fun decreaseAmount(name: String) {
        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.prepareStatement("UPDATE menu SET amount = amount - 1 " +
                    "WHERE name= ?").use { preparedStatement ->
                preparedStatement.setString(1, name)
                preparedStatement.executeUpdate()
            }
        }
    }

    fun increaseAmount(name: String, number: Int) {
        DriverManager.getConnection("jdbc:sqlite:menu.db").use { connection ->
            connection.prepareStatement("UPDATE menu SET amount = amount + ? " +
                    "WHERE name= ?").use { preparedStatement ->
                preparedStatement.setInt(1, number)
                preparedStatement.setString(2, name)
                preparedStatement.executeUpdate()
            }
        }
    }
}