package com.yushi.leke.fragment.musicplayer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
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


/**
 * Created by wm on 2016/2/4.
 */
public class PlayQueueFragment extends DialogFragment {

    private RecyclerView.ItemDecoration itemDecoration;
    private PlaylistAdapter adapter;
    private ArrayList<MusicInfo> playlist;
    private MusicInfo musicInfo;
    private int currentlyPlayingPosition = 0;
    private RecyclerView recyclerView;  //弹出的activity列表
    private LinearLayoutManager layoutManager;

    private PlayQuueuListener mQueueListener;
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




        recyclerView = (RecyclerView) view.findViewById(R.id.play_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    //
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);

    }

    private String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();




    }

    class PlaylistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<MusicInfo> playlist = new ArrayList<>();

        public PlaylistAdapter(ArrayList<MusicInfo> list) {
            playlist = list;
        }

        public void updateDataSet(ArrayList<MusicInfo> list) {
            this.playlist = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_fragment_playqueue_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            musicInfo = playlist.get(position);


        }

        @Override
        public int getItemCount() {
            return playlist == null ? 0 : playlist.size();
        }


        class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            public ItemViewHolder(View itemView) {
                super(itemView);




            }

            @Override
            public void onClick(View v) {



            }
        }

    }


}
