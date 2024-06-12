package school.hei.restoration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import school.hei.restoration.model.Restaurant;

import java.util.List;

@AllArgsConstructor
@Data
public class AllMenuSaleAtDate {
    private Restaurant restaurant;
    private List<MenuNumberSale> menuNumberSales;
}
