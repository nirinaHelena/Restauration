package school.hei.restoration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.IngredientTemplateRepo;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.model.dto.IngredientMostUsed;
import school.hei.restoration.model.dto.IngredientMostUsedByMenu;
import school.hei.restoration.model.IngredientTemplate;
import school.hei.restoration.model.Menu;
import school.hei.restoration.model.Unity;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class IngredientServiceTest {
    @Mock
    private IngredientRepo ingredientRepo;
    @Mock
    private IngredientTemplateRepo ingredientTemplateRepo;
    @Mock
    private MovementRepo movementRepo;
    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        when(movementRepo.getIngredientMostUsed(any(Integer.class), any(Instant.class), any(Instant.class))).thenReturn(List.of(bread(), sausage()));
        when(ingredientTemplateRepo.getById(1)).thenReturn(breadTemplate());
        when(ingredientTemplateRepo.getById(2)).thenReturn(sausageTemplate());when(ingredientRepo.menuWhereIngredientIsMostUsed(eq(breadTemplate()))).thenReturn(burger());
        when(ingredientRepo.menuWhereIngredientIsMostUsed(eq(sausageTemplate()))).thenReturn(hotDog());

    }

    public IngredientMostUsed bread(){
        return new IngredientMostUsed(1, 100);
    }

    public IngredientMostUsed sausage(){
        return new IngredientMostUsed(2, 200);
    }

    public IngredientTemplate breadTemplate(){
        return new IngredientTemplate(1, "bread", 500,
                new Unity(1, "piece"));
    }

    public IngredientTemplate sausageTemplate(){
        return new IngredientTemplate(2, "sausage", 500,
                new Unity(2, "kg"));
    }

    public Menu burger(){
        return new Menu(1, "burger");
    }

    public Menu hotDog(){
        return new Menu(2, "hot dog");
    }


    @Test
    void read_most_used_ingredients_by_menu_id_ok(){
        int limit = 3;
        Instant begin = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.parse("2023-12-31T23:59:59Z");

        List<IngredientMostUsedByMenu> actual = ingredientService.ingredientMostUsedByMenus(limit, begin, end);
        List<Menu> menus = actual.stream().map(IngredientMostUsedByMenu::menuWhichMostUsedIngredient)
                        .toList();

        assertEquals(2, actual.size());
        assertTrue(menus.contains(burger()));
        assertTrue(menus.contains(hotDog()));

        verify(movementRepo).getIngredientMostUsed(eq(limit), eq(begin), eq(end));
        verify(ingredientTemplateRepo).getById(1);
        verify(ingredientTemplateRepo).getById(2);
        verify(ingredientRepo).menuWhereIngredientIsMostUsed(eq(breadTemplate()));
        verify(ingredientRepo).menuWhereIngredientIsMostUsed(eq(sausageTemplate()));
    }

}