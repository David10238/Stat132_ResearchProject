import com.google.gson.JsonSyntaxException
import java.io.FileWriter

const val FIVE_DIGIT = 10000
val FOUR_DIGIT = 1000

class TeamData(val number: Int) {
    var oprSum = 0.0
    private val digits = if (number < FIVE_DIGIT) "Four" else "Five"
    var eventsPlayed = 0
    val averageOPR get() = (oprSum / eventsPlayed).toInt()
    val displayByDigit get() = "$digits $averageOPR"
    val displayByNumber get() = "$number $averageOPR"
}

class Calculator {
    val teamMap = LinkedHashMap<Int, TeamData>()
    fun pushData(ranking: Ranking) {
        val number = ranking.team.team_number
        if (number < FOUR_DIGIT)
            return
        if (!teamMap.containsKey(number))
            teamMap[number] = TeamData(number)
        teamMap[number]!!.oprSum += ranking.opr
        teamMap[number]!!.eventsPlayed++
    }
}

fun main() {
    val emptyKeys = arrayListOf<String>()
    val erroredKeys = arrayListOf<String>()
    val calculator = Calculator()

    val keys = API.getRR2EventKeys()

    println(keys.size)
    keys.forEach { key ->
        // some keys give malformed JSON
        try {
            val rankings = API.getRankings(key)
            println(API.getRankings(key))
            rankings.forEach(calculator::pushData)
            if(rankings.isEmpty())
                emptyKeys.add(key)
        }
        catch (e:JsonSyntaxException){
            erroredKeys.add(key)
        }
    }

    val numberWriter = FileWriter("dataByTeamNumber.txt", false)
    numberWriter.append("number opr")

    val digitWriter = FileWriter("dataByDigitCount.txt", false)
    digitWriter.append("digits opr")

    calculator.teamMap.values.forEach {
        numberWriter.append("\n${it.displayByNumber}")
        digitWriter.append("\n${it.displayByDigit}")
    }

    numberWriter.close()
    digitWriter.close()

    println("EmptyKeys")
    println(emptyKeys)
    println("ErroredKeys")
    println(erroredKeys)
}