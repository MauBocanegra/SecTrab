package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.Contacts;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private ArrayList<Contacts> mDataset;
    Context mContext;

    public ContactsAdapter(ArrayList<Contacts> myDataset, Context mContext){
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // ------------------------------------------------------------- //
    // ---------------- VIEW HOLDER IMPLEMENTATION ----------------- //
    //-------------------------------------------------------------- //

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.textViewNombre.setText(mDataset.get(position).getNombreEmpresa());
        holder.textViewUbicacion.setText(mDataset.get(position).getUbicacionEmpresa());
        holder.textViewTelefono.setText(mDataset.get(position).getTelefonoEmpresa());
    }

    @Override
    public int getItemCount(){ return mDataset.size();}

    // -------------------------------------------------- //
    // ---------------- VIEWHOLDER CLASS ---------------- //
    //--------------------------------------------------- //

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View fullCard;
        private TextView textViewNombre;
        private TextView textViewUbicacion;
        private TextView textViewTelefono;
        private ImageButton imageButtonLlamada;


        public ViewHolder(View v)
        {
            super (v);
            fullCard = v.findViewById(R.id.itemcontacts_cardView);
            textViewNombre = v.findViewById(R.id.itemcontacts_textView_nombre);
            textViewTelefono = v.findViewById(R.id.itemcontacts_textView_telefono);
            textViewUbicacion = v.findViewById(R.id.itemcontects_textView_ubicacion);
            imageButtonLlamada = v.findViewById(R.id.itemcontacts_imageButton_llamada);
        }
    }
}
