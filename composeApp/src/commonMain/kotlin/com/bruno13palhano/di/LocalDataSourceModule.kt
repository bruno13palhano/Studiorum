package com.bruno13palhano.di

import app.cash.sqldelight.ColumnAdapter
import com.bruno13palhano.Database
import com.bruno13palhano.data.datasource.BookDataSource
import com.bruno13palhano.data.datasource.BookDataSourceImpl
import com.bruno13palhano.database.Book
import org.koin.dsl.module

val databaseModule = module {
    single {
        Database(
            driver = get(),
            BookAdapter = Book.Adapter(
                categoriesAdapter = categoriesAdapter
            )
        )
    }
    single { get<Database>().bookQueries }
}

val localDataSourceModule = module {
    single<BookDataSource> {
        BookDataSourceImpl(bookQueries = get())
    }
}

private val categoriesAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String): List<String> {
        return if (databaseValue.isEmpty()) {
            emptyList()
        } else {
            databaseValue.split(",")
        }
    }

    override fun encode(value: List<String>): String {
        return value.joinToString(",")
    }
}