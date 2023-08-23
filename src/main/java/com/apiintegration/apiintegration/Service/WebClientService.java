package com.apiintegration.apiintegration.Service;

import com.apiintegration.apiintegration.Entities.AccessToken;
import com.apiintegration.apiintegration.Entities.Customer;
import com.apiintegration.apiintegration.Entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

import static com.apiintegration.apiintegration.URL.URLConstants.*;

@Service
public class WebClientService {

        private final WebClient webClient;

        private AccessToken accessToken;

        @Autowired
        private ObjectMapper mapper;

        public WebClientService(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder
                                .baseUrl(BASE_URL)
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .build();
        }

        public void fetchAccessToken(User user) throws JsonProcessingException {
                String userJSON = mapper.writeValueAsString(user);
                String accessTokenJson = this.webClient.post()
                                .uri(FETCH_ACCESS_TOKEN_ENDPOINT)
                                .body(BodyInserters.fromValue(userJSON))
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError,
                                                error -> Mono.error(new RuntimeException("error!")))
                                .onStatus(HttpStatusCode::is5xxServerError,
                                                error -> Mono.error(new RuntimeException("error!")))
                                .bodyToMono(String.class)
                                .block();
                accessToken = mapper.readValue(accessTokenJson, AccessToken.class);
                accessToken.setAccess_token("Bearer " + accessToken.getAccess_token());
                System.out.println(accessToken.getAccess_token());
        }

        public String createCustomer(Customer customer) throws JsonProcessingException {
                String customerJSON = mapper.writeValueAsString(customer);
                return this.webClient.post()
                                .uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS)
                                                .queryParam("cmd", "create")
                                                .build())
                                .header("Authorization",
                                "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=") 
                                // accessToken.getAccess_token())
                                .body(BodyInserters.fromValue(customerJSON))
                                .retrieve()
                                .bodyToMono(String.class)
                                .block().trim();
        }

        public List<Customer> getAllCustomer() {
                return this.webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS)
                                                .queryParam("cmd", "get_customer_list")
                                                .build())
                                .header("Authorization",
                                "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=") 
                                // accessToken.getAccess_token())
                                .retrieve()
                                .bodyToFlux(Customer.class)
                                .collectList()
                                .block().subList(0, 9);
        }

        public String deleteCustomer(String uuid) {
                return Objects.requireNonNull(this.webClient.post()
                                .uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS)
                                                .queryParam("cmd", "delete")
                                                .queryParam("uuid", uuid)
                                                .build())
                                .header("Authorization",
                                "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=") 
                                // accessToken.getAccess_token())
                                .retrieve()
                                .bodyToMono(String.class)
                                .block(), "Error!").trim();
        }

        public String updateCustomer(String uuid, Customer customer) throws JsonProcessingException {
                String customerJSON = mapper.writeValueAsString(customer);
                return Objects.requireNonNull(this.webClient.post()
                                .uri(uriBuilder -> uriBuilder.path(API_ENDPOINTS)
                                                .queryParam("cmd", "update")
                                                .queryParam("uuid", uuid)
                                                .build())
                                .header("Authorization",
                                "Bearer dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=") 
                                // accessToken.getAccess_token())
                                .body(BodyInserters.fromValue(customerJSON))
                                .retrieve()
                                .bodyToMono(String.class)
                                .block(), "Error!").trim();
        }

}
