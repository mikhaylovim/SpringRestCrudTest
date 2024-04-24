package com.example.springrestcrudtest.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomOpenApiController {

    private final ResourceLoader resourceLoader;

    public CustomOpenApiController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping(value = "/api-docs", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Resource getOpenApiDocs() {
        return resourceLoader.getResource("classpath:openapi.yaml");
    }
}
