package foodanalysis.analysis.substance

import java.io.InputStream

interface SubstanceImporter {

    fun dryRun(inputStreams: List<InputStream>): SubstanceImport

    fun update(inputStreams: List<InputStream>): SubstanceImport
}
