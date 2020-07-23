package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadVendors() {
        final List<Vendor> vendors = Arrays.asList(
                Vendor.builder()
                        .name("La Covacha")
                        .build(),
                Vendor.builder()
                        .name("La Rosadita")
                        .build(),
                Vendor.builder()
                        .name("Austral Construcciones")
                        .build(),
                Vendor.builder()
                        .name("Electroingenieria")
                        .build(),
                Vendor.builder()
                        .name("Hotel Los Sauces")
                        .build()
        );

        vendorRepository.saveAll(vendors);

    }

    private void loadCustomers() {
        final List<Customer> customers = Arrays.asList(
                Customer.builder()
                        .firstname("Samwise")
                        .lastname("Gamyi")
                        .build(),
                Customer.builder()
                        .firstname("Frodo")
                        .lastname("Baggins")
                        .build(),
                Customer.builder()
                        .firstname("Bilbo")
                        .lastname("Baggins")
                        .build(),
                Customer.builder()
                        .firstname("Gandalf")
                        .lastname("The Gray")
                        .build(),
                Customer.builder()
                        .firstname("Meriadoc")
                        .lastname("Frod")
                        .build(),
                Customer.builder()
                        .firstname("Peregrin")
                        .lastname("Tuk")
                        .build(),
                Customer.builder()
                        .firstname("Aragorn")
                        .lastname("Ranger")
                        .build()
        );

        customerRepository.saveAll(customers);

    }

    private void loadCategories() {
        final List<Category> categories = Arrays.asList(
                Category.builder().name("Fruits").build(),
                Category.builder().name("Dried").build(),
                Category.builder().name("Fresh").build(),
                Category.builder().name("Exotic").build(),
                Category.builder().name("Nuts").build());

        categoryRepository.saveAll(categories);

    }
}
