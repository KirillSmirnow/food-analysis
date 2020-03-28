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

    fun isContainedIn(text: String): Boolean = isNameContainedIn(text) || isCodeContainedIn(text)

    private fun isNameContainedIn(text: String): Boolean {
        return names.any { text.contains(it, ignoreCase = true) }
    }

    private fun isCodeContainedIn(text: String): Boolean {
        return code != null && text.contains(code, ignoreCase = true)
    }

    fun isSameAs(substance: Substance): Boolean = isNameSame(substance) || isCodeSame(substance)

    private fun isNameSame(substance: Substance): Boolean {
        return names.any { name ->
            substance.names.any { name.equals(it, ignoreCase = true) }
        }
    }

    private fun isCodeSame(substance: Substance): Boolean {
        return code != null && substance.code.equals(code, ignoreCase = true)
    }

    fun apply(substance: Substance): Substance? {
        if (names == substance.names && code == substance.code && healthImpact == substance.healthImpact) {
            return null
        }
        return Substance(id, substance.names, substance.code, substance.healthImpact)
    }
}
