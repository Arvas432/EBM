
interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}