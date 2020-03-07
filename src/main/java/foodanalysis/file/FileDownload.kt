package foodanalysis.file

import java.io.InputStream

class FileDownload(id: String, name: String, val downloadStream: InputStream) : FileInfo(id, name)
