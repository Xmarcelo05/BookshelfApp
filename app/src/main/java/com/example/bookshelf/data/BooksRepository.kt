package com.example.bookshelf.data

import android.util.Log
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Repository interface for fetching books data.
 */
interface BooksRepository {
    /**
     * Fetches a list of books matching the given [query].
     */
    suspend fun getBooks(query: String = "manga"): List<Book>

    /**
     * Fetches detailed information for a single book by [id].
     */
    suspend fun getBook(id: String): Book?
}

/**
 * Network implementation of [BooksRepository] that uses [BooksApiService].
 */
class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {

    private val tag = "NetworkBooksRepository"

    override suspend fun getBooks(query: String): List<Book> {
        return try {
            val searchResponse = booksApiService.searchBooks(query)
            val items = searchResponse.items

            if (items.isNullOrEmpty()) {
                Log.w(tag, "Search items empty for query '$query'. Falling back to curated manga.")
                return MangaFallbackData.getByQuery(query)
            }

            // Following codelab: fetch individual book details to retrieve high quality thumbnails
            val books = coroutineScope {
                items.map { item ->
                    async {
                        try {
                            val details = booksApiService.getBookDetails(item.id)
                            val info = details.volumeInfo ?: item.volumeInfo
                            val rawUrl = info?.imageLinks?.thumbnail
                                ?: info?.imageLinks?.smallThumbnail
                            val title = info?.title ?: "Manga #${item.id}"
                            val genre = info?.categories?.firstOrNull() ?: "Manga"
                            val desc = info?.description ?: ""

                            if (!rawUrl.isNullOrEmpty()) {
                                // Google Books API returns http URLs; replace with https as required by Android
                                val secureUrl = rawUrl.replace("http:", "https:")
                                Book(
                                    id = item.id,
                                    title = title,
                                    imgSrc = secureUrl,
                                    genre = genre,
                                    description = desc
                                )
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            Log.e(tag, "Failed to fetch details for book ${item.id}", e)
                            null
                        }
                    }
                }.awaitAll().filterNotNull()
            }

            if (books.isNotEmpty()) {
                books
            } else {
                MangaFallbackData.getByQuery(query)
            }
        } catch (e: Exception) {
            Log.e(tag, "API error fetching books for query '$query'. Using fallback dataset.", e)
            MangaFallbackData.getByQuery(query)
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val item = booksApiService.getBookDetails(id)
            val info = item.volumeInfo
            val rawUrl = info?.imageLinks?.thumbnail ?: info?.imageLinks?.smallThumbnail
            val secureUrl = rawUrl?.replace("http:", "https:") ?: ""
            Book(
                id = item.id,
                title = info?.title ?: "Manga",
                imgSrc = secureUrl,
                genre = info?.categories?.firstOrNull() ?: "Manga",
                description = info?.description ?: ""
            )
        } catch (e: Exception) {
            Log.e(tag, "Error fetching book $id", e)
            MangaFallbackData.mangaList.firstOrNull { it.id == id }
        }
    }
}
