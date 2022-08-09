package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoOutput.ProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.ListProductByWarehouse;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalProductByWarehouseDto;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.model.Warehouse;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class responsible for business rules and communication with the Product Repository layer;
 * @author Diovana Valim, Rafael Cavalcante
 * @version 0.0.1
 */
@Service
public class ProductService implements IProductService {

    /**
     * Dependency Injection of the Product Repository.
     */
    @Autowired
    private IProductRepository productRepository;

    /**
     * Dependency Injection of the Batch Repository.
     */
    @Autowired
    private IBatchRepository batchRepository;


    /**
     * Method to find a list of products and return a ProductDto.
     * @return a list of objects of type ProductDto.
     */
    @Override
    public List<ProductOutputDto> getAllProducts(){
        List<Product> products = (List<Product>) productRepository.findAll();

        if (products.size() == 0) throw new ResourceNotFoundException("No Products Found");

        return products.stream().map(ProductOutputDto::new).collect(Collectors.toList());
    }

    /**
     * Method to find a list of products of a specified category and return a ProductDto.
     * @param category of type String.
     * @return a list of objects of type ProductDto.
     */
    @Override
    public List<ProductOutputDto> getProductsByCategory(String category){
        List<Product> products = productRepository.findAllByType(category);

        if (products.size() == 0) throw new ResourceNotFoundException("No Products Found");

        return products.stream().map(ProductOutputDto::new).collect(Collectors.toList());

    }

    /**
     * Method to find a product by id;
     * @param id of type long. Product identifier;
     * @return an object of type Product;
     */
    @Override
    public Product findProduct(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid product for id %d", id));
        }

        return product.get();
    }

    @Override
    public ListProductByWarehouse listProductByWarehouse(long productId) {
        List<Batch> batchList = batchRepository.findBatchByProductId(productId);

        Set<TotalProductByWarehouseDto> totalProductByWarehouseDtoSet = new HashSet<TotalProductByWarehouseDto>();

        // FIXME - Same Warehouse, same TotalProductByWarehouseDto Object
        for (Batch batch : batchList) {
            TotalProductByWarehouseDto totalProductByWarehouseDto = new TotalProductByWarehouseDto(
                    batch.getOrderEntry().getSection().getWarehouse().getId(),
                    batch.getCurrentQuantity()
            );

            totalProductByWarehouseDtoSet.add(totalProductByWarehouseDto);
        }

        return new ListProductByWarehouse(productId, totalProductByWarehouseDtoSet);
    }
}
