
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
    val zip_code: Int,
    val country: String,
    val rookie_year: Int,
    val website: String,
)

data class Season(
    val season_key: String,
    val description: String,
    val is_active: Boolean
)