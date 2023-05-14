package ru.clevertec.ecl.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.UserListDto;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.utils.TestUtils;

import java.util.List;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void toDtoTest_shouldTransferUserObjectToUserDto() {
        //given
        UserDto expected = TestUtils.findUserDtoForCertificateMapperTest();
        User user = TestUtils.findUserForCertificateMapperTest();

        //when
        UserDto actual = userMapper.toDto(user);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toListDtoTest_shouldTransferListOfUserObjectsToListOfUserListDto() {
        //given
        List<UserListDto> expected = TestUtils.findListOfUserListDtoForCertificateMapperTest();
        List<User> users = TestUtils.findUsersForCertificateMapperTest();

        //when
        List<UserListDto> actual = userMapper.toDto(users);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromSaveDtoTest_shouldTransferUserSaveDtoToUserObject() {
        //given
        User expected = TestUtils.findUserForCertificateMapperFromSaveDtoTest();
        UserSaveDto userSaveDto = TestUtils.findUserSaveDtoForCertificateMapperFromSaveDtoTest();

        //when
        User actual = userMapper.fromSaveDto(userSaveDto);

        //then
        Assertions.assertEquals(expected, actual);
    }
}