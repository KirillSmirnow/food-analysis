package foodanalysis.analysis

import foodanalysis.analysis.substance.SubstanceRepository
import org.springframework.stereotype.Service

@Service
class AnalyserImpl(private val substanceRepository: SubstanceRepository) : Analyser {

    private val codeRegex = Regex("[EЕ][- ]?(\\d+[a-z]?)", RegexOption.IGNORE_CASE)
    private val englishE = "E"
    private val russianE = "Е"

    override fun analyse(text: String): Report {
        val extractedCodes = codeRegex.findAll(text)
            .distinct()
            .flatMap { sequenceOf(englishE + it.groupValues[1], russianE + it.groupValues[1]) }
            .toList()
        val detectedSubstances = extractedCodes.mapNotNull { substanceRepository.findByCode(it) }
        return Report(detectedSubstances)
    }
}
