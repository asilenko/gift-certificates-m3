package com.epam.esm.service;

import com.epam.esm.dao.jpa.JPATagDAO;
import com.epam.esm.domain.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.model.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    private final TagBusinessModel tagBusinessModel = dataProvider.createTestTagBusinessModel();
    private final Tag tag = dataProvider.createTestTag();
    private final Set<Tag> tags = dataProvider.createTagsSet();
    private final Set<TagBusinessModel>tagsBusinessModel = dataProvider.createTagsBMSet();

    @Test
    void shouldReturnProperTagBusinessModelWhenResourceIsFound() throws ResourceNotFoundException {
        //GIVEN
        when(JPATagDAO.findById(TAG_ID)).thenReturn(Optional.of(tag));
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagBusinessModel);
        TagBusinessModel expected = tagBusinessModel;
        //WHEN
        var actual = tagService.getTagById(TAG_ID);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnTagsListWhenResourceIsFound() {
        //GIVEN
        List<TagBusinessModel> expected = List.of(tagBusinessModel);
        List<Tag> tags = List.of(tag);
        //WHEN
        when(JPATagDAO.findAll(PAGE_NUMBER, PAGE_SIZE)).thenReturn(tags);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagBusinessModel);
        List <TagBusinessModel> actual = tagService.getAll(PAGE_NUMBER,PAGE_SIZE).getContent();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnProperTagBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        TagBusinessModel expected = tagBusinessModel;
        //WHEN
        when(JPATagDAO.create(tag)).thenReturn(tag);
        when(tagMapper.toTag(tagBusinessModel)).thenReturn(tag);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagBusinessModel);
        TagBusinessModel actual = tagService.addNewTag(tagBusinessModel);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileTagIsBeingRemoved() throws ResourceNotFoundException {
        //GIVEN
        //WHEN
        tagService.removeTag(TAG_ID);
        //THEN
        verify(JPATagDAO, Mockito.times(NUMBER_OF_INVOCATIONS)).delete(TAG_ID);
    }
}
