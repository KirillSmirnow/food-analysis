package foodanalysis.bot

import java.io.InputStream

interface Bot {

    fun sendMessage(chatId: Long, text: String)

    fun fetchFile(fileId: String): InputStream
}
