package com.we.exam.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.we.exam.R;
import com.we.exam.database.ExamWordsDao;
import com.we.exam.json.ExamWords;

import java.util.List;

/**
 * Created by zhou on 2017/8/2.
 */

public class WordsRecyclerViewAdapter extends RecyclerView.Adapter<WordsRecyclerViewAdapter.Item> {
    private Context context;
    private List<ExamWords> list;
    private ExamWordsDao dao;


    public WordsRecyclerViewAdapter(Context context, List<ExamWords> list){
        this.context=context;
        this.list=list;
        dao=new ExamWordsDao(context);
    }


    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_words, parent, false);
        return new Item(itemView);
    }

    @Override
    public void onBindViewHolder(final Item holder, final int position) {

        holder.questionView.setText("0"+(position+1)+"："+list.get(position).getQuestion());
        holder.answerViewA.setText(list.get(position).getOptions().get(0));
        holder.answerViewB.setText(list.get(position).getOptions().get(1));
        holder.answerViewC.setText(list.get(position).getOptions().get(2));
        holder.answerViewD.setText(list.get(position).getOptions().get(3));
        holder.answerView1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(holder.answerView2.getCheckedRadioButtonId()!=-1) {
                    holder.answerView2.clearCheck();
                }
                if(checkedId==holder.answerViewA.getId()){
                    dao.updateCheck(list.get(position).getId(),0+"");
                }else if(checkedId==holder.answerViewB.getId()){
                    dao.updateCheck(list.get(position).getId(),1+"");
                }
            }
        });
        holder.answerView2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(holder.answerView1.getCheckedRadioButtonId()!=-1){
                    holder.answerView1.clearCheck();
                }
                if(checkedId==holder.answerViewC.getId()){
                    dao.updateCheck(list.get(position).getId(),2+"");
                }else if(checkedId==holder.answerViewD.getId()){
                    dao.updateCheck(list.get(position).getId(),3+"");
                }
            }
        });
        List<ExamWords> wordsList=dao.getCheckById(list.get(position).getId());
        if(wordsList!=null&&!wordsList.isEmpty()&&wordsList.size()!=0){
            String check=wordsList.get(0).getCheck();
            if (!TextUtils.isEmpty(check)) {
                switch (Integer.valueOf(check)){
                    case 0:
                        holder.answerView1.check(holder.answerViewA.getId());
                        break;
                    case 1:
                        holder.answerView1.check(holder.answerViewB.getId());
                        break;
                    case 2:
                        holder.answerView2.check(holder.answerViewC.getId());
                        break;
                    case 3:
                        holder.answerView2.check(holder.answerViewD.getId());
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class Item extends RecyclerView.ViewHolder {
        public TextView questionView;
        public RadioGroup answerView;
        public RadioGroup answerView1;
        public RadioGroup answerView2;

        public RadioButton answerViewA;
        public RadioButton answerViewB;
        public RadioButton answerViewC;
        public RadioButton answerViewD;

        public Item(View itemView) {
            super(itemView);
            questionView=(TextView)itemView.findViewById(R.id.question);
            answerView=(RadioGroup) itemView.findViewById(R.id.answer);
            answerView1=(RadioGroup) itemView.findViewById(R.id.answer1);
            answerView2=(RadioGroup) itemView.findViewById(R.id.answer2);
            answerViewA=(RadioButton) itemView.findViewById(R.id.answer_a);
            answerViewB=(RadioButton) itemView.findViewById(R.id.answer_b);
            answerViewC=(RadioButton) itemView.findViewById(R.id.answer_c);
            answerViewD=(RadioButton) itemView.findViewById(R.id.answer_d);
        }
    }


}
