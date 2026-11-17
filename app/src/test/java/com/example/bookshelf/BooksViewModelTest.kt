package com.example.bookshelf

import com.example.bookshelf.fake.FakeDataSource
import com.example.bookshelf.fake.FakeNetworkBooksRepository
import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.screens.BooksUiState
import com.example.bookshelf.ui.screens.BooksViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BooksViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun booksViewModel_getBooks_verifyBooksUiStateSuccess() = runTest {
        val booksViewModel = BooksViewModel(
            booksRepository = FakeNetworkBooksRepository()
        )
        assertEquals(
            BooksUiState.Success(FakeDataSource.fakeBooksList),
            booksViewModel.booksUiState
        )
    }

    @Test
    fun booksViewModel_selectGenre_updatesSelectedGenre() = runTest {
        val booksViewModel = BooksViewModel(
            booksRepository = FakeNetworkBooksRepository()
        )
        booksViewModel.selectGenre("Shonen")
        assertEquals("Shonen", booksViewModel.selectedGenre)
    }

    @Test
    fun booksViewModel_searchQuery_updatesSearchQuery() = runTest {
        val booksViewModel = BooksViewModel(
            booksRepository = FakeNetworkBooksRepository()
        )
        booksViewModel.onSearchQueryChange("Naruto")
        assertEquals("Naruto", booksViewModel.searchQuery)
    }
}
