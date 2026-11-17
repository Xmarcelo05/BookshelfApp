package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BooksSearchResponse
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo

object FakeDataSource {
    const val idOne = "manga_1"
    const val idTwo = "manga_2"
    const val titleOne = "Berserk"
    const val titleTwo = "Naruto"
    const val imgOne = "https://cdn.myanimelist.net/images/manga/2/253146.jpg"
    const val imgTwo = "https://cdn.myanimelist.net/images/manga/3/117681.jpg"

    val fakeBookItems = listOf(
        BookItem(
            id = idOne,
            volumeInfo = VolumeInfo(
                title = titleOne,
                categories = listOf("Seinen"),
                imageLinks = ImageLinks(thumbnail = imgOne)
            )
        ),
        BookItem(
            id = idTwo,
            volumeInfo = VolumeInfo(
                title = titleTwo,
                categories = listOf("Shonen"),
                imageLinks = ImageLinks(thumbnail = imgTwo)
            )
        )
    )

    val fakeSearchResponse = BooksSearchResponse(
        items = fakeBookItems,
        totalItems = 2
    )

    val fakeBooksList = listOf(
        Book(
            id = idOne,
            title = titleOne,
            imgSrc = imgOne,
            genre = "Seinen",
            description = ""
        ),
        Book(
            id = idTwo,
            title = titleTwo,
            imgSrc = imgTwo,
            genre = "Shonen",
            description = ""
        )
    )
}
