package com.tryfinch.api.services.blocking.hris

import com.tryfinch.api.TestServerExtension
import com.tryfinch.api.client.okhttp.FinchOkHttpClient
import com.tryfinch.api.models.*
import com.tryfinch.api.models.HrisPayStatementRetrieveManyParams
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestServerExtension::class)
class PayStatementServiceTest {

    @Test
    fun callRetrieveMany() {
        val client =
            FinchOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .accessToken("test-api-key")
                .build()
        val payStatementService = client.hris().payStatements()
        val getPayStatementsResponse =
            payStatementService.retrieveMany(
                HrisPayStatementRetrieveManyParams.builder()
                    .requests(
                        listOf(
                            HrisPayStatementRetrieveManyParams.Request.builder()
                                .paymentId("e8b90071-0c11-471c-86e8-e303ef2f6782")
                                .build()
                        )
                    )
                    .build()
            )
        println(getPayStatementsResponse)
        getPayStatementsResponse.responses().forEach { it.validate() }
    }
}
