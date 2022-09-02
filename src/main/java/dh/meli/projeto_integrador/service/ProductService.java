package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.exception.ForbiddenException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class responsible for business rules and communication with the Product Repository layer;
 *
 * @author Diovana Valim, Rafael Cavalcante, Amanda Marinelli e Thiago Almeida
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
     *
     * @return a list of objects of type ProductDto.
     */
    @Override
    public List<ProductOutputDto> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();

        if (products.size() == 0) throw new ResourceNotFoundException("No Products Found");

        return products.stream().map(ProductOutputDto::new).collect(Collectors.toList());
    }

    /**
     * Method to find a list of products of a specified category and return a ProductDto.
     *
     * @param category of type String.
     * @return a list of objects of type ProductDto.
     */
    @Override
    public List<ProductOutputDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findAllByType(category);

        if (products.size() == 0) throw new ResourceNotFoundException("No Products Found");

        return products.stream().map(ProductOutputDto::new).collect(Collectors.toList());
    }

    /**
     * Method to find a product by id;
     *
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

    /**
     * Method to list product stock quantity by Warehouse;
     *
     * @param productId of type long. Product identifier;
     * @return an object of type Product;
     */
    @Override
    public ListProductByWarehouseDto listProductByWarehouse(long productId) {
        List<Batch> batchList = batchRepository.findBatchByProductId(productId);

        if (batchList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid batch stock for product %d",
                    productId));
        }

        List<TotalProductByWarehouseDto> totalProductByWarehouseDtoList = new ArrayList<TotalProductByWarehouseDto>();

        for (Batch batch : batchList) {
            TotalProductByWarehouseDto totalProductByWarehouseDto = new TotalProductByWarehouseDto(
                    batch.getOrderEntry().getSection().getWarehouse().getId(),
                    batch.getCurrentQuantity()
            );

            long batchWarehouseId = batch.getOrderEntry().getSection().getWarehouse().getId();

            List<TotalProductByWarehouseDto> hasWarehouse = totalProductByWarehouseDtoList
                    .stream()
                    .filter(totalProductByWh -> totalProductByWh.getWarehouseCode() == batchWarehouseId)
                    .collect(Collectors.toList());

            if (hasWarehouse.isEmpty()) {
                totalProductByWarehouseDtoList.add(totalProductByWarehouseDto);
            } else {
                totalProductByWarehouseDtoList.forEach(totalProductByWarehouse -> {
                    if (totalProductByWarehouse.getWarehouseCode() == batchWarehouseId) {
                        int finalQuantity = totalProductByWarehouse.getTotalQuantity() + batch.getCurrentQuantity();
                        totalProductByWarehouse.setTotalQuantity(finalQuantity);
                    }
                });
            }
        }

        return new ListProductByWarehouseDto(productId, totalProductByWarehouseDtoList);
    }

    /**
     * Method to create a new product
     * @param newListProductDto an object of type NewProductDto
     * @throws ForbiddenException exception thrown when a product(s) already exists
     * @return a list of objects of type Product
     */
    @Override
    public List<GenericProductOutputDto> createNewProduct(List<NewProductDto> newListProductDto) {

        List<GenericProductOutputDto> newProductsList = new ArrayList<>();
        List<NewProductDto> newProducts = new ArrayList<>();
        List<String> listOfAlreadyExistingProducts = new ArrayList<>();

        newListProductDto.forEach(newProductDto -> {
           Product productAlreadyExists = productRepository.findByName(newProductDto.getName());

           if (productAlreadyExists != null) {
               listOfAlreadyExistingProducts.add(productAlreadyExists.getName());
           } else {
               newProducts.add(newProductDto);
           }
        });
            if (!listOfAlreadyExistingProducts.isEmpty()) {
                throw new ForbiddenException(String.format("The product %s already exists.", listOfAlreadyExistingProducts));
            }

            newProducts.forEach(newProductOutputDto -> {
               Product savedProduct = Product.builder()
                       .name(newProductOutputDto.getName())
                       .type(newProductOutputDto.getType())
                       .categoryName(newProductOutputDto.getCategoryName())
                       .price(newProductOutputDto.getPrice())
                       .build();
                newProductsList.add(new GenericProductOutputDto(productRepository.save(savedProduct)));
            });
        return newProductsList;
    }

    /**
     * A get method that return a list of products filtered by category name
     * @param categoryName String
     * @throws ResourceNotFoundException generic exception thrown when a product with the category passed by param doesn't exist
     * @return a list of of objects of type GenericProductOutputDto
     */
    @Override
    public List<GenericProductOutputDto> findProductsByCategoryName(String categoryName) {
        List<Product> productByCategoryName = productRepository.findByCategoryName(categoryName);

        if (productByCategoryName.size() == 0) {
            throw new ResourceNotFoundException(String.format("There is not existing products for this category: %s.", categoryName));
        }
        return productByCategoryName.stream().map(GenericProductOutputDto::new).collect(Collectors.toList());
    }

    /**
     * Private method for to find a product by id. Used in more than one @Override methods for to verify the product identifier
     * @param productId Long product identifier
     * @throws ResourceNotFoundException generic exception thrown when a product with the id passed by param doesn't exist
     * @return an object of type product
     */
    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Not exists a product with this id: %d", productId)));
    }

    /**
     * An update method that return a partial product update
     * @param productId Long product identifier
     * @param productChanges data on format map (key, value) with the info to update the product
     * @return an object of type GenericProductOutputDto with the updated information about the product
     */
    @Override
    public UpdateProductOutputDto partialUpdateOfProduct(Long productId, Map<String, ?> productChanges) {

        Product productToUpdate = findProductById(productId);
        String updateMessage = "The product %s was successfully updated!";

        productChanges.forEach((key, value) -> {
            switch (key) {
                case "name": productToUpdate.setName((String) value); break;
                case "type": productToUpdate.setType((String) value); break;
                case "categoryName": productToUpdate.setCategoryName((String) value); break;
                case "price": productToUpdate.setPrice((Double) value); break;
            }
        });
        return new UpdateProductOutputDto(String.format(updateMessage, productToUpdate.getName()), new GenericProductOutputDto(productRepository.save(productToUpdate)));
    }

    /**
     * Method to find a product by id and return some properties about the batches;
     * @param id of type long. Product identifier;
     * @param order of type character that identifies the specified order to list the result.
     * @return a DTO with informations of the product and his batches;
     */
    @Override
    public ProductStockDto getProductBatchProps(Long id, Character order) {
        Product product = findProduct(id);

        List<Batch> batchesInput = batchRepository.findBatchByProductId(id);

        if (batchesInput.isEmpty()) {
            throw new ResourceNotFoundException("No available batch found for this product.");
        }
        List<Batch> sortedFilteredList = sortByOrder(filterByDueDate(batchesInput), order);

        if (sortedFilteredList.isEmpty()) {
            throw new ResourceNotFoundException("Product found, but no Batch of given product has 3 or more weeks until due date");
        }

        return new ProductStockDto(product, sortedFilteredList);
    }

    /**
     * Method to filter a list of batches to contain only batches that have 3 or more weeks until their due date;
     * @param batchList a  List of Batch to be filtered.
     * @return a filtered list of batches;
     */
    private static List<Batch> filterByDueDate(List<Batch> batchList) {
        return batchList.stream()
                .filter(batch -> DAYS.between(LocalDate.now(), batch.getDueDate()) > 21)
                .collect(Collectors.toList());
    }

    /**
     * Method to sorted a list of batches
     * @param batchList a  List of Batch to be sorted.
     * @param order a  List of Batch to be sorted.
     * @return a filtered list of batches;
     */
    private static List<Batch> sortByOrder(List<Batch> batchList, Character order) {
        switch (order) {
            case 'L':
                return batchList.stream()
                        .sorted(Comparator.comparingLong(Batch::getId))
                        .collect(Collectors.toList());
            case 'Q':
                return batchList.stream()
                        .sorted(Comparator.comparingInt(Batch::getCurrentQuantity))
                        .collect(Collectors.toList());
            default:
                return batchList.stream()
                        .sorted(Comparator.comparing(Batch::getDueDate))
                        .collect(Collectors.toList());
        }
    }
}
