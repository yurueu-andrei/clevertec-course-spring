package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.UserListDto;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.dto.UserUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ServiceException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.CertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;

import java.util.List;

/**
 * Service class for users with <b>CRUD</b> operations and mapping into <b>DTO</b>.
 *
 * @author Yuryeu Andrei
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) throws ServiceException {
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException("User with id = " + id + " was not found"));
    }

    public List<UserListDto> findAll(Pageable pageable) throws ServiceException {
        try {
            List<User> list = userRepository.findAll(pageable).toList();
            return userMapper.toDto(list);
        } catch (Exception ex) {
            throw new ServiceException("Users were not found");
        }
    }

    @Transactional
    public UserDto add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            return userMapper.toDto(userRepository.save(user));
        } catch (Exception ex) {
            throw new ServiceException("User was not added");
        }
    }

    @Transactional
    public boolean update(UserUpdateDto userUpdateDto) throws ServiceException {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The user was not updated. The user was not found. id = %d",
                                        userUpdateDto.getId())));
        try {
            settingUpdatedFields(user, userUpdateDto);
            userRepository.flush();
            return true;
        } catch (Exception ex) {
            throw new ServiceException("User with id = " + userUpdateDto.getId() + " was not updated");
        }
    }

    private void settingUpdatedFields(
            User user,
            UserUpdateDto userUpdateDto
    ) {
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }

        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }

        if (userUpdateDto.getAge() != null) {
            user.setAge(userUpdateDto.getAge());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getPassword() != null) {
            user.setPassword(userUpdateDto.getPassword());
        }
    }

    @Transactional
    public boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The user was not deleted. The user was not found. id = %d", id)));
        try {
            userRepository.delete(user);
            return true;
        } catch (Exception ex) {
            throw new ServiceException("User with id = " + id + " was not deleted");
        }
    }

    @Transactional
    public boolean addOrderToUser(Long userId, Long certificateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The order was not added. The user was not found. id = %d", userId)));

        GiftCertificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The order was not added. The certificate was not found. id = %d", userId)));
        Order order = new Order()
                .setUser(user)
                .setCertificate(certificate);
        orderRepository.save(order);
        return true;
    }

    @Transactional
    public boolean deleteOrderFromUser(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ServiceException(
                                String.format(
                                        "The order was not deleted. The order was not found. id = %d", orderId)));
        if (order.getUser().getId().equals(userId)) {
            orderRepository.delete(order);
        } else {
            throw new ServiceException(String.format("The order with id = %d was not deleted from user with id = %d. " +
                    "Order does not belong to this user", orderId, userId));
        }
        return true;
    }
}
