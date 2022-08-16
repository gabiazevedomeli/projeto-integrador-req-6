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
     * @return a list of objects of type Product
     */
    @Override
    public List<NewProductOutputDto> createNewProduct(List<NewProductDto> newListProductDto) {
        List<NewProductOutputDto> newProductsList = new ArrayList<>();
        List<String> listOfAlreadyExistingProducts = new ArrayList<>();
        newListProductDto.forEach(newProductDto -> {
           Product productAlreadyExists = productRepository.findByName(newProductDto.getName());

           if (productAlreadyExists != null) {
               listOfAlreadyExistingProducts.add(productAlreadyExists.getName());
           }

           Product savedProduct = Product.builder()
                   .name(newProductDto.getName())
                   .type(newProductDto.getType())
                   .categoryName(newProductDto.getCategoryName())
                   .price(newProductDto.getPrice())
                   .build();
            newProductsList.add(new NewProductOutputDto(productRepository.save(savedProduct)));
        });

        if (!listOfAlreadyExistingProducts.isEmpty()) {
            throw new ForbiddenException(String.format("The product %s already exists.", listOfAlreadyExistingProducts));
        }
        return newProductsList;
    }

    /**
     * A get method that return a list of products filtered by category name
     * @param categoryName String
     * @return a list of of objects of type NewProductOutputDto
     */
    @Override
    public List<NewProductOutputDto> findByCategoryName(String categoryName) {
        List<Product> productByCategoryName = productRepository.findByCategoryName(categoryName);

        if (productByCategoryName.size() == 0) {
            throw new ResourceNotFoundException(String.format("There is not existing products for this category: %s.", categoryName));
        }

        return productByCategoryName.stream().map(NewProductOutputDto::new).collect(Collectors.toList());
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Not exists a product with this id: %d", productId)));
    }
    /**
     * An update method that return a partial product update
     * @param productId Long product identifier
     * @param productChanges data on format Map<Key, Value> with the info to update the product
     * @return an object of type NewProductOutputDto with the updated information about the product
     */
    @Override
    public NewProductOutputDto partialUpdateOfProduct(Long productId, Map<String, ?> productChanges) {
        Product productToUpdate = findProductById(productId);

        productChanges.forEach((key, value) -> {
            switch (key) {
                case "name": productToUpdate.setName((String) value); break;
                case "type": productToUpdate.setType((String) value); break;
                case "categoryName": productToUpdate.setCategoryName((String) value); break;
                case "price": productToUpdate.setPrice((Double) value);
            }
        });
        return new NewProductOutputDto(productRepository.save(productToUpdate));
    }

    /**
     * Method that deletes a product by id
     * @param productId Long product identifier
     */
    @Override
    public void deleteProduct(Long productId) {
        List<Batch> productToDelete = batchRepository.findBatchByProductId(productId);
        productToDelete.forEach(product -> {
            findProductById(product.getProduct().getId());
            productRepository.delete(product.getProduct());
        });
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
