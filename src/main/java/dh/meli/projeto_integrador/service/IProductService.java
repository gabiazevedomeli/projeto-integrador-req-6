package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Interface to specify service methods implemented on ProductService class.
 * @author Rafael Cavalcante
 * @version 0.0.1
 */
public interface IProductService {
    /**
     * Method for to get all products
     * @return a list of objects of type ProductOutputDto
     */
    List<ProductOutputDto> getAllProducts();

    /**
     * Method for to get products by category
     * @param category String
     * @return a list of output objects of type ProductOutputDto
     */
    List<ProductOutputDto> getProductsByCategory(String category);

    /**
     * Method for to find product by id
     * @param id long
     * @return an object of type Product
     */
    Product findProduct(long id);

     /**
     * Method to find a product by id and return some properties about the batches
     * @param id Long
     * @param order Character
     * @return an object of type ProductStockDto
     */
    ProductStockDto getProductBatchProps(Long id,Character order);

    /**
     * Method for to list all products by warehouse
     * @param productId long id received user request
     * @return an output object of type ListProductByWarehouseDto
     */
    ListProductByWarehouseDto listProductByWarehouse(long productId);

    /**
     * Method implemented by ProductService that creates a one or many products at the same time
     * @param newProductDto a List of objects of type newProductDto
     * @return a List of type NewProductOutputDto
     */
    List<GenericProductOutputDto> createNewProduct(List<NewProductDto> newProductDto);

    /**
     * Method implemented by ProductService that gets all products by product category name
     * @param categoryName String
     * @return a List of objects of type NewProductOutputDto
     */
    List<GenericProductOutputDto> findProductsByCategoryName(String categoryName);

    /**
     * Method implemented by ProductService that makes a partial update of a product
     * @param productId Long product identifier
     * @param productChanges data on format Map<Key, Value> with the info to update the product
     * @return an updated object of type NewProductOutputDto
     */
    UpdateProductOutputDto partialUpdateOfProduct(Long productId, Map<String, ?> productChanges);
}
