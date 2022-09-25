package com.epam.esm.model;

import com.epam.esm.domain.User;
import org.springframework.stereotype.Component;

/**
 * Maps User from persistence layer to UserBusinessModel and vice versa.
 *
 * @see com.epam.esm.domain.User
 * @see UserBusinessModel
 */
@Component
public class UserMapper {

    /**
     * Maps BusinessModel to EntityModel.
     *
     * @param user
     * @return UserBusinessModel
     * @see User
     * @see UserBusinessModel
     */
    public UserBusinessModel toUserBusinessModel(User user) {
        UserBusinessModel userBusinessModel = new UserBusinessModel();
        userBusinessModel.setId(user.getId());
        userBusinessModel.setName(user.getName());
        return userBusinessModel;
    }
}
