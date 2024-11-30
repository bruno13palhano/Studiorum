package com.bruno13palhano.model

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val categories: List<String>,
    val pages: Int,
    val wasRead: Boolean,
    val timestamp: Long
) {
    companion object {
        val EMPTY = Book(
            id = 0L,
            title = "",
            author = "",
            categories = emptyList(),
            pages = 0,
            wasRead = false,
            timestamp = 0L
        )
    }
}
