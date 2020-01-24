package com.skyegibney.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        todoListView = (RecyclerView)findViewById(R.id.todoList);
        todoInputView = (EditText)findViewById(R.id.todoInput);
        addButtonView = (Button)findViewById(R.id.addButton);

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

        itemsAdapter = new ItemsAdapter(todoItems, onLongClickListener);
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
