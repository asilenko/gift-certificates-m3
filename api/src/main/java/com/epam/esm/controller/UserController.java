package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.UserLinker;
import com.epam.esm.model.UserBusinessModel;
import com.epam.esm.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Process requests for users resources.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserLinker userLinker;

    public UserController(UserService userService, UserLinker userLinker) {
        this.userService = userService;
        this.userLinker = userLinker;
    }

    /**
     * Get User resource by specified id.
     *
     * @param id of user
     * @return UserBusinessModel
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserBusinessModel> getById(@PathVariable Long id) throws ResourceNotFoundException {
        var user = userService.find(id);
        userLinker.addLink(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Gets all users.
     *
     * @return List of UserBusinessModel
     *
     * Request example:
     * <pre>
     * GET /users/?pageNumber=1&pageSize=3 HTTP/1.1
     * </pre>
     */
    @GetMapping
    public ResponseEntity<CollectionModel<UserBusinessModel>> getAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        var page = userService.findAll(pageNumber, pageSize);
        CollectionModel<UserBusinessModel> collectionModel = userLinker.addLinks(page);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
}
