package edu.hanu.mycart;

import java.security.Policy;
import java.util.*;

import edu.hanu.mycart.models.Product;
import retrofit2.Call;
import retrofit2.http.GET;
//https://hanu-congnv.github.io/mpr-cart-api/products.json
public interface JsonPlaceHolder {
    @GET("products.json")
    Call<List<Product>> getProduct();
}
