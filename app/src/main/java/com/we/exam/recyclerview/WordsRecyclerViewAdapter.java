package com.we.exam.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

//        holder.answerViewA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.check(0);
//                dao.updateCheck(list.get(position).getId(),0+"");
//            }
//        });
//        holder.answerViewB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.check(1);
//                dao.updateCheck(list.get(position).getId(),1+"");
//            }
//        });
//        holder.answerViewC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.check(2);
//                dao.updateCheck(list.get(position).getId(),2+"");
//            }
//        });
//        holder.answerViewD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.check(3);
//                dao.updateCheck(list.get(position).getId(),3+"");
//            }
//        });
        holder.answerViewA.setOnClickListener(new MyOnClickListener(holder,position));
        holder.answerViewB.setOnClickListener(new MyOnClickListener(holder,position));
        holder.answerViewC.setOnClickListener(new MyOnClickListener(holder,position));
        holder.answerViewD.setOnClickListener(new MyOnClickListener(holder,position));
        List<ExamWords> wordsList=dao.getCheckById(list.get(position).getId());
        if(wordsList!=null&&!wordsList.isEmpty()&&wordsList.size()!=0){
            String check=wordsList.get(0).getCheck();
            if (!TextUtils.isEmpty(check)) {
                switch (Integer.valueOf(check)){
                    case 0:
                        holder.check(holder.answerViewA.getId());
                        break;
                    case 1:
                        holder.check(holder.answerViewB.getId());
                        break;
                    case 2:
                        holder.check(holder.answerViewC.getId());
                        break;
                    case 3:
                        holder.check(holder.answerViewD.getId());
                        break;
                }
            }
        }
    }

    private class MyOnClickListener implements View.OnClickListener{
        private Item item;
        private int position=0;
        public MyOnClickListener(Item item,int position){
            this.item=item;
            this.position=position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.answer_a:
                    item.check(0);
                    dao.updateCheck(list.get(position).getId(),0+"");
                    break;
                case R.id.answer_b:
                    item.check(1);
                    dao.updateCheck(list.get(position).getId(),1+"");
                    break;
                case R.id.answer_c:
                    item.check(2);
                    dao.updateCheck(list.get(position).getId(),2+"");
                    break;
                case R.id.answer_d:
                    item.check(3);
                    dao.updateCheck(list.get(position).getId(),3+"");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class Item extends RecyclerView.ViewHolder {
        public TextView questionView;

        public RadioButton answerViewA;
        public RadioButton answerViewB;
        public RadioButton answerViewC;
        public RadioButton answerViewD;

        public Item(View itemView) {
            super(itemView);
            questionView=(TextView)itemView.findViewById(R.id.question);
            answerViewA=(RadioButton) itemView.findViewById(R.id.answer_a);
            answerViewB=(RadioButton) itemView.findViewById(R.id.answer_b);
            answerViewC=(RadioButton) itemView.findViewById(R.id.answer_c);
            answerViewD=(RadioButton) itemView.findViewById(R.id.answer_d);
        }


        public void check(int i){
            switch (i){
                case 0:
                    answerViewA.setChecked(true);
                    answerViewB.setChecked(false);
                    answerViewC.setChecked(false);
                    answerViewD.setChecked(false);
                    break;
                case 1:
                    answerViewA.setChecked(false);
                    answerViewB.setChecked(true);
                    answerViewC.setChecked(false);
                    answerViewD.setChecked(false);
                    break;
                case 2:
                    answerViewA.setChecked(false);
                    answerViewB.setChecked(false);
                    answerViewC.setChecked(true);
                    answerViewD.setChecked(false);
                    break;
                case 3:
                    answerViewA.setChecked(false);
                    answerViewB.setChecked(false);
                    answerViewC.setChecked(false);
                    answerViewD.setChecked(true);
                    break;
            }

        }
    }


}
