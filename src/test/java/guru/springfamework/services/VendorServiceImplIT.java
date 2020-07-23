package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
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
public class VendorServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);

    }

    @Test
    public void patchVendor() {
        Vendor vendorToUpdate = getVendor("La Rosadita");
        vendorToUpdate.setName("La Rosada");

        final VendorDTO patchedVendorDTO = vendorService.patchVendor(vendorToUpdate.getId(), VendorDTO.builder()
                .name(vendorToUpdate.getName())
                .build());

        assertNotNull(patchedVendorDTO);
        assertEquals("La Rosada", patchedVendorDTO.getName());
        assertThat("La Rosadita", not(equalTo(patchedVendorDTO.getName())));

    }

    private Vendor getVendor(String name) {
        return vendorRepository.findAll()
                .stream()
                .filter(vendor -> vendor.getName().equals(name))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }
}
