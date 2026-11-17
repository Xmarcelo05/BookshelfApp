package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.fake.FakeBooksApiService
import com.example.bookshelf.fake.FakeDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {

    @Test
    fun networkBooksRepository_getBooks_verifyBooksList() = runTest {
        val repository = NetworkBooksRepository(
            booksApiService = FakeBooksApiService()
        )
        val result = repository.getBooks("manga")
        assertEquals(FakeDataSource.fakeBooksList, result)
    }

    @Test
    fun networkBooksRepository_getBook_verifyBookDetails() = runTest {
        val repository = NetworkBooksRepository(
            booksApiService = FakeBooksApiService()
        )
        val result = repository.getBook(FakeDataSource.idOne)
        assertEquals(FakeDataSource.fakeBooksList[0], result)
    }
}
