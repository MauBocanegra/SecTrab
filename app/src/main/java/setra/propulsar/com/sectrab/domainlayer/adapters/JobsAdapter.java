package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.Jobs;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private ArrayList<Jobs> mDataset;
    Context mContext;

    public JobsAdapter(ArrayList<Jobs> myDataset, Context mContext){
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // ------------------------------------------------------------- //
    // ---------------- VIEW HOLDER IMPLEMENTATION ----------------- //
    //-------------------------------------------------------------- //

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_vacancies, parent, false);
        return new JobsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewNombre.setText(mDataset.get(position).getNombreEmpresa());
        holder.textViewInformacion.setText(mDataset.get(position).getInfoEmpresa());
        holder.textViewPuesto.setText(mDataset.get(position).getPuestoEmpresa());
        holder.textViewDescripcion.setText(mDataset.get(position).getDescripcionEmpleo());
        holder.textViewUbicacion.setText(mDataset.get(position).getUbicacionEmpleo());
        Picasso.with(mContext).load(Uri.parse((mDataset.get(position)).getLinkImagenEmpresa())).into(holder.imageViewImagen);
    }

    @Override
    public int getItemCount() { return mDataset.size(); }

    // -------------------------------------------------- //
    // ---------------- VIEWHOLDER CLASS ---------------- //
    //--------------------------------------------------- //

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private View fullcard;
        private ImageView imageViewImagen;
        private TextView textViewNombre;
        private TextView textViewInformacion;
        private TextView textViewPuesto;
        private TextView textViewDescripcion;
        private TextView textViewUbicacion;
        private Button buttonMasInfo;

        public ViewHolder(View v)
        {
            super (v);
            fullcard = v.findViewById(R.id.itemjob_cardView_fullview);
            imageViewImagen = v.findViewById(R.id.itemjob_imageView_imagenempresa);
            textViewNombre = v.findViewById(R.id.itemjob_textView_nombreempresa);
            textViewInformacion = v.findViewById(R.id.itemjob_textView_infoempresa);
            textViewPuesto = v.findViewById(R.id.itemjob_textView_puesto);
            textViewDescripcion = v.findViewById(R.id.itemjob_textView_descripcion);
            textViewUbicacion = v.findViewById(R.id.itemjob_textView_ubicacion);
            buttonMasInfo = v.findViewById(R.id.itemjob_button_informacion);
        }
    }
}
