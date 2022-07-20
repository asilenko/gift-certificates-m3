package com.epam.esm.dao.jdbc;

import com.epam.esm.domain.Tag;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJDBCConfig.class}, loader = AnnotationConfigContextLoader.class)
class JdbcTagDAOIT {
    @Autowired
    private JdbcTagDAO jdbcTagDAO;
    @Autowired
    private Tag tag;

    @Test
    @Order(2)
    void findByIdShouldReturnTagIIfTagExist() {
        //GIVEN

        //WHEN
        Optional<Tag> tagFromDB = jdbcTagDAO.findById(tag.getId());

        //THEN
        assertEquals(tag, tagFromDB.get());
    }

    @Test
    @Order(3)
    void findByIdShouldReturnEmptyOptionalIfNoTagWithSuchId() {
        //GIVEN

        //WHEN
        Optional<Tag> tagFromDB = jdbcTagDAO.findById(-1L);

        //THEN
        assertFalse(tagFromDB.isPresent());
    }

    @Test
    @Order(1)
    void tagIsPresentInDBAfterBeingCreated() {
        //GIVEN
        tag.setName("Tag to insert");

        //WHEN
        var insertedTag = jdbcTagDAO.create(tag);
        var retrievedTag = jdbcTagDAO.findById(insertedTag.getId()).orElseThrow();

        //THEN
        assertEquals(tag, retrievedTag);
    }

    @Test
    @Order(4)
    void tagIsNotPresentInDBAfterBeingDeleted() {
        //GIVEN

        //WHEN
        boolean isTagPresentBeforeDeleting = jdbcTagDAO.findById(tag.getId()).isPresent();
        jdbcTagDAO.delete(tag.getId());

        //THEN
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(isTagPresentBeforeDeleting).isTrue();
        sa.assertThat(jdbcTagDAO.findById(tag.getId()).isPresent()).isFalse();
        sa.assertAll();
    }

    @Test
    @Order(5)
    void findAllShouldReturnAllTagsPresentInDB(){
        //GIVEN
        Tag firstTagToAdd = createTag("first tag");
        Tag secondTagToAdd = createTag("second tag");

        jdbcTagDAO.create(firstTagToAdd);
        jdbcTagDAO.create(secondTagToAdd);

        Set<Tag> expected = Set.of(firstTagToAdd, secondTagToAdd);
        //WHEN
        Set<Tag> actual = jdbcTagDAO.findAll();
        //THEN
        assertEquals(expected,actual);
    }

    private Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        return tag;
    }
}
