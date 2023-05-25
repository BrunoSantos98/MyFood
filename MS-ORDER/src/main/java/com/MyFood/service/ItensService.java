package com.MyFood.service;

import com.MyFood.dto.ItemDto;

public interface ItensService {
    ItemDto createNewItem(ItemDto itemDto);

    String deleteItem(ItemDto itemDto);
}
