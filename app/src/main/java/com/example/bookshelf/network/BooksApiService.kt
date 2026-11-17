package com.example.bookshelf.network

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BooksSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for Google Books API.
 */
interface BooksApiService {

    /**
     * Searches for books matching [query].
     */
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): BooksSearchResponse

    /**
     * Fetches detailed information for a specific book volume by [id].
     */
    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") id: String
    ): BookItem
}
