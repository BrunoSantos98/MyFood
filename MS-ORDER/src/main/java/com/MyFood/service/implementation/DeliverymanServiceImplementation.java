package com.MyFood.service.implementation;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.dto.DeliverymanToCustomerDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.exceptions.ObjectRequiredNotFoundException;
import com.MyFood.model.Deliveryman;
import com.MyFood.repository.DeliverymanRepository;
import com.MyFood.service.DeliveryManService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeliverymanServiceImplementation implements DeliveryManService {

    private final DeliverymanRepository repository;

    public DeliverymanServiceImplementation(DeliverymanRepository repository) {
        this.repository = repository;
    }

    private boolean verifyDeliverymanNotExists(DeliverymanDto deliveryman) {
        if(repository.existsByCpf(deliveryman.cpf())){
            throw new ObjectConflictException("Cpf ja cadastrado para outro Entregador");
        } else if(repository.existsByLicensePlate(deliveryman.licensePlate())) {
            throw new ObjectConflictException("Placa de veiculo ja cadastrada para outro entregador");
        }else{
            return true;
        }
    }

    private DeliverymanDto deliverymanModelToDeliverymanDto(Deliveryman deliverymanModel) {
        return new DeliverymanDto( deliverymanModel.getName(), deliverymanModel.getCpf(),
                deliverymanModel.getPhone(), deliverymanModel.getAutomobile(), deliverymanModel.getLicensePlate(),
                deliverymanModel.getColorMobile());
    }

    @Override
    public DeliverymanDto createNewDeliveryman(DeliverymanDto deliveryman) {

        verifyDeliverymanNotExists(deliveryman);
        Deliveryman deliverymanModel = repository.save(new Deliveryman(null, deliveryman.name(),
                deliveryman.cpf(), deliveryman.phone(), deliveryman.automobile(), deliveryman.licensePlate(),
                deliveryman.colorMobile()));
        return deliverymanModelToDeliverymanDto(deliverymanModel);
    }

    @Override
    public Deliveryman findDeliverymanById(UUID id) {
        Optional<Deliveryman> deliveryman = repository.findById(id);
        if(deliveryman.isPresent()){
            return deliveryman.get();
        }else{
            throw new ObjectRequiredNotFoundException("Entregador nao encontrado através do ID informado");
        }
    }

    @Override
    public Deliveryman findDeliverymanByCpf(String cpf) {
        if(repository.existsByCpf(cpf)){
            return repository.findByCpf(cpf);
        }else{
            throw new ObjectRequiredNotFoundException("Entregador nao encontrado através do CPF informado");
        }
    }

    @Override
    public DeliverymanToCustomerDto findDeliverymanByLicensePlate(String licensePlate) {
        if(repository.existsByLicensePlate(licensePlate)){
            Deliveryman deliveryman = repository.findByLicensePlate(licensePlate);
            return new DeliverymanToCustomerDto(deliveryman.getName(), deliveryman.getPhone(),
                    deliveryman.getAutomobile(), deliveryman.getLicensePlate(), deliveryman.getColorMobile());
        }else{
            throw new ObjectRequiredNotFoundException("Entregador nao encontrado através da placa informada");
        }
    }

    @Override
    public void deleteDeliveryman(String deliverymanInformation, String typeInformation) {
        switch(typeInformation.toLowerCase()){
            case "cpf":
                repository.delete(findDeliverymanByCpf(deliverymanInformation));
                break;
            case "id":
                repository.delete(findDeliverymanById(UUID.fromString(deliverymanInformation)));
                break;
            case "license":
                repository.deleteByLicensePlate(findDeliverymanByLicensePlate(deliverymanInformation).licensePlate());
                break;
            default:
                throw new ObjectRequiredNotFoundException("Tipo de informacao invalida");
        }
    }
}
