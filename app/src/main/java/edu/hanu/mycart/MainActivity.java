package edu.hanu.mycart;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.hanu.mycart.adapter.ProductAdapter;
import edu.hanu.mycart.db.CartRepository;
import edu.hanu.mycart.db.DbHelper;
import edu.hanu.mycart.models.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    CartRepository cartRepository;
    List<Product> products;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getApplicationContext().deleteDatabase("carts.db");
//        SQLiteDatabase db = new DbHelper(getApplicationContext()).getWritableDatabase();
//        db.delete("carts",null,null);
//        db.execSQL("DROP TABLE "+ "carts");
//        db.close();
        RecyclerView rvProduct = findViewById(R.id.rvProducts);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hanu-congnv.github.io/mpr-cart-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<Product>> callProducts = jsonPlaceHolder.getProduct();
        callProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Response "+response.body());
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                products = response.body();
                productAdapter = new ProductAdapter(products);
                rvProduct.setAdapter(productAdapter);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                rvProduct.setLayoutManager(gridLayoutManager);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
//        MenuItem search = menu.findItem(R.id.actionSearch);
//        SearchView searchView = (SearchView) search.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return false;
//            }
//        });
        return true;
    }

    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }
        if (!filteredList.isEmpty()) {
            productAdapter.filterList(filteredList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cart:
                toCartActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toCartActivity() {
        Intent in = new Intent(this, CartActivity.class);
        //move to Cart Activity
        startActivity(in);
    }
}
