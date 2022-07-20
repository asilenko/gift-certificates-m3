package com.epam.esm.service;

import com.epam.esm.dao.jdbc.JdbcTagDAO;
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

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Long TAG_ID = 1L;
    private final DataProvider dataProvider = new DataProvider();

    @Mock
    private JdbcTagDAO jdbcTagDAO;

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
        when(jdbcTagDAO.findById(TAG_ID)).thenReturn(Optional.of(tag));
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
        Set<TagBusinessModel> expected = Set.of(tagBusinessModel);
        Set<Tag> tags = Set.of(tag);
        //WHEN
        when(jdbcTagDAO.findAll()).thenReturn(tags);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagBusinessModel);
        Set <TagBusinessModel> actual = tagService.getAll();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnProperTagBusinessModelWhenSuccessfullyAdded() {
        //GIVEN
        TagBusinessModel expected = tagBusinessModel;
        //WHEN
        when(jdbcTagDAO.create(tag)).thenReturn(tag);
        when(tagMapper.toTag(tagBusinessModel)).thenReturn(tag);
        when(tagMapper.toTagBusinessModel(tag)).thenReturn(tagBusinessModel);
        TagBusinessModel actual = tagService.addNewTag(tagBusinessModel);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void deleteMethodShouldBeCalledWhileTagIsBeingRemoved() {
        //GIVEN
        //WHEN
        tagService.removeTag(TAG_ID);
        //THEN
        verify(jdbcTagDAO, Mockito.times(1)).delete(TAG_ID);
    }
}
