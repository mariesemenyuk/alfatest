package com.example.alfatest.integration;

import com.example.alfatest.GiphyServiceProxy;
import com.example.alfatest.RateServiceProxy;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "feign.hystrix.enabled=true"
})
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
class GiphyClientIntegrationTest {

    @Autowired
    private WireMockServer mockRatesService;

    @Autowired
    private GiphyServiceProxy giphyServiceProxy;

    @BeforeEach
    void setUp() throws IOException {
        GiphyMocks.setupMockRichGifResponse(mockRatesService);
        GiphyMocks.setupMockBrokeGifResponse(mockRatesService);
    }

    @Test
    public void whenGetRichGifResponseIsNotEmpty() {
        assertFalse(giphyServiceProxy.getRichGif(5).isEmpty());
    }

    @Test
    public void whenGetBrokeGifResponseIsNotEmpty() {
        assertFalse(giphyServiceProxy.getBrokeGif(5).isEmpty());
    }

    @Test
    public void whenGetGifResponseContainsFields() {
        assertTrue(giphyServiceProxy.getBrokeGif(5)
                .contains("\"type\":\"gif\"" ));
    }
}
