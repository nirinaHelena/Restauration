package school.hei.restoration.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import school.hei.restoration.repository.model.Restaurant;

import java.util.List;

@AllArgsConstructor
@Data
public class AllMenuSaleAtDate {
    private Restaurant restaurant;
    private List<MenuNumberSale> menuNumberSales;
}
