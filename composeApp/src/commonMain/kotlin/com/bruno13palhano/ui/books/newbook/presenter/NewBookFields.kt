package com.bruno13palhano.ui.books.newbook.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bruno13palhano.model.Book

internal class NewBookFields {
    var title by mutableStateOf("")
        private set
    var author by mutableStateOf("")
        private set
    var pages by mutableStateOf("")
        private set

    fun updateTitleChange(newValue: String) {
        title = newValue
    }

    fun updateAuthorChange(newValue: String) {
        author = newValue
    }

    fun updatePagesChange(newValue: String) {
        pages = newValue
    }

    fun isValid() = title.isNotBlank() && author.isNotBlank() && pages.isNotBlank()

    fun toBook() = Book(
        id = 0L,
        title = title,
        author = author,
        pages = pages.toInt()
    )
}