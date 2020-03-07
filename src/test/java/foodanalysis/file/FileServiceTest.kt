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
}
