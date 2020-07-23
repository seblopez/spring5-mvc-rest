package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.text.MessageFormat;

@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(source = "id", target = "vendor_url", qualifiedByName = "idToVendorsUrl")
    VendorDTO vendorToVendorDto(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDTO vendorDTO);

    @Named("idToVendorsUrl")
    default String idToVendorsUrl(Long id) {
        return MessageFormat.format("/api/v1/vendors/{0}", id.toString());
    }

}
