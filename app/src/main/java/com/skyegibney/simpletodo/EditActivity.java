package com.skyegibney.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity
{
    // Declare views
    EditText inputView;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Find views
        inputView = findViewById(R.id.editTodoInput);
        doneButton = findViewById(R.id.btnDone);

        // Change title
        getSupportActionBar().setTitle("Edit Item");

        // Populate text field with current item
        inputView.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // When done editing, the user clicks on the done button which sends data back
        // to the main activity to save
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();

                intent.putExtra(MainActivity.KEY_ITEM_TEXT, inputView.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,
                        getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
