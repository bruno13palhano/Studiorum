package com.bruno13palhano.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bruno13palhano.database.BookQueries
import com.bruno13palhano.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class BookDataSourceImpl(
    private val bookQueries: BookQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BookDataSource {
    override suspend fun insert(book: Book) {
        bookQueries.insert(
            title = book.title,
            author = book.author,
            categories = book.categories,
            pages = book.pages.toLong(),
            wasRead = book.wasRead,
            timestamp = book.timestamp
        )
    }

    override suspend fun update(book: Book) {
        bookQueries.update(
            title = book.title,
            author = book.author,
            pages = book.pages.toLong(),
            categories = book.categories,
            wasRead = book.wasRead,
            timestamp = book.timestamp,
            id = book.id,
        )
    }

    override suspend fun delete(id: Long) {
        bookQueries.delete(id = id)
    }

    override fun getById(id: Long): Flow<Book?> {
        return bookQueries.getById(id = id, mapper = ::mapToBook)
            .asFlow()
            .mapToOne(context = dispatcher)
            .catch { it.printStackTrace() }
    }

    override fun getAll(): Flow<List<Book>> {
        return bookQueries.getAll(mapper = ::mapToBook)
            .asFlow()
            .mapToList(context = dispatcher)
    }

    private fun mapToBook(
        id: Long,
        title: String,
        author: String,
        categories: List<String>,
        pages: Long,
        wasRead: Boolean,
        timestamp: Long
    ) = Book(
        id = id,
        title = title,
        author = author,
        categories = categories,
        pages = pages.toInt(),
        wasRead = wasRead,
        timestamp = timestamp
    )
}