package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Bookshelf Home screen.
 */
sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    /** Current UI state representing the request status */
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    /** Currently selected manga genre */
    var selectedGenre: String by mutableStateOf("Todos")
        private set

    /** Current text inside the search field */
    var searchQuery: String by mutableStateOf("")
        private set

    /**
     * Initializes the ViewModel by querying manga books.
     */
    init {
        getBooks("manga")
    }

    /**
     * Fetches books matching the given query from the repository.
     */
    fun getBooks(query: String = "manga") {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                val books = booksRepository.getBooks(query)
                if (books.isEmpty()) {
                    BooksUiState.Error
                } else {
                    BooksUiState.Success(books)
                }
            } catch (e: IOException) {
                BooksUiState.Error
            } catch (e: HttpException) {
                BooksUiState.Error
            } catch (e: Exception) {
                BooksUiState.Error
            }
        }
    }

    /**
     * Changes the selected genre and performs a search for that genre.
     */
    fun selectGenre(genre: String) {
        selectedGenre = genre
        searchQuery = ""
        val query = if (genre == "Todos") "manga" else "$genre manga"
        getBooks(query)
    }

    /**
     * Updates the active search query text.
     */
    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    /**
     * Triggers search based on user input.
     */
    fun onSearchSubmit(query: String) {
        if (query.isNotBlank()) {
            selectedGenre = "Personalizado"
            getBooks(query)
        }
    }

    /**
     * Factory for [BooksViewModel] that retrieves [BooksRepository] from [BookshelfApplication].
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}
