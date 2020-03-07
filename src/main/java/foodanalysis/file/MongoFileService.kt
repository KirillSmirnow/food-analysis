package foodanalysis.file

import com.mongodb.client.gridfs.model.GridFSFile
import foodanalysis.MainException
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class MongoFileService(private val gridFsTemplate: GridFsTemplate) : FileService {

    override fun upload(uploadStream: InputStream, filename: String): FileInfo {
        val id = gridFsTemplate.store(uploadStream, filename).toHexString()
        return FileInfo(id, filename)
    }

    override fun getInfo(id: String): FileInfo {
        val file = getFile(id)
        return FileInfo(id, file.filename)
    }

    override fun download(id: String): FileDownload {
        val file = getFile(id)
        val downloadStream = gridFsTemplate.getResource(file).inputStream
        return FileDownload(id, file.filename, downloadStream)
    }

    private fun getFile(id: String): GridFSFile {
        return gridFsTemplate.findOne(query(where("_id").isEqualTo(id)))
                ?: throw MainException("Файл не найден")
    }
}
