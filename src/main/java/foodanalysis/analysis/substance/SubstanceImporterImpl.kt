package foodanalysis.analysis.substance

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.stereotype.Service
import java.io.InputStream
import java.nio.charset.StandardCharsets

@Service
class SubstanceImporterImpl(private val substanceRepository: SubstanceRepository) : SubstanceImporter {

    override fun dryRun(inputStreams: List<InputStream>): SubstanceImport {
        val existing = substanceRepository.findAll()
        val importing = inputStreams.flatMap { parseCsv(it) }
        return SubstanceImport(existing, importing)
    }

    private fun parseCsv(inputStream: InputStream): List<Substance> {
        val format = CSVFormat.DEFAULT.withFirstRecordAsHeader()
        return CSVParser.parse(inputStream, StandardCharsets.UTF_8, format).records
                .map { Substance.of(extractList(it, "Name"), it["Code"], it["HealthImpact"]) }
    }

    private fun extractList(record: CSVRecord, key: String): List<String> {
        return record.parser.headerNames.withIndex()
                .filter { it.value == key }
                .map { record[it.index] }
                .filterNot { it.isBlank() }
    }

    override fun update(inputStreams: List<InputStream>): SubstanceImport {
        TODO("Not yet implemented")
    }
}
