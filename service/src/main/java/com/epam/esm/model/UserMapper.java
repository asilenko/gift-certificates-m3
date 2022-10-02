package com.epam.esm.model;

import com.epam.esm.domain.User;
import org.springframework.stereotype.Component;

/**
 * Maps User from persistence layer to UserModel and vice versa.
 *
 * @see com.epam.esm.domain.User
 * @see UserModel
 */
@Component
public class UserMapper {

    /**
     * Maps BusinessModel to EntityModel.
     *
     * @param user
     * @return UserModel
     * @see User
     * @see UserModel
     */
    public UserModel toUserBusinessModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        return userModel;
    }
}
