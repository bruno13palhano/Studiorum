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
            pages = book.pages.toLong()
        )
    }

    override suspend fun update(book: Book) {
        bookQueries.update(
            title = book.title,
            author = book.author,
            pages = book.pages.toLong(),
            id = book.id
        )
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
        pages: Long
    ) = Book(
        id = id,
        title = title,
        author = author,
        pages = pages.toInt()
    )
}