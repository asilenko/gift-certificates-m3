package com.epam.esm.model;

import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void userModelModelMappedFromUserShouldHaveProperFieldsValues() {
        //GIVEN
        DataProvider dataProvider = new DataProvider();
        UserMapper userMapper = new UserMapper();
        UserModel expected = dataProvider.createUserModel();
        User userToBeMapped = dataProvider.createUser();
        //WHEN
        UserModel actual = userMapper.toUserBusinessModel(userToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }
}
