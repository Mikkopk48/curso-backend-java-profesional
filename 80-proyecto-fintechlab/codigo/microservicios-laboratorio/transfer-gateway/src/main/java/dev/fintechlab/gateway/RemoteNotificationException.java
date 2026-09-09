package dev.fintechlab.gateway;
public class RemoteNotificationException extends RuntimeException { private final int status; public RemoteNotificationException(int status,String message){super(message);this.status=status;} public int status(){return status;} }
