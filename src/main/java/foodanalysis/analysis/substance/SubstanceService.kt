package foodanalysis.analysis.substance

import java.io.InputStream

interface SubstanceService {

    fun parseCsv(inputStreams: List<InputStream>): List<Substance>

    fun parseCsvAndUpdate(inputStreams: List<InputStream>): List<Substance>
}
