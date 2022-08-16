package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewProductOutputDto {
    private String name;
    private String type;
    private String categoryName;
    private Double price;

    public NewProductOutputDto(Product product) {
        this.name = product.getName();
        this.type = product.getType();
        this.categoryName = product.getCategoryName();
        this.price = product.getPrice();
    }
}
