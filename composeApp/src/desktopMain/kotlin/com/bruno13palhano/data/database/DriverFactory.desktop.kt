package com.bruno13palhano.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bruno13palhano.Database
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val sqlDriverModule: Module = module {
    val databasePath = File("/home/daniela/Documentos", "studiorum.db")

    single<SqlDriver> {
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")

        Database.Schema.create(driver)

        driver
    }
}