package com.example.bookshelf.data

import com.example.bookshelf.model.Book

/**
 * Curated manga dataset with verified HTTPS cover thumbnails and genres.
 * Used when the Google Books public API reaches rate limits (HTTP 429) or offline.
 */
object MangaFallbackData {
    val mangaList: List<Book> = listOf(
        Book(
            id = "berserk",
            title = "Berserk",
            imgSrc = "https://cdn.myanimelist.net/images/manga/2/253146.jpg",
            genre = "Seinen",
            description = "Guts, un antiguo mercenario conocido como el Espadachín Negro, busca venganza."
        ),
        Book(
            id = "jujutsu_kaisen",
            title = "Jujutsu Kaisen",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/216464.jpg",
            genre = "Shonen",
            description = "Yuji Itadori se une a los hechiceros de Jujutsu para combatir maldiciones letales."
        ),
        Book(
            id = "naruto",
            title = "Naruto",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/117681.jpg",
            genre = "Shonen",
            description = "Naruto Uzumaki sueña con convertirse en el Hokage de la Aldea de la Hoja."
        ),
        Book(
            id = "bleach",
            title = "Bleach",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/180031.jpg",
            genre = "Shonen",
            description = "Ichigo Kurosaki obtiene los poderes de un Shinigami para proteger a los vivos y a los espíritus."
        ),
        Book(
            id = "hunter_x_hunter",
            title = "Hunter x Hunter",
            imgSrc = "https://cdn.myanimelist.net/images/manga/2/253119.jpg",
            genre = "Aventura",
            description = "Gon Freecss emprende una travesía para convertirse en un Cazador legendario y encontrar a su padre."
        ),
        Book(
            id = "demon_slayer",
            title = "Demon Slayer: Kimetsu no Yaiba",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/179023.jpg",
            genre = "Acción",
            description = "Tanjiro Kamado lucha contra demonios para curar a su hermana Nezuko."
        ),
        Book(
            id = "attack_on_titan",
            title = "Attack on Titan",
            imgSrc = "https://cdn.myanimelist.net/images/manga/2/37846.jpg",
            genre = "Fantasía",
            description = "La humanidad resiste dentro de inmensos muros contra titanes devoradores."
        ),
        Book(
            id = "spy_x_family",
            title = "Spy x Family",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/219741.jpg",
            genre = "Comedia",
            description = "Un espía, una asesina y una telépata forman una peculiar familia falsa."
        ),
        Book(
            id = "fruits_basket",
            title = "Fruits Basket",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/182512.jpg",
            genre = "Romance",
            description = "Tohru Honda descubre el secreto zodiacal de la misteriosa familia Sohma."
        ),
        Book(
            id = "sailor_moon",
            title = "Sailor Moon",
            imgSrc = "https://cdn.myanimelist.net/images/manga/2/179833.jpg",
            genre = "Shojo",
            description = "Usagi Tsukino despierta como la legendaria guerrera de la justicia Sailor Moon."
        ),
        Book(
            id = "vagabond",
            title = "Vagabond",
            imgSrc = "https://cdn.myanimelist.net/images/manga/1/259070.jpg",
            genre = "Seinen",
            description = "La vida y sendero marcial del legendario espadachín Miyamoto Musashi."
        ),
        Book(
            id = "fullmetal_alchemist",
            title = "Fullmetal Alchemist",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/243675.jpg",
            genre = "Fantasía",
            description = "Los hermanos Elric buscan la Piedra Filosofal para restaurar sus cuerpos."
        ),
        Book(
            id = "monster",
            title = "Monster",
            imgSrc = "https://cdn.myanimelist.net/images/manga/3/258224.jpg",
            genre = "Seinen",
            description = "El neurocirujano Kenzo Tenma persigue a un psicópata al que salvó la vida años atrás."
        )
    )

    fun getByQuery(query: String): List<Book> {
        val q = query.trim().lowercase()
        if (q.isEmpty() || q == "manga" || q == "todos" || q == "all") {
            return mangaList
        }
        val filtered = mangaList.filter {
            it.title.lowercase().contains(q) ||
            it.genre.lowercase().contains(q) ||
            it.description.lowercase().contains(q)
        }
        return if (filtered.isNotEmpty()) filtered else mangaList
    }
}
