package ru.pkozlov.brackets.excel.core.service

import ru.pkozlov.brackets.excel.core.dto.*
import ru.pkozlov.brackets.excel.core.dto.Node.Level
import ru.pkozlov.brackets.excel.core.exception.TooLargeSizeException
import ru.pkozlov.brackets.excel.core.util.emptyBracketDto
import ru.pkozlov.brackets.excel.core.util.groupQueueBy
import ru.pkozlov.brackets.excel.core.util.pollAndAddLast
import ru.pkozlov.brackets.excel.core.util.toQueue
import java.util.*
import kotlin.math.log2

class BracketGenerationService(
    private val templateDefinitionComponent: TemplateDefinitionComponent
) {
    fun generate(
        tournamentName: String,
        category: Category,
        participants: Collection<ParticipantDto>
    ): BracketDto {
        if (participants.isEmpty()) return emptyBracketDto(tournamentName = tournamentName, category = category)

        val bracketSize: Int = defineBracketSize(participants.size)
        val preLastLevelCapacity: Int = bracketSize - participants.size
        val template: Template = templateDefinitionComponent.define(participants.size)
        val graph: Node = createGraph(bracketSize)

        when (participants.size) {
            3 -> fillCircleGrid(graph, participants)
            else -> fillOlympicGrid(graph, preLastLevelCapacity, participants)
        }

        return BracketDto(
            tournamentName = tournamentName,
            category = category,
            template = template,
            graph = graph
        )
    }

    private fun fillOlympicGrid(
        graph: Node,
        preLastLevelCapacity: Int,
        participants: Collection<ParticipantDto>
    ) {
        val teams: Queue<Queue<ParticipantDto>> =
            participants
                .groupQueueBy { it.team }.values
                .sortedByDescending { it.size }
                .toQueue()

        val flatGraph: TreeMap<Level, LinkedList<Node>> =
            graph.flat(TreeMap<Level, LinkedList<Node>>(Level.comporator)) { it.level }

        val preLastLevel: Queue<Node> = flatGraph.run { get(lastKey().previous()) ?: LinkedList() }

        processPreLastLevel(preLastLevelCapacity, preLastLevel, teams)
        processLastLevel(preLastLevel, teams)
    }

    private fun fillCircleGrid(
        graph: Node,
        participants: Collection<ParticipantDto>
    ) {
        val flatGraph: TreeMap<Level, LinkedList<Node>> =
            graph.flat(TreeMap<Level, LinkedList<Node>>(Level.comporator)) { it.level }

        val lastLevel: Queue<Node> = flatGraph.lastEntry().value
        val participantsQueue: Queue<ParticipantDto> = participants.toQueue()

        repeat(6) {
            lastLevel.poll().apply {
                val part: ParticipantDto = participantsQueue.poll()
                participant = part
                participantsQueue.add(part)
            }
        }
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
            participantsSize == 3 -> 8
            participantsSize == 4 -> 4
            participantsSize <= 8 -> 8
            participantsSize <= 16 -> 16
            else -> throw TooLargeSizeException("Count of participants is $participantsSize. Max grid is 16.")
        }

    private fun createGraph(bracketSize: Int): Node {
        val deepLevel: Level = Level.valueOf(log2(bracketSize.toDouble()).toInt())
        return createNode(Level.ZERO, deepLevel)
    }

    private fun createNode(currentLevel: Level, deepLevel: Level): Node =
        if (currentLevel == deepLevel) Node(level = currentLevel)
        else Node(
            level = currentLevel,
            left = createNode(currentLevel.next(), deepLevel),
            right = createNode(currentLevel.next(), deepLevel)
        )
}