package foodanalysis.bot

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

class LongPollingBot(private val options: BotOptions) : TelegramLongPollingBot(options.options) {

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
        if (message.hasText()) {
            // process text
        } else if (message.hasPhoto()) {
            // process photo
        }
    }

    private fun sendMessage(chatId: Long, text: String) {
        execute(SendMessage(chatId, text).enableHtml(true).disableWebPagePreview())
    }
}
