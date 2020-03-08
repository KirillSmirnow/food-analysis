package foodanalysis.bot

import foodanalysis.request.RequestService
import foodanalysis.user.TelegramAccount
import foodanalysis.user.UserService
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.InputStream

class LongPollingBot(private val options: BotOptions,
                     private val userService: UserService,
                     private val requestService: RequestService
) : TelegramLongPollingBot(options.options), Bot {

    init {
        ApiContextInitializer.init()
        TelegramBotsApi().registerBot(this)
    }

    override fun getBotUsername(): String = options.username

    override fun getBotToken(): String = options.token

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.isUserMessage) {
            processUserMessage(update.message)
        }
    }

    private fun processUserMessage(message: Message) {
        val user = message.from
        val account = TelegramAccount(user.id.toLong(), user.userName, user.firstName, user.lastName)
        userService.authenticate(account)

        if (message.hasText()) {
            requestService.createOfText(message.text)
        } else if (message.hasPhoto()) {
            message.photo.forEach {
                requestService.createOfTelegramImage(it.fileId)
            }
        }
    }

    override fun sendMessage(chatId: Long, text: String) {
        execute(SendMessage(chatId, text).enableHtml(true))
    }

    override fun fetchFile(fileId: String): InputStream {
        val fileInfo = execute(GetFile().setFileId(fileId))
        return downloadFileAsStream(fileInfo)
    }
}
