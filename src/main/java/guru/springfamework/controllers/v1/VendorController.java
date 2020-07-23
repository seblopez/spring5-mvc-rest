package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    final VendorService vendorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAll() {
        return new VendorListDTO(vendorService.getVendors());
    }

    @GetMapping("/{id}")
    public VendorDTO getById(@PathVariable String id) {
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createCustomer(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(Long.valueOf(id), vendorDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchCustomer(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(Long.valueOf(id), vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }

}
