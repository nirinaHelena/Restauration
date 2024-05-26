package school.hei.restoration.repository.dto;

import school.hei.restoration.repository.model.Menu;

public record MenuNumberSale (
        Menu menu,
        int numberOfMenuSale,
        double amountOfMenuSale
){
}
