package com.example.alfatest.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class RateMocks {

    public static void setupMockLatestRatesResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/latest.json?app_id=${app_id}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        RateMocks.class.getClassLoader().getResourceAsStream("resources/rate_latest_rub_response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockDateRatesResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/historical/{date}.json?app_id=${app_id}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        RateMocks.class.getClassLoader().getResourceAsStream("resources/rate_latest_rub_response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockCurrenciesResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/api/currencies.json"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        RateMocks.class.getClassLoader().getResourceAsStream("resources/currencies_response.json"),
                                        defaultCharset()))));
    }
}
