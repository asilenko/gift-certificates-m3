package com.epam.esm.model;

import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {
    private final TagMapper tagMapper = new TagMapper();
    private final DataProvider dataProvider = new DataProvider();

    @Test
    void tagMappedFromTagModelShouldHaveProperFieldsValues() {
        //GIVEN
        Tag expected = dataProvider.createTestTag();
        TagModel tagModelToBeMapped = dataProvider.createTagModel();
        //WHEN
        Tag actual = tagMapper.toTag(tagModelToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void tagModelMappedFromTagHasProperFieldsValues() {
        //GIVEN
        TagModel expected = dataProvider.createTagModel();
        Tag tagToBeMapped = dataProvider.createTestTag();
        //WHEN
        TagModel actual = tagMapper.toTagBusinessModel(tagToBeMapped);
        //THEN
        assertEquals(expected, actual);
    }
}
