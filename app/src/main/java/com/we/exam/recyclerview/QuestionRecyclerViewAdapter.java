package com.we.exam.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    private QuestionInfo questionInfo;
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
    public void onBindViewHolder(final Item holder, final int position) {
        holder.word_id.setText((position/5<9?"0":"")+(position/5+1)+"-0"+(position%5+1));
        if(!TextUtils.isEmpty(list.get(position).getCheck())){
            holder.word_id.setBackground(context.getDrawable(R.drawable.shape_bg_question));
        }else {
            holder.word_id.setBackground(context.getDrawable(R.drawable.shape_bg_question_un));
        }
        holder.word_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionInfo!=null){
                    questionInfo.click((position/5+1),(position%5+1));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public void setQuestionInfo(QuestionInfo questionInfo) {
        this.questionInfo = questionInfo;
    }

    public class Item extends RecyclerView.ViewHolder{
        public TextView word_id;
        public Item(View itemView) {
            super(itemView);
            word_id=(TextView)itemView.findViewById(R.id.word_id);
        }
    }

    public interface QuestionInfo{
        void click(int page,int number);
    }
}
