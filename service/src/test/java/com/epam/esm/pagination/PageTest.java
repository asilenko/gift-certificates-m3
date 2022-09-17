package com.epam.esm.pagination;


import com.epam.esm.model.GiftCertificateBusinessModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PageTest {

    @ParameterizedTest
    @CsvSource(value = {"1:5:10:true", "1:10:10:false", "3:10:30:false", "2:10:30:true"}, delimiter = ':')
    void hasNextShouldReturnProperValue(int number, int size, int total, boolean expected) {
        // Given
        Page<GiftCertificateBusinessModel> page = new Page<>(number, size, total, new ArrayList<>());
        // When
        boolean actual = page.hasNext();
        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:5:10:2", "1:10:10:1", "3:10:30:3", "2:10:30:3"}, delimiter = ':')
    void getNextPageNumberShouldReturnProperValue(int number, int size, int total, int expected) {
        // Given
        Page<GiftCertificateBusinessModel> page = new Page<>(number, size, total, new ArrayList<>());
        // When
        int actual = page.getNextPageNumber();
        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:5:10:false", "1:10:10:false", "3:10:30:true", "2:10:30:true"}, delimiter = ':')
    void hasPreviousShouldReturnProperValue(int number, int size, int total, boolean expected) {
        // Given
        Page<GiftCertificateBusinessModel> page = new Page<>(number, size, total, new ArrayList<>());
        // When
        boolean actual = page.hasPrevious();
        // Then
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:5:10:1", "1:10:10:1", "3:10:30:2", "2:10:30:1"}, delimiter = ':')
    void getPreviousPageNumberShouldReturnProperValue(int number, int size, int total, int expected) {
        // Given
        Page<GiftCertificateBusinessModel> page = new Page<>(number, size, total, new ArrayList<>());
        // When
        int actual = page.getPreviousPageNumber();
        // Then
        assertEquals(expected, actual);
    }
}
