package ru.clevertec.ecl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Spy
    private OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @InjectMocks
    private OrderService orderService;

    @Nested
    class FindById {

        @Test
        void findByIdTest_shouldReturnOrderDtoWithId3() {
            //given
            Long id = 3L;
            Order order = TestUtils.getOrder(3);
            OrderDto expected = TestUtils.findOrderDto();

            //when
            when(orderRepository.findById(id)).thenReturn(Optional.of(order));
            OrderDto actual = orderService.findById(id);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findByIdTest_shouldThrowServiceExceptionForNonExistentOrder() {
            //when
            when(orderRepository.findById(55L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> orderService.findById(55L));
        }
    }

    @Test
    void findAllTest_shouldReturnAllOrdersWithIdFrom1To3() {
        //given
        Pageable pageable = PageRequest.of(0, 3);
        List<Order> orders = TestUtils.findOrders();
        List<OrderDto> expected = TestUtils.findOrderDtos();

        //when
        when(orderRepository.findAllOrders(pageable)).thenReturn(new PageImpl<>(orders));
        List<OrderDto> actual = orderService.findAll(pageable);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByUserIdTest_shouldReturnAllOrdersOfUserWithId3() {
        //given
        Long id = 3L;
        Pageable pageable = PageRequest.of(0, 3);
        List<Order> orders = TestUtils.findOrders();
        List<OrderDto> expected = TestUtils.findOrderDtos();

        //when
        when(orderRepository.findByUserId(id, pageable)).thenReturn(orders);
        List<OrderDto> actual = orderService.findAllByUserId(id, pageable);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    class FindByIdAndUserId {

        @Test
        void findByIdAndUserIdTest_shouldReturnOrderWhichBelongsToUser() {
            //given
            Long orderId = 3L;
            Long userId = 2L;
            OrderListDto expected = TestUtils.findOrderListDto();

            //when
            when(orderRepository.findById(orderId)).thenReturn(Optional.of(TestUtils.getOrder(3)));
            OrderListDto actual = orderService.findByIdAndUserId(userId, orderId);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findByIdAndUserIdTest_shouldThrowServiceExceptionForNonExistentOrder() {
            //given
            Long orderId = 55L;
            Long userId = 2L;

            //when
            when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> orderService.findByIdAndUserId(userId, orderId));
        }

        @Test
        void findByIdAndUserIdTest_shouldThrowServiceExceptionForOrderWhichDoesNotBelongToGivenUser() {
            //given
            Long orderId = 3L;
            Long userId = 3L;

            //when
            when(orderRepository.findById(orderId)).thenReturn(Optional.of(TestUtils.getOrder(3)));

            //then
            Assertions.assertThrows(ServiceException.class, () -> orderService.findByIdAndUserId(userId, orderId));
        }
    }
}