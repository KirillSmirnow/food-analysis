package foodanalysis.analysis

import foodanalysis.analysis.substance.SubstanceRepository
import org.springframework.stereotype.Service

@Service
class AnalyserImpl(private val substanceRepository: SubstanceRepository) : Analyser {

    private val codeRegex = Regex("[EЕ][- ]?(\\d+[a-z]?)", RegexOption.IGNORE_CASE)

    override fun analyse(text: String): Report {
        val extractedCodes = codeRegex.findAll(text).map { "E" + it.groupValues[1] }.distinct().toList()
        val detectedSubstances = extractedCodes.mapNotNull { substanceRepository.findByCode(it) }
        return Report(detectedSubstances)
    }
}
