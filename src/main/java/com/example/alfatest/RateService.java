package com.example.alfatest;

import com.example.alfatest.exception.NotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

@Service
public class RateService {

    @Autowired
    private RateServiceProxy rateProxy;

    @Autowired
    private GiphyServiceProxy gifProxy;

    public String getImageUrl(String currency) {

        JsonObject currencies = JsonParser.parseString(rateProxy.getAllCurrencies()).getAsJsonObject();
        currency = currency.toUpperCase();
        if(!currencies.has(currency)) throw new NotFoundException();

        JsonObject ratesJsonObject = JsonParser.parseString(rateProxy.retrieveCurrentRate()).getAsJsonObject();
        long date = ratesJsonObject.get("timestamp").getAsLong();
        LocalDate currentDate = LocalDate.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault());
        Double currentRate = ratesJsonObject.get("rates").getAsJsonObject().get(currency).getAsDouble();

        JsonObject yesterdayRatesJsonObject = JsonParser
                .parseString(rateProxy.retrieveHistoricalRate(currentDate.minusDays(1).toString())).getAsJsonObject();
        Double yesterdayRate = yesterdayRatesJsonObject.get("rates").getAsJsonObject().get(currency).getAsDouble();

        Random rand = new Random();
        String gif = null;

        if(currentRate > yesterdayRate) {
            gif = gifProxy.getRichGif(rand.nextInt(20));
        } else {
            gif = gifProxy.getBrokeGif(rand.nextInt(20));
        }
        JsonArray gifData = JsonParser.parseString(gif).getAsJsonObject().getAsJsonArray("data");
        String url = gifData.get(0).getAsJsonObject()
                .get("images").getAsJsonObject()
                .get("original").getAsJsonObject()
                .get("url").getAsString();

        return url;
    }
}
