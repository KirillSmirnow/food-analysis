package foodanalysis.analysis.substance

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Service
import java.io.InputStream
import java.nio.charset.Charset.defaultCharset

@Service
class SubstanceServiceImpl(private val substanceRepository: SubstanceRepository) : SubstanceService {

    override fun parseCsv(inputStreams: List<InputStream>): List<Substance> {
        return inputStreams.flatMap { parseCsv(it) }
    }

    private fun parseCsv(inputStream: InputStream): List<Substance> {
        val format = CSVFormat.DEFAULT.withFirstRecordAsHeader()
        return CSVParser.parse(inputStream, defaultCharset(), format).records
                .map { Substance.of(extractList(it, "Name"), it["Code"], it["HealthImpact"]) }
    }

    private fun extractList(record: CSVRecord, key: String): List<String> {
        return record.parser.headerNames.withIndex()
                .filter { it.value == key }
                .map { record[it.index] }
                .filterNot { it.isBlank() }
    }

    override fun parseCsvAndUpdate(inputStreams: List<InputStream>): List<Substance> {
        TODO()
    }
}
