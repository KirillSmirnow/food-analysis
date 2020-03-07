package foodanalysis.bot

import foodanalysis.Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.bots.DefaultBotOptions
import java.net.Authenticator
import java.net.PasswordAuthentication

@Configuration
open class BotConfig(private val properties: Properties) {

    @Bean
    open fun bot(): LongPollingBot {
        val telegramBot = properties.telegramBot
        val botOptions = BotOptions(telegramBot.username, telegramBot.token)
        botOptions.options = options()
        return LongPollingBot(botOptions)
    }

    private fun options(): DefaultBotOptions {
        val options = DefaultBotOptions()
        val socksProxy = properties.socksProxy
        if (socksProxy.host != null && socksProxy.port > 0) {
            options.proxyType = DefaultBotOptions.ProxyType.SOCKS5
            options.proxyHost = socksProxy.host
            options.proxyPort = socksProxy.port
        }
        if (socksProxy.username != null && socksProxy.password != null) {
            setGlobalNetCredentials(socksProxy.username!!, socksProxy.password!!)
        }
        return options
    }

    private fun setGlobalNetCredentials(username: String, password: String) {
        Authenticator.setDefault(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password.toCharArray())
            }
        })
    }
}
