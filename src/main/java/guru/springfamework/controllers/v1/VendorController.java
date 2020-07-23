package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Vendor Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    final VendorService vendorService;

    @ApiOperation(value = "Get all vendors", response = VendorListDTO.class, responseContainer = "Json")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAll() {
        return new VendorListDTO(vendorService.getVendors());
    }

    @ApiOperation(value = "Get vendor by its id", response = VendorDTO.class)
    @GetMapping("/{id}")
    public VendorDTO getById(@PathVariable String id) {
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @ApiOperation(value = "Creates a new vendor", response = VendorDTO.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    @ApiOperation(value = "Updates an existing vendor and if it doesn't exist, creates a new one", response = VendorDTO.class)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(Long.valueOf(id), vendorDTO);
    }

    @ApiOperation(value = "Updates an existing vendor", response = VendorDTO.class)
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchCustomer(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(Long.valueOf(id), vendorDTO);
    }

    @ApiOperation(value = "Deletes a vendor by its id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }

}
