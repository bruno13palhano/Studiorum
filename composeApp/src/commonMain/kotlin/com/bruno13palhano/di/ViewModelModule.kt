package com.bruno13palhano.di

import com.bruno13palhano.ui.books.books.viewmodel.BooksViewModel
import com.bruno13palhano.ui.books.editbook.viewmodel.EditBookViewModel
import com.bruno13palhano.ui.books.newbook.viewmodel.NewBookViewModel
import com.bruno13palhano.ui.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::BooksViewModel)
    viewModelOf(::NewBookViewModel)
    viewModelOf(::EditBookViewModel)
}