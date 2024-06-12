package school.hei.restoration.model.dto;

import school.hei.restoration.model.Menu;

public record MenuNumberSale (
        Menu menu,
        int numberOfMenuSale,
        double amountOfMenuSale
){
}
