package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toDto(List<Order> order);

    OrderListDto toListDto(Order order);
}
