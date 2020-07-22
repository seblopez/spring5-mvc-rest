package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;
    final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        final Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            final String errorMessage = MessageFormat.format("Customer id {0} not found", id);
            log.error(errorMessage);
            return new NotFoundException(errorMessage);
        });
        return customerMapper.customerToCustomerDto(customer);
    }

}
