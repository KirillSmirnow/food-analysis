package foodanalysis.image

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ImageToTextConverterTestConfig::class])
class ImageToTextConverterTest {

    @Autowired
    lateinit var imageToTextConverter: ImageToTextConverter

    @Test
    fun `When apply OCR for image, then response contains necessary text`() {
        val imageStream = javaClass.getResourceAsStream("/foodanalysis/image/rus-text.jpg")
        val text = imageToTextConverter.convert(imageStream)
        assertThat(text)
            .containsIgnoringCase("Текст имеет тему")
            .containsIgnoringCase("В тексте всегда есть главная мысль")
            .containsIgnoringCase("Текст можно озаглавить")
            .containsIgnoringCase("В тексте можно выделить части")
            .containsIgnoringCase("Предложения и части текста связаны между собой")
    }
}
