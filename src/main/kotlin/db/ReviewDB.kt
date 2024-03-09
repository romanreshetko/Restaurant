package db

import entity.Dish
import entity.Review
import interfaces.Course
import java.sql.DriverManager

object ReviewDB {

    fun createReviewTable() {
        val createTableSQL = """
                CREATE TABLE IF NOT EXISTS review (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(255),
                    mark INTEGER,
                    comment VARCHAR(255)
                )
            """.trimIndent()

        DriverManager.getConnection("jdbc:sqlite:review.db").use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL)
            }
        }
    }

    fun insertReview(review: Review) {
        DriverManager.getConnection("jdbc:sqlite:review.db").use { connection ->
            connection.prepareStatement("INSERT INTO review (name, mark, comment) " +
                    "VALUES (?, ?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, review.name)
                preparedStatement.setInt(2, review.mark)
                preparedStatement.setString(3, review.comment)
                preparedStatement.executeUpdate()
            }
        }
    }

    fun markByName(name: String): MutableList<Int> {
        val list = mutableListOf<Int>()
        DriverManager.getConnection("jdbc:sqlite:review.db").use { connection ->
            connection.prepareStatement("SELECT mark FROM review " +
                    "WHERE name= ?").use { preparedStatement ->
                preparedStatement.setString(1, name)
                val result = preparedStatement.executeQuery()
                while (result.next()) {
                    list.add(result.getInt("mark"))
                }
            }
        }
        return list
    }

    fun commentByName(name: String): MutableList<String> {
        val list = mutableListOf<String>()
        DriverManager.getConnection("jdbc:sqlite:review.db").use { connection ->
            connection.prepareStatement("SELECT comment FROM review " +
                    "WHERE name= ?").use { preparedStatement ->
                preparedStatement.setString(1, name)
                val result = preparedStatement.executeQuery()
                while (result.next()) {
                    list.add(result.getString("comment"))
                }
            }
        }
        return list
    }
}