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
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CertificateRepository certificateRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService userService;

    @Nested
    class FindById {

        @Test
        void findByIdTest_shouldReturnUserDtoWithId1() {
            //given
            Long id = 1L;
            UserDto expected = TestUtils.findUserDto();

            //when
            when(userRepository.findById(id)).thenReturn(Optional.of(TestUtils.getUser(1)));
            UserDto actual = userService.findById(1L);

            //then
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void findByIdTest_shouldThrowServiceExceptionForNonExistentUser() {
            //when
            when(userRepository.findById(7L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.findById(7L));
        }
    }

    @Test
    void findAllTest_shouldReturnAllUsersWithIdFrom1To2() {
        //given
        Pageable pageable = PageRequest.of(0, 2);
        List<UserListDto> expected = TestUtils.findListOfUserListDto();

        //when
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(TestUtils.findUsers()));
        List<UserListDto> actual = userService.findAll(pageable);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddUserAndReturnUserDto() {
        //given
        User userWithoutId = TestUtils.getUser(1).setId(null);
        User userWithId = TestUtils.getUser(1);
        UserSaveDto userSaveDto = new UserSaveDto("Andrei",
                "Yuryeu",
                20,
                "andrei.yurueu1@gmail.com",
                "andr562ei123");
        UserDto expected = TestUtils.findUserDto();

        //when
        when(userRepository.save(userWithoutId)).thenReturn(userWithId);
        UserDto actual = userService.add(userSaveDto);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    class Update {

        @Test
        void updateTest_shouldUpdateUserAndReturnTrueInCaseOfSuccessfulUpdate() {
            //given
            Long id = 3L;
            User user = TestUtils.getUser(3);
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setId(id);
            userUpdateDto.setFirstName("Hi");
            //when
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            boolean actual = userService.update(userUpdateDto);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void updateTest_shouldThrowServiceExceptionInCaseOfUnsuccessfulUpdate() {
            //given
            Long id = 3L;
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setId(id);
            userUpdateDto.setFirstName("Hi");
            //when
            when(userRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.update(userUpdateDto));
        }
    }

    @Nested
    class Delete {

        @Test
        void deleteTest_shouldReturnTrueInCaseOfSuccessfulDelete() {
            //given
            Long id = 3L;
            User user = TestUtils.getUser(3);

            //when
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            boolean actual = userService.delete(id);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void deleteTest_shouldThrowServiceExceptionInCaseOfUnsuccessfulDelete() {
            //given
            Long id = 15L;

            //when
            when(userRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.delete(id));
        }
    }

    @Nested
    class AddOrderToUser {

        @Test
        void addOrderToUserTest_shouldAddOrderAndReturnTrueInCaseOfSuccessfulAdd() {
            //given
            Long id = 2L;
            GiftCertificate certificate = TestUtils.getCertificate(10);
            User user = TestUtils.getUser(2);

            //when
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(certificateRepository.findById(10L)).thenReturn(Optional.of(certificate));
            boolean actual = userService.addOrderToUser(id, 10L);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void addOrderToUserTest_shouldThrowServiceExceptionInCaseOfNonExistentCertificate() {
            //given
            User user = TestUtils.getUser(2);

            //when
            when(userRepository.findById(2L)).thenReturn(Optional.of(user));
            when(certificateRepository.findById(11L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.addOrderToUser(2L, 11L));
        }

        @Test
        void addOrderToUserTest_shouldThrowServiceExceptionInCaseOfNonExistentUser() {
            //when
            when(userRepository.findById(7L)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.addOrderToUser(7L, 10L));
        }
    }

    @Nested
    class DeleteOrderFromUser {

        @Test
        void deleteOrderFromUserTest_shouldDeleteOrderAndReturnTrueInCaseOfSuccessfulDelete() {
            //given
            Order order = TestUtils.getOrder(2);

            //when
            when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
            boolean actual = userService.deleteOrderFromUser(2L, 2L);

            //then
            Assertions.assertTrue(actual);
        }

        @Test
        void deleteOrderFromUserTest_shouldThrowServiceExceptionForOrderWhichDoesNotBelongToGivenUser() {
            //given
            Order order = TestUtils.getOrder(3);

            //when
            when(orderRepository.findById(3L)).thenReturn(Optional.of(order));

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.deleteOrderFromUser(1L, 3L));
        }

        @Test
        void deleteOrderFromUserTest_shouldThrowServiceExceptionForOrderWhichDoesNotExist() {
            //given
            Long id = 20L;

            //when
            when(orderRepository.findById(id)).thenReturn(Optional.empty());

            //then
            Assertions.assertThrows(ServiceException.class, () -> userService.deleteOrderFromUser(1L, 20L));
        }
    }
}