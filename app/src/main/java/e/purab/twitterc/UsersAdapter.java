package e.purab.twitterc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private List<User> users;
    private OnItemClickListener onItemClickListener;

    public UsersAdapter(List<User> users, OnItemClickListener listener) {
        this.users = users;
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, boolean isTweet);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        holder.username.setText(users.get(position).getUsername());
        holder.onItemClickListener = onItemClickListener;
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView username;
        private Button tweet;
        private OnItemClickListener onItemClickListener;
        private int position;

        public UserHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            tweet = itemView.findViewById(R.id.tweet);
            tweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView, position, true);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView, position, false);
                }
            });
        }
    }
}
