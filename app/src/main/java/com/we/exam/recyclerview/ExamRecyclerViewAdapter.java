package com.we.exam.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.we.exam.R;
import com.we.exam.json.ExamWords;

import java.util.List;

/**
 * Created by zhou on 2017/8/2.
 */

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<ExamRecyclerViewAdapter.Item> {
    private Context context;
    private List<ExamWords> list;
    private WordsRecyclerViewAdapter adapter;


    public ExamRecyclerViewAdapter(Context context,List<ExamWords> list){
        this.context=context;
        this.list=list;
    }


    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exam, parent, false);
        return new Item(itemView);
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        adapter=new WordsRecyclerViewAdapter(context,list.subList(position*5,position*5+5));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size()/5:null;
    }

    public class Item extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public Item(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycler_words);
        }
    }


}
