/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.submitdetail;

import java.io.Serializable;

/**
 *
 * @author TrongNS
 */
public class SubmitDetailDTO implements Serializable{
    private String submitId;
    private String questionId;
    private String answerChoice;
    private boolean correct;
    private float point;

    public SubmitDetailDTO() {
    }

    public SubmitDetailDTO(String submitId, String questionId, String answerChoice, boolean correct, float point) {
        this.submitId = submitId;
        this.questionId = questionId;
        this.answerChoice = answerChoice;
        this.correct = correct;
        this.point = point;
    }

    /**
     * @return the submitId
     */
    public String getSubmitId() {
        return submitId;
    }

    /**
     * @param submitId the submitId to set
     */
    public void setSubmitId(String submitId) {
        this.submitId = submitId;
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
     * @return the answerChoice
     */
    public String getAnswerChoice() {
        return answerChoice;
    }

    /**
     * @param answerChoice the answerChoice to set
     */
    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
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

    /**
     * @return the point
     */
    public float getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(float point) {
        this.point = point;
    }
    
    
}
