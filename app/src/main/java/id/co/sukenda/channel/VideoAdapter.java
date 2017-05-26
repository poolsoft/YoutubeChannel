package id.co.sukenda.channel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.sukenda.channel.model.Item;

/**
 * Created by sukenda on 26/05/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Item> items;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Item Item);
    }

    public VideoAdapter(Context context, List<Item> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        holder.clickListener(items.get(position), onItemClickListener);
        holder.textViewTitle.setText(items.get(position).getSnippet().getTitle());
        holder.textViewTanggal.setText("");
        Picasso.with(context).load(items.get(position).getSnippet().getThumbnails().getHigh().getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTanggal;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_video_title);
            textViewTanggal = (TextView) itemView.findViewById(R.id.text_video_tanggal);
            imageView = (ImageView) itemView.findViewById(R.id.image_video);

        }

        void clickListener(final Item item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
