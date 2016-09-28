package com.example.admin.myapplication;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //this method will show how many orders made
    public void display(int number)
    {
        TextView quantitytextview = (TextView)findViewById(R.id.quantity_text_view);
        quantitytextview.setText("" + number);
    }

    //this method will update the price of the order
    public void price(int number)
    {
        TextView pricetextview = (TextView)findViewById(R.id.price_text_view);
        pricetextview.setText(NumberFormat.getCurrencyInstance().format(number));

    }
    //call this method to increment the quantity
    public void increment(View view)
    {
        if(quantity==100)
        {
            Toast.makeText(this,"You can not have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }
    //call this method to decrement the quantity
    public void decrement(View view)
    {

        if(quantity==1)
        {
            Toast.makeText(this,"You can not have less than one coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    //this method will display message
    public void displayMessage(String message)
    {
        TextView pricetextview = (TextView)findViewById(R.id.price_text_view);
        pricetextview.setText(message);
    }
    //this method will calculate price of an order
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice = 5;

        //add one if the whipped cream is checked
        if(addWhippedCream)
        {
           basePrice = basePrice + 1;
        }
        //add two if the add chocolate is checked
        if(addChocolate)
        {
            basePrice = basePrice + 2;
        }

        return basePrice * quantity;
    }
    //this method will create summary order
    private String createOrderSummary(String name,int price,boolean whippedCream,boolean chocolate)
    {
       String priceMessage = "Name :" + name;
        priceMessage = priceMessage + "\nAdd whipped cream?" + whippedCream;
        priceMessage = priceMessage + "\nAdd chocolate?" + chocolate;
        priceMessage = priceMessage + "\nQuantity :"+ quantity;
        priceMessage = priceMessage + "\nTotal R:" + price;
        priceMessage = priceMessage + "\nThank you";
        return priceMessage;
    }
    //this method will submit the orders
    public void submitOrder(View view)
    {
        CheckBox whippedcream = (CheckBox)findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedcream.isChecked();

        EditText namefield = (EditText)findViewById(R.id.name);
        String name = namefield.getText().toString();
        CheckBox chocolates = (CheckBox)findViewById(R.id.chocolate);
        boolean hasChocolate = chocolates.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String message = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(message);


}}
