package foodanalysis.request

import java.util.*

interface RequestReporter {

    fun sendReportToUser(requestId: UUID)
}
