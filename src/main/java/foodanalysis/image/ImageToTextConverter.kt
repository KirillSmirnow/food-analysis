package foodanalysis.image

import java.io.InputStream

interface ImageToTextConverter {

    fun convert(imageStream: InputStream): String
}
