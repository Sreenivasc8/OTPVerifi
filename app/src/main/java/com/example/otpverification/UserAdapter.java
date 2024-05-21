package com.example.otpverification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_data, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView heightTextView;
        private TextView religionTextView;
        private TextView communityTextView;
        private TextView smokeTextView;
        private TextView statusTextView;
        private TextView drinkingTextView;
        private TextView languageTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            heightTextView = itemView.findViewById(R.id.heightTextView);
            religionTextView = itemView.findViewById(R.id.religionTextView);
            communityTextView = itemView.findViewById(R.id.communityTextView);
            smokeTextView = itemView.findViewById(R.id.smokeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            drinkingTextView = itemView.findViewById(R.id.drinkingTextView);
            languageTextView = itemView.findViewById(R.id.languageTextView);
        }

        public void bind(User user) {
            heightTextView.setText("Height: " + user.getHeight());
            religionTextView.setText("Religion: " + user.getReligion());
            communityTextView.setText("Community: " + user.getCommunity());
            smokeTextView.setText("Smoke: " + user.getSmoke());
            statusTextView.setText("Status: " + user.getStatus());
            drinkingTextView.setText("Drinking: " + user.getDrinking());
            languageTextView.setText("Language: " + user.getLanguage());
        }
    }
}
