package foodanalysis.controller

import foodanalysis.analysis.substance.SubstanceImport
import foodanalysis.analysis.substance.SubstanceImporter
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/substances")
class SubstanceController(private val substanceImporter: SubstanceImporter) {

    @PutMapping
    fun import(@RequestParam files: List<MultipartFile>, @RequestParam mode: Mode): SubstanceImport {
        val fileStreams = files.map { it.inputStream }
        return when (mode) {
            Mode.DRY_RUN -> substanceImporter.dryRun(fileStreams)
            Mode.UPDATE -> substanceImporter.update(fileStreams)
        }
    }

    enum class Mode {
        DRY_RUN,
        UPDATE
    }
}
