package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.exception.ForbiddenException;
import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Batch;
import dh.meli.projeto_integrador.model.Product;
import dh.meli.projeto_integrador.repository.IBatchRepository;
import dh.meli.projeto_integrador.repository.IProductRepository;
import dh.meli.projeto_integrador.util.Generators;
import dh.meli.projeto_integrador.utilReq6.GeneratorsRequirementSix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    IProductRepository productRepository;

    @Mock
    IBatchRepository batchRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getProduct()));
    }

    @Test
    void findProductTest() {
        long id = 0;
        Product product = productService.findProduct(id);

        assertThat(product.getId()).isEqualTo(Generators.getProduct().getId());
        assertThat(product.getType()).isEqualTo(Generators.getProduct().getType());
        assertThat(product.getPrice()).isEqualTo(Generators.getProduct().getPrice());

        verify(productRepository, atLeastOnce()).findById(id);
    }

    @Test
    void findProduct_WhenProductDontExistsTest() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        long id = 0;

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.findProduct(id));

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid product for id %d", id));

        verify(productRepository, atLeastOnce()).findById(id);
    }

    @Test
    void getAllProducts_returnListProducts_whenProductsExists() {
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Generators.productList());

        List<ProductOutputDto> products = productService.getAllProducts();


        assertThat(products).isNotNull();
        assertThat((products.size())).isEqualTo(2);
    }

    @Test
    void getAllProducts_throwsNotFoundException_whenProductsDontExist() {
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Generators.emptyProductDtoList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.getAllProducts());

        assertThat(exception.getMessage()).isEqualTo("No Products Found");
    }

    @Test
    void getProductsByCategory_returnListProducts_whenProductsExists() {
        BDDMockito.when(productRepository.findAllByType(anyString()))
                .thenReturn(Generators.productList());

        List<ProductOutputDto> products = productService.getProductsByCategory(Generators.validProduct1().getType());


        assertThat(products).isNotNull();
        assertThat((products.size())).isEqualTo(2);
    }

    @Test
    void getProductsByCategory_returnListProducts_whenProductsDontExist() {
        BDDMockito.when(productRepository.findAllByType(anyString()))
                .thenReturn(Generators.emptyProductDtoList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.getProductsByCategory(Generators.validProduct1().getType()));

        assertThat(exception.getMessage()).isEqualTo("No Products Found");
    }

    @Test
    void getProductBatchProps_returnProductStockDto_whenProductAndValidBatchExistsOrderByDueDate() {
        BDDMockito.when(batchRepository.findBatchByProductId(anyLong()))
                .thenReturn(Generators.validBatchList());

        ProductStockDto productStockDto = productService.getProductBatchProps(1L, 'V');

        assertThat(productStockDto.getBatchStockDto().get(0).getBatchNumber()).isEqualTo(1L);
        assertThat(productStockDto.getBatchStockDto().get(1).getBatchNumber()).isEqualTo(2L);


        verify(batchRepository, atLeastOnce()).findBatchByProductId(anyLong());
    }

    @Test
    void getProductBatchProps_returnProductStockDto_whenProductAndValidBatchExistsOrderByQuantity() {
        BDDMockito.when(batchRepository.findBatchByProductId(anyLong()))
                .thenReturn(Generators.validBatchList());

        ProductStockDto productStockDto = productService.getProductBatchProps(1L, 'Q');

        assertThat(productStockDto.getBatchStockDto().get(0).getBatchNumber()).isEqualTo(2L);
        assertThat(productStockDto.getBatchStockDto().get(1).getBatchNumber()).isEqualTo(1L);

        verify(batchRepository, atLeastOnce()).findBatchByProductId(anyLong());
    }

    @Test
    void getProductBatchProps_returnProductStockDto_whenProductAndValidBatchExistsOrderByBatch() {
        BDDMockito.when(batchRepository.findBatchByProductId(anyLong()))
                .thenReturn(Generators.validBatchList());

        ProductStockDto productStockDto = productService.getProductBatchProps(1L, 'L');

        assertThat(productStockDto.getBatchStockDto().get(0).getBatchNumber()).isEqualTo(1L);
        assertThat(productStockDto.getBatchStockDto().get(1).getBatchNumber()).isEqualTo(2L);
        verify(batchRepository, atLeastOnce()).findBatchByProductId(anyLong());
    }

    @Test
    void getProductBatchProps_returnProductStockDto_whenBatchDontExist() {
        BDDMockito.when(batchRepository.findBatchByProductId(anyLong()))
                .thenReturn(Generators.emptyBatchList());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.getProductBatchProps(2L, 'Q'));

        assertThat(exception.getMessage()).isEqualTo("No available batch found for this product.");
        verify(batchRepository, atLeastOnce()).findBatchByProductId(anyLong());
    }

    @Test
    void getProductBatchProps_returnProductStockDto_whenBatchInDueDateDontExist() {
        BDDMockito.when(batchRepository.findBatchByProductId(anyLong()))
                .thenReturn(Generators.notAbleBatchList());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                productService.getProductBatchProps(2L, 'Q'));

        assertThat(exception.getMessage()).isEqualTo("Product found, but no Batch of given product has 3 or more weeks until due date");
        verify(batchRepository, atLeastOnce()).findBatchByProductId(anyLong());
    }

    @Test
    void listProductByWarehouseTest() {
        BDDMockito.when(batchRepository.findBatchByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getBatches());


        Product product = Generators.getProduct();

        ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(product.getId());

        assertThat(listProductByWarehouseDto.getProductId())
                .isEqualTo(Generators.getListProductByWarehouseDto().getProductId());

        for (int i = 0; i < listProductByWarehouseDto.getWarehouses().size(); i++) {
            TotalProductByWarehouseDto totalProductByWarehouseDto = listProductByWarehouseDto.getWarehouses().get(i);

            TotalProductByWarehouseDto generatedTotalProductByWarehouseDto = Generators
                    .getListProductByWarehouseDto()
                    .getWarehouses()
                    .get(i);

            assertThat(totalProductByWarehouseDto.getWarehouseCode())
                    .isEqualTo(generatedTotalProductByWarehouseDto.getWarehouseCode());
            assertThat(totalProductByWarehouseDto.getTotalQuantity())
                    .isEqualTo(generatedTotalProductByWarehouseDto.getTotalQuantity());
        }

        verify(batchRepository, atLeastOnce()).findBatchByProductId(product.getId());
    }

    @Test
    void listProductByWarehouse_WhenBatchListIsEmptyTest() {
        List<Batch> batchList = new ArrayList<>();
        BDDMockito.when(batchRepository.findBatchByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(batchList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ListProductByWarehouseDto listProductByWarehouseDto = productService.listProductByWarehouse(
                    Generators.getProduct().getId()
            );
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid batch stock for product %d",
                Generators.getProduct().getId()));

        verify(batchRepository, atLeastOnce()).findBatchByProductId(Generators.getProduct().getId());
    }

    // Testes referentes aos endpoints criados no requisito 06

    @Test
    @DisplayName("Creates a new product when the product doesn't exists.")
    void createNewProduct_whenProductDoesntExist() {

        List<GenericProductOutputDto> newProductsOutputList = GeneratorsRequirementSix.createNewProductsOutputList();
        List<NewProductDto> newProductInputList = GeneratorsRequirementSix.createNewProductsList();
        Product savedProduct = GeneratorsRequirementSix.productWithId1();

        BDDMockito.when(productRepository.save((ArgumentMatchers.any(Product.class))))
                .thenReturn(savedProduct);

        BDDMockito.when(productRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(null);

        List<GenericProductOutputDto> result = productService.createNewProduct(newProductInputList);

        assertThat(result.size() >= 1).isTrue();
        assertThat(result.get(0).getName()).isEqualTo(newProductsOutputList.get(0).getName());
        assertThat(result.get(0).getCategoryName()).isEqualTo(newProductsOutputList.get(0).getCategoryName());

        verify(productRepository, atLeastOnce()).save(ArgumentMatchers.any(Product.class));
        verify(productRepository, atLeastOnce()).findByName(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("Throw exception when the product already exists.")
    void throwException_whenProductAlreadyExists() {

        List<NewProductDto> newProductInputList = GeneratorsRequirementSix.createNewProductSingleList();
        Product savedProduct = GeneratorsRequirementSix.productWithId1();

        BDDMockito.when(productRepository.save((ArgumentMatchers.any(Product.class))))
                .thenReturn(savedProduct);

        BDDMockito.when(productRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(savedProduct);

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            productService.createNewProduct(newProductInputList);
        });

        assertThat(exception.getMessage()).isEqualTo(("The product [Maçã] already exists."));
        verify(productRepository, atLeastOnce()).findByName(ArgumentMatchers.anyString());
        verify(productRepository, never()).save(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("Find products by category name when products exists.")
    void findProductsByCategoryName_whenProductsExists() {
        BDDMockito.when(productRepository.findByCategoryName(ArgumentMatchers.anyString()))
                .thenReturn(GeneratorsRequirementSix.productList());

        List<GenericProductOutputDto> products = productService.findProductsByCategoryName(GeneratorsRequirementSix.productWithId3().getCategoryName());

        assertThat((products.size() >= 1)).isTrue();
        assertThat(products).isNotNull();
    }

    @Test
    @DisplayName("Throw exception when products with the category name doesn't exists")
    void throwException_whenProductsByCategoryNameDoesntExists() {

        String productNotFound = GeneratorsRequirementSix.productWithId2().getCategoryName();

        BDDMockito.when(productRepository.findByCategoryName(ArgumentMatchers.anyString()))
                .thenReturn(GeneratorsRequirementSix.emptyProductList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.findProductsByCategoryName(GeneratorsRequirementSix.productWithId2().getCategoryName()));

        assertThat(exception.getMessage()).isEqualTo(String.format("There is not existing products for this category: %s.", productNotFound));
        verify(productRepository, atLeastOnce()).findByCategoryName(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("Do the partial update when product already exists")
    void partialUpdateOfAProduct_whenProductAlreadyExists() {

        Long productId = 1L;

        Map<String, String> changesToUpdate = GeneratorsRequirementSix.updateChanges();

        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(GeneratorsRequirementSix.productWithId1()));

        BDDMockito.when(productRepository.save(ArgumentMatchers.any(Product.class)))
                .thenReturn(GeneratorsRequirementSix.updatedProduct());

        UpdateProductOutputDto updateProductOutputDto = productService.partialUpdateOfProduct(productId, changesToUpdate);

        assertThat(updateProductOutputDto.getUpdatedProduct().getName()).isEqualTo(changesToUpdate.get("name"));
        assertThat(updateProductOutputDto.getUpdatedProduct().getId()).isEqualTo(productId);
        assertThat(updateProductOutputDto.getUpdatedProduct().getCategoryName()).isEqualTo(changesToUpdate.get("categoryName"));

        verify(productRepository, atLeastOnce()).save(ArgumentMatchers.any(Product.class));
        verify(productRepository, atLeastOnce()).findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Throw exception when product with the id doesn't exists")
    void partialUpdateOfAProduct_throwException_whenProductWithIdDoesntExists() {

        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Long productId = 1L;

        Map<String, String> changesToUpdate = GeneratorsRequirementSix.updateChanges();

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                productService.partialUpdateOfProduct(productId, changesToUpdate));

            assertThat(exception.getMessage()).isEqualTo(String.format("Not exists a product with this id: %d", productId));
            verify(productRepository, atLeastOnce()).findById(ArgumentMatchers.anyLong());
            verify(productRepository, never()).save(ArgumentMatchers.any(Product.class));
    }
}
