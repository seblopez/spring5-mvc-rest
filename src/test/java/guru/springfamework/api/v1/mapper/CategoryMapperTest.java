package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CategoryMapperTest {

    static final String NAME = "Joe";
    static final Long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        // given
        final Category category = Category.builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        final CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());

    }
}
