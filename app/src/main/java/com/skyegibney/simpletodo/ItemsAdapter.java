package com.skyegibney.simpletodo;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Used to display items in the recylcer view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{
    public interface OnLongClickListener
    {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener)
    {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    public ItemsAdapter(List<String> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1,
                parent, false);

        return new ViewHolder(todoView);
    }

    // Binds data to a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        // Grab item at position
        String item = items.get(position);

        // Bind item to view holder
        holder.bind(item);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    // Manages the subviews generated under the recycler view by the adapter
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item)
        {
            textView.setText(item);
            textView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view)
                {
                    longClickListener.onItemLongClicked(getAdapterPosition());

                    return true;
                }
            });
        }
    }
}
