import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

object API {
    ////////////////////////////
    // POLLING FUNCTIONS
    ////////////////////////////
    fun getSeasons() = getListFromPath("seasons", Season::class.java)

    fun getRR2Events() = getListFromPath("event?season_key=1819", Event::class.java)
    fun getRR2EventKeys() = getRR2Events().map(Event::event_key)
    fun getRankings(event_key:String) = getListFromPath("event/${event_key}/rankings", Ranking::class.java)

    ////////////////////////////
    // CONSTANTS
    ////////////////////////////
    private const val BASE_URL = "https://theorangealliance.org/api"
    private const val APP_NAME = "StatisticsProject"
    const val RR2_KEY = "1819"
    val KEY by lazy{
        val stringBuilder = StringBuilder()
        val file = File("apiCode.txt")
        Scanner(file).use {
            while(it.hasNextLine())
                stringBuilder.append(it.nextLine())
        }
        stringBuilder.toString()
    }

    private val GSON = Gson()

    private val httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build()

    ////////////////////////////
    // functions to read from api
    ////////////////////////////
    fun getText(path:String):String{
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("$BASE_URL/$path"))
            .setHeader("Content-Type", "application/json")
            .setHeader("X-TOA-Key", KEY)
            .setHeader("X-Application-Origin", APP_NAME)
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }

    fun<MODEL> getModelFromText(text:String, type: Class<MODEL>):MODEL{
        return GSON.fromJson(text, type)
    }

    fun<MODEL> getModelFromPath(path:String, type: Class<MODEL>):MODEL{
        return getModelFromText(getText(path), type)
    }

    fun<MODEL> getListFromPath(path:String, type: Class<MODEL>):List<MODEL>{
        val text = getText(path)
        return getListFromText(text, type)
    }

    fun<MODEL> getListFromText(text:String, type: Class<MODEL>):List<MODEL>{
        val jsonArray = JsonParser.parseString(text) as JsonArray
        return jsonArray.map {
            getModelFromText(it.toString(), type)
        }
    }
}