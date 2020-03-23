package foodanalysis.analysis.substance

import java.util.*
import java.util.UUID.randomUUID

data class Substance(
        val id: UUID,
        val names: List<String>,
        val code: String?,
        val healthImpact: String?
) {
    companion object {
        fun of(names: List<String>, code: String?, healthImpact: String?): Substance {
            return Substance(randomUUID(), names, code, healthImpact)
        }
    }

    fun isContainedIn(text: String): Boolean = nameIsContainedIn(text) || codeIsContainedIn(text)

    private fun nameIsContainedIn(text: String): Boolean = names.any { it in text }

    private fun codeIsContainedIn(text: String): Boolean = code != null && code in text
}
