package guru.springfamework.api.v1.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerListDTO {
    List<CustomerDTO> customers;
}
