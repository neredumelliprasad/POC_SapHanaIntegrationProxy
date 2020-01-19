package com.sap.spring.hana.controllers;

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.*;

@CrossOrigin
@RestController
@RequestMapping( "/masterdata" )
public class SapHanaProxyController extends AbstractProxyController
{
    private static final Logger logger = CloudLoggerFactory.getLogger(SapHanaProxyController.class);
    private static final Gson gson = new Gson();
    RestTemplate restTemplate = new RestTemplate();

    @Value("${sap-connection-server}")
    String baseUrl;

    @RequestMapping(value = "/product/getByKey", method = RequestMethod.GET )
    public ResponseEntity getProduct( @RequestParam( defaultValue = "" ) final List<String> keys)
    {
        String uri = baseUrl.trim()+"/masterdata/product/getByKey?keys="+"".join(",",keys);
        logProxyRequest(uri,null,null);
        ResponseEntity responseEntity = restTemplate.getForEntity(uri, Object.class);
        logProxyResponse(responseEntity);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity postProduct(@RequestBody List<Object> rawProductDataList)
    {
        ResponseEntity responseEntity = null;
        try{
            String uri = baseUrl.trim()+"/masterdata/product";
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity(rawProductDataList, headers);
            logProxyRequest(uri,headers,rawProductDataList);
            responseEntity = restTemplate.exchange(uri,HttpMethod.PUT, entity, Object.class);
            logProxyResponse(responseEntity);
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }catch (HttpStatusCodeException e)
        {
            logProxyResponse(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getResponseBodyAsByteArray());
        }
    }
}
