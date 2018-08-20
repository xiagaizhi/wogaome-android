package com.yufan.library.widget.customkeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.R;

import java.util.List;

/**
 * Created by wang on 2017/6/23.
 */

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.KeyboardHolder> {
    private Context context;
    private List<String> datas;
    private OnKeyboardClickListener listener;

    public KeyboardAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public KeyboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_key_board, parent, false);
        KeyboardHolder holder = new KeyboardHolder(view);
        setListener(holder);
        return holder;
    }

    private void setListener(final KeyboardHolder holder) {
        holder.tvKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onKeyClick(datas.get(holder.getAdapterPosition()));
                }
            }
        });
        holder.rlDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDeleteClick();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(KeyboardHolder holder, final int position) {
        if (position == 9) {
            holder.tvKey.setText(datas.get(position));
            holder.tvKey.setClickable(false);
            holder.tvKey.setBackgroundResource(R.color.btn_gray);
        } else if (position == 11) {
            holder.rlDel.setVisibility(View.VISIBLE);
            holder.tvKey.setVisibility(View.GONE);
        } else {
            holder.tvKey.setText(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class KeyboardHolder extends RecyclerView.ViewHolder {
        public TextView tvKey;
        public RelativeLayout rlDel;
        private View convertView;

        public KeyboardHolder(View itemView) {
            super(itemView);
            convertView = itemView;
            tvKey = itemView.findViewById(R.id.tv_key);
            rlDel = itemView.findViewById(R.id.rl_del);
        }

        public View getconvertView() {
            return convertView;
        }
    }

    public interface OnKeyboardClickListener {
        void onKeyClick(String data);

        void onDeleteClick();
    }

    public void setOnKeyboardClickListener(OnKeyboardClickListener listener) {
        this.listener = listener;
    }
}
