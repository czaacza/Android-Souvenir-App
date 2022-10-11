package fi.metropolia.project.souvenirapp.model.data


data class Memory(
    val id: Int,
    var title: String,
    val description: String,
    val location: String,
    val date: String,
    val light: Float,
    val imageUri: String,
) {
    override fun toString(): String {
        return "id: ${id}\n" +
                "title: ${title}\n" +
                "description: ${description}\n" +
                "location: ($location)\n" +
                "date: $date\n" +
                "light: $light\n" +
                "image: $imageUri"
    }
}