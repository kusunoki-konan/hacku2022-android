package jp.kusunoki.hacku2022_android.data.model

data class YoutubeList(
    val items: List<YoutubeItem>
) {
    data class YoutubeItem(
        val id: Id,
        val snippet: Snippet
    ) {
        data class Id(
            val kind: String,
            val videoId: String
        )

        data class Snippet(
            val channelId: String,
            val title: String,
            val description: String,
            val thumbnails: Thumbnails,
            val channelTitle: String,

            ) {
            data class Thumbnails(
                val medium: Medium
            ) {
                data class Medium(
                    val url: String,
                    val width: Int,
                    val height: Int
                )
            }
        }
    }
}
