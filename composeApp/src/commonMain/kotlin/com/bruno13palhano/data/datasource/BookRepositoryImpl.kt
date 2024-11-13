package com.bruno13palhano.data.datasource

import com.bruno13palhano.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(private val bookDataSource: BookDataSource) : BookRepository {
    override suspend fun insert(book: Book) {
        bookDataSource.insert(book)
    }

    override fun getById(id: Long): Flow<Book?> {
        return bookDataSource.getById(id)
    }

    override fun getAll(): Flow<List<Book>> {
        return bookDataSource.getAll()
    }
}
