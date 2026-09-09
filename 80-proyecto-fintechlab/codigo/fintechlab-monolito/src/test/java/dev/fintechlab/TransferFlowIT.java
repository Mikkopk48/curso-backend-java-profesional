package dev.fintechlab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers @SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferFlowIT {
    @Container static final MySQLContainer<?> MYSQL=new MySQLContainer<>("mysql:8.4.11");
    @DynamicPropertySource static void db(DynamicPropertyRegistry r){r.add("spring.datasource.url",MYSQL::getJdbcUrl);r.add("spring.datasource.username",MYSQL::getUsername);r.add("spring.datasource.password",MYSQL::getPassword);}
    @Autowired TestRestTemplate http;
    @Test void repeatsOneIntentWithoutCreatingAnotherResult(){
        Map<?,?> customer=http.postForObject("/api/customers",Map.of("fullName","Cliente Ficticio","email","demo-"+UUID.randomUUID()+"@example.test"),Map.class);
        String customerId=customer.get("id").toString();
        Map<?,?> first=http.postForObject("/api/accounts",Map.of("customerId",customerId,"currency","ARS","openingBalance","100.00","alias","Origen"),Map.class);
        Map<?,?> second=http.postForObject("/api/accounts",Map.of("customerId",customerId,"currency","ARS","openingBalance","0.00","alias","Destino"),Map.class);
        HttpHeaders headers=new HttpHeaders();headers.setContentType(MediaType.APPLICATION_JSON);headers.set("Idempotency-Key","it-"+UUID.randomUUID());
        var body=Map.of("originAccountId",first.get("id"),"destinationAccountId",second.get("id"),"amount","25.00","currency","ARS");
        var created=http.exchange("/api/transfers",HttpMethod.POST,new HttpEntity<>(body,headers),Map.class);
        var replay=http.exchange("/api/transfers",HttpMethod.POST,new HttpEntity<>(body,headers),Map.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);assertThat(replay.getStatusCode()).isEqualTo(HttpStatus.OK);assertThat(replay.getBody().get("id")).isEqualTo(created.getBody().get("id"));
    }
}
