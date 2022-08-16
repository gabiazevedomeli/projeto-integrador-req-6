package dh.meli.projeto_integrador.controller;


import dh.meli.projeto_integrador.service.CartService;
import dh.meli.projeto_integrador.util.Generators;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import dh.meli.projeto_integrador.dto.dtoInput.CartDto;
import dh.meli.projeto_integrador.dto.dtoOutput.TotalPriceDto;
import dh.meli.projeto_integrador.dto.dtoOutput.UpdateStatusDto;
import dh.meli.projeto_integrador.exception.ForbiddenException;
import dh.meli.projeto_integrador.model.Cart;
import dh.meli.projeto_integrador.utils.GenerateCart;
import dh.meli.projeto_integrador.utils.GenerateCartDto;
import dh.meli.projeto_integrador.utils.GenerateTotalPrice;
import dh.meli.projeto_integrador.utils.GenerateUpdateStatusDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService service;

    @InjectMocks
    CartController cartController;

    @Mock
    private CartService cartService;

    @Test
    void getCartById_returnCartDto_whenSuccess() throws Exception {

        BDDMockito.when(service.getCartById(anyLong()))
                .thenReturn(Generators.validCartDto());

        ResultActions response = mockMvc.perform(get("/api/v1/fresh-products/orders/{id}", Generators.validCart1().getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.total",
                        CoreMatchers.is(Generators.validCartDto().getTotal())))
                .andExpect(jsonPath("$.customerName",
                        CoreMatchers.is(Generators.validCartDto().getCustomerName())));

        verify(service, atLeastOnce()).getCartById(Generators.validCart1().getId());
    }

    @Test
    @DisplayName("Insert new Cart")
    void create_insertNewCart_WhenNewCart() {
        BDDMockito.when(cartService.createCart(ArgumentMatchers.any(CartDto.class)))
                .thenReturn(GenerateTotalPrice.newTotalPrice());

        CartDto cartDto = GenerateCartDto.newCartDto();

        TotalPriceDto totalPriceDto = GenerateTotalPrice.newTotalPrice();

        ResponseEntity<TotalPriceDto> response = cartController.createNewPurchaseOrder(cartDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalPrice()).isEqualTo(totalPriceDto.getTotalPrice());

        verify(cartService, atLeastOnce()).createCart(ArgumentMatchers.any(CartDto.class));
    }

    @Test
    void create_returnForbiddenException_WhenOrderStatusFinished() {
        CartDto cartDto = GenerateCartDto.newCartDtoFinished();

        ForbiddenException exception = Assertions.assertThrows(ForbiddenException.class, () -> {
            cartController.createNewPurchaseOrder(cartDto);
        });
    }

    @Test
    @DisplayName("Update cart status with success")
    void update_returnUpdateCart_whenCartExists() {
        BDDMockito.when(cartService.updateStatusCart(ArgumentMatchers.anyLong()))
                .thenReturn(GenerateUpdateStatusDto.newUpdateStatusDto());

        Cart cart = GenerateCart.newCartWithId1();

        ResponseEntity<UpdateStatusDto> response = cartController.updatePurchaseOrder(cart.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Cart Finished successfully");

        verify(cartService, atLeastOnce()).updateStatusCart(cart.getId());
    }

}
























