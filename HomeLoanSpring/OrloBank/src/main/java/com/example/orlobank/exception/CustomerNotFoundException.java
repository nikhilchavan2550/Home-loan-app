
package com.example.orlobank.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String custId) {
        super("Customer with ID '" + custId + "' not found.");
    }
}