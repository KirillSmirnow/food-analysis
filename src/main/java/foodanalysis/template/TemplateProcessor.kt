package foodanalysis.template

interface TemplateProcessor {

    fun applyModel(name: String, model: Any? = null): String
}
