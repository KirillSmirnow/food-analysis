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
        val options = BotOptions(properties.telegramBot.username, properties.telegramBot.token)
        options.options = options()
        return LongPollingBot(options)
    }

    private fun options(): DefaultBotOptions {
        val options = DefaultBotOptions()
        options.proxyType = DefaultBotOptions.ProxyType.SOCKS5
        options.proxyHost = properties.socksProxy.host
        options.proxyPort = properties.socksProxy.port
        setGlobalNetCredentials(properties.socksProxy.username, properties.socksProxy.password)
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
