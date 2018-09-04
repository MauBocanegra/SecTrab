package setra.propulsar.com.sectrab.domainlayer.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.InfoChat;

public class InfoChatAdapter extends RecyclerView.Adapter<InfoChatAdapter.ViewHolder>{

    private ArrayList<InfoChat> mDataset;
    Context mContext;

    public InfoChatAdapter(ArrayList<InfoChat> myDataset, Context mContext){
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // ------------------------------------------------------------- //
    // ---------------- VIEW HOLDER IMPLEMENTATION ----------------- //
    //-------------------------------------------------------------- //

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appinfo, parent, false);
        return new InfoChatAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.titulo.setText(mDataset.get(position).getTitulo());
        holder.contenido.setText(mDataset.get(position).getContenido());

    }

    @Override
    public int getItemCount() { return mDataset.size();}

    // -------------------------------------------------- //
    // ---------------- VIEWHOLDER CLASS ---------------- //
    //--------------------------------------------------- //

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView contenido;

        public ViewHolder(View v) {
            super(v);
            titulo = v.findViewById(R.id.itemappinfo_textView_titulo);
            contenido = v.findViewById(R.id.itemappinfo_textView_contenido);
        }
    }
}
