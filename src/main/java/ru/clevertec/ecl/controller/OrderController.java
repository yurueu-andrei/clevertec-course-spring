package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * Endpoint, which finds the Order by id given in the URL and return it as OrderDto
     *
     * @param id ID of target entity
     * @return returns <b>OrderDto</b> made out of found Order
     * @see OrderDto
     */
    @GetMapping("/{id}")
    public OrderDto findById(
            @PathVariable Long id
    ) throws ServiceException {
        return orderService.findById(id);
    }

    /**
     * Endpoint, which finds all Orders and return it as OrderDtos
     *
     * @param pageNumber used for pagination, defines the number of the page
     * @param pageSize   used for pagination, defines the number of tags on the page
     * @return returns <b>OrderDto</b> made out of found Orders
     * @see OrderDto
     */
    @GetMapping
    public List<OrderDto> findAll(
            @RequestParam int pageSize,
            @RequestParam int pageNumber
    ) throws ServiceException {
        return orderService.findAll(pageNumber, pageSize);
    }
}
