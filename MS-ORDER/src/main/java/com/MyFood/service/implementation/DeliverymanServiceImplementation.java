package com.MyFood.service.implementation;

import com.MyFood.dto.DeliverymanDto;
import com.MyFood.exceptions.ObjectConflictException;
import com.MyFood.model.Deliveryman;
import com.MyFood.repository.DeliverymanRepository;
import com.MyFood.service.DeliveryManService;
import org.springframework.web.server.ServerErrorException;

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
}
