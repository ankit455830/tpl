package com.tw.joi.delivery.domain;


import lombok.Builder;
import lombok.Getter;


@Getter
public class GroceryStore extends Outlet {

    @Builder
    public GroceryStore(String name, String description, String outletId) {
        super(name, description, outletId);
    }

}
