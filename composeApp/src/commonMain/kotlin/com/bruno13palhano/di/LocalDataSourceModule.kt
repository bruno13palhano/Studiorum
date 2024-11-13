package com.bruno13palhano.di

import com.bruno13palhano.Database
import com.bruno13palhano.data.datasource.BookDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single { Database(driver = get()) }
}

val localDataSourceModule = module {
    singleOf(::LocalDataSourceModule)
}

expect class LocalDataSourceModule() {
    val bookLocalDataSource: BookDataSource
}