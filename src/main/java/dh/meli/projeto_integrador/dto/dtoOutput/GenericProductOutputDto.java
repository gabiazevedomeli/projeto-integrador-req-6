package dh.meli.projeto_integrador.dto.dtoOutput;

import dh.meli.projeto_integrador.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of GenericProductOutputDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of GenericProductOutputDto Class
 */
@Setter
/**
 * Method Default Constructor implemented by Lombok lib
 */
@NoArgsConstructor
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Class used to create a Data Transfer Output Object to return a Product
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class GenericProductOutputDto {
    private String name;
    private String type;
    private String categoryName;
    private Double price;

    public GenericProductOutputDto(Product product) {
        this.name = product.getName();
        this.type = product.getType();
        this.categoryName = product.getCategoryName();
        this.price = product.getPrice();
    }
}
