package edu.hanu.mycart.adapter;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

import edu.hanu.mycart.R;
import edu.hanu.mycart.db.CartRepository;
import edu.hanu.mycart.db.DbHelper;
import edu.hanu.mycart.models.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    public List<CartItem> cartItems;
    Context context;
    CartRepository cartRepository;
    int Total;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View itemView = li.inflate(R.layout.cart_item, parent, false);
        cartRepository = CartRepository.getInstance(context);
        cartItems = cartRepository.all();
        TotalPrice();
        TextView TotalPrice = parent.findViewById(R.id.total);
        System.out.println(TotalPrice);
        TotalPrice.setText("Total Price: "+Total);
        return new CartHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem, position);

    }

    private void TotalPrice() {
        int res = 0;
        for (CartItem item: cartItems) {
            res+=item.getTotalPrice();
        }
        Total = res;
        System.out.println(Total);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        public CartHolder(@NonNull View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(CartItem cartItem, int position) {
            TextView itemName, itemQuantity, itemPrice;
            ImageView itemImg;
            ImageButton itemDelete;
            itemName = itemView.findViewById(R.id.product_name);
            itemQuantity = itemView.findViewById(R.id.quantity);
            itemPrice = itemView.findViewById(R.id.total_price);
            itemImg = itemView.findViewById(R.id.imageView);
            itemName.setText(cartItem.getProduct().getName());
            itemQuantity.setText("Quantity: "+String.valueOf(cartItem.getQuantity()));
            DecimalFormat formatter = new DecimalFormat("#,###");
            String TotalPrice = formatter.format(cartItem.getTotalPrice());
            itemPrice.setText("Total Price: "+TotalPrice);
            Context imgContext = itemImg.getContext();
            //Set src for imageView
            String url = cartItem.getProduct().getThumbnail();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(imgContext)
                            .load(url)
                            .into(itemImg);
                }
            });
//            Executor executor = Executors.newSingleThreadExecutor();
//            Glide.with(imgContext)
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
//                            Toast.makeText(imgContext, "Oops, failed to fetch images", Toast.LENGTH_SHORT).show();
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
//                    .into(itemImg);
            itemImg.setScaleType(CENTER_CROP);
            itemImg.setClipToOutline(true);
            itemImg.setBackgroundResource(R.drawable.product_item);
            itemDelete = itemView.findViewById(R.id.delete);
            itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItems.remove(position);
                    notifyItemRemoved(position);
                    cartRepository = CartRepository.getInstance(context);
                    cartRepository.deleteItem(cartItem.getCartId());
                    v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
        }
    }
}
