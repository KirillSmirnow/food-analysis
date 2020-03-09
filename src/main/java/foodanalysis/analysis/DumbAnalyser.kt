package foodanalysis.analysis

import org.springframework.stereotype.Service

@Service
class DumbAnalyser : Analyser {

    override fun analyse(text: String): Report {
        val boundedText = if (text.length > 4000) "${text.substring(0, 4000)}..." else text
        return Report("$boundedText\n\n<u>Result</u>: <b>OK</b>")
    }
}
