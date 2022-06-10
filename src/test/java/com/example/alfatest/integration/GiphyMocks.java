package com.example.alfatest.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class GiphyMocks {

    public static void setupMockRichGifResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/gifs/search?api_key=${giphy_app_id}&q=rich&limit=1&offset={offset}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GiphyMocks.class.getClassLoader().getResourceAsStream("resources/gif_response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockBrokeGifResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/gifs/search?api_key=${giphy_app_id}&q=broke&limit=1&offset={offset}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        GiphyMocks.class.getClassLoader().getResourceAsStream("resources/gif_response.json"),
                                        defaultCharset()))));
    }
}
