package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;

import java.util.List;

/**
 * Service class for users with <b>CRUD</b> operations and mapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDto findById(Long id) throws ServiceException {
        return orderRepository.findById(id).map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException("Order with id = " + id + " was not found"));
    }

    public List<OrderDto> findAll(Pageable pageable) throws ServiceException {
        try {
            List<Order> list = orderRepository.findAllOrders(pageable).toList();
            return orderMapper.toDto(list);
        } catch (Exception ex) {
            throw new ServiceException("Orders were not found");
        }
    }


    public List<OrderDto> findAllByUserId(Long userId, Pageable pageable) throws ServiceException {
        try {
            List<Order> list = orderRepository.findByUserId(userId, pageable);
            return orderMapper.toDto(list);
        } catch (Exception ex) {
            throw new ServiceException("Orders were not found");
        }
    }

    public OrderListDto findByIdAndUserId(Long userId, Long orderId) throws ServiceException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ServiceException("Order with id = " + orderId + " was not found"));
        if (order.getUser().getId().equals(userId)) {
            return orderMapper.toListDto(order);
        } else {
            throw new ServiceException(String.format(
                    "Order with id = %d does not belong to user with id = %d", orderId, userId));
        }
    }
}
