package com.bruno13palhano.data.datasource

import com.bruno13palhano.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun insert(book: Book)

    suspend fun update(book: Book)

    fun getById(id: Long): Flow<Book?>

    fun getAll(): Flow<List<Book>>
}