package com.MyFood.service.implementation;

import com.MyFood.dto.ItemDto;
import com.MyFood.model.ItensModel;
import com.MyFood.repository.ItensRepository;
import com.MyFood.service.ItensService;

import java.util.Optional;

public class ItensServiceImplementation implements ItensService {

    private final ItensRepository repository;

    public ItensServiceImplementation(ItensRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemDto createNewItem(ItemDto itemDto) {
        Optional<ItensModel> findItem =
                repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
        if(findItem.isPresent()){
            return new ItemDto(findItem.get().getName(), findItem.get().getValue(), findItem.get().getQuantity());
        }else{
            ItensModel itensModel =
                    repository.save(new ItensModel(null, itemDto.name(), itemDto.price(), itemDto.quantity()));
            return new ItemDto(itensModel.getName(), itensModel.getValue(), itensModel.getQuantity());
        }
    }

    @Override
    public String deleteItem(ItemDto itemDto) {
        Optional<ItensModel> findItem =
                repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
        if(findItem.isPresent()){
            repository.delete(findItem.get());
            return "Item  deletado com sucesso";
        }else{
            return "Item nao encontrado na base de dados, tente deletar novamente.";
        }
    }
}
