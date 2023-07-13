package com.cloudflare.billonsigndate.utils;

import com.cloudflare.billonsigndate.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class HttpUtility {

  private static Logger log = LoggerFactory.getLogger(HttpUtility.class);

  public static String convertObjectToJson(Object object) {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      return ow.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  public String get(String serviceUrl, String query, Map<String, String> headers) throws IOException, InterruptedException {

    final HttpClient httpClient = HttpClient.newBuilder()
                                            .version(HttpClient.Version.HTTP_2)
                                            .build();

    HttpRequest.Builder builder = HttpRequest.newBuilder()
                                             .GET()
                                             .setHeader("User-Agent", "Java 11 HttpClient Bot")
                                             .setHeader("Content-Type", "application/x-www-form-urlencoded");

    for (Map.Entry<String, String> header : headers.entrySet()) {
      builder.setHeader(header.getKey(), header.getValue());
    }
    if (query != null && query.length() > 0) {
      builder.uri(URI.create(serviceUrl + "?" + query));
    } else {
      builder.uri(URI.create(serviceUrl));
    }
    HttpRequest request = builder.build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    // print status code
    System.out.println(response.statusCode());
    if (response.statusCode() == 200 || response.statusCode() == 201) {
      String responseBody = response.body();
      System.out.println(responseBody);
      return responseBody;
    } else {
      String responseBody = response.body();
      log.error("Unable to get the Data from " + serviceUrl + "\n" + responseBody);
    }
    return null;
  }

  public Response post(String serviceUrl, Map<String, Object> data, Map<String, String> headers) throws IOException, InterruptedException {
    return post(serviceUrl, convertObjectToJson(data), headers);
  }

  public Response post(String serviceUrl, String data, Map<String, String> headers) throws IOException, InterruptedException {
    log.info("**************Post call service URL ************" + serviceUrl);
    log.info("**************Post call data  ************" + data);
    log.info("**************Post call headers  ************" + headers);

    final HttpClient httpClient = HttpClient.newBuilder()
                                            .version(HttpClient.Version.HTTP_2)
                                            .build();

    HttpRequest.Builder builder = HttpRequest.newBuilder()
                                             .POST(HttpRequest.BodyPublishers.ofString(data))
                                             .uri(URI.create(serviceUrl))
                                             .setHeader("User-Agent", "Java 11 HttpClient Bot");

    for (Map.Entry<String, String> header : headers.entrySet()) {
      builder.setHeader(header.getKey(), header.getValue());
    }
    HttpRequest request = builder.build();
    log.info("**************Post call http request  ************" + request);

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    log.info("**************Post call Response Body status code  ************" + response.statusCode());

    // print response body
    String responseBody = response.body();
    log.info("**************Post call Response Body  ************" + responseBody);
    Response result = new Response();
    result.setResponseBody(response.body());
    result.setStatusCode(response.statusCode());

    return result;
  }
}
