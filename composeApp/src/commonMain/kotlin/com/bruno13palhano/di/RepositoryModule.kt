package com.bruno13palhano.di

import com.bruno13palhano.data.datasource.BookRepository
import com.bruno13palhano.data.datasource.BookRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::BookRepositoryImpl) {
        bind<BookRepository>()
    }
}