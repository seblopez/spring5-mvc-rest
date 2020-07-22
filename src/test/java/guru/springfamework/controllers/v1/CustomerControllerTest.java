package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAll() throws Exception {
        // given
        when(customerService.getCustomers())
                .thenReturn(Arrays.asList(
                        CustomerDTO.builder()
                                .firstname("Sam")
                                .lastname("Gamyi")
                                .ordersUrl("/shop/customers/1/orders/")
                                .build(),
                        CustomerDTO.builder()
                                .firstname("Frodo")
                                .lastname("Baggins")
                                .ordersUrl("/shop/customers/2/orders/")
                                .build()
        ));

        // when/then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));


    }

    @Test
    public void getByIdOk() throws Exception {
        // given
        when(customerService.getCustomerById(1L)).thenReturn(CustomerDTO.builder()
                .firstname("Sam")
                .lastname("Gamyi")
                .ordersUrl("/shop/customers/1/orders/")
                .build());

        // when/then
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Sam")));

    }

    @Ignore
    @Test
    public void getByIdReturns404() throws Exception {
        // given
        when(customerService.getCustomerById(1232L)).thenThrow(NotFoundException.class);

        // when/then
        mockMvc.perform(get("/api/v1/customers/1232")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
