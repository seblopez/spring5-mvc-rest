package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "A";
    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {
        //given
        List<Category> categories = Arrays.asList(
                Category.builder().id(ID).name(NAME).build(),
                Category.builder().id(2L).name("B").build(),
                Category.builder().id(3L).name("C").build()
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        final List<CategoryDTO> allCategories = categoryService.getAllCategories();

        // then
        assertNotNull(allCategories);
        assertEquals(3, categories.size());


    }

    @Test
    public void getCategoryByName() {
        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.of(Category.builder()
                        .id(ID)
                        .name(NAME)
                        .build()));

        final CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        assertNotNull(categoryDTO);
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}
