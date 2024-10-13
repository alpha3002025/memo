package io.chagchagchag.example.item_moving_redis.product.entity.factory;

import io.chagchagchag.example.item_moving_redis.product.entity.Product;

import java.util.function.Supplier;

public class ProductFactory {
    /**
     * @param title 프로덕트 명
     * @param viewOrderSupplier : 외부에서 레디스 자료구조를 통해 viewOrder 를 얻어오는 Supplier
     * @return
     */
    public static Product newProduct(String title, Supplier<Integer> viewOrderSupplier){
        return Product.builder()
                .viewOrder(viewOrderSupplier.get())
                .title(title)
                .build();
    }

    public static Product newProduct(Integer viewOrder, String title){
        return Product.builder()
                .viewOrder(viewOrder)
                .title(title)
                .build();
    }
}
