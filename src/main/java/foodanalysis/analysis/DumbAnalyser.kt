package foodanalysis.analysis

import org.springframework.stereotype.Service

@Service
class DumbAnalyser : Analyser {

    override fun analyse(text: String): Report {
        return Report("$text\n\nResult: OK")
    }
}
