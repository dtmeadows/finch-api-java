package org.finch.api.services.blocking

import org.finch.api.TestServerExtension
import org.finch.api.client.okhttp.FinchOkHttpClient
import org.finch.api.models.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestServerExtension::class)
class AccountServiceTest {

    @Test
    fun callDisconnect() {
        val client =
            FinchOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .accessToken("test-api-key")
                .clientId("string")
                .clientSecret("string")
                .build()
        val accountService = client.account()
        val disconnectResponse =
            accountService.disconnect(AccountDisconnectParams.builder().build())
        println(disconnectResponse)
        disconnectResponse.validate()
    }

    @Test
    fun callIntrospect() {
        val client =
            FinchOkHttpClient.builder()
                .baseUrl(TestServerExtension.BASE_URL)
                .accessToken("test-api-key")
                .clientId("string")
                .clientSecret("string")
                .build()
        val accountService = client.account()
        val introspection = accountService.introspect(AccountIntrospectParams.builder().build())
        println(introspection)
        introspection.validate()
    }
}
