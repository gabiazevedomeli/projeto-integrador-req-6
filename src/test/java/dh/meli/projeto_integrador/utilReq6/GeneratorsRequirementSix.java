package dh.meli.projeto_integrador.utilReq6;

import dh.meli.projeto_integrador.dto.dtoInput.NewProductDto;
import dh.meli.projeto_integrador.dto.dtoOutput.GenericProductOutputDto;
import dh.meli.projeto_integrador.dto.dtoOutput.UpdateProductOutputDto;
import dh.meli.projeto_integrador.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorsRequirementSix {

    public static GenericProductOutputDto validProductOutputDto1() {
        return GenericProductOutputDto.builder()
                .name("Maçã")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static GenericProductOutputDto validProductOutputDto2() {
        return GenericProductOutputDto.builder()
                .name("Iogurte")
                .type("Refrigerado")
                .categoryName("Laticínios")
                .price(20.1)
                .build();
    }

    public static GenericProductOutputDto validProductByCategory1() {
        return GenericProductOutputDto.builder()
                .name("Maçã")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static GenericProductOutputDto validProductByCategory2() {
        return GenericProductOutputDto.builder()
                .name("Uva")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static NewProductDto validNewProductDto1() {
        return NewProductDto.builder()
                .name("Maçã")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static NewProductDto validNewProductDto2() {
        return NewProductDto.builder()
                .name("Iogurte")
                .type("Refrigerado")
                .categoryName("Laticínios")
                .price(20.1)
                .build();
    }

    public static List<NewProductDto> createNewProductsList() {
        List<NewProductDto> newProductsList = new ArrayList<>();
        newProductsList.add(validNewProductDto1());
        newProductsList.add(validNewProductDto2());

        return newProductsList;
    }

    public static List<NewProductDto> createNewProductSingleList() {
        List<NewProductDto> newProductsList = new ArrayList<>();
        newProductsList.add(validNewProductDto1());

        return newProductsList;
    }

    public static Product productWithId1() {
        return Product.builder()
                .id(1L)
                .name("Maçã")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static Product productWithId2() {
        return Product.builder()
                .id(2L)
                .name("Iogurte")
                .type("Refrigerado")
                .categoryName("Laticínios")
                .price(20.1)
                .build();
    }

    public static Product productWithId3() {
        return Product.builder()
                .id(3L)
                .name("Uva")
                .type("Fresco")
                .categoryName("Frutas")
                .price(20.1)
                .build();
    }

    public static UpdateProductOutputDto updateProductOutputDto() {
        return UpdateProductOutputDto.builder()
                .updateMessage(String.format("The product %s was successfully updated!", validProductOutputDto1().getName()))
                .updatedProduct(validProductOutputDto1())
                .build();
    }

    public static Map<String, String> updateChanges() {
        Map<String, String> changesToUpdate = new HashMap<>();

        changesToUpdate.put("name", "Iogurte");
        changesToUpdate.put("type", "Refrigerados");
        changesToUpdate.put("categoryName", "Laticínios");

        return changesToUpdate;
    }

    public static Product updatedProduct() {
        return Product.builder()
                .id(1L)
                .name("Iogurte")
                .type("Refrigerado")
                .categoryName("Laticínios")
                .price(20.1)
                .build();
    }

    public static List<GenericProductOutputDto> createNewProductsOutputList() {
        List<GenericProductOutputDto> newProductsList = new ArrayList<>();
        newProductsList.add(validProductOutputDto1());
        newProductsList.add(validProductOutputDto2());

        return newProductsList;
    }

    public static List<GenericProductOutputDto> listOfProductsByCategoryName() {
        List<GenericProductOutputDto> newProductsList = new ArrayList<>();
        newProductsList.add(validProductByCategory1());
        newProductsList.add(validProductByCategory2());
        return newProductsList;
    }

    public static List<Product> productList() {
        List<Product> productList = new ArrayList<>();
        productList.add(productWithId1());
        productList.add(productWithId3());

        return productList;
    }

    public static List<Product> emptyProductList() {
        return new ArrayList<>();
    }
}
