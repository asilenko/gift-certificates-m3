package com.epam.esm.model;

import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {
    private final TagMapper tagMapper = new TagMapper();
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void tagMappedFromTagBusinessModelShouldHaveProperFieldsValues() {
        //GIVEN
        Tag expected = dataProvider.createTestTag();
        TagBusinessModel tagBusinessModelToBeMapped = dataProvider.createTestTagBusinessModel();
        //WHEN
        Tag actual = tagMapper.toTag(tagBusinessModelToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void tagBusinessModelMappedFromTagHasProperFieldsValues() {
        //GIVEN
        TagBusinessModel expected = dataProvider.createTestTagBusinessModel();
        Tag tagToBeMapped = dataProvider.createTestTag();
        //WHEN
        TagBusinessModel actual = tagMapper.toTagBusinessModel(tagToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }
}
