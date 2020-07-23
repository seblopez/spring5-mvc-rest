package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    static final String NAME = "Samwise";
    static final String LAST_NAME = "Gamyi";
    public static final String CUSTOMER_ORDERS_URL = "/shop/customers/2333/orders/";
    public static final String CUSTOMER_URL = "/api/v1/customers/2333";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAll() throws Exception {
        // given
        when(customerService.getCustomers())
                .thenReturn(Arrays.asList(
                        CustomerDTO.builder()
                                .firstname(NAME)
                                .lastname(LAST_NAME)
                                .ordersUrl("/shop/customers/1/orders/")
                                .build(),
                        CustomerDTO.builder()
                                .firstname("Frodo")
                                .lastname("Baggins")
                                .ordersUrl("/shop/customers/2/orders/")
                                .build()
        ));

        // when/then
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void getByIdOk() throws Exception {
        // given
        when(customerService.getCustomerById(1L)).thenReturn(CustomerDTO.builder()
                .firstname(NAME)
                .lastname(LAST_NAME)
                .ordersUrl("/shop/customers/1/orders/")
                .build());

        // when/then
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));

    }

    @Test
    public void getByIdReturns404Error() throws Exception {
        // given
        when(customerService.getCustomerById(1232L)).thenThrow(ResourceNotFoundException.class);

        // when/then
        mockMvc.perform(get("/api/v1/customers/1232")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createCustomerOk() throws Exception {
        // given
        when(customerService.createCustomer(any(CustomerDTO.class)))
                .thenReturn(CustomerDTO.builder()
                        .firstname(NAME)
                        .lastname(LAST_NAME)
                        .ordersUrl(CUSTOMER_ORDERS_URL)
                        .customerUrl(CUSTOMER_URL)
                        .build());
        // when/then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CustomerDTO.builder()
                        .firstname(NAME)
                        .lastname(LAST_NAME)
                        .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)))
                .andExpect(jsonPath("$.ordersUrl", equalTo(CUSTOMER_ORDERS_URL)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));

    }

    @Test
    public void updateCustomerOk() throws Exception {
        // given
        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class)))
                .thenReturn(CustomerDTO.builder()
                        .firstname("Sam")
                        .lastname(LAST_NAME)
                        .ordersUrl(CUSTOMER_ORDERS_URL)
                        .customerUrl(CUSTOMER_URL)
                        .build());
        // when/then
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CustomerDTO.builder()
                        .firstname("Sam")
                        .lastname(LAST_NAME)
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Sam")))
                .andExpect(jsonPath("$.ordersUrl", equalTo(CUSTOMER_ORDERS_URL)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));

    }

    @Test
    public void deleteCustomerOk() throws Exception {
        // when/then
        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(any());

    }
}
