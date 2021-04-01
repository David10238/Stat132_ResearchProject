
fun main(){
    val seasons = API.getSeasons()

    for(season in seasons)
        println(season)
}