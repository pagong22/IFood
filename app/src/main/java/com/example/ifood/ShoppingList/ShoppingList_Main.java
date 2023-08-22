package com.example.ifood.ShoppingList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ifood.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList_Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_main);

        List<reminderModel> reminderList = new ArrayList<reminderModel>();

        reminderList.add(new reminderModel("lemon",5));
        reminderList.add(new reminderModel("Strawberry",1));
        reminderList.add(new reminderModel("Beef Mince",2));

        Toast.makeText(ShoppingList_Main.this, reminderList.get(1).getName(), Toast.LENGTH_LONG).show();


    }
}