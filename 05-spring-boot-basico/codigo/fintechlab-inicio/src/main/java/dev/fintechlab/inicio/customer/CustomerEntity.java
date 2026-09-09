package dev.fintechlab.inicio.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_customers_email",
                columnNames = "email"))
class CustomerEntity {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "full_name", length = 120, nullable = false)
    private String fullName;

    @Column(length = 180, nullable = false)
    private String email;

    protected CustomerEntity() {
    }

    CustomerEntity(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    void update(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    String id() {
        return id;
    }

    String fullName() {
        return fullName;
    }

    String email() {
        return email;
    }
}
