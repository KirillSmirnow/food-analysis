package foodanalysis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "main")
open class Properties {

    val telegramBot = TelegramBot()

    class TelegramBot {
        lateinit var username: String
        lateinit var token: String
    }

    val socksProxy = SocksProxy()

    class SocksProxy {
        var host: String? = null
        var port = 0
        var username: String? = null
        var password: String? = null
    }
}
