package guru.springfamework.api.v1.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendorDTO implements Serializable {
    final static long serialVersionUID = -7352635434565893933L;

    String name;
    String vendor_url;

}
