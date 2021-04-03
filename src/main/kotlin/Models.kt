data class Team(
    val team_key: String,
    val region_key: String,
    val league_key: String,
    val team_number: Int,
    val team_name_short: String,
    val team_name_long: String,
    val robot_name: String,
    val last_active: Int,
    val city: String,
    val state_prov: String,
    val zip_code: String,
    val country: String,
    val rookie_year: Int,
    val website: String
)

data class Season(
    val season_key: String,
    val description: String,
    val is_active: Boolean
)

data class Event(
    val event_key: String,
    val season_key: String,
    val region_key: String,
    val league_key: String,
    val event_code: String,
    val event_type_key: String,
    val division_key: Int,
    val division_name: String,
    val event_name: String,
    val start_date: String,
    val end_date: String,
    val week_key: String,
    val city: String,
    val state_prov: String,
    val country: String,
    val venue: String,
    val website: String,
    val time_zone: String,
    val is_public: Boolean,
    val data_source: Int
)

data class Ranking(
    val rank_key: String,
    val event_key: String,
    val team_key: String,
    val rank: Int,
    val rank_change: Int,
    val opr: Double,
    val np_opr: Double,
    val wins: Int,
    val losses: Int,
    val ties: Int,
    val highest_qual_score: Int,
    val ranking_points: Int,
    val qualifying_points: Int,
    val tie_breaker_points: Int,
    val disqualified: Int,
    val played: Int,
    val team: Team
)
