package com.MyFood.services.implemenntation;

import com.MyFood.dto.OrderRestaurantDto;
import com.MyFood.model.ItensCardapio;
import com.MyFood.repository.ItensCardapioRepository;
import com.MyFood.services.ItensCardapioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItensCardapioServiceImplementation implements ItensCardapioService {

    private final ItensCardapioRepository repository;

    public ItensCardapioServiceImplementation(ItensCardapioRepository repository) {
        this.repository = repository;
    }

    private OrderRestaurantDto transformListModelsInListDto(ItensCardapio itensCardapio) {
        return new OrderRestaurantDto(itensCardapio.getRestaurant().getName(),itensCardapio.getName(), itensCardapio.getValue());
    }

    private List<ItensCardapio> getListOfProductsModel(List<String> productsName) {
        List<ItensCardapio> list = new ArrayList<>();
        for(int i =0; i < productsName.size(); i++){
            list.add(repository.findByName(productsName.get(i)));
        }
        return list;
    }

    @Override
    public List<OrderRestaurantDto> getListOfProducts(List<String> productsName) {
        List<ItensCardapio> listItensModel = getListOfProductsModel(productsName);
        return listItensModel.stream().map(this::transformListModelsInListDto).toList();
    }
}
