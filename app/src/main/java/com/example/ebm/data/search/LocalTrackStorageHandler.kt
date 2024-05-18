interface LocalTrackStorageHandler {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}