package foodanalysis.bot

import org.telegram.telegrambots.bots.DefaultBotOptions

data class BotOptions(val username: String, val token: String) {
    var options: DefaultBotOptions = DefaultBotOptions()
}
