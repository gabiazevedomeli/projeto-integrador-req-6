package dh.meli.projeto_integrador.controller;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.*;
import dh.meli.projeto_integrador.service.ProductService;
import dh.meli.projeto_integrador.util.Generators;
import dh.meli.projeto_integrador.utilReq6.GeneratorsRequirementSix;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @MockBean
    ProductService service;

    @Test
    void listAllProducts_returnListOfProducts_whenSuccessTest() throws Exception {
        List<ProductOutputDto> list = Generators.productDtoList();
        BDDMockito.when(service.getAllProducts())
                .thenReturn(list);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProductDto1().getName())));
    }


    @Test
    void listProductByCategoryTest() throws Exception {
        List<ProductOutputDto> list = Generators.productDtoList();
        BDDMockito.when(service.getProductsByCategory(anyString()))
                .thenReturn(list);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/{category}",
                Generators.validProductDto1().getType())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(list.size())))
                .andExpect(jsonPath("$[0].name",
                        CoreMatchers.is(Generators.validProductDto1().getName())));
    }

    @Test
    void listProductByWarehouseTest() {
        BDDMockito.when(productService.listProductByWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(Generators.getListProductByWarehouseDto());

        long id = 1;

        ResponseEntity<ListProductByWarehouseDto> response = productController.listProductByWarehouse(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getProductId())
                .isEqualTo(Generators.getListProductByWarehouseDto().getProductId());
        assertThat(response.getBody().getWarehouses().get(0).getWarehouseCode())
                .isEqualTo(Generators.getListProductByWarehouseDto().getWarehouses().get(0).getWarehouseCode());

        verify(productService, atLeastOnce()).listProductByWarehouse(id);
    }

    @Test
    void getProductBatches() throws Exception {
        ProductStockDto productStockDto = Generators.getProductStockDtos();
        BDDMockito.when(service.getProductBatchProps(anyLong(), anyChar()))
                .thenReturn(productStockDto);

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/list/{id}",
                Generators.getProduct().getId())
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())

                .andExpect(jsonPath("$.batchStockDto.size()",
                        CoreMatchers.is(productStockDto.getBatchStockDto().size())))
                .andExpect(jsonPath("$.name",
                        CoreMatchers.is(productStockDto.getName())));
    }

    // Testes dos métodos implementados no requisito 06

    @Test
    void createNewProduct_whenProductsDoesntExists() {
        BDDMockito.when(productService.createNewProduct(ArgumentMatchers.anyList()))
                .thenReturn(GeneratorsRequirementSix.createNewProductsOutputList());

        List<NewProductDto> newProductsList = GeneratorsRequirementSix.createNewProductsList();

        ResponseEntity<List<GenericProductOutputDto>> response = productController.createNewProducts(newProductsList);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get(0).getName()).isEqualTo(newProductsList.get(0).getName());

        verify(productService, atLeastOnce()).createNewProduct(ArgumentMatchers.anyList());
    }

    @Test
    void updateProduct_whenProductsExists() {

        Long productId = 1L;

        Map<String, String> changesToUpdate = GeneratorsRequirementSix.updateChanges();

        BDDMockito.when(productService.partialUpdateOfProduct(productId, changesToUpdate))
                .thenReturn(GeneratorsRequirementSix.updateProductOutputDto());

        ResponseEntity<UpdateProductOutputDto> updatedProduct = productController.partialUpdateProduct(productId, changesToUpdate);

        assertThat(updatedProduct.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedProduct.getBody()).isNotNull();
        assertThat(updatedProduct.getBody().getUpdateMessage()).isEqualTo(String.format("The product %s was successfully updated!", updatedProduct.getBody().getUpdatedProduct().getName()));

        verify(productService, atLeastOnce()).partialUpdateOfProduct(productId, changesToUpdate);
    }

    @Test
    void findProductsByCategoryName_whenProductsWithCategoryExists() {

        BDDMockito.when(productService.findProductsByCategoryName(ArgumentMatchers.anyString()))
                .thenReturn(GeneratorsRequirementSix.listOfProductsByCategoryName());

        String category = "Frutas";

        ResponseEntity<List<GenericProductOutputDto>> listProductsByCategory = productController.getProductsByCategoryName(category);

        assertThat(listProductsByCategory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listProductsByCategory.getBody()).isNotNull();
        assertThat(listProductsByCategory.getBody().get(0).getCategoryName()).isEqualTo(category);

        verify(productService, atLeastOnce()).findProductsByCategoryName(category);
    }
}