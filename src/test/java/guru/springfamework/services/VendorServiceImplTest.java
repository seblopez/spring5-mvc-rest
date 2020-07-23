package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    static final String NAME = "La Covachita";
    private final VendorDTO baseVendorDTO = VendorDTO.builder()
            .name(NAME)
            .build();
    static final long ID = 1L;
    static final String VENDOR_URL = "/api/v1/vendors/1";
    private static final String SA = " S. A.";
    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getVendorsOk() {
        // given
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(
                Vendor.builder()
                        .id(1L)
                        .name("La Covacha")
                        .build(),
                Vendor.builder()
                        .id(2L)
                        .name("La Covachita")
                        .build()
        ));

        // when
        final List<VendorDTO> vendors = vendorService.getVendors();

        // then
        assertNotNull(vendors);
        assertEquals(2, vendors.size());

    }

    @Test
    public void getVendorById() {
        // given
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(Vendor.builder()
                .id(ID)
                .name(NAME)
                .build()));

        // when
        final VendorDTO vendorDTO = vendorService.getVendorById(1L);

        // then
        assertNotNull(vendorDTO);
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(VENDOR_URL, vendorDTO.getVendor_url());
    }

    @Test
    public void createVendor() {
        // given
        when(vendorRepository.save(any(Vendor.class))).thenReturn(Vendor.builder()
                .id(ID)
                .name(NAME)
                .build());

        // when
        final VendorDTO savedVendorDTO = vendorService.createVendor(baseVendorDTO);

        // then
        assertNotNull(savedVendorDTO);
        assertEquals(NAME, savedVendorDTO.getName());
        assertEquals(VENDOR_URL, savedVendorDTO.getVendor_url());

    }

    @Test
    public void updateVendor() {
        // given
        when(vendorRepository.save(any(Vendor.class))).thenReturn(Vendor.builder()
                .id(ID)
                .name(NAME + SA)
                .build());

        // when
        final VendorDTO updatedVendorDTO = vendorService.updateVendor(1L, VendorDTO.builder()
                .name(NAME + SA)
                .build());
        // then
        assertNotNull(updatedVendorDTO);
        assertEquals(NAME + SA, updatedVendorDTO.getName());
        assertEquals(VENDOR_URL, updatedVendorDTO.getVendor_url());
    }

    @Test
    public void deleteVendorById() {
        // when
        vendorService.deleteVendorById(1L);

        // then
        verify(vendorRepository).deleteById(anyLong());

    }
}
