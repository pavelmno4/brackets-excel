package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Node
import ru.pkozlov.brackets.excel.core.dto.Node.Level
import ru.pkozlov.brackets.excel.core.dto.ParticipantDto
import ru.pkozlov.brackets.excel.core.dto.Team
import ru.pkozlov.brackets.excel.core.exception.TooLargeSizeException
import ru.pkozlov.brackets.excel.core.util.groupByDeque
import java.util.*
import kotlin.math.log2

class BracketGenerationService {
    fun generate(participants: Collection<ParticipantDto>): Node {
        val bracketSize: Int = defineBracketSize(participants.size)
        val preLastLevelCapacity: Int = bracketSize - participants.size
        val teams: Map<Team, Deque<ParticipantDto>> = participants.groupByDeque { it.team }

        val rootNode: Node = createGraph(bracketSize)
        return rootNode
    }

    private fun defineBracketSize(participantsSize: Int): Int =
        when {
            participantsSize <= 2 -> 2
            participantsSize == 3 -> 3
            participantsSize <= 4 -> 4
            participantsSize <= 8 -> 8
            participantsSize <= 16 -> 16
            participantsSize <= 32 -> 32
            else -> throw TooLargeSizeException("Count of participants is $participantsSize. Max grid is 32.")
        }

    private fun createGraph(bracketSize: Int): Node {
        val deepLevel: Level = Level.valueOf(log2(bracketSize.toDouble()).toInt())
        return createNode(Level.ZERO, deepLevel)
    }

    private fun createNode(currentLevel: Level, deepLevel: Level): Node =
        if (currentLevel == deepLevel) Node(level = currentLevel)
        else Node(
            level = currentLevel,
            left = createNode(Level.valueOf(currentLevel.value + 1), deepLevel),
            right = createNode(Level.valueOf(currentLevel.value + 1), deepLevel)
        )
}