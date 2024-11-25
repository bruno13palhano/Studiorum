package com.bruno13palhano.model

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val pages: Int
) {
    companion object {
        val EMPTY = Book(
            id = 0L,
            title = "",
            author = "",
            pages = 0
        )
    }
}
