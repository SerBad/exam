package com.we.exam.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.we.exam.R;
import com.we.exam.json.ExamWords;

import java.util.List;

/**
 * Created by zhou on 2017/8/3.
 */

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.Item> {
    private Context context;
    private List<ExamWords> list;
    public QuestionRecyclerViewAdapter(Context context,List<ExamWords> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_question, parent, false);
        return new QuestionRecyclerViewAdapter.Item(itemView);
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        holder.word_id.setText(list.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():null;
    }

    public class Item extends RecyclerView.ViewHolder{
        public TextView word_id;
        public Item(View itemView) {
            super(itemView);
            word_id=(TextView)itemView.findViewById(R.id.word_id);
        }
    }
}
