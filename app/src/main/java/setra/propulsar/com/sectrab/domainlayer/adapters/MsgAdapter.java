package setra.propulsar.com.sectrab.domainlayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import setra.propulsar.com.sectrab.R;
import setra.propulsar.com.sectrab.domainlayer.models.Msg;
import setra.propulsar.com.sectrab.domainlayer.models.OwnMsg;
import setra.propulsar.com.sectrab.presentationlayer.Dialogs.DialogShowOnly;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        final Context c = holder.link.getContext();
        holder.msg.setText(mDataset.get(position).getMsg());
        holder.fecha.setText(mDataset.get(position).getTimeStamp().split("T")[0]);

        int msgType=mDataset.get(position).getType();

        switch (msgType){
            //Ubicacion
            case 3:{
                holder.msg.setVisibility(View.GONE);
                holder.fecha.setVisibility(View.GONE);
                holder.mapPreview.setVisibility(View.VISIBLE);

                Picasso.with(fragment.getActivity()).load("https://maps.googleapis.com/maps/api/staticmap?center=" +mDataset.get(position).getMsg()+ "&zoom=17&size=500x400&maptype=roadmap" + "&markers=color:red%7C" +mDataset.get(position).getMsg()).into(holder.mapPreview);
                final String loc = mDataset.get(position).getMsg();
                holder.mapPreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri gmmIntentUri = Uri.parse("geo:" + loc + "?q=" + loc + "(Ubicación definida");
                        //geo:<lat>,<long>?q=<lat>,<long>(Label+Name)
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(c.getPackageManager()) != null) {
                            c.startActivity(mapIntent);
                        }
                    }
                });
                break;
            }
            case 4:{
                holder.msg.setVisibility(View.GONE);
                holder.fecha.setVisibility(View.GONE);
                holder.mapPreview.setVisibility(View.VISIBLE);
                Picasso.with(fragment.getActivity()).load(mDataset.get(position).getUrl()).into(holder.mapPreview);

                holder.mapPreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogShowOnly diag = new DialogShowOnly();
                        diag.setImgURL(mDataset.get(pos).getUrl());
                        diag.show(fragment.getActivity().getSupportFragmentManager(),"");
                    }
                });
                break;
            }
            case 1:{
                holder.msg.setVisibility(View.VISIBLE);
                holder.fecha.setVisibility(View.VISIBLE);
                holder.mapPreview.setVisibility(View.GONE);

                //Deteccion y presentacion de paginas web
                Pattern pattern = Pattern.compile("[^ \\s]+\\.[^ \\s\\d]+");
                Matcher matcher = pattern.matcher(mDataset.get(position).getMsg());
                if (matcher.find()) {
                    final String urlLink = matcher.group();
                    String sub1 = mDataset.get(position).getMsg().substring(0, matcher.start());
                    String sub2 = mDataset.get(position).getMsg().substring(matcher.start(), matcher.end());
                    String sub3 = mDataset.get(position).getMsg().substring(matcher.end(), mDataset.get(position).getMsg().length());
                    String underlinedSt = sub1 + "<u><b>" + sub2 + "</b></u>" + sub3;
                    String[] underlinedArr = underlinedSt.split("\n");
                    underlinedSt = "";
                    for (String st : underlinedArr) {
                        underlinedSt += st + "<br>";
                    }
                    underlinedSt = underlinedSt.substring(0, underlinedSt.length() - 4);
                    holder.msg.setText(Html.fromHtml(underlinedSt));
                    holder.msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String linkURL = "";
                            if (urlLink.startsWith("http://") || urlLink.startsWith("https://"))
                                linkURL = urlLink;
                            else
                                linkURL = "http://" + urlLink;

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkURL));
                            c.startActivity(browserIntent);
                        }
                    });
                }
                break;
            }

            case 2:{
                holder.msg.setVisibility(View.VISIBLE);
                holder.fecha.setVisibility(View.VISIBLE);
                holder.mapPreview.setVisibility(View.GONE);
                break;
            }
        }

        Log.d("DebugMsg","["+mDataset.get(position).getMsg()+"]msgLen="+mDataset.get(position).getMsg().length()+" url["+mDataset.get(position).getUrl()+"]Len="+mDataset.get(position).getUrl().length());

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
