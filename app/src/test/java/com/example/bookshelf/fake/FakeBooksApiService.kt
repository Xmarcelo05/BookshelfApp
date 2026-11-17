package com.example.bookshelf.fake

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BooksSearchResponse
import com.example.bookshelf.network.BooksApiService

class FakeBooksApiService : BooksApiService {
    override suspend fun searchBooks(query: String, maxResults: Int): BooksSearchResponse {
        return FakeDataSource.fakeSearchResponse
    }

    override suspend fun getBookDetails(id: String): BookItem {
        return FakeDataSource.fakeBookItems.firstOrNull { it.id == id }
            ?: FakeDataSource.fakeBookItems.first()
    }
}
