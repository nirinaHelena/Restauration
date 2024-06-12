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
    }

    @Test
    void ingredientMostUsedByMenus() {
        int limit = 3;
        Instant begin = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.parse("2023-12-31T23:59:59Z");

        IngredientMostUsed bread = new IngredientMostUsed(1, 100);
        IngredientMostUsed sausage = new IngredientMostUsed(2, 200);
        List<IngredientMostUsed> ingredientMostUses = Arrays.asList(bread, sausage);

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);

        Unity kg = new Unity(2, "kg");
        IngredientTemplate sausageTemplate = new IngredientTemplate(2, "sausage", 500, kg);

        when(movementRepo.getIngredientMostUsed(eq(limit), eq(begin), eq(end))).thenReturn(ingredientMostUses);
        when(ingredientTemplateRepo.getById(1)).thenReturn(breadTemplate);
        when(ingredientTemplateRepo.getById(2)).thenReturn(sausageTemplate);
        Menu burger = new Menu(1, "burger");
        when(ingredientRepo.menuWhereIngredientIsMostUsed(eq(breadTemplate))).thenReturn(burger);
        Menu hotDog = new Menu(2, "hot dog");
        when(ingredientRepo.menuWhereIngredientIsMostUsed(eq(sausageTemplate))).thenReturn(hotDog);

        List<IngredientMostUsedByMenu> ingredientMostUsedByMenus = ingredientService.ingredientMostUsedByMenus(limit, begin, end);

        assertEquals(2, ingredientMostUsedByMenus.size());

        IngredientMostUsedByMenu result1 = ingredientMostUsedByMenus.get(0);
        assertEquals(1, result1.idIngredientTemplate());
        assertEquals("bread", result1.nameIngredient());
        assertEquals(burger, result1.menuWhichMostUsedIngredient());

        IngredientMostUsedByMenu result2 = ingredientMostUsedByMenus.get(1);
        assertEquals(2, result2.idIngredientTemplate());
        assertEquals("sausage", result2.nameIngredient());
        assertEquals(hotDog, result2.menuWhichMostUsedIngredient());

        verify(movementRepo).getIngredientMostUsed(eq(limit), eq(begin), eq(end));
        verify(ingredientTemplateRepo).getById(1);
        verify(ingredientTemplateRepo).getById(2);
        verify(ingredientRepo).menuWhereIngredientIsMostUsed(eq(breadTemplate));
        verify(ingredientRepo).menuWhereIngredientIsMostUsed(eq(sausageTemplate));

    }
}