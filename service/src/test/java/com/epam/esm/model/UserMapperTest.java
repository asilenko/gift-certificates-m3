package com.epam.esm.model;

import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void userBusinessModelModelMappedFromUserShouldHaveProperFieldsValues() {
        //GIVEN
        DataProvider dataProvider = new DataProvider();
        UserMapper userMapper = new UserMapper();
        UserBusinessModel expected = dataProvider.createUserBusinessModel();
        User userToBeMapped = dataProvider.createUser();
        //WHEN
        UserBusinessModel actual = userMapper.toUserBusinessModel(userToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }
}
