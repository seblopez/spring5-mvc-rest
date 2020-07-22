package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    public static final String NAME = "Samwise";
    public static final String LAST_NAME = "Gamyi";
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
                                .id(1L)
                                .firstname("Sam")
                                .lastname(LAST_NAME)
                                .build(),
                        Customer.builder()
                                .id(2L)
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
                .firstname(NAME)
                .lastname(LAST_NAME)
                .build()));

        // when
        final CustomerDTO customerDTO = customerService.getCustomerById(1L);

        // then
        assertNotNull(customerDTO);
        assertEquals(NAME, customerDTO.getFirstname());
        assertEquals(LAST_NAME, customerDTO.getLastname());
        assertEquals("/shop/customers/1/orders/", customerDTO.getOrdersUrl());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCustomerByIdNotFound() {
        // given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        customerService.getCustomerById(234L);


    }

    @Test
    public void createCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstname(NAME)
                .lastname(LAST_NAME)
                .build();
        // given
        final long newId = 2333L;
        when(customerRepository.save(Customer.builder()
                .firstname(NAME)
                .lastname(LAST_NAME)
                .build()))
                .thenReturn(Customer.builder()
                        .id(newId)
                        .firstname(NAME)
                        .lastname(LAST_NAME)
                        .build());

        // when
        final CustomerDTO savedCustomerDTO = customerService.createCustomer(customerDTO);

        // then
        assertNotNull(savedCustomerDTO);
        assertEquals(NAME, savedCustomerDTO.getFirstname());
        assertEquals(LAST_NAME, savedCustomerDTO.getLastname());
        assertEquals("/shop/customers/2333/orders/", savedCustomerDTO.getOrdersUrl());
    }

    @Test
    public void updateCustomer() {
        // given
        when(customerRepository.save(any(Customer.class))).thenReturn(Customer.builder()
                .id(1L)
                .firstname("Sam")
                .lastname(LAST_NAME)
                .build());

        // when
        final CustomerDTO savedCustomerDTO = customerService.updateCustomer(1L, CustomerDTO.builder()
                .firstname("Sam")
                .lastname(LAST_NAME)
                .build());

        // then
        assertNotNull(savedCustomerDTO);
        assertEquals("Sam", savedCustomerDTO.getFirstname());
        assertEquals(LAST_NAME, savedCustomerDTO.getLastname());
        assertEquals("/api/v1/customers/1", savedCustomerDTO.getCustomerUrl());

    }

    @Test
    public void patchCustomerOk() {
        // given
        when(customerRepository.save(any(Customer.class))).thenReturn(Customer.builder()
                .id(1L)
                .firstname("Sam")
                .lastname(LAST_NAME)
                .build());

        // when
        final CustomerDTO savedCustomerDTO = customerService.updateCustomer(1L, CustomerDTO.builder()
                .firstname("Sam")
                .lastname(LAST_NAME)
                .build());

        // then
        assertNotNull(savedCustomerDTO);
        assertEquals("Sam", savedCustomerDTO.getFirstname());
        assertEquals(LAST_NAME, savedCustomerDTO.getLastname());
        assertEquals("/api/v1/customers/1", savedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomerNoChangesOk() {
        // given
        when(customerRepository.save(any(Customer.class))).thenReturn(Customer.builder()
                .id(1L)
                .firstname(NAME)
                .lastname(LAST_NAME)
                .build());

        // when
        final CustomerDTO savedCustomerDTO = customerService.updateCustomer(1L, CustomerDTO.builder().build());

        // then
        assertNotNull(savedCustomerDTO);
        assertEquals(NAME, savedCustomerDTO.getFirstname());
        assertEquals(LAST_NAME, savedCustomerDTO.getLastname());
        assertEquals("/api/v1/customers/1", savedCustomerDTO.getCustomerUrl());

    }

    @Test
    public void delete() {
        customerService.deleteCustomerById(1L);

        verify(customerRepository).deleteById(anyLong());

    }
}
