package foodanalysis.request

import foodanalysis.MainException
import foodanalysis.analysis.Report
import foodanalysis.bot.Bot
import foodanalysis.template.TemplateProcessor
import foodanalysis.user.TelegramAccount
import foodanalysis.user.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class RequestReporterImpl(private val templateProcessor: TemplateProcessor,
                          private val requestService: RequestService,
                          private val userService: UserService,
                          private val bot: Bot
) : RequestReporter {

    override fun sendReportToUser(requestId: UUID) {
        val request = requestService.getById(requestId)
        val report = request.report ?: throw MainException("Request is not completed")
        val user = userService.getById(request.userId)
        if (user.telegramAccount != null) {
            sendReportViaTelegram(report, user.telegramAccount)
        }
    }

    private fun sendReportViaTelegram(report: Report, telegramAccount: TelegramAccount) {
        val text = templateProcessor.applyModel("report", mapOf(Pair("report", report)))
        bot.sendMessage(telegramAccount.id, text)
    }
}
