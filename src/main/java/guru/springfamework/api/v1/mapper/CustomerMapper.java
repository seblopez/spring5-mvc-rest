package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.MessageFormat;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mappings(
            {@Mapping(source = "id", target = "ordersUrl", qualifiedByName = "idToOrdersUrl"),
             @Mapping(source = "id", target = "customerUrl", qualifiedByName = "idToCustomerUrl")}
            )
    CustomerDTO customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    @Named("idToOrdersUrl")
    default String idToOrdersUrl(Long id) {
        return MessageFormat.format("/shop/customers/{0}/orders/", id.toString());
    }

    @Named("idToCustomerUrl")
    default String idToCustomerUrl(Long id)  {
        return MessageFormat.format("/api/v1/customers/{0}", id.toString());
    }
}
