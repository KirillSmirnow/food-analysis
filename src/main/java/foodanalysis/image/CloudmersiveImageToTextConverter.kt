package foodanalysis.image

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.env.Environment
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import java.io.InputStream

@Service
class CloudmersiveImageToTextConverter(environment: Environment) : ImageToTextConverter {

    private val log = LoggerFactory.getLogger(javaClass)

    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://api.cloudmersive.com")
        .defaultHeader("apiKey", environment.getProperty("OCR_API_KEY"))
        .defaultHeader("language", "RUS")
        .defaultHeader("recognitionMode", "Normal")
        .build()

    override fun convert(imageStream: InputStream): String {
        val formData = LinkedMultiValueMap<String, Any>()
        formData.add("imageFile", InputStreamResource(imageStream))
        val response = restTemplate.postForObject("/ocr/photo/toText", HttpEntity(formData), Response::class.java)
        log.info("Conversion result: $response")
        return response?.normalizedTextResult ?: ""
    }

    private data class Response(
        @JsonProperty("TextResult") val textResult: String,
        @JsonProperty("MeanConfidenceLevel") val meanConfidenceLevel: String
    ) {
        val normalizedTextResult = textResult.replace(Regex("\\s+"), " ")
    }
}
