package com.example.freelanceproject.Util.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.example.freelanceproject.BusinessModel.Freelancer;

import java.util.ArrayList;

public class FreelancerListDiffCallback extends DiffUtil.Callback {

    private ArrayList<Freelancer> mOldClients;
    private ArrayList<Freelancer> mNewClients;

    public FreelancerListDiffCallback(ArrayList<Freelancer> oldClients, ArrayList<Freelancer> newClients) {
        mOldClients = oldClients;
        mNewClients = newClients;
    }

    @Override
    public int getOldListSize() {
        return mOldClients != null ? mOldClients.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewClients != null ? mNewClients.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldClients.get(oldItemPosition).getId().equals(mNewClients.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mNewClients.get(newItemPosition).equals(mOldClients.get(oldItemPosition));
    }

    // TODO: 9/10/2019 implement the payload function to determine what has changed in an item

}
