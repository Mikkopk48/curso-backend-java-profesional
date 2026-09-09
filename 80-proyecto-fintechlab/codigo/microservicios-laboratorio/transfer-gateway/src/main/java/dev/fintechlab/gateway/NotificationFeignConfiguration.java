package dev.fintechlab.gateway;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
public class NotificationFeignConfiguration { @Bean ErrorDecoder notificationErrorDecoder(){return (method,response)->new RemoteNotificationException(response.status(),"Notification service respondió "+response.status());} }
