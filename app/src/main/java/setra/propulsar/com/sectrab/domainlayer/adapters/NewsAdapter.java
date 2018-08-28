package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.News;
import setra.propulsar.com.sectrab.presentationlayer.activities.NewsDetalleActivity;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM=1, VIEW_TYPE_LOADING=0;

    private ArrayList<News> mDataset;
    Context mContext;

    public NewsAdapter(ArrayList<News> myDataset, Context mContext){
        mDataset = myDataset;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) instanceof News ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    // ------------------------------------------------------------- //
    // ---------------- VIEW HOLDER IMPLEMENTATION ----------------- //
    //-------------------------------------------------------------- //

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        holder.textViewTitulo.setText(mDataset.get(position).getTituloNoticia());
        holder.textViewDescripcion.setText(mDataset.get(position).getInfoNoticia());
        Picasso.with(mContext).load(Uri.parse((mDataset.get(position)).getLinkImagenNoticia())).into(holder.imageViewPortada);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // -------------------------------------------------- //
    // ---------------- VIEWHOLDER CLASS ---------------- //
    //--------------------------------------------------- //

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View fullCard;
        private TextView textViewTitulo;
        private TextView textViewDescripcion;
        private ImageView imageViewPortada;

        public ViewHolder(View v)
        {
            super (v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewsDetalleActivity.class);
                    intent.putExtra("Id",mDataset.get(getLayoutPosition()).getIdNoticia());
                    mContext.startActivity(intent);
                }
            });
            fullCard = v.findViewById(R.id.itemnews_cardView);
            textViewTitulo = v.findViewById(R.id.itemnews_textView_titulo);
            textViewDescripcion = v.findViewById(R.id.itemnews_textView_infonoticia);
            imageViewPortada = v.findViewById(R.id.itemnews_imageView_portada);

        }
    }
}
