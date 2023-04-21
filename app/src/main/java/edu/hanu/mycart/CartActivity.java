package edu.hanu.mycart;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import edu.hanu.mycart.adapter.CartAdapter;
import edu.hanu.mycart.db.CartRepository;
import edu.hanu.mycart.db.DbHelper;
import edu.hanu.mycart.models.CartItem;

public class CartActivity extends AppCompatActivity {
    CartRepository cartRepository;
    List<CartItem> cartItems;
    RecyclerView rvCart;
    CartAdapter adapter;

    int TotalPriceOfCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRepository = CartRepository.getInstance(this);
        rvCart = findViewById(R.id.rvCartItems);
        cartItems = cartRepository.all();
        adapter = new CartAdapter(cartItems);
        rvCart.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(linearLayoutManager);

    }
}