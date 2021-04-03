data class Competitions(
    val comps: List<Competition>
)

data class Competition(
    val matches: List<Match>
)

data class Match(
    val actualStartTime: String,
    val description: String,
    val tournamentLevel: String,
    val series: Int,
    val matchNumber: Int,
    val scoreRedFinal: Int,
    val scoreRedFoul: Int,
    val scoreRedAuto: Int,
    val scoreBlueFinal: Int,
    val scoreBlueFoul: Int,
    val scoreBlueAuto: Int,
    val postResultTime: String,
    val teams: List<Team>,
    val modifiedOn: String
)

data class Team(
    val teamNumber: Int,
    val station: String,
    val dq: Boolean,
    val onField: Boolean
)