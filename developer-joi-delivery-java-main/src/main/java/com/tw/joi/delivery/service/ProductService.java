package com.tw.joi.delivery.service;

import com.tw.joi.delivery.domain.GroceryProduct;
import com.tw.joi.delivery.seedData.SeedData;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final List<GroceryProduct> products = SeedData.groceryProducts;

    public GroceryProduct getProduct(String productId, String outletId) {
        Objects.requireNonNull(productId, "productId cannot be null");
        Objects.requireNonNull(outletId, "outletId cannot be null");
        return products.stream()
            .filter(groceryProduct ->
                        groceryProduct.getProductId().equals(productId)
                            && groceryProduct.getStore().getOutletId().equals(outletId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Product not found for productId=" + productId + ", outletId=" + outletId));
    }

}
