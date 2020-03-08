package foodanalysis

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AmqpConfig {

    @Bean
    open fun performImageDownloadingQueue() = Queue("performImageDownloading")

    @Bean
    open fun performOcrQueue() = Queue("performOcr")

    @Bean
    open fun performAnalysisQueue() = Queue("performAnalysis")
}
