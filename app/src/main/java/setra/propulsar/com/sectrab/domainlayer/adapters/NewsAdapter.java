package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> mDataset;
    Context mContext;

    public NewsAdapter(ArrayList<News> myDataset, Context mContext){
        mDataset = myDataset;
        this.mContext = mContext;
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

    }

    @Override
    public int getItemCount() {
        return 0;
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
            fullCard = v.findViewById(R.id.itemnews_cardView);
            textViewTitulo = v.findViewById(R.id.itemnews_textView_titulo);
            textViewDescripcion = v.findViewById(R.id.itemnews_textView_contenido);
            imageViewPortada = v.findViewById(R.id.itemnews_imageView_portada);

        }
    }
}
