package com.example.fxexercise.controller;


import com.example.fxexercise.repository.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.fxexercise.services.PriceService;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class PricePublisherController {

    @Autowired
    private PriceService messageService;

    //can't use PathVariable here since instrument name contains slashes...
    @GetMapping("/prices/**")
    public PriceDTO getPriceByInstrumentName(HttpServletRequest request){
        String instrumentName = getPathParamWithSlashesFromRequest(request);
        return getPriceByInstrument(instrumentName);
    }

    //...but can use RequestParam
    @GetMapping("/prices/")
    public PriceDTO getPriceByInstrumentName(@RequestParam String instrumentName){
        return getPriceByInstrument(instrumentName);
    }

    private PriceDTO getPriceByInstrument(String instrumentName){
        Optional<Price> priceOptional = messageService.getPriceForInstrument(instrumentName);
        Price price = priceOptional.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Price for instrument %S not found", instrumentName)));

        return PriceDTO.fromPrice(price);
    }

    private String getPathParamWithSlashesFromRequest(HttpServletRequest request){
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        String matchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
    }

}
