package com.sap.spring.hana.controllers;
/**
 * Created by roychoud on 18 Jan 2020.
 */

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * History:
 * <ul>
 * <li> 18 Jan 2020 : roychoud - Created
 * </ul>
 *
 * @authors roychoud : Arunava Roy Choudhury
 * © 2020 HERE
 */
public class AbstractProxyController
{
    protected static final Logger logger = CloudLoggerFactory.getLogger(SapHanaProxyController.class);
    protected static final Gson gson = new Gson();

    protected final void logProxyRequest(String uri, HttpHeaders headers,Object data){
        String body = "REQUEST URI: |"+uri+"| HEADERS: |"+gson.toJson(headers)+"| BODY: |"+gson.toJson(data)+"|";
        logger.debug(body);
    }
    protected final void logProxyResponse(ResponseEntity responseEntity){
        String body = "RESPONSE STATUS: |"+responseEntity.getStatusCode().value()+"| HEADERS: |"
            +gson.toJson(responseEntity.getHeaders())+"| BODY: |"+gson.toJson(responseEntity.getBody());
        logger.debug(body);
    }
    protected final void logProxyResponse(HttpStatusCodeException e){
        String body = "ERROR-RESPONSE STATUS: |"+e.getStatusCode().value()+"| HEADERS: |"
            +gson.toJson(e.getResponseHeaders())+"| BODY: |"+e.getResponseBodyAsString()+"|";
        logger.debug(body);
    }
}
