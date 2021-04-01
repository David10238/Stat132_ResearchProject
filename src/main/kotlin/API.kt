import java.io.File
import java.util.*

object API {
    val key by lazy{
        val stringBuilder = StringBuilder()
        val file = File("apiCode.txt")
        Scanner(file).use {
            while(it.hasNextLine())
                stringBuilder.append(it.nextLine())
        }
        stringBuilder.toString()
    }
}