package e.purab.twitterc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetHolder> {
    private List<Tweet> tweets;

    public TweetsAdapter(List<Tweet> tweets){
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public TweetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
        return new TweetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetHolder holder, int position) {
        holder.message.setText(tweets.get(position).getMessage());
        holder.author.setText(tweets.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class TweetHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView author;

        public TweetHolder(final View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            author = itemView.findViewById(R.id.author);
        }
    }
}
