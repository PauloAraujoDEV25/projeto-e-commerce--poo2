package com.adacommerce.mapper;

import com.adacommerce.dto.VendaDTO;
import com.adacommerce.dto.ItemVendaDTO;
import com.adacommerce.model.Venda;
import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {
    
    public static VendaDTO toDTO(Venda venda) {
        if (venda == null) {
            return null;
        }
        
        List<ItemVendaDTO> itensDTO = venda.getItens().stream()
            .map(ItemVendaMapper::toDTO)
            .collect(Collectors.toList());
        
        return new VendaDTO(
            venda.getId(),
            venda.getDataCriacao(),
            venda.getCliente().getId(),
            venda.getCliente().getNome(),
            venda.getStatus().getDescricao(),
            venda.getValorTotal(),
            itensDTO
        );
    }
    
    public static VendaDTO toDTOSemItens(Venda venda) {
        if (venda == null) {
            return null;
        }
        
        return new VendaDTO(
            venda.getId(),
            venda.getDataCriacao(),
            venda.getCliente().getId(),
            venda.getCliente().getNome(),
            venda.getStatus().getDescricao(),
            venda.getValorTotal(),
            null
        );
    }
}
