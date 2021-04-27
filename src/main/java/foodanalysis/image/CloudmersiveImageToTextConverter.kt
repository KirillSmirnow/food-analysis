package foodanalysis.image

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import java.io.InputStream

@Service
class CloudmersiveImageToTextConverter : ImageToTextConverter {

    private val log = LoggerFactory.getLogger(javaClass)

    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://api.cloudmersive.com")
        .defaultHeader("apiKey", "8b29a109-06e7-4dc6-9a51-3c6727994348")
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
