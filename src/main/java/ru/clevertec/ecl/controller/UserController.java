package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderListDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.UserListDto;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.dto.UserUpdateDto;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

/**
 * Controller for users with <b>CRUD</b> operations
 *
 * @author Yuryeu Andrei
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    /**
     * Endpoint, which finds the User by id given in the URL and return it as UserDto
     *
     * @param id ID of target entity
     * @return returns <b>UserDto</b> made out of found User
     * @see UserDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable Long id
    ) throws ServiceException {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint, which finds all Users and return it as UserListDtos
     *
     * @param pageable used for pagination, defines the number of tags on the page and the number of page
     * @return returns <b>UserListDtos</b> made out of found Users
     * @see UserListDto
     */
    @GetMapping
    public ResponseEntity<List<UserListDto>> findAll(
            Pageable pageable
    ) throws ServiceException {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Endpoint, which gets UserSaveDto and creates User
     *
     * @param userSaveDto contains all the fields of User except id
     * @return returns created entity as <b>UserDto</b>
     * @see UserSaveDto
     */
    @PostMapping
    public ResponseEntity<UserDto> add(
            @RequestBody UserSaveDto userSaveDto
    ) throws ServiceException {
        return new ResponseEntity<>(userService.add(userSaveDto), HttpStatus.OK);
    }

    /**
     * Endpoint, which updates existing User
     *
     * @param userUpdateDto contains id and the fields to be updated
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     * @see UserUpdateDto
     */
    @PutMapping
    public boolean update(
            @RequestBody UserUpdateDto userUpdateDto
    ) throws ServiceException {
        return userService.update(userUpdateDto);
    }

    /**
     * Endpoint, which deletes existing User
     *
     * @param id id of the entity to be deleted
     * @return returns <b>boolean</b> value (only true in case of successful operation)
     * @throws ServiceException in case of failure
     */
    @DeleteMapping("/{id}")
    public boolean delete(
            @PathVariable Long id
    ) throws ServiceException {
        return userService.delete(id);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> findUserOrders(
            @PathVariable Long id,
            Pageable pageable
    ) throws ServiceException {
        return new ResponseEntity<>(orderService.findAllByUserId(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<OrderListDto> findUserOrder(
            @PathVariable Long id,
            @PathVariable Long orderId
    ) throws ServiceException {
        return new ResponseEntity<>(orderService.findByIdAndUserId(id, orderId), HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    public boolean addOrderToUser(
            @PathVariable Long id,
            @RequestParam Long certificateId
    ) throws ServiceException {
        return userService.addOrderToUser(id, certificateId);
    }

    @DeleteMapping("/{id}/orders/{orderId}")
    public boolean deleteOrderFromUser(
            @PathVariable Long id,
            @PathVariable Long orderId
    ) throws ServiceException {
        return userService.deleteOrderFromUser(id, orderId);
    }
}
