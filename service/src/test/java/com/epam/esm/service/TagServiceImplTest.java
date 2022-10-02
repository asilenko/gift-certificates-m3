package com.epam.esm.service;

import com.epam.esm.dao.jpa.JPATagDAO;
import com.epam.esm.dataprovider.DataProvider;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Long TAG_ID = 1L;
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer PAGE_SIZE = 10;
    private static final int NUMBER_OF_INVOCATIONS = 1;
    private final DataProvider dataProvider = new DataProvider();

    @Mock
    private JPATagDAO JPATagDAO;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    private final TagModel tagModel = dataProvider.createTagModel();
    private final Tag tag = dataProvider.createTestTag();

    @Test
    void shouldReturnProperTagBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(JPATagDAO.findById(TAG_ID)).thenReturn(Optional.of(tag));
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagModel);
        TagModel expected = tagModel;
        //WHEN
        var actual = tagService.find(TAG_ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTagsListWhenResourceIsFound() {
        //GIVEN
        List<TagModel> expected = List.of(tagModel);
        List<Tag> tags = List.of(tag);
        //WHEN
        when(JPATagDAO.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(tags);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagModel);
        List <TagModel> actual = tagService.findAll(PAGE_NUMBER,PAGE_SIZE).getContent();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnProperTagBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        TagModel expected = tagModel;
        //WHEN
        when(JPATagDAO.create(tag)).thenReturn(tag);
        when(tagMapper.toTag(tagModel)).thenReturn(tag);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagModel);
        TagModel actual = tagService.create(tagModel);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileTagIsBeingRemoved() throws ResourceNotFoundException {
        //GIVEN
        //WHEN
        tagService.delete(TAG_ID);
        //THEN
        verify(JPATagDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).delete(TAG_ID);
    }
}
