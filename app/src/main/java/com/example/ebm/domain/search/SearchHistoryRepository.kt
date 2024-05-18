
interface SearchHistoryRepository {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}