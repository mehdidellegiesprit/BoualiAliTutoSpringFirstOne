package com.bouali.gestiondestock.services.impl;

import com.bouali.gestiondestock.dto.CategoryDto;
import com.bouali.gestiondestock.exception.EntityNotFoundException;
import com.bouali.gestiondestock.exception.ErrorCodes;
import com.bouali.gestiondestock.exception.InvalidEntityException;
import com.bouali.gestiondestock.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService service ;

    @Test
    public void shouldSaveCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .code("Cat test not saved2223333333555")
                .designation("Designation not saved22233333333333555")
                .idEntreprise(1)
                .build();
        CategoryDto savedCategory = service.save(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals(expectedCategory.getCode(),savedCategory.getCode());
        assertEquals(expectedCategory.getDesignation(),savedCategory.getDesignation());
        assertEquals(expectedCategory.getIdEntreprise(),savedCategory.getIdEntreprise());
    }
    @Test
    public void shouldUpdateCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .code("Cat test not saved2223333333555")
                .designation("Designation not saved22233333333333555")
                .idEntreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        CategoryDto categoryToUpdate = savedCategory ;
        categoryToUpdate.setCode("cat update");

        savedCategory = service.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCode(),savedCategory.getCode());
        assertEquals(categoryToUpdate.getDesignation(),savedCategory.getDesignation());
        assertEquals(categoryToUpdate.getIdEntreprise(),savedCategory.getIdEntreprise());
    }
    @Test
    public void shouldThrowInvalidEntityException(){
        CategoryDto expectedCategory = CategoryDto.builder().build() ;

        InvalidEntityException expectedException  = assertThrows(InvalidEntityException.class,()->service.save(expectedCategory));

        assertEquals(ErrorCodes.CATEGORY_NOT_VALID,expectedException.getErrorCode());
        assertEquals(1,expectedException.getErrors().size());
        assertEquals("Veuiller renseigner le code de la categorie",expectedException.getErrors().get(0));
    }
    @Test
    public void shouldThrowEntityNotFoundException(){
        EntityNotFoundException expectedException  = assertThrows(EntityNotFoundException.class,()->service.findById(0));

        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND,expectedException.getErrorCode());
        assertEquals("Aucun Category avec l'ID = 0n'ete trouve dans la BDD",expectedException.getMessage());
    }

}