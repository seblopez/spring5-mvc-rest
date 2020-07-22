package guru.springfamework.api.v1.model;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CustomerDTO implements Serializable {
    final static long serialVersionUID = 9121676912155893170L;
    String firstname;
    String lastname;
    String ordersUrl;
    String customerUrl;

    @Builder.Default
    Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
