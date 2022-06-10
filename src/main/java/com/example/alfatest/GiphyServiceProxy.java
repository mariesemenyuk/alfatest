package com.example.alfatest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphy-gif", url = "${giphy_url}")
public interface GiphyServiceProxy {

    @GetMapping("/v1/gifs/search?api_key=${giphy_app_id}&q=rich&limit=1&offset={offset}")
    public String getRichGif(@PathVariable("offset") int offset);

    @GetMapping("/v1/gifs/search?api_key=${giphy_app_id}&q=broke&limit=1&offset={offset}")
    public String getBrokeGif(@PathVariable("offset") int offset);
}
