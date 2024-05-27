package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.Service.MenuService;
import school.hei.restoration.repository.model.Menu;

@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Menu menu){
        menuService.save(menu);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }
}
