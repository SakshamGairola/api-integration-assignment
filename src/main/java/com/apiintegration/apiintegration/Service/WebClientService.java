package com.apiintegration.apiintegration.Service;

import com.apiintegration.apiintegration.Entities.AccessToken;
import com.apiintegration.apiintegration.Entities.Customer;
import com.apiintegration.apiintegration.Entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static com.apiintegration.apiintegration.URL.URLConstants.*;

@Service
public class WebClientService {

    private final WebClient webClient;

    @Autowired
    private ObjectMapper mapper;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).defaultHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).build();
    }

    public String fetchAccessToken(User user) throws JsonProcessingException {
        String userJSON = mapper.writeValueAsString(user);
        String accessTokenJson =
                this.webClient.post().uri(FETCH_ACCESS_TOKEN_ENDPOINT).body(BodyInserters.fromValue(userJSON)).retrieve().onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new RuntimeException("Error!"))).onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new RuntimeException("Error!"))).bodyToMono(String.class).block();

        AccessToken accessToken = mapper.readValue(accessTokenJson, AccessToken.class);
        accessToken.setAccess_token("Bearer " + accessToken.getAccess_token());
        return accessTokenJson;
    }

    public String createCustomer(Customer customer, AccessToken token) throws JsonProcessingException {
        String customerJSON = mapper.writeValueAsString(customer);
        return this.webClient.post().uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS).queryParam("cmd", "create").build()).header("Authorization", token.getAccess_token())
                .body(BodyInserters.fromValue(customerJSON)).retrieve().onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Error!"))).onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new RuntimeException("Error!"))).bodyToMono(String.class).block().trim();
    }

    public List<Customer> getAllCustomer(AccessToken token) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS).queryParam("cmd",
                        "get_customer_list").build()).header("Authorization", token.getAccess_token())
                .retrieve().onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new RuntimeException(
                        "Error!"))).onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Error!"))).bodyToFlux(Customer.class).collectList().block().subList(0, 10);
    }

    public String deleteCustomer(String uuid, AccessToken token) {
        return Objects.requireNonNull(this.webClient.post().uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS).queryParam("cmd", "delete").queryParam("uuid", uuid).build()).header("Authorization", token.getAccess_token())
                .retrieve().onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(new RuntimeException(
                        "Error!"))).onStatus(HttpStatusCode::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Error!"))).bodyToMono(String.class).block(), "Error"
                + "!").trim();
    }

    public String updateCustomer(String uuid, Customer customer, AccessToken token) throws JsonProcessingException {
        String customerJSON = mapper.writeValueAsString(customer);
        return Objects.requireNonNull(this.webClient.post().uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS).queryParam("cmd", "update").queryParam("uuid", uuid).build()).header("Authorization", token.getAccess_token())
                .body(BodyInserters.fromValue(customerJSON)).retrieve().onStatus(HttpStatusCode::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Error!"))).onStatus(HttpStatusCode::is5xxServerError, error -> Mono.error(new RuntimeException("Error!"))).bodyToMono(String.class).block(), "Error!").trim();
    }

}
