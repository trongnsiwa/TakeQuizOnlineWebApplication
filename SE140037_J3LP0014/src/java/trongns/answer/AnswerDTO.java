/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.answer;

import java.io.Serializable;

/**
 *
 * @author TrongNS
 */
public class AnswerDTO implements Serializable {
    private String answerId;
    private String questionId;
    private String content;
    private boolean correct;

    public AnswerDTO() {
    }

    public AnswerDTO(String answerId, String questionId, String content, boolean correct) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.content = content;
        this.correct = correct;
    }

    /**
     * @return the answerId
     */
    public String getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId the answerId to set
     */
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    /**
     * @return the questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the correct
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * @param correct the correct to set
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
    
    
}
