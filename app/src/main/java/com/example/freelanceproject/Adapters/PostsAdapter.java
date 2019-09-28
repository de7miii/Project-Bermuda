package com.example.freelanceproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.freelanceproject.BusinessModel.Client;
import com.example.freelanceproject.BusinessModel.Freelancer;
import com.example.freelanceproject.BusinessModel.User;
import com.example.freelanceproject.R;
import com.example.freelanceproject.Util.DiffUtil.ClientsListDiffCallback;
import com.example.freelanceproject.Util.DiffUtil.FreelancerListDiffCallback;
import com.example.freelanceproject.Util.DiffUtil.ObjectListDiffCallback;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final String TAG = PostsAdapter.class.getSimpleName();

    private static final int ITEM_TYPE_CLIENT = -1;
    private static final int ITEM_TYPE_FREELANCER = -2;

    private ArrayList<Client> mClientsList;
    private ArrayList<Freelancer> mFreelancersList;

    private ArrayList<Object> items = new ArrayList<>();
    private ArrayList<Object> itemsFull = new ArrayList<>(items);

    private Context mContext;

    public PostsAdapter(Context context) {
        mContext = context;
    }

    public PostsAdapter(Context context, ArrayList<Client> clientsList, ArrayList<Freelancer> freelancersList) {
        mContext = context;
        items.addAll(clientsList);
        items.addAll(freelancersList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_view_home, parent, false);

        if (viewType == ITEM_TYPE_CLIENT){
            return new ClientViewHolder(view);
        }else{
            return new FreelancerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Object currentItem = items.get(position);
        if (holder instanceof FreelancerViewHolder){
            ((FreelancerViewHolder) holder).bind((Freelancer) currentItem);
        }else if (holder instanceof ClientViewHolder){
            ((ClientViewHolder) holder).bind((Client) currentItem);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof  Client){
            return ITEM_TYPE_CLIENT;
        }else {
            return ITEM_TYPE_FREELANCER;
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull FreelancerViewHolder holder, int position) {
//
//        Freelancer currentFreelancer = mFreelancersList.get(position);
//
//        holder.nameTxtView.setText(currentFreelancer.getName());
//        holder.skillsTxtView.setText(getSkillsString(currentFreelancer.getSkills()));
//        holder.credScoreTxtView.setText(Integer.toString(currentFreelancer.getCredScore()));
//        holder.minRatesTxtView.setText(Integer.toString(currentFreelancer.getRatesMin()));
//        holder.maxRatesTxtView.setText(Integer.toString(currentFreelancer.getRatesMax()));
//        holder.descTxtView.setText(currentFreelancer.getDescription());
//
//        Glide.with(mContext).load("https://via.placeholder.com/350x250.png?text=Placeholder+Image")
//                .into(holder.displayImg);
//    }

    @Override
    public int getItemCount() {
        return items!= null ? items.size() : 0;
    }

    class FreelancerViewHolder extends RecyclerView.ViewHolder{
        private ImageView displayImg;
        private MaterialTextView nameTxtView, skillsTxtView, credScoreTxtView, minRatesTxtView, maxRatesTxtView, descTxtView;

        public FreelancerViewHolder(@NonNull View itemView) {
            super(itemView);
            displayImg = itemView.findViewById(R.id.display_picture_image_view);
            nameTxtView = itemView.findViewById(R.id.user_name_text_view);
            skillsTxtView = itemView.findViewById(R.id.skills_text_view);
            credScoreTxtView = itemView.findViewById(R.id.cred_score_text_view);
            minRatesTxtView = itemView.findViewById(R.id.min_rates_text_view);
            maxRatesTxtView = itemView.findViewById(R.id.max_rates_text_view);
            descTxtView = itemView.findViewById(R.id.description_text_view);
        }

        public void bind(Freelancer freelancer){
            nameTxtView.setText(freelancer.getName());
            skillsTxtView.setText(getSkillsString(freelancer.getSkills()));
            credScoreTxtView.setText("CreadScore: " + Integer.toString(freelancer.getCredScore()));
            minRatesTxtView.setText(Integer.toString(freelancer.getRatesMin()) + Currency.getInstance(Locale.US).getSymbol());
            maxRatesTxtView.setText(Integer.toString(freelancer.getRatesMax()) + Currency.getInstance(Locale.US).getSymbol());
            descTxtView.setText(freelancer.getDescription());
            Glide.with(mContext).load("https://via.placeholder.com/350x250.png?text=Placeholder+Image")
                    .into(displayImg);
        }
    }

    class ClientViewHolder extends RecyclerView.ViewHolder{
        private ImageView displayImg;
        private MaterialTextView nameTxtView, skillsTxtView, credScoreTxtView, minRatesTxtView, maxRatesTxtView, descTxtView;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            displayImg = itemView.findViewById(R.id.display_picture_image_view);
            nameTxtView = itemView.findViewById(R.id.user_name_text_view);
            skillsTxtView = itemView.findViewById(R.id.skills_text_view);
            credScoreTxtView = itemView.findViewById(R.id.cred_score_text_view);
            minRatesTxtView = itemView.findViewById(R.id.min_rates_text_view);
            maxRatesTxtView = itemView.findViewById(R.id.max_rates_text_view);
            descTxtView = itemView.findViewById(R.id.description_text_view);
        }

        public void bind(Client client){
            nameTxtView.setText(client.getName());
            skillsTxtView.setText(" ");
            credScoreTxtView.setText("CreadScore: " + Integer.toString(client.getCredScore()));
            minRatesTxtView.setText(Integer.toString(client.getRatesMin()) + Currency.getInstance(Locale.US).getSymbol());
            maxRatesTxtView.setText(Integer.toString(client.getRatesMax()) + Currency.getInstance(Locale.US).getSymbol());
            descTxtView.setText(client.getDescription());
            Glide.with(mContext).load("https://via.placeholder.com/350x250.png?text=Placeholder+Image")
                    .into(displayImg);
        }
    }

    private String getSkillsString(ArrayList<String> skills){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s :
                skills) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
        }

    public void setClientsList(ArrayList<Client> newClientsList){
        mClientsList = newClientsList;
        items.addAll(newClientsList);
        itemsFull.addAll(newClientsList);
        Log.i(TAG, "setClientsList: " + items.size());
        notifyDataSetChanged();
    }

    public void setFreelancersList(ArrayList<Freelancer> newFreelancersList){
        mFreelancersList = newFreelancersList;
        items.addAll(newFreelancersList);
        itemsFull.addAll(newFreelancersList);
        Log.i(TAG, "setFreelancersList: " + items.size());
        notifyDataSetChanged();
    }

    public void updateClientList(ArrayList<Client> newClientsList){
        ArrayList<Client> oldClientsList = mClientsList;
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ObjectListDiffCallback(oldClientsList, newClientsList));
        result.dispatchUpdatesTo(this);
        for (Object obj :
                newClientsList) {
            if (!items.contains(obj)){
                items.add(obj);
                Log.i(TAG, "updateClientList: " + items.size());
            }
        }
    }

    public void updateFreelancersList(ArrayList<Freelancer> newFreelancersList){
        ArrayList<Freelancer> oldFreelancerList = mFreelancersList;
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ObjectListDiffCallback(oldFreelancerList, newFreelancersList));
        result.dispatchUpdatesTo(this);
        for (Object obj :
                newFreelancersList) {
            if (!items.contains(obj)){
                items.add(obj);
                Log.i(TAG, "updateFreelancersList: " + items.size());
            }
        }
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Object> filteredList = new ArrayList<>();
            Log.i(TAG, "performFiltering: " + constraint);
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(itemsFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Object obj :
                        itemsFull) {
                    if (((User)obj).getUserName().toLowerCase().contains(filterPattern)){
                        Log.i(TAG, "performFiltering: Username: " + ((User)obj).getUserName());
                        filteredList.add(obj);
                    }
                    if (((User)obj).getName().toLowerCase().contains(filterPattern)){
                        Log.i(TAG, "performFiltering: Name: " + ((User)obj).getName());
                        filteredList.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
