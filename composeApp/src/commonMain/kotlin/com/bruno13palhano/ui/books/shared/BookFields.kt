package com.bruno13palhano.ui.books.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bruno13palhano.model.Book

internal class BookFields {
    var id: Long = 0L
        private set
    var title by mutableStateOf("")
        private set
    var author by mutableStateOf("")
        private set
    var pages by mutableStateOf("")
        private set
    var categories by mutableStateOf(emptyList<String>())
        private set
    var wasRead by mutableStateOf(false)
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

    fun toBook(id: Long = 0L) = Book(
        id = id,
        title = title,
        author = author,
        categories = categories,
        pages = pages.toInt(),
        wasRead = wasRead,
        timestamp = System.currentTimeMillis()
    )

    fun fromBook(book: Book) {
        id = book.id
        title = book.title
        author = book.author
        pages = book.pages.toString()
    }
}