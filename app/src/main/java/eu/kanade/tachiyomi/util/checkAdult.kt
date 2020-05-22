package eu.kanade.tachiyomi.util

fun checkAdult(genres: String): Boolean {
    if (genres.contains("nsfw", true)) return true
    val theNSFWkeywrods = arrayOf(
            "adult",
            "doujinshi",
            "Ecchi",
            "Hentai",
            "Sexual",
            "loli",
            "lolicon",
            "yuri",
            "yaoi",
            "smut",
            "shota",
            "shotacon",
            "incest"
    )

    if (genres.contains("mature", true) && (genres.contains("romance", true) || genres.contains("gender bender", true))) return true

    for (element in theNSFWkeywrods) {
        if (genres.contains(element, true)) return true
    }
    return false
}
