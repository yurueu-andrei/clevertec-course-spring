package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderDto> findById(
            @PathVariable Long id
    ) throws ServiceException {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
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
    public ResponseEntity<List<OrderDto>> findAll(
            @RequestParam("size") int pageSize,
            @RequestParam("page") int pageNumber,
            Pageable pageable
    ) throws ServiceException {
        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
    }
}
