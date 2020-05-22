package eu.kanade.tachiyomi.source.model

import eu.kanade.tachiyomi.data.database.models.MangaImpl
import eu.kanade.tachiyomi.util.checkAdult
import timber.log.Timber
import java.io.Serializable

interface SManga : Serializable {

    var url: String

    var title: String

    var artist: String?

    var author: String?

    var description: String?

    var genre: String?

    var status: Int

    var thumbnail_url: String?

    var initialized: Boolean

    var isSFW: Int?

    fun hasCustomCover() = thumbnail_url?.startsWith("Custom-") == true

    fun copyFrom(other: SManga) {
        if (other.author != null)
            author = other.author

        if (other.artist != null)
            artist = other.artist

        if (other.description != null)
            description = other.description

        if (other.genre != null)
            genre = other.genre

        // TODO remove the Timber(s)
        Timber.i("Genres in SManga copyFrom")
        Timber.e(genre)
        Timber.e(other.genre)
        // mark - added filter to thumbnail
        if (other.isSFW == 1 || checkAdult(other.genre.toString())) {
            url = "$url/nsfw" // doing this so I can filter any link with the "nsfw" keyword at the end...
            isSFW = 1
            thumbnail_url = "https://fakeimg.pl/225x340/282828/eae0d0/?text=copyFrom%0AReplaced%0Ain-SManga" // ${other.title.replace(" ", "%0A")}"
        } else if (other.thumbnail_url != null && !hasCustomCover())
            thumbnail_url = other.thumbnail_url

        status = other.status

        if (!initialized)
            initialized = other.initialized
    }

    companion object {
        const val UNKNOWN = 0
        const val ONGOING = 1
        const val COMPLETED = 2
        const val LICENSED = 3

        fun create(): SManga {
            return MangaImpl()
        }
    }
}
