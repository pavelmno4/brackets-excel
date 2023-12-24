package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.Node
import ru.pkozlov.brackets.excel.core.dto.Node.Level
import ru.pkozlov.brackets.excel.core.dto.ParticipantDto
import ru.pkozlov.brackets.excel.core.exception.TooLargeSizeException
import ru.pkozlov.brackets.excel.core.util.groupQueueBy
import ru.pkozlov.brackets.excel.core.util.pollAndAddLast
import ru.pkozlov.brackets.excel.core.util.toQueue
import java.util.*
import kotlin.math.log2

class BracketGenerationService {
    fun generate(participants: Collection<ParticipantDto>): Node {
        val bracketSize: Int = defineBracketSize(participants.size)
        val preLastLevelCapacity: Int = bracketSize - participants.size

        val teams: Queue<Queue<ParticipantDto>> =
            participants
                .groupQueueBy { it.team }.values
                .sortedByDescending { it.size }
                .toQueue()

        val graph: Node = createBracket(bracketSize)

        val flatGraph: TreeMap<Level, Queue<Node>> =
            graph.flat(TreeMap<Level, Queue<Node>>(Level.comporator)) { it.level }

        val preLastLevel: Queue<Node> = flatGraph.run { get(lastKey().previous()) ?: LinkedList() }

        processPreLastLevel(preLastLevelCapacity, preLastLevel, teams)
        processLastLevel(preLastLevel, teams)

        return graph
    }

    private fun processPreLastLevel(
        preLastLevelCapacity: Int,
        preLastLevel: Queue<Node>,
        teams: Queue<Queue<ParticipantDto>>
    ) {
        repeat(preLastLevelCapacity) {
            preLastLevel.poll().apply { participant = teams.pollAndAddLast() }
        }
    }

    private fun processLastLevel(
        preLastLevel: Queue<Node>,
        teams: Queue<Queue<ParticipantDto>>
    ) {
        while (preLastLevel.isNotEmpty()) {
            preLastLevel.poll().apply {
                left?.participant = teams.pollAndAddLast()
                right?.participant = teams.pollAndAddLast()
            }
        }
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

    private fun createBracket(bracketSize: Int): Node {
        val deepLevel: Level = Level.valueOf(log2(bracketSize.toDouble()).toInt())
        return createGraph(Level.ZERO, deepLevel)
    }

    private fun createGraph(currentLevel: Level, deepLevel: Level): Node =
        if (currentLevel == deepLevel) Node(level = currentLevel)
        else Node(
            level = currentLevel,
            left = createGraph(currentLevel.next(), deepLevel),
            right = createGraph(currentLevel.next(), deepLevel)
        )
}