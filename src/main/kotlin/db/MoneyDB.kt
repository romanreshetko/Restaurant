package db

import entity.Dish
import java.sql.DriverManager

object MoneyDB {

    fun createMoneyRecord() {
        val createTableSQL = """
                CREATE TABLE IF NOT EXISTS money (
                    money INTEGER
                )
            """.trimIndent()

        val insertSQL = """INSERT INTO money (money) VALUES (0)""".trimIndent()

        val checkEmptySQL = """SELECT * FROM money""".trimIndent()

        DriverManager.getConnection("jdbc:sqlite:money.db").use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL)

                val result = statement.executeQuery(checkEmptySQL)
                if (!result.next()) {
                    statement.executeUpdate(insertSQL)
                }
            }
        }
    }

    fun getMoney() : Int {
        DriverManager.getConnection("jdbc:sqlite:money.db").use { connection ->
            connection.prepareStatement("SELECT * FROM money").use { preparedStatement ->
                val result = preparedStatement.executeQuery()
                var money = 0
                if (result.next()) {
                    money = result.getInt("money")
                }
                return money
            }
        }
    }

    fun updateMoney(sum: Int) {
        DriverManager.getConnection("jdbc:sqlite:money.db").use { connection ->
            connection.prepareStatement("UPDATE money SET money = ?").use { preparedStatement ->
                preparedStatement.setInt(1, sum)
                preparedStatement.executeUpdate()
            }
        }
    }
}