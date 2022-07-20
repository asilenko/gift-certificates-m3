package com.epam.esm.dao.jdbc;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {com.epam.esm.config.SpringJDBCConfig.class}, loader = AnnotationConfigContextLoader.class)
class JdbcGiftCertificateDAOIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GiftCertificateDAO giftCertificateDAO;
    @Autowired
    GiftCertificate giftCertificate;

    @Test
    void findByIdShouldReturnGiftCertificateIIfCertificateExist() throws ResourceNotFoundException {
        //GIVEN
        setGiftCertificateValues(giftCertificate, "1");
        //WHEN
        GiftCertificate insertedGC = giftCertificateDAO.create(giftCertificate);
        GiftCertificate retrievedGC = giftCertificateDAO.findById(insertedGC.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No resource with id " + insertedGC.getId()));
        //THEN
        assertEquals(giftCertificate, retrievedGC);
    }

    @Test
    void findAll() {
        //GIVEN
        GiftCertificate firstCertificate = createCertificate("first");
        GiftCertificate secondCertificate = createCertificate("second");

        giftCertificateDAO.create(firstCertificate);
        giftCertificateDAO.create(secondCertificate);

        List<GiftCertificate> expected = new ArrayList<>(ImmutableList.of(firstCertificate, secondCertificate));
        //WHEN
        var actual = giftCertificateDAO.findAll();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void giftCertificateIsPresentInDBAfterBeingCreated() {
        //GIVEN
        GiftCertificate giftCertificateToBeCreated = createCertificate("2");
        //WHEN
        var insertedGiftCertificate = giftCertificateDAO.create(giftCertificateToBeCreated);
        var retrievedGiftCertificate = giftCertificateDAO
                .findById(insertedGiftCertificate.getId())
                .orElseThrow();
        //THEN
        assertEquals(insertedGiftCertificate, retrievedGiftCertificate);
    }

    @Test
    void giftCertificateIsNotPresentInDBAfterBeingDeleted() {
        //GIVEN
        GiftCertificate giftCertificateToBeDeleted = createCertificate("TBD");
        giftCertificateToBeDeleted = giftCertificateDAO.create(giftCertificateToBeDeleted);
        //WHEN
        boolean isGiftCertificatePresentBeforeBeingDeleted = giftCertificateDAO.findById(giftCertificateToBeDeleted
                        .getId())
                .isPresent();
        giftCertificateDAO.delete(giftCertificateToBeDeleted.getId());
        //THEN
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(isGiftCertificatePresentBeforeBeingDeleted).isTrue();
        sa.assertThat(giftCertificateDAO.findById(giftCertificateToBeDeleted.getId()).isPresent()).isFalse();
        sa.assertAll();
    }

    @Test
    void givenValuesShouldChangeInDBAfterUpdate() throws ResourceNotFoundException {
        //GIVEN
        GiftCertificate giftCertificateBeforeUpdate = createCertificate("before update");
        giftCertificateDAO.create(giftCertificateBeforeUpdate);
        long id = giftCertificateBeforeUpdate.getId();

        GiftCertificate giftCertificateWithDataToUpdate = new GiftCertificate();
        giftCertificateWithDataToUpdate.setName("Name after update");
        giftCertificateWithDataToUpdate.setDescription("another description");
        giftCertificateWithDataToUpdate.setPrice(new BigDecimal("50.00"));
        giftCertificateWithDataToUpdate.setDuration(5);
        giftCertificateWithDataToUpdate.setId(id);
        //WHEN
        GiftCertificate giftCertificateAfterUpdate =
                giftCertificateDAO.update(giftCertificateWithDataToUpdate);
        //THEN
        assertEquals(giftCertificateWithDataToUpdate, giftCertificateAfterUpdate);
    }

    private GiftCertificate createCertificate(String namePostfix) {
        GiftCertificate giftCertificate = new GiftCertificate();
        setGiftCertificateValues(giftCertificate, namePostfix);
        return giftCertificate;
    }

    private void setGiftCertificateValues(GiftCertificate giftCertificate, String namePostfix) {
        giftCertificate.setName("Test certificate " + namePostfix);
        giftCertificate.setDescription("some description");
        giftCertificate.setPrice(new BigDecimal("42.00"));
        giftCertificate.setDuration(50);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    @AfterEach
    private void cleanDBAfterTest() {
        jdbcTemplate.update("DELETE FROM giftCertificates");
    }
}
