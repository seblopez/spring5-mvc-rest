package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getCustomers() {
        // given
        List<Customer> customers = Arrays.asList(
                        Customer.builder()
                                .firstname("Sam")
                                .lastname("Gamyi")
                                .build(),
                        Customer.builder()
                                .firstname("Frodo")
                                .lastname("Baggins")
                                .build()
                );

        when(customerRepository.findAll()).thenReturn(customers);

        // when
        final List<CustomerDTO> customerListDTO = customerService.getCustomers();

        // then
        assertNotNull(customerListDTO);
        assertEquals(2, customerListDTO.size());

    }

    @Test
    public void getCustomerByIdOk() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(Customer.builder()
                .id(1L)
                .firstname("Samwise")
                .lastname("Gamyi")
                .build()));

        // when
        final CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertNotNull(customerDTO);
        assertEquals("Samwise", customerDTO.getFirstname());
        assertEquals("Gamyi", customerDTO.getLastname());
        assertEquals("/shop/customers/1/orders/", customerDTO.getOrdersUrl());

    }

    @Test(expected = NotFoundException.class)
    public void getCustomerByIdNotFound() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        customerService.getCustomerById(234L);


    }
}
