package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);

    }

    @Test
    public void patchCustomerUpdateFirstNameReturnsOk() {
        Customer customerToUpdate = getCustomer("Samwise", "Gamyi");
        customerToUpdate.setFirstname("Sam");

        final CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customerToUpdate.getId(), CustomerDTO.builder()
                .firstname(customerToUpdate.getFirstname())
                .build());

        assertNotNull(patchedCustomerDTO);
        assertEquals("Sam", patchedCustomerDTO.getFirstname());
        assertThat("Samwise", not(equalTo(patchedCustomerDTO.getFirstname())));
        assertThat(customerToUpdate.getLastname(), equalTo(patchedCustomerDTO.getLastname()));

    }

    @Test
    public void patchCustomerUpdateLastNameReturnsOk() {
        Customer customerToUpdate = getCustomer("Frodo", "Baggins");
        customerToUpdate.setLastname("Bolson");

        final CustomerDTO patchedCustomerDTO = customerService.patchCustomer(customerToUpdate.getId(), CustomerDTO.builder()
                .lastname(customerToUpdate.getLastname())
                .build());

        assertNotNull(patchedCustomerDTO);
        assertEquals("Bolson", patchedCustomerDTO.getLastname());
        assertThat(customerToUpdate.getFirstname(), equalTo(patchedCustomerDTO.getFirstname()));
        assertThat("Baggins", not(equalTo(patchedCustomerDTO.getLastname())));

    }

    private Customer getCustomer(String firstName, String lastName) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getFirstname().equals(firstName))
                .filter(customer -> customer.getLastname().equals(lastName))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }
}
