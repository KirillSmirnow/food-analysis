package foodanalysis.user

import foodanalysis.MainException
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
@ContextConfiguration(classes = [UserServiceTestConfig::class])
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Test(expected = MainException::class)
    fun `Given user not authenticated, when get authenticated user, then failure`() {
        userService.getAuthenticatedUser()
    }

    @Test
    fun `Given user authenticated, when get authenticated user, then response contains correct information`() {
        val telegramAccount = TelegramAccount(100432, "username", "firstName", "lastName")
        userService.authenticate(telegramAccount)

        val user = userService.getAuthenticatedUser()
        assertThat(user.telegramAccount).isEqualToComparingFieldByField(telegramAccount)
    }
}
