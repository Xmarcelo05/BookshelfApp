# Bookshelf App - Manga Edition 📚✨

Aplicación Android desarrollada en **Jetpack Compose** basada en el proyecto oficial de Google Developers: [Codelab: Create a Bookshelf App](https://developer.android.com/codelabs/basic-android-kotlin-compose-bookshelf?hl=es-419#2) (Unidad 5 de Android Basics with Compose).

Esta edición está especialmente configurada para explorar y mostrar **portadas de mangas de diversos géneros** (Shonen, Seinen, Shojo, Acción, Fantasía, Romance, Aventura, etc.), implementando las mejores prácticas de arquitectura recomendadas por Google y comparables con el proyecto de referencia [MarsPhotos-Repository-and-Manual-DI](https://github.com/Xmarcelo05/MarsPhotos-Repository-and-Manual-DI.git).

---

## 🚀 Características Principales

1. **Consumo de API REST con Retrofit y Gson**:
   - Conexión al servicio web de **Google Books API** (`https://www.googleapis.com/books/v1/`).
   - Búsqueda de libros mediante el parámetro `q` (`/volumes?q={query}`).
   - Petición detallada para cada libro individual (`/volumes/{volume_id}`) para obtener miniaturas en alta resolución, autor, categoría y descripción, cumpliendo con la especificación de dos llamadas asíncronas del Codelab.
   - Conversión automática de URLs de miniaturas de `http` a `https` (requerido para la carga segura en Android).
   - Modo de resiliencia con fallback a mangas populares cuando la API pública de Google Books supera cuotas de consulta (código HTTP 429) o en modo sin conexión.

2. **Carga Asíncrona de Imágenes con Coil**:
   - Uso del componente `AsyncImage` para cargar portadas en segundo plano.
   - Indicadores visuales de carga (`loading_img`) y error (`ic_broken_image`).
   - Proporción de aspecto optimizada (`aspectRatio(0.7f)`) para formato portada de libro/manga.

3. **Arquitectura y Separación de Responsabilidades**:
   - **Capa de Datos**: Repositorio (`BooksRepository` / `NetworkBooksRepository`) que aísla la fuente de datos de la interfaz de usuario.
   - **Inyección Manual de Dependencias**: Contenedor a nivel de aplicación (`AppContainer` y `DefaultAppContainer`) instanciado en `BookshelfApplication`.
   - **Capa de Presentación (UI)**: `BooksViewModel` gestionando el estado con `BooksUiState` (`Loading`, `Success`, `Error`), instanciado mediante `ViewModelProvider.Factory`.
   - **Diseño en Jetpack Compose**:
     - Cuadrícula adaptable con desplazamiento vertical (`LazyVerticalGrid`).
     - Barra de búsqueda interactiva en tiempo real.
     - Selector horizontal de filtros por géneros de manga (Todos, Shonen, Seinen, Shojo, Acción, Fantasía, Romance, Aventura).
     - Tarjetas estilizadas con degradado, etiquetas de género y tipografía Material 3.

4. **Suite Completa de Pruebas Unitarias**:
   - Implementación de servicios y repositorios falsos para pruebas (`FakeBooksApiService`, `FakeNetworkBooksRepository`, `FakeDataSource`).
   - `TestDispatcherRule` con `UnconfinedTestDispatcher` para corrutinas de prueba.
   - Pruebas unitarias de ViewModel (`BooksViewModelTest`) y Repositorio (`NetworkBooksRepositoryTest`).

---

## 🏛️ Estructura del Proyecto

```
com.example.bookshelf
├── BookshelfApplication.kt          # Inicialización del AppContainer (Manual DI)
├── MainActivity.kt                  # Activity principal con Jetpack Compose
├── data
│   ├── AppContainer.kt              # Contenedor de dependencias
│   ├── BooksRepository.kt           # Interfaz e implementación de repositorio
│   └── MangaFallbackData.kt         # Catálogo de mangas de respaldo
├── model
│   └── Book.kt                      # Modelos DTOs de Google Books y modelo Book
├── network
│   └── BooksApiService.kt           # Interfaz de Retrofit para Google Books API
└── ui
    ├── BookshelfApp.kt              # Estructura Scaffold con TopAppBar
    ├── screens
    │   ├── BooksViewModel.kt        # ViewModel y BooksUiState (Loading, Success, Error)
    │   └── HomeScreen.kt            # Cuadrícula, filtros, estados de carga y error
    └── theme
        ├── Color.kt                 # Paleta de colores temáticos
        ├── Shape.kt                 # Formas redondeadas
        ├── Theme.kt                 # BookshelfTheme (Material 3)
        └── Type.kt                  # Tipografía
```

En la carpeta de pruebas unitarias (`app/src/test/java/com/example/bookshelf`):
```
├── BooksViewModelTest.kt            # Pruebas del ViewModel y estados
├── NetworkBooksRepositoryTest.kt    # Pruebas del Repositorio con FakeApiService
├── fake
│   ├── FakeBooksApiService.kt
│   ├── FakeDataSource.kt
│   └── FakeNetworkBooksRepository.kt
└── rules
    └── TestDispatcherRule.kt
```

---

## 🛠️ Tecnologías y Librerías Utilizadas

- **Kotlin** 1.9.10
- **Android Gradle Plugin (AGP)** 8.1.2
- **Jetpack Compose** (Compose BOM 2023.08.00)
- **Material 3**
- **Retrofit 2.9.0** con **Gson Converter**
- **Coil 2.4.0** (`coil-compose`)
- **Coroutines & Kotlinx Coroutines Test 1.7.3**
- **JUnit 4.13.2**

---

## 🧪 Ejecución de Pruebas Unitarias

Para ejecutar las pruebas unitarias desde la terminal:

```bash
./gradlew test
```

Salida esperada:
```
BUILD SUCCESSFUL
Task :app:testDebugUnitTest
Task :app:testReleaseUnitTest
```

---

## 📱 Cómo abrir y ejecutar en Android Studio

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Xmarcelo05/BookshelfApp.git
   ```
2. Abre **Android Studio** y selecciona **File > Open**, navegando a la carpeta clonada.
3. Espera a que Gradle sincronice las dependencias del proyecto.
4. Conecta un dispositivo físico o inicia un emulador Android (API 24 o superior).
5. Haz clic en **Run 'app'** (`Shift + F10`).
