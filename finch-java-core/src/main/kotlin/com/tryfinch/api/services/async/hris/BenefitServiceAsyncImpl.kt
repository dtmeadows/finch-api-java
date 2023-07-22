package com.tryfinch.api.services.async.hris

import com.tryfinch.api.core.ClientOptions
import com.tryfinch.api.core.RequestOptions
import com.tryfinch.api.core.http.HttpMethod
import com.tryfinch.api.core.http.HttpRequest
import com.tryfinch.api.core.http.HttpResponse.Handler
import com.tryfinch.api.errors.FinchError
import com.tryfinch.api.models.CompanyBenefit
import com.tryfinch.api.models.CreateCompanyBenefitsResponse
import com.tryfinch.api.models.HrisBenefitCreateParams
import com.tryfinch.api.models.HrisBenefitListPageAsync
import com.tryfinch.api.models.HrisBenefitListParams
import com.tryfinch.api.models.HrisBenefitListSupportedBenefitsPageAsync
import com.tryfinch.api.models.HrisBenefitListSupportedBenefitsParams
import com.tryfinch.api.models.HrisBenefitRetrieveParams
import com.tryfinch.api.models.HrisBenefitUpdateParams
import com.tryfinch.api.models.SupportedBenefit
import com.tryfinch.api.models.UpdateCompanyBenefitResponse
import com.tryfinch.api.services.async.hris.benefits.IndividualServiceAsync
import com.tryfinch.api.services.async.hris.benefits.IndividualServiceAsyncImpl
import com.tryfinch.api.services.errorHandler
import com.tryfinch.api.services.json
import com.tryfinch.api.services.jsonHandler
import com.tryfinch.api.services.withErrorHandler
import java.util.concurrent.CompletableFuture

class BenefitServiceAsyncImpl
constructor(
    private val clientOptions: ClientOptions,
) : BenefitServiceAsync {

    private val errorHandler: Handler<FinchError> = errorHandler(clientOptions.jsonMapper)

    private val individuals: IndividualServiceAsync by lazy {
        IndividualServiceAsyncImpl(clientOptions)
    }

    override fun individuals(): IndividualServiceAsync = individuals

    private val createHandler: Handler<CreateCompanyBenefitsResponse> =
        jsonHandler<CreateCompanyBenefitsResponse>(clientOptions.jsonMapper)
            .withErrorHandler(errorHandler)

    /**
     * **Availability: Automated and Assisted Benefits providers**
     *
     * Creates a new company-wide benefit. Please use the `/meta` endpoint to view available types
     * for each provider.
     */
    override fun create(
        params: HrisBenefitCreateParams,
        requestOptions: RequestOptions
    ): CompletableFuture<CreateCompanyBenefitsResponse> {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("employer", "benefits")
                .putAllQueryParams(params.getQueryParams())
                .putAllHeaders(clientOptions.headers)
                .putAllHeaders(params.getHeaders())
                .body(json(clientOptions.jsonMapper, params.getBody()))
                .build()
        return clientOptions.httpClient.executeAsync(request, requestOptions).thenApply { response
            ->
            response
                .use { createHandler.handle(it) }
                .apply {
                    if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                        validate()
                    }
                }
        }
    }

    private val retrieveHandler: Handler<CompanyBenefit> =
        jsonHandler<CompanyBenefit>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /**
     * **Availability: Automated Benefits providers only**
     *
     * Lists benefit information for a given benefit
     */
    override fun retrieve(
        params: HrisBenefitRetrieveParams,
        requestOptions: RequestOptions
    ): CompletableFuture<CompanyBenefit> {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("employer", "benefits", params.getPathParam(0))
                .putAllQueryParams(params.getQueryParams())
                .putAllHeaders(clientOptions.headers)
                .putAllHeaders(params.getHeaders())
                .build()
        return clientOptions.httpClient.executeAsync(request, requestOptions).thenApply { response
            ->
            response
                .use { retrieveHandler.handle(it) }
                .apply {
                    if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                        validate()
                    }
                }
        }
    }

    private val updateHandler: Handler<UpdateCompanyBenefitResponse> =
        jsonHandler<UpdateCompanyBenefitResponse>(clientOptions.jsonMapper)
            .withErrorHandler(errorHandler)

    /**
     * **Availability: Automated and Assisted Benefits providers**
     *
     * Updates an existing company-wide benefit
     */
    override fun update(
        params: HrisBenefitUpdateParams,
        requestOptions: RequestOptions
    ): CompletableFuture<UpdateCompanyBenefitResponse> {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.POST)
                .addPathSegments("employer", "benefits", params.getPathParam(0))
                .putAllQueryParams(params.getQueryParams())
                .putAllHeaders(clientOptions.headers)
                .putAllHeaders(params.getHeaders())
                .body(json(clientOptions.jsonMapper, params.getBody()))
                .build()
        return clientOptions.httpClient.executeAsync(request, requestOptions).thenApply { response
            ->
            response
                .use { updateHandler.handle(it) }
                .apply {
                    if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                        validate()
                    }
                }
        }
    }

    private val listHandler: Handler<List<CompanyBenefit>> =
        jsonHandler<List<CompanyBenefit>>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /**
     * **Availability: Automated Benefits providers only**
     *
     * List all company-wide benefits.
     */
    override fun list(
        params: HrisBenefitListParams,
        requestOptions: RequestOptions
    ): CompletableFuture<HrisBenefitListPageAsync> {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("employer", "benefits")
                .putAllQueryParams(params.getQueryParams())
                .putAllHeaders(clientOptions.headers)
                .putAllHeaders(params.getHeaders())
                .build()
        return clientOptions.httpClient.executeAsync(request, requestOptions).thenApply { response
            ->
            response
                .use { listHandler.handle(it) }
                .apply {
                    if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                        forEach { it.validate() }
                    }
                }
                .let { HrisBenefitListPageAsync.Response.Builder().items(it).build() }
                .let { HrisBenefitListPageAsync.of(this, params, it) }
        }
    }

    private val listSupportedBenefitsHandler: Handler<List<SupportedBenefit>> =
        jsonHandler<List<SupportedBenefit>>(clientOptions.jsonMapper).withErrorHandler(errorHandler)

    /**
     * **Availability: Automated and Assisted Benefits providers**
     *
     * Lists available types and configurations for the provider associated with the access token.
     */
    override fun listSupportedBenefits(
        params: HrisBenefitListSupportedBenefitsParams,
        requestOptions: RequestOptions
    ): CompletableFuture<HrisBenefitListSupportedBenefitsPageAsync> {
        val request =
            HttpRequest.builder()
                .method(HttpMethod.GET)
                .addPathSegments("employer", "benefits", "meta")
                .putAllQueryParams(params.getQueryParams())
                .putAllHeaders(clientOptions.headers)
                .putAllHeaders(params.getHeaders())
                .build()
        return clientOptions.httpClient.executeAsync(request, requestOptions).thenApply { response
            ->
            response
                .use { listSupportedBenefitsHandler.handle(it) }
                .apply {
                    if (requestOptions.responseValidation ?: clientOptions.responseValidation) {
                        forEach { it.validate() }
                    }
                }
                .let {
                    HrisBenefitListSupportedBenefitsPageAsync.Response.Builder().items(it).build()
                }
                .let { HrisBenefitListSupportedBenefitsPageAsync.of(this, params, it) }
        }
    }
}
