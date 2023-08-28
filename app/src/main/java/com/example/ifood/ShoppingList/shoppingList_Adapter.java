package com.example.ifood.ShoppingList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class shoppingList_Adapter extends RecyclerView.Adapter<shoppingList_Adapter.ItemViewHolder> {

    private List<shoppingList_Model> shoppingList;
    private String uid;  //gets uid from the main activity
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(); // initialize DatabaseReference here

    // Added uid to the constructor
    public shoppingList_Adapter(List<shoppingList_Model> shoppingList, String uid) {
        this.shoppingList = shoppingList;
        this.uid = uid;  // set the uid
    }

    @NonNull
    @Override
    public shoppingList_Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_itemview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shoppingList_Adapter.ItemViewHolder holder, int position) {
        shoppingList_Model item = shoppingList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAbsoluteAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    shoppingList_Model itemToDelete = shoppingList.get(currentPosition);

                    shoppingList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);

                    DatabaseReference itemToDeleteReference = mDatabaseReference.child("Users").child(uid).child("shoppingList").child(itemToDelete.getName());
                    itemToDeleteReference.removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.mainFeed_name);
            quantityTextView = itemView.findViewById(R.id.asdads);
        }
    }
}
