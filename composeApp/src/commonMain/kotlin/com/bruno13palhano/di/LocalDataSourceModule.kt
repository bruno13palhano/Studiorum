package com.bruno13palhano.di

import com.bruno13palhano.Database
import com.bruno13palhano.data.datasource.BookDataSource
import com.bruno13palhano.data.datasource.BookDataSourceImpl
import org.koin.dsl.module

val databaseModule = module {
    single { Database(driver = get()) }
    single { get<Database>().bookQueries }
}

val localDataSourceModule = module {
    single<BookDataSource> {
        BookDataSourceImpl(bookQueries = get())
    }
}