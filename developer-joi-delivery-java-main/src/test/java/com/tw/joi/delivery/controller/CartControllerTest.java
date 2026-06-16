package com.tw.joi.delivery.controller;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tw.joi.delivery.domain.Cart;
import com.tw.joi.delivery.dto.request.AddProductRequest;
import com.tw.joi.delivery.service.CartService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.NoSuchElementException;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldAddTheRequestedProductToTheCart() throws Exception {

        String url = "/cart/product";
        AddProductRequest addProductRequest = new AddProductRequest();
        addProductRequest.setProductId("product101");
        addProductRequest.setUserId("user101");
        addProductRequest.setOutletId("store101");

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(addProductRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn400WhenAddProductRequestIsInvalid() throws Exception {
        String url = "/cart/product";

        String invalidRequestJson = "{\"outletId\":\"store101\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(invalidRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(cartService);

    }

    @Test
    void shouldReturnTheCart() throws Exception {
        String url = "/cart/view?userId={userId}";
        String userId = "user101";
        Cart cart = Cart.builder()
                .cartId("cart101")
                .build();
        when(cartService.getCartForUser(userId)).thenReturn(cart);

        mockMvc.perform(MockMvcRequestBuilders.get(url, "user101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Is.is("cart101")));
    }

    @Test
    void shouldReturn404WhenCartNotFound() throws Exception {
        String url = "/cart/view?userId={userId}";
        String userId = "missing-user";
        when(cartService.getCartForUser(userId)).thenThrow(new NoSuchElementException("Cart not found for userId: missing-user"));

        mockMvc.perform(MockMvcRequestBuilders.get(url, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is("Cart not found for userId: missing-user")));
    }
}