package com.bruno13palhano.di

import com.bruno13palhano.Database
import com.bruno13palhano.data.datasource.BookDataSource
import com.bruno13palhano.data.datasource.BookDataSourceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class LocalDataSourceModule actual constructor() : KoinComponent {
    private val database: Database by inject()

    actual val bookLocalDataSource: BookDataSource
        = BookDataSourceImpl(bookQueries = database.bookQueries)
}