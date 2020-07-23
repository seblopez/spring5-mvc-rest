package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
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

public class VendorControllerTest {

    public static final String VENDOR_URL = "/api/v1/vendors/1";
    public static final String SA = " S. A.";
    private final String NAME = "La Covachita";

    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorService vendorService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllOk() throws Exception {
        when(vendorService.getVendors())
                .thenReturn(Arrays.asList(
                        VendorDTO.builder()
                                .name("La Covacha")
                                .build(),
                        VendorDTO.builder()
                                .name("El triunfo S.R.L.")
                                .build(),
                        VendorDTO.builder()
                                .name("La Estrella Federal S.A.")
                                .build(),
                        VendorDTO.builder()
                                .name("La Rosadita")
                                .build()
                ));

        // when/then
        mockMvc.perform(get("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(4)));

    }

    @Test
    public void getByIdOk() throws Exception {
        // given
        when(vendorService.getVendorById(1L)).thenReturn(VendorDTO.builder()
                .name(NAME)
                .vendor_url(VENDOR_URL)
                .build());

        // when/then
        mockMvc.perform(get("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void getByIdReturns404Error() throws Exception {
        // given
        when(vendorService.getVendorById(1232L)).thenThrow(ResourceNotFoundException.class);

        // when/then
        mockMvc.perform(get("/api/v1/vendors/1232")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createVendorOk() throws Exception {
        when(vendorService.createVendor(any(VendorDTO.class)))
                .thenReturn(VendorDTO.builder()
                        .name(NAME)
                        .vendor_url(VENDOR_URL)
                        .build());
        // when/then
        mockMvc.perform(post("/api/v1/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(VendorDTO.builder()
                        .name(NAME)
                        .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    public void updateVendorOk() throws Exception {
        // given
        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class)))
                .thenReturn(VendorDTO.builder()
                        .name(NAME + SA)
                        .vendor_url(VENDOR_URL)
                        .build());
        // when/then
        mockMvc.perform(put("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(VendorDTO.builder()
                        .name(NAME + SA)
                        .vendor_url(VENDOR_URL)
                        .build())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME + SA)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }


    @Test
    public void deleteVendor() throws Exception {
        // when/then
        mockMvc.perform(delete("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(any());

    }
}
