package com.MyFood.service.implementation;

import com.MyFood.dto.ItemDto;
import com.MyFood.model.ItensModel;
import com.MyFood.repository.ItensRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItensServiceImplementationTest {

    @Mock
    private ItensRepository repository;
    @InjectMocks
    private ItensServiceImplementation service;

    private ItensModel item = new ItensModel(UUID.randomUUID(), "X-burguer", 25.00, 1);
    private ItemDto itemDto = new ItemDto("X-burguer", 25.00, 1);

    @Test
    @DisplayName("Cria um novo item")
    void shouldBeCreateNewItem(){
        given(repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity())).willReturn(Optional.empty());
        given(repository.save(any(ItensModel.class))).willReturn(item);

        service.createNewItem(itemDto);

        verify(repository,times(1)).findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
        verify(repository,times(1)).save(any(ItensModel.class));
    }

    @Test
    @DisplayName("Retorna um item existente")
    void shouldBeReturnExistItem(){
        given(repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity())).willReturn(Optional.of(item));

        service.createNewItem(itemDto);

        verify(repository,times(1)).findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
        verify(repository,times(0)).save(any(ItensModel.class));
    }

    @Test
    @DisplayName("Deleta um item existente")
    void shouldBeDeleteItem(){
        given(repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity())).willReturn(Optional.of(item));

        String response = service.deleteItem(itemDto);

        assertEquals("Item  deletado com sucesso", response);
        verify(repository,times(1)).findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
        verify(repository,times(1)).delete(item);
    }

    @Test
    @DisplayName("Tenta deletar um item mas nao o encontra")
    void shouldBeTryAndFailToDeleteItem(){
        given(repository.findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity())).willReturn(Optional.empty());

        String response = service.deleteItem(itemDto);

        assertEquals("Item nao encontrado na base de dados, tente deletar novamente.", response);
        verify(repository,times(1)).findByNameAndValueAndQuantity(itemDto.name(), itemDto.price(), itemDto.quantity());
    }
}