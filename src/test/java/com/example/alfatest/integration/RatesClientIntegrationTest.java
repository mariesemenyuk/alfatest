package com.example.alfatest.integration;

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
class RatesClientIntegrationTest {

    @Autowired
    private WireMockServer mockRatesService;

    @Autowired
    private RateServiceProxy rateServiceProxy;

    @BeforeEach
    void setUp() throws IOException {
        RateMocks.setupMockLatestRatesResponse(mockRatesService);
        RateMocks.setupMockDateRatesResponse(mockRatesService);
        RateMocks.setupMockCurrenciesResponse(mockRatesService);
    }

    @Test
    public void whenGetLatestRateResponseIsNotEmpty() {
        assertFalse(rateServiceProxy.retrieveCurrentRate().isEmpty());
    }

    @Test
    public void whenGetAllCurrenciesResponseIsNotEmpty() {
        assertFalse(rateServiceProxy.getAllCurrencies().isEmpty());
    }

    @Test
    public void whenGetRateOnDateResponseIsNotEmpty() {
        assertFalse(rateServiceProxy.retrieveHistoricalRate("2022-06-09").isEmpty());
    }

    @Test
    public void whenGetRateOnDateResponseContainsFields() {
        assertTrue(rateServiceProxy.retrieveCurrentRate()
                .contains("\"timestamp\": " ));
        assertTrue(rateServiceProxy.retrieveCurrentRate()
                .contains("\"base\": " ));
        assertTrue(rateServiceProxy.retrieveCurrentRate()
                .contains("\"rates\": " ));
    }
}
