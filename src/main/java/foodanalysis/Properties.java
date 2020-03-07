package foodanalysis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "main")
public class Properties {

    private TelegramBot telegramBot;
    private SocksProxy socksProxy;

    @Data
    public static class TelegramBot {
        private String username;
        private String token;
    }

    @Data
    public static class SocksProxy {
        private String host;
        private int port;
        private String username;
        private String password;
    }
}
