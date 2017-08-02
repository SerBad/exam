package com.we.exam.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 2017/8/1.
 */

public class ExamWords implements Serializable {
    private String answer;
    private String question;
    private String id;
    private List<String> options;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
