package com.example.freelanceproject.Util.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;

import java.util.ArrayList;

public class ObjectListDiffCallback extends DiffUtil.Callback {
    private ArrayList<Object> mOldItems;
    private ArrayList<Object> mNewItems;

    public ObjectListDiffCallback(ArrayList oldItems, ArrayList newItems) {
        mOldItems = oldItems;
        mNewItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return mOldItems != null? mOldItems.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewItems != null? mNewItems.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        if (mOldItems.get(oldItemPosition) instanceof Freelancer && mNewItems.get(newItemPosition) instanceof Freelancer){
            Freelancer oldItem = (Freelancer) mOldItems.get(oldItemPosition);
            Freelancer newItem = (Freelancer) mNewItems.get(newItemPosition);
            return oldItem.getId().equals(newItem.getId());
        }else{
            Client oldItem = (Client) mOldItems.get(oldItemPosition);
            Client newItem = (Client) mNewItems.get(newItemPosition);
            return oldItem.getId().equals(newItem.getId());
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
    }
}
