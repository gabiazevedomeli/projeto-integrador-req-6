package dh.meli.projeto_integrador.dto.dtoInput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;

/**
 * Method Getter implemented by Lombok lib for get access the private attributes of the NewProductDto Class
 */
@Getter
/**
 * Method Setter implemented by Lombok lib for set the private attributes of the NewProductDto Class
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
public class NewProductDto {

    @NotBlank(message = "The name field is required")
    @Pattern(regexp = "(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$", message = "The product name must be start with a capital letter.")
    private String name;

    @NotBlank(message = "The type field is required")
    @Pattern(regexp = "(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$", message = "The product type must be start with a capital letter.")
    private String type;

    @NotBlank(message = "The category field is required")
    @Pattern(regexp = "(?=^.{2,60}$)^[A-ZÀÁÂĖÈÉÊÌÍÒÓÔÕÙÚÛÇ][a-zàáâãèéêìíóôõùúç]+(?:[ ](?:das?|dos?|de|e|[A-Z][a-z]+))*$", message = "The category name must be start with a capital letter.")
    private String categoryName;

    @NotNull(message = "The price field is required")
    @DecimalMin(value = "1.0")
    private Double price;
}
