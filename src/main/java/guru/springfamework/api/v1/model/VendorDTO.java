package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Vendor name")
    String name;

    @ApiModelProperty(value = "Vendor URL")
    @JsonProperty
    String vendor_url;

}
