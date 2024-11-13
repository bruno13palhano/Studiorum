package com.bruno13palhano.di

import com.bruno13palhano.data.database.sqlDriverModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun applicationModule() = listOf(
    repositoryModule,
    databaseModule,
    sqlDriverModule,
    localDataSourceModule,
    viewModelModule
)

fun initKoin(appDeclaration: KoinAppDeclaration) {
    startKoin {
        modules(applicationModule())
        appDeclaration()
    }
}