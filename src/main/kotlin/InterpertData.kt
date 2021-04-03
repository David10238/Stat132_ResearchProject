import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.math.max

fun readJSon(): Competitions {
    val stringBuilder = StringBuilder()
    val file = File("comps.json")
    Scanner(file).use {
        while (it.hasNextLine())
            stringBuilder.append(it.nextLine())
    }

    val text = stringBuilder.toString()

    return Gson().fromJson(text, Competitions::class.java)
}

const val FOUR_DIGIT = 1000
const val FIVE_DIGIT = 10000

class TeamData(private val number: Int) {
    private val scores = arrayListOf<Int>()
    private val averages = arrayListOf<Double>()
    private var doneStuffThisIteration = false

    fun pushScore(score: Int) {
        scores.add(score)
        doneStuffThisIteration = true
    }

    fun averageComp() {
        if (doneStuffThisIteration)
            averages.add(scores.average())
        scores.clear()
        doneStuffThisIteration = false
    }

    val average get() = averages.average().toInt()

    val displayNumber get() = "$number $average"
    val displayDigit get() = "${if (number < FIVE_DIGIT) "Four" else "Five"} $average"
}

val teamMap = LinkedHashMap<Int, TeamData>()

fun evaluateComp(comp: Competition) {
    comp.matches.forEach(::evaluateMatch)
    teamMap.values.forEach(TeamData::averageComp)
}

fun evaluateMatch(match: Match) {
    if (match.teams.size != 1)
        return
    val team = match.teams[0].teamNumber
    if (team < FOUR_DIGIT)
        return
    if (!teamMap.containsKey(team))
        teamMap[team] = TeamData(team)
    teamMap[team]!!.pushScore(match.scoreBlueFinal)
}

fun main() {
    val competitions = readJSon().comps
    competitions.forEach(::evaluateComp)

    val numberWriter = FileWriter("dataByTeamNumber.txt", false)
    numberWriter.append("number score")

    val digitWriter = FileWriter("dataByDigitCount.txt", false)
    digitWriter.append("digits score")

    teamMap.values.filter { it.average != 0 }.forEach {
        numberWriter.append("\n${it.displayNumber}")
        digitWriter.append("\n${it.displayDigit}")
    }

    digitWriter.close()
    numberWriter.close()

    println("Number of teams ${teamMap.values.size}")
    println("Number of teams with zero average ${teamMap.values.filter { it.average == 0 }.size}")
}