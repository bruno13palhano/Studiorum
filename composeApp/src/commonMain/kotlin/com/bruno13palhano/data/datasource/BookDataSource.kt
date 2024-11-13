package com.bruno13palhano.data.datasource

import com.bruno13palhano.model.Book
import kotlinx.coroutines.flow.Flow

interface BookDataSource {
    suspend fun insert(book: Book)

    fun getById(id: Long): Flow<Book?>

    fun getAll(): Flow<List<Book>>
}