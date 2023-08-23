package com.example.ifood.ShoppingList;

/*
* Recycler View adapter for shopping list
*
* */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;

import java.util.List;

public class shoppingList_Adapter extends RecyclerView.Adapter<shoppingList_Adapter.ItemViewHolder> {

    private List<shoppingList_Model> shoppingList;

    public shoppingList_Adapter(List<shoppingList_Model> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public shoppingList_Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_itemview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shoppingList_Adapter.ItemViewHolder holder, int position) {
        //gets the object on the array list and transfers it into a new singular object called item
        shoppingList_Model item = shoppingList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                shoppingList.remove(position);
//                notifyItemRemoved(position);
                int currentPosition = holder.getAbsoluteAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    shoppingList.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView quantityTextView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }

    }


}
