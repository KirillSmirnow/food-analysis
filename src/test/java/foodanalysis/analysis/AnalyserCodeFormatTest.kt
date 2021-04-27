package foodanalysis.analysis

import foodanalysis.analysis.substance.Substance
import foodanalysis.analysis.substance.SubstanceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@ContextConfiguration(classes = [AnalyserTestConfig::class])
class AnalyserCodeFormatTest {

    @Autowired
    lateinit var analyser: Analyser

    @Autowired
    lateinit var substanceRepository: SubstanceRepository

    @Before
    fun setUp() {
        substanceRepository.deleteAll()
        substanceRepository.saveAll((500..511).map { Substance.of(emptyList(), "E$it", null) })
    }

    @Test
    fun `When analyse text with different code formats, then all substances detected`() {
        val text = listOf(
            "E500", "E-501", "E 502", // uppercase English
            "e503", "e-504", "e 505", // lowercase English
            "Е506", "Е-507", "Е 508", // uppercase Russian
            "е509", "е-510", "е 511"  // lowercase Russian
        ).joinToString()
        val result = analyser.analyse(text).substances
        assertThat(result).isEqualTo(substanceRepository.findAll())
    }
}
