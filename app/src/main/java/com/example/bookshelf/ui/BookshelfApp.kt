@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BooksViewModel
import com.example.bookshelf.ui.screens.HomeScreen

@Composable
fun BookshelfApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookshelfTopAppBar(scrollBehavior = scrollBehavior) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                booksUiState = booksViewModel.booksUiState,
                selectedGenre = booksViewModel.selectedGenre,
                searchQuery = booksViewModel.searchQuery,
                onGenreSelected = booksViewModel::selectGenre,
                onSearchQueryChange = booksViewModel::onSearchQueryChange,
                onSearchSubmit = booksViewModel::onSearchSubmit,
                onResetSearch = booksViewModel::resetSearch,
                retryAction = {
                    val query = if (booksViewModel.searchQuery.isNotBlank()) {
                        booksViewModel.searchQuery
                    } else if (booksViewModel.selectedGenre != "Personalizado" && booksViewModel.selectedGenre != "Todos") {
                        "${booksViewModel.selectedGenre} manga"
                    } else {
                        "manga"
                    }
                    booksViewModel.getBooks(query)
                },
                contentPadding = innerPadding
            )
        }
    }
}

@Composable
fun BookshelfTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
    )
}
