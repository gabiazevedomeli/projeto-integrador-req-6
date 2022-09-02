package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for intermediating the requests sent by the user with the responses provided by the Service;
 * @author Diovana Valim, Rafael Cavalcante, Amanda Marinelli e Thiago Almeida.
 * @version 0.0.1
 */
@RestController
@Validated
@RequestMapping("/api/v1")
public class ProductController {

    /**
     * Dependency Injection of the ProductService.
     */
    @Autowired
    private ProductService productService;

    /**
     * A get method that when called will return in the body request a list of products present in the Database
     * @return Response Entity of type List of productDto and the corresponding HttpStatus ;
     */
    @GetMapping("/fresh-products")
    public ResponseEntity<List<ProductOutputDto>> listAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * A POST method responsible for saving the infos of a new Product at application's database.
     * @param listNewProductDto a list of objects of type NewProductDto
     * @return Response Entity of type List of NewProductOutputDto and the corresponding HttpStatus.
     */
    @PostMapping("/fresh-products/create-products")
    public ResponseEntity<List<GenericProductOutputDto>> createNewProducts(@RequestBody @Valid List<NewProductDto> listNewProductDto) {
        return new ResponseEntity<>(productService.createNewProduct(listNewProductDto), HttpStatus.CREATED);
    }

    /**
     * A GET method responsible for find and get products filtered by category name
     * @param categoryName string
     * @return Response Entity of type List of NewProductOutputDto and the corresponding HttpStatus.
     */
    @GetMapping("/fresh-products/category/{categoryName}")
    public ResponseEntity<List<GenericProductOutputDto>> getProductsByCategoryName(@PathVariable String categoryName) {
        return new ResponseEntity<>(productService.findProductsByCategoryName(categoryName), HttpStatus.OK);
    }

    /**
     * A PATCH method responsible for to update a product partially
     * @param productId Long product identifier
     * @param productChanges data on format map (key, value) with the info to update the product came from the request body
     * @return Response Entity of type List of NewProductOutputDto with the updated information about the product and the corresponding HttpStatus.
     */
    @PatchMapping("/fresh-products/update-product/{productId}")
    public ResponseEntity<UpdateProductOutputDto> partialUpdateProduct(@PathVariable Long productId, @RequestBody Map<String, ?> productChanges) {
        return new ResponseEntity<>(productService.partialUpdateOfProduct(productId, productChanges), HttpStatus.OK);
    }

    /**
     * A get method that when called will return in the body request a list of products of a specified category,
     * present in the Database
     * @param category a String received by the URL request to determine the type of product returned
     * @return Response Entity of type List of productDto and the corresponding HttpStatus ;
     */
    @GetMapping("/fresh-products/{category}")
    public ResponseEntity<List<ProductOutputDto>> listProductByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    /**
     * A get method responsible for listing product stock by warehouse
     * @param productId a valid product entity identifier received by path variable;
     * @return Response Entity of a list which type is ListProductByWarehouseDto and the corresponding HttpStatus;
     */
    @GetMapping("/fresh-products/warehouse/product/{productId}")
    public ResponseEntity<ListProductByWarehouseDto> listProductByWarehouse(@PathVariable long productId) {
        ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(productId);

        return new ResponseEntity<ListProductByWarehouseDto>(listProductByWarehouseDto, HttpStatus.OK);
    }

    /**
     * Method to get the product batche's properties by id through the endpoint "/api/v1/fresh-products/fresh-products/list/{id}?order={order}
     * if the user does not specified the order, the method uses the dueDate as default for the output
     * @param  id (long type) received by url.
     * @param order (character type) may be received by url, to order the API output.
     * @return a list of properties for the specified product.
     */
    @GetMapping("/fresh-products/list/{id}")
    public ResponseEntity<ProductStockDto> getProductBatches(@PathVariable long id,
                                                             @RequestParam (required = false, defaultValue = "V") Character order) {
        return ResponseEntity.ok(productService.getProductBatchProps(id, order));
        
    }

}
