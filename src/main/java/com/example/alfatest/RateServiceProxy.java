package com.example.alfatest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "current-rate", url = "${rate_url}")
public interface RateServiceProxy {

    @GetMapping("/api/latest.json?app_id=${app_id}")
    public String retrieveCurrentRate();

    @GetMapping("/api/historical/{date}.json?app_id=${app_id}")
    public String retrieveHistoricalRate(@PathVariable("date") String date);

    @GetMapping("/api/currencies.json")
    public String getAllCurrencies();
}
