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
class AnalyserTest {

    @Autowired
    lateinit var analyser: Analyser

    @Autowired
    lateinit var substanceRepository: SubstanceRepository

    private val simpleSubstance = Substance.of(emptyList(), "E381", null)
    private val longCodeSubstance = Substance.of(emptyList(), "E1422", null)
    private val longCodeSubstringSubstance = Substance.of(emptyList(), "E142", null)
    private val suffixCodeSubstance = Substance.of(emptyList(), "E960i", null)

    @Before
    fun setUp() {
        substanceRepository.deleteAll()
        substanceRepository.saveAll(
            listOf(
                simpleSubstance, longCodeSubstance, longCodeSubstringSubstance, suffixCodeSubstance
            )
        )
    }

    @Test
    fun `When analyse text without substances, then result is empty`() {
        val text = "Hello! There are no any substances like E*** or something..."
        val result = analyser.analyse(text).substances
        assertThat(result).isEmpty()
    }

    @Test
    fun `When analyse text with a simple substance, then result is the simple substance`() {
        val text = "Contents: olive oil, E381, salt."
        val result = analyser.analyse(text).substances
        assertThat(result).isEqualTo(listOf(simpleSubstance))
    }

    @Test
    fun `When analyse text with a long code substance, then result is the long code substance`() {
        val text = "Contents: E1422"
        val result = analyser.analyse(text).substances
        assertThat(result).isEqualTo(listOf(longCodeSubstance))
    }

    @Test
    fun `When analyse text with a suffix code substance, then result is the suffix code substance`() {
        val text = "Contents: E960i."
        val result = analyser.analyse(text).substances
        assertThat(result).isEqualTo(listOf(suffixCodeSubstance))
    }

    @Test
    fun `When analyse text with several substances, then result is ordered by occurrence`() {
        val text = "Contents of the product: flour, eggs, salt, water, E142, E381, E960i and E1422."
        val result = analyser.analyse(text).substances
        assertThat(result).isEqualTo(
            listOf(
                longCodeSubstringSubstance, simpleSubstance, suffixCodeSubstance, longCodeSubstance
            )
        )
    }
}
