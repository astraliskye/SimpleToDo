package com.skyegibney.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final String KEY_ITEM_TEXT = "todo_item_text";
    public static final String KEY_ITEM_POSITION = "todo_item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> todoItems;

    RecyclerView todoListView;
    EditText todoInputView;
    Button addButtonView;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListView = findViewById(R.id.todoList);
        todoInputView = findViewById(R.id.todoInput);
        addButtonView = findViewById(R.id.addButton);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener()
        {
            public void onItemLongClicked(int position)
            {
                todoItems.remove(position);
                itemsAdapter.notifyItemRemoved(position);

                Toast.makeText(getApplicationContext(), "Item was removed",
                        Toast.LENGTH_SHORT).show();

                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener()
        {
            public void onItemClicked(int position)
            {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(KEY_ITEM_TEXT, todoItems.get(position));
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent, EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(todoItems, onLongClickListener, onClickListener);
        todoListView.setAdapter(itemsAdapter);
        todoListView.setLayoutManager(new LinearLayoutManager(this));

        addButtonView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newItem = todoInputView.getText().toString();
                todoItems.add(newItem);
                itemsAdapter.notifyItemInserted(todoItems.size() - 1);
                todoInputView.setText("");

                Toast.makeText(getApplicationContext(), "Item was added",
                        Toast.LENGTH_SHORT).show();

                saveItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE)
        {
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            todoItems.set(position, data.getStringExtra(KEY_ITEM_TEXT));

            itemsAdapter.notifyItemChanged(position);

            saveItems();

            Toast.makeText(getApplicationContext(), "Successfully updated item",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile()
    {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems()
    {
        try
        {
            todoItems = new ArrayList<>(FileUtils.readLines(getDataFile(),
                    Charset.defaultCharset()));
        }
        catch (IOException e)
        {
            Log.e("MainActivity", "Error reading items.", e);
            todoItems = new ArrayList<>();
        }
    }

    private void saveItems()
    {
        try
        {
            FileUtils.writeLines(getDataFile(), todoItems);
        }
        catch (IOException e)
        {
            Log.e("MainActivity", "Error saving items.", e);
        }
    }
}
