package dev.fintechlab.customer;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name="customer")
public class CustomerEntity {
    @Id @Column(columnDefinition="binary(16)") private UUID id;
    @Column(name="full_name", nullable=false, length=120) private String fullName;
    @Column(nullable=false, unique=true, length=180) private String email;
    protected CustomerEntity() {}
    private CustomerEntity(UUID id, String fullName, String email) { this.id=id; this.fullName=fullName; this.email=email.toLowerCase(); }
    public static CustomerEntity create(String fullName, String email) { return new CustomerEntity(UUID.randomUUID(), fullName.strip(), email.strip()); }
    public UUID getId(){return id;} public String getFullName(){return fullName;} public String getEmail(){return email;}
}
