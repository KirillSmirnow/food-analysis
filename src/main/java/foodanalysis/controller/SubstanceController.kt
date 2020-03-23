package foodanalysis.controller

import foodanalysis.analysis.substance.Substance
import foodanalysis.analysis.substance.SubstanceService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/substances")
class SubstanceController(private val substanceService: SubstanceService) {

    @PutMapping
    fun import(@RequestParam files: List<MultipartFile>, @RequestParam mode: Mode): List<Substance> {
        val fileStreams = files.map { it.inputStream }
        return when (mode) {
            Mode.PARSE -> substanceService.parseCsv(fileStreams)
            Mode.UPDATE -> substanceService.parseCsvAndUpdate(fileStreams)
        }
    }

    enum class Mode {
        PARSE,
        UPDATE
    }
}
