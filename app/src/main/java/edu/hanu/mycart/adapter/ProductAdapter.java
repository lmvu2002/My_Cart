package edu.hanu.mycart.adapter;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.hanu.mycart.R;
import edu.hanu.mycart.db.CartRepository;
import edu.hanu.mycart.models.CartItem;
import edu.hanu.mycart.models.Product;
import kotlin.Unit;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    public List<Product> products;
    List<CartItem> cartItems;
    CartRepository cartRepository;
    int Total;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        cartRepository = CartRepository.getInstance(parent.getContext());
        cartItems = cartRepository.all();
        View itemView = li.inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product, position);
    }

    public void filterList(List<Product> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Product product, int position) {
            TextView productName = itemView.findViewById(R.id.product_name);
            TextView productPrice = itemView.findViewById(R.id.product_price);
            ImageView productImg = itemView.findViewById(R.id.imageView);
            productName.setText(product.getName());
            DecimalFormat formatter = new DecimalFormat("#,###");
            String UnitPrice = formatter.format(product.getUnitPrice());
            productPrice.setText("VND"+ UnitPrice);
            Context context = productImg.getContext();
            //Set src for imageView
            String url = product.getThumbnail();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context)
                            .load(url)
                            .into(productImg);
                }
            });
//            Executor executor = Executors.newSingleThreadExecutor();
//            Glide.with(context)
//                    .load(url)
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(
//                                @Nullable GlideException e,
//                                Object model,
//                                Target<Drawable> target,
//                                boolean isFirstResource
//                        ) {
//                            // Handle error
//                            Toast.makeText(context, "Oops, failed to fetch images", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(
//                                Drawable resource,
//                                Object model,
//                                Target<Drawable> target,
//                                DataSource dataSource,
//                                boolean isFirstResource
//                        ) {
//                            // Handle success
//                            System.out.println(url+" OK");
//                            return false;
//                        }
//                    })
//                    .into(productImg);
            itemView.setBackgroundResource(R.drawable.product_item);
            productImg.setScaleType(CENTER_CROP);
            productImg.setClipToOutline(true);
            productImg.setBackgroundResource(R.drawable.product_item);

            ImageButton addToCartBtn = itemView.findViewById(R.id.addToCart);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAdd(product.getId())) {//If the product is already in the cart
                        int quantity = cartRepository.getQuantity(product.getId());
                        System.out.println(quantity);
                        cartRepository.editCartQuantity(product.getId(), quantity + 1);
                        cartItems = cartRepository.all();
                    } else {
                        cartRepository.addCart(product.getId(), product.getName(), product.getCategory(), product.getUnitPrice(), product.getThumbnail(), 1);
                        cartItems = cartRepository.all();
                    }
                    v.performHapticFeedback(1);

                }

                private boolean isAdd(Long productId) {
                    System.out.println(productId);
                    if (cartItems == null || cartItems.size() == 0) {
                        System.out.println("False");
                        return false;
                    }
                    for(CartItem item: cartRepository.all()) {
                        System.out.println("Cart Item: "+item.getProduct().getId());
                        if(Objects.equals(item.getProduct().getId(), productId)) {
                            System.out.println("True");
                            return true;
                        }
                    }
                    return false;
                }
            });
            /*ImageButton addCart = itemView.findViewById(R.id.my_cart);
            addCart.setOnClickListener();*/

        }
    }
}
