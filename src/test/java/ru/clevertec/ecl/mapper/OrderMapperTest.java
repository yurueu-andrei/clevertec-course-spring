package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;

class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        this.orderMapper = Mappers.getMapper(OrderMapper.class);
    }

    @Test
    void toDtoTest_shouldTransferOrderObjectToOrderDto() {
        //given
        OrderDto expected = TestUtils.findOrderDtoForTagMapperTest();
        Order order = TestUtils.findOrderForTagMapperTest();

        //when
        OrderDto actual = orderMapper.toDto(order);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toDtoTest_shouldTransferListOfOrderObjectsToListOfOrderDto() {
        //given
        List<OrderDto> expected = TestUtils.findOrderDtosForTagMapperTest();
        List<Order> orders = TestUtils.findOrdersForTagMapperTest();

        //when
        List<OrderDto> actual = orderMapper.toDto(orders);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toListDtoTest_shouldTransferOrderObjectToOrderListDto() {
        //given
        OrderListDto expected = TestUtils.findOrderListDtoForTagMapperTest();
        Order order = TestUtils.findOrderForTagMapperTest();

        //when
        OrderListDto actual = orderMapper.toListDto(order);

        //then
        Assertions.assertEquals(expected, actual);
    }
}