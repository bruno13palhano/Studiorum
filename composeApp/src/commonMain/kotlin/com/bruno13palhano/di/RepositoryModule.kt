package com.bruno13palhano.di

import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.data.datasource.BookRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RepositoryModule)
}

class RepositoryModule : KoinComponent {
    private val localDataSourceModule: LocalDataSourceModule by inject()

    val bookRepository: BookRepository =
        BookRepositoryImpl(bookDataSource = localDataSourceModule.bookLocalDataSource)
}