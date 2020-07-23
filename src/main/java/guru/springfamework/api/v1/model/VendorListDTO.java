package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VendorListDTO {

    @ApiModelProperty(value = "List of vendors", required = true)
    List<VendorDTO> vendors;
}
