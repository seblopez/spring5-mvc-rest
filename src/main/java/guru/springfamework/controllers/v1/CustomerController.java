package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAll() {
        return new CustomerListDTO(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getById(@PathVariable String id) {
        return customerService.getCustomerById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(Long.valueOf(id), customerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(Long.valueOf(id), customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Long.valueOf(id));
    }

}
