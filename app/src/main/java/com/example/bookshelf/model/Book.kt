package com.example.bookshelf.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for Google Books search endpoint.
 */
data class BooksSearchResponse(
    @SerializedName("items") val items: List<BookItem>? = null,
    @SerializedName("totalItems") val totalItems: Int = 0
)

/**
 * Data Transfer Object representing a single book item from Google Books API.
 */
data class BookItem(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo? = null
)

/**
 * Volume metadata.
 */
data class VolumeInfo(
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("authors") val authors: List<String>? = null,
    @SerializedName("categories") val categories: List<String>? = null,
    @SerializedName("imageLinks") val imageLinks: ImageLinks? = null
)

/**
 * Image thumbnails returned by Google Books API.
 */
data class ImageLinks(
    @SerializedName("smallThumbnail") val smallThumbnail: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null
)

/**
 * Domain model used across the UI layer.
 */
data class Book(
    val id: String,
    val title: String,
    val imgSrc: String,
    val genre: String = "Manga",
    val description: String = ""
)
