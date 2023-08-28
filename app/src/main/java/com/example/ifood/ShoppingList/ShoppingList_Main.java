package com.example.ifood.ShoppingList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ifood.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList_Main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private shoppingList_Adapter adapter;
    private List<shoppingList_Model> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_main);

        itemList.add(new shoppingList_Model("lemon","2"));
        itemList.add(new shoppingList_Model("grapes","5"));
        itemList.add(new shoppingList_Model("orange","3"));

        //Add to RTDB

        recyclerView = findViewById(R.id.recyclerViewShoppingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new shoppingList_Adapter(itemList);
        recyclerView.setAdapter(adapter);



        EditText insertProduct = findViewById(R.id.getProductName);
        EditText insertQuantity = findViewById(R.id.getQuantity);


        //Add new Item to Shopping List
        Button addShoppingList = findViewById(R.id.shoppingList_confirm);
        addShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productNameTemp = insertProduct.getText().toString();
                String insertQuantityTemp = insertQuantity.getText().toString();

                itemList.add(new shoppingList_Model(productNameTemp,insertQuantityTemp));
                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });


    }
}