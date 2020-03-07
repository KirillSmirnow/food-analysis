package foodanalysis.file

import foodanalysis.MainException
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.io.ByteArrayInputStream
import kotlin.random.Random

@RunWith(SpringRunner::class)
@DataMongoTest
@ContextConfiguration(classes = [FileServiceTestConfig::class])
class FileServiceTest {

    @Autowired
    lateinit var fileService: FileService

    @Test(expected = MainException::class)
    fun `Given file doesn't exist, when get info, then failure`() {
        fileService.getInfo("test")
    }

    @Test
    fun `When upload file, then response contains correct information`() {
        val filename = "test"
        val uploadStream = ByteArrayInputStream(ByteArray(1000000))
        val fileInfo = fileService.upload(uploadStream, filename)
        assertThat(fileInfo.id).isNotBlank()
        assertThat(fileInfo.name).isEqualTo(filename)
    }

    @Test
    fun `Given file uploaded, when get info, then response contains correct information`() {
        val filename = "test"
        val uploadStream = ByteArrayInputStream(ByteArray(1000000))
        val fileInfo = fileService.upload(uploadStream, filename)

        val infoRetrievedById = fileService.getInfo(fileInfo.id)
        assertThat(infoRetrievedById.id).isEqualTo(fileInfo.id)
        assertThat(infoRetrievedById.name).isEqualTo(filename)
    }

    @Test
    fun `Given file uploaded, when download file, then contents are equal`() {
        val originalContent = Random.nextBytes(64000000)
        val fileInfo = fileService.upload(originalContent.inputStream(), "test")

        val fileDownload = fileService.download(fileInfo.id)
        assertThat(fileDownload.downloadStream).hasSameContentAs(originalContent.inputStream())
    }
}
