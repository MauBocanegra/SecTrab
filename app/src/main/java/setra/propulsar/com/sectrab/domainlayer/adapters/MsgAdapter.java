package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.Msg;
import setra.propulsar.com.sectrab.domainlayer.models.OwnMsg;
import setra.propulsar.com.sectrab.presentationlayer.fragments.ChatFragment;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private final int VIEW_OWN = 1;
    private final int VIEW_OTHER = 0;

    private ArrayList<Msg> mDataset;
    private ChatFragment fragment;

    public MsgAdapter(ArrayList<Msg> myDataset, ChatFragment a) {
        mDataset = myDataset;
        fragment=a;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) instanceof OwnMsg ? VIEW_OWN : VIEW_OTHER;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vh;
        View v;
        if (viewType == VIEW_OWN) {
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_msg_own, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_msg_other, parent, false);

        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView msg;
        public TextView fecha;
        public View divider;
        public TextView link;
        public ImageView mapPreview;
        public ImageView iconProfile;

        public ViewHolder(View v) {
            super(v);
            fecha = (TextView)v.findViewById(R.id.msg_fecha);
            msg = (TextView)v.findViewById(R.id.msg_msg);
            divider = v.findViewById(R.id.chatSeparator);
            link = (TextView)v.findViewById(R.id.chat_link_text);
            mapPreview = (ImageView)v.findViewById(R.id.mapPreview);
            iconProfile = (ImageView)v.findViewById(R.id.iconPerfil);
        }
    }
}
