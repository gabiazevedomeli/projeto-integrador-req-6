package dh.meli.projeto_integrador.utils;

import dh.meli.projeto_integrador.dto.dtoInput.ProductDto;
import dh.meli.projeto_integrador.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GenerateProduct {
    public static Product newProduct1() {
        return Product.builder()
                .id(1)
                .categoryName("Frutas")
                .name("Morango")
                .type("Fresco")
                .price(5.0)
                .build();
    }

    public static ProductDto newProductDto() {
        return ProductDto.builder()
                .productId(1L)
                .quantity(10)
                .build();
    }

    public static List<ProductDto> newProductListDto(){
        List<ProductDto> products = new ArrayList<>();
        products.add(newProductDto());

        return products;
    }
}
