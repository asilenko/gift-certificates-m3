package com.epam.esm.service;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserModel;
import com.epam.esm.model.UserMapper;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserModel find(Long id) throws ResourceNotFoundException {
        return userMapper.toUserBusinessModel(userDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserModel> findAll(Integer pageNumber, Integer pageSize) {
        var total = userDAO.getTotal();
        var orders = userDAO.findAll(pageNumber, pageSize)
                .stream()
                .map(userMapper::toUserBusinessModel)
                .collect(Collectors.toList());
        return new Page<>(pageNumber, pageSize, total, orders);
    }
}
