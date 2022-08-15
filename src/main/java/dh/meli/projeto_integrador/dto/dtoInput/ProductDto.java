package dh.meli.projeto_integrador.dto.dtoInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the ProductDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the ProductDto Class
 */
@Setter
/**
 * Method Constructor with all arguments implemented by Lombok lib
 */
@AllArgsConstructor
/**
 * Method builder implemented by Lombok lib
 */
@Builder
/**
 * Class used to create a Data Transfer Object for Product POJO
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class ProductDto {

    @NotNull(message = "The product must have an ID number")
    private Long productId;

    @NotNull(message = "The product must have a quantity")
    @Min(value = 1)
    private int quantity;
}
