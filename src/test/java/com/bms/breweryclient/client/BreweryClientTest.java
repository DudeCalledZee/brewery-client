package com.bms.breweryclient.client;

import com.bms.breweryclient.modal.BeerDto;
import com.bms.breweryclient.modal.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());

        assertNotNull(dto);
    }

    @Test
    void saveNewBeer() {
        BeerDto dto = BeerDto.builder().beerName("Drink").beerType("Me").build();
        URI uri = client.saveNewBeer(dto);

        assertNotNull(uri);
    }

    @Test
    void updateBeer() {
        BeerDto dto = BeerDto.builder().beerName("Beer").build();

        client.updateBeerById(UUID.randomUUID(), dto);
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }
    @Test
    void getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
    }

    @Test
    void saveNewCustomer() {
        URI uri = client.saveNewCustomer(CustomerDto.builder().name("New Customer").build());
        assertNotNull(uri);
    }

    @Test
    void updateCustomerById() {
        client.updateCustomerById(UUID.randomUUID(), CustomerDto.builder().name("new Customer").build());
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }
}