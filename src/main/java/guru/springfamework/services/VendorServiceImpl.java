package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
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
public class VendorServiceImpl implements VendorService {

    final VendorRepository vendorRepository;
    final VendorMapper vendorMapper;

    @Override
    public List<VendorDTO> getVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDto)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDto)
                .orElseThrow(() -> {
                    final String errorMessage = MessageFormat.format("Customer id {0} not found", id);
                    log.error(errorMessage);
                    return new ResourceNotFoundException(errorMessage);
                });
    }

    @Override
    @Transactional
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        return saveAndReturnVendor(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    private VendorDTO saveAndReturnVendor(Vendor vendor) {
        final Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDto(savedVendor);
    }

    @Override
    @Transactional
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        final Vendor vendorToUpdate = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendorToUpdate.setId(id);
        return saveAndReturnVendor(vendorToUpdate);
    }

    @Override
    @Transactional
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    return saveAndReturnVendor(vendor);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
