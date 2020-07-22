package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return customerRepository
                .findById(id)
                .map(customerMapper::customerToCustomerDto)
                .orElseThrow(() -> {
                    final String errorMessage = MessageFormat.format("Customer id {0} not found", id);
                    log.error(errorMessage);
                    return new NotFoundException(errorMessage);
                });
    }

    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return saveAndReturnCustomerDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        final Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnCustomerDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if(customerDTO.getFirstname() != null) {
                        customer.setFirstname(customerDTO.getFirstname());
                    }
                    if(customerDTO.getLastname() != null) {
                        customer.setLastname(customerDTO.getLastname());
                    }
                    return customerMapper.customerToCustomerDto(customerRepository.save(customer));
                }).orElseThrow(RuntimeException::new);
    }

    private CustomerDTO saveAndReturnCustomerDTO(Customer customer) {
        final Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

}
