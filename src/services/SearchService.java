package services;

import enums.ProductCategory;
import models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchService {

    private final Map<Integer, Product> products;

    public SearchService(Map<Integer, Product> products){
        this.products = products;
    }

    public List<Product> searchByName(String name){
        List<Product> items = new ArrayList<>();
        for (int prodId : products.keySet()){
            if (products.get(prodId).getName().equals(name)){
                items.add(products.get(prodId));
            }
        }

        return items;
    }

    public List<Product> searchByCategory(ProductCategory category){
        List<Product> items = new ArrayList<>();
        for (int prodId : products.keySet()){
            if (products.get(prodId).getCategory().equals(category)){
                items.add(products.get(prodId));
            }
        }

        return items;
    }
}
