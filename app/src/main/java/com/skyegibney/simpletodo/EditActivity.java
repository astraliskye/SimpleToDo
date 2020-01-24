package com.skyegibney.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity
{
    EditText inputView;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        inputView = findViewById(R.id.editTodoInput);
        doneButton = findViewById(R.id.btnDone);

        getSupportActionBar().setTitle("Edit Item");

        inputView.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

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
