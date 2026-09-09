package dev.fintechlab.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication @EnableFeignClients
public class TransferGatewayApplication { public static void main(String[] args){SpringApplication.run(TransferGatewayApplication.class,args);} }
