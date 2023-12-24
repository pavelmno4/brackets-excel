package ru.pkozlov.brackets.excel.core.dto

data class Node(
    val level: Level,
    val left: Node? = null,
    val right: Node? = null,
    var participant: ParticipantDto? = null
) {
    enum class Level(val value: Int) {
        ZERO(value = 0),
        ONE(value = 1),
        TWO(value = 2),
        THREE(value = 3),
        FOUR(value = 4),
        FIVE(value = 5);

        companion object Companion {
            fun valueOf(integer: Int): Level =
                entries.firstOrNull { it.value == integer } ?: throw IllegalArgumentException()

            val comporator: Comparator<Level> = compareBy(Level::value).reversed()
        }
    }
}