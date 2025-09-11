package com.adacommerce.mapper;

import com.adacommerce.dto.ItemVendaDTO;
import com.adacommerce.dto.ItemVendaRequestDTO;
import com.adacommerce.model.ItemVenda;
import com.adacommerce.model.Produto;

public class ItemVendaMapper {
    
    public static ItemVendaDTO toDTO(ItemVenda itemVenda) {
        if (itemVenda == null) {
            return null;
        }
        
        return new ItemVendaDTO(
            itemVenda.getId(),
            itemVenda.getProduto().getId(),
            itemVenda.getProduto().getNome(),
            itemVenda.getQuantidade(),
            itemVenda.getValorVenda(),
            itemVenda.getValorTotal()
        );
    }
    
    public static ItemVenda toEntity(ItemVendaRequestDTO itemRequestDTO, Produto produto) {
        if (itemRequestDTO == null || produto == null) {
            return null;
        }
        
        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(itemRequestDTO.getQuantidade());
        item.setValorVenda(itemRequestDTO.getValorVenda());
        
        return item;
    }
    
    public static void updateEntity(ItemVenda item, ItemVendaRequestDTO itemRequestDTO) {
        if (item == null || itemRequestDTO == null) {
            return;
        }
        
        item.setQuantidade(itemRequestDTO.getQuantidade());
        item.setValorVenda(itemRequestDTO.getValorVenda());
    }
}
