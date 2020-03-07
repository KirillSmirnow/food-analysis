package foodanalysis.file

import java.io.InputStream

interface FileService {

    fun upload(uploadStream: InputStream, filename: String): FileInfo

    fun getInfo(id: String): FileInfo

    fun download(id: String): FileDownload
}
