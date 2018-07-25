package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    // ---------------- VIEW HOLDER IMPLEMENTATION ---------------- //
    //-------------------------------------------------------------- //

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount(){ return mDataset.size();}

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView){
            super (itemView);
        }
    }
}
