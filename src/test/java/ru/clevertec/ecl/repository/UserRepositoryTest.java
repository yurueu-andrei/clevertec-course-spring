package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findIdOfUserWithTheHighestCostOfAllOrdersTest_shouldReturnId5() {
        //given
        Long expected = 5L;

        //when
        Long actual = userRepository.findIdOfUserWithTheHighestCostOfAllOrders(PageRequest.of(0, 1)).get(0);

        //then
        Assertions.assertEquals(expected, actual);
    }
}