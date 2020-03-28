package foodanalysis.analysis.substance

class SubstanceImport(val existing: List<Substance>, val importing: List<Substance>) {

    val add = importing.filter { importingSubstance ->
        existing.none { it.isSameAs(importingSubstance) }
    }

    val remove = existing.filter { existingSubstance ->
        importing.none { it.isSameAs(existingSubstance) }
    }

    val modify = existing.mapNotNull { existingSubstance ->
        val importingSubstance = importing.find { it.isSameAs(existingSubstance) }
        val modifiedSubstance = if (importingSubstance != null) existingSubstance.apply(importingSubstance) else null
        if (modifiedSubstance != null) Pair(existingSubstance, modifiedSubstance) else null
    }
}
