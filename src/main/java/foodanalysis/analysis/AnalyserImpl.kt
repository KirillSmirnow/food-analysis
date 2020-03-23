package foodanalysis.analysis

import foodanalysis.analysis.substance.SubstanceRepository
import org.springframework.stereotype.Service

@Service
class AnalyserImpl(private val substanceRepository: SubstanceRepository) : Analyser {

    override fun analyse(text: String): Report {
        val substances = substanceRepository.findAll()
        val containedSubstances = substances.filter { it.isContainedIn(text) }
        return Report(containedSubstances)
    }
}
