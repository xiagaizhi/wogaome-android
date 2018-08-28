package com.yushi.leke.fragment.musicplayer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PlayQueueFragment extends DialogFragment {

    private RecyclerView recyclerView;  //弹出的activity列表
    private LinearLayoutManager layoutManager;
    private List<MediaSessionCompat.QueueItem> queue=new ArrayList<>();
    private PlayQuueuListener mQueueListener;
    private TextView tv_queue_name;
    private long currentQueueId;

    public void setCurrentQueue(long activeQueueItemId) {
        currentQueueId=activeQueueItemId;
    }

    public interface PlayQuueuListener{
        void onPlay(int position);
    }

    public void setQueueListener(PlayQuueuListener listener){
        mQueueListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置样式

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDatePickerDialog);

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);
        View view = inflater.inflate(R.layout.dialog_fragment_queue, container);
        view.findViewById(R.id.rl_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_queue_name=  view.findViewById(R.id.tv_queue_name);
        recyclerView = (RecyclerView) view.findViewById(R.id.play_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new PlaylistAdapter(queue));

        return view;
    }

    public void setQueue(List<MediaSessionCompat.QueueItem> queue){
        this.queue=queue;

    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }

    class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ItemViewHolder> {
        private List<MediaSessionCompat.QueueItem> playlist = new ArrayList<>();

        public PlaylistAdapter(List<MediaSessionCompat.QueueItem> list) {
            playlist = list;
        }

        public void updateDataSet(List<MediaSessionCompat.QueueItem> list) {

            this.playlist = list;

        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_fragment_playqueue_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.tv_title.setText(playlist.get(position).getDescription().getTitle());
        if(playlist.get(position).getQueueId()==currentQueueId){
            holder.tv_title.setSelected(true);
        }else {
            holder.tv_title.setSelected(false);
        }
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(getActivity()).getTransportControls();
                currentQueueId=playlist.get(position).getQueueId();
                controls.skipToQueueItem(currentQueueId);
                notifyDataSetChanged();
            }
        });
        }

        @Override
        public int getItemCount() {
            return playlist == null ? 0 : playlist.size();
        }


        class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;

            public ItemViewHolder(View itemView) {
                super(itemView);
                tv_title=  itemView.findViewById(R.id.tv_title);
            }

        }

    }


}
