package org.example.userservice.repository;

import org.example.userservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT user FROM Customer user WHERE user.email = :email")
    Optional<Customer> getCustomerByEmail(@Param("email") String email);
}
