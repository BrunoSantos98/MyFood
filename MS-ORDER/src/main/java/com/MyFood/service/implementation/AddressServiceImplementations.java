package com.MyFood.service.implementation;

import com.MyFood.dto.AddressDto;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.AddressModel;
import com.MyFood.repository.AddressRepository;
import com.MyFood.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplementations implements AddressService {

    private final AddressRepository repository;

    public AddressServiceImplementations(AddressRepository repository) {
        this.repository = repository;
    }

    private AddressModel findAddressByInformations(AddressDto addressDto) {
        return repository.findByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
    }

    private boolean existsAddressWithoutException(AddressDto addressDto) {
        return repository.existsByCepAndLogradouroAndNumber(addressDto.cep(), addressDto.logradouro(), addressDto.number());
    }

    private AddressDto addressModelToAddressDto(AddressModel addressModel) {
        return new AddressDto(addressModel.getCep(), addressModel.getLogradouro(), addressModel.getNumber(),
                addressModel.getComplemento(), addressModel.getBairro(), addressModel.getUf());
    }

    private AddressModel existsAddressOrThrowsException(String cep, String logradouro, short number) {
        if(existsAddressWithoutException(new AddressDto(cep, logradouro, number, null, null, null))){
            return findAddressByInformations(new AddressDto(cep, logradouro, number, null, null, null));
        }else{
            throw new ObjectRequiredNotFoundException("Endereço não encontrado com base nas informações solicitadas.");
        }
    }

    @Override
    public AddressDto getNewAddress(AddressDto addressDto) {
        if(existsAddressWithoutException(addressDto)){
            return addressDto;
        }else{
            AddressModel addressModel = repository.save(
                    new AddressModel(null, addressDto.cep(), addressDto.logradouro(), addressDto.number(),
                            addressDto.complemento(), addressDto.bairro(), addressDto.uf()));
            return addressModelToAddressDto(addressModel);
        }
    }

    @Override
    public AddressDto getAddressByInformations(String cep, String logradouro, short number) {
        return addressModelToAddressDto(existsAddressOrThrowsException(cep, logradouro, number));
    }

    @Override
    public AddressModel getAddressModelByInformations(String cep, String logradouro, short number) {
        return existsAddressOrThrowsException(cep,logradouro,number);
    }

    @Override
    public void deleteAddress(AddressDto addressDto) {
        AddressModel address = existsAddressOrThrowsException(addressDto.cep(), addressDto.logradouro(), addressDto.number());
        repository.delete(address);
    }
}
