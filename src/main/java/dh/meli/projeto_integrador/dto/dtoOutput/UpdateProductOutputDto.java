package dh.meli.projeto_integrador.dto.dtoOutput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
 * Class used to create a Data Transfer Output Object to return an Update Product
 * @author Gabriela Azevedo
 * @version 0.0.1
 * @see java.lang.Object
 */
public class UpdateProductOutputDto {
    private String updateMessage;
    private GenericProductOutputDto updatedProduct;

}
