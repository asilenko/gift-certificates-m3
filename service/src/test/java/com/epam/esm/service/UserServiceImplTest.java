package com.epam.esm.service;

import com.epam.esm.dao.jpa.JPAUserDAO;
import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Long ID = 1L;
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer PAGE_SIZE = 10;
    private final DataProvider dataProvider = new DataProvider();
    private final User user = dataProvider.createUser();
    private final UserModel userModel = dataProvider.createUserModel();

    @Mock
    private JPAUserDAO jpaUserDAO;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnProperUserBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(jpaUserDAO.findById(ID)).thenReturn(Optional.of(user));
        when(userMapper.toUserBusinessModel(user)).thenReturn(userModel);
        UserModel expected = userModel;
        //WHEN
        var actual = userService.find(ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnUsersListWhenResourceIsFound() {
        //GIVEN
        List<UserModel> expected = List.of(userModel);
        List<User> users = List.of(user);
        //WHEN
        when(jpaUserDAO.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(users);
        when(userMapper.toUserBusinessModel(user)).thenReturn(userModel);
        List <UserModel> actual = userService.findAll(PAGE_NUMBER,PAGE_SIZE).getContent();
        //THEN
        assertEquals(expected, actual);
    }
}
