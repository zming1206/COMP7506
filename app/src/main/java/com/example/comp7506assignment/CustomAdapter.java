package com.example.comp7506assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Item> localDataSet;

    private OnClickListener onClickListener;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView institution;
        private final ImageView icon;
        private final FrameLayout row_item;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            icon = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.name);
            institution = (TextView) view.findViewById(R.id.institution);
            row_item = (FrameLayout) view.findViewById(R.id.row_item);
        }

        public FrameLayout getRowItem() {
            return row_item;
        }

        public ImageView getIcon() {
            return icon;
        }

        public TextView getName() {
            return name;
        }

        public TextView getInstitution() {
            return institution;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CustomAdapter(ArrayList<Item> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText(localDataSet.get(position).getName());
        viewHolder.getInstitution().setText(localDataSet.get(position).getInstitution());
        //viewHolder.getIcon().setImageDrawable( R.drawable.catt);

        viewHolder.getRowItem().setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, localDataSet.get(position));
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, Item item);
    }
}
