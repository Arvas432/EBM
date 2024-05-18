

interface SearchHistoryInteractor {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}