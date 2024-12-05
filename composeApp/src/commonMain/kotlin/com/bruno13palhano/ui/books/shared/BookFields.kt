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

    private var _categories by mutableStateOf(emptySet<String>())
    val categories get() = _categories.toList()

    var wasRead by mutableStateOf(false)
        private set

    var category by mutableStateOf("")
        private set

    fun updateTitleChange(newValue: String) {
        title = newValue
    }

    fun updateAuthorChange(newValue: String) {
        author = newValue
    }

    fun addCategory(category: String) {
        if (category.isNotBlank()) {
            _categories = _categories.plus(category.trim())
            this.category = ""
        }
    }

    fun removeCategory(newValue: String) {
        _categories = _categories.minus(newValue)
    }

    fun updateCategoryChange(newValue: String) {
        category = newValue.trim()
    }

    fun updatePagesChange(newValue: String) {
        pages = newValue
    }

    fun updateWasReadChange(newValue: Boolean) {
        wasRead = newValue
    }

    fun isValid() = title.isNotBlank() && author.isNotBlank()
            && pages.isNotBlank() && categories.isNotEmpty()

    fun toBook(id: Long = 0L) = Book(
        id = id,
        title = title,
        author = author,
        categories = _categories.toList(),
        pages = pages.toInt(),
        wasRead = wasRead,
        timestamp = System.currentTimeMillis()
    )

    fun fromBook(book: Book) {
        id = book.id
        title = book.title
        author = book.author
        _categories = book.categories.toSet()
        wasRead = book.wasRead
        pages = book.pages.toString()
    }
}