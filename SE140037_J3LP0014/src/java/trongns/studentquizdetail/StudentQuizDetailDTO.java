/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.studentquizdetail;

import java.io.Serializable;

/**
 *
 * @author TrongNS
 */
public class StudentQuizDetailDTO implements Serializable{
    private String id;
    private String questionId;
    private String questionContent;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int answerCorrect;

    public StudentQuizDetailDTO() {
    }

    public StudentQuizDetailDTO(String id, String questionId, String questionContent, String answer1, String answer2, String answer3, String answer4, int answerCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answerCorrect = answerCorrect;
    }

   

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the questionContent
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * @param questionContent the questionContent to set
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * @return the answer1
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * @param answer1 the answer1 to set
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     * @return the answer2
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * @param answer2 the answer2 to set
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /**
     * @return the answer3
     */
    public String getAnswer3() {
        return answer3;
    }

    /**
     * @param answer3 the answer3 to set
     */
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    /**
     * @return the answer4
     */
    public String getAnswer4() {
        return answer4;
    }

    /**
     * @param answer4 the answer4 to set
     */
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    /**
     * @return the answerCorrect
     */
    public int getAnswerCorrect() {
        return answerCorrect;
    }

    /**
     * @param answerCorrect the answerCorrect to set
     */
    public void setAnswerCorrect(int answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    
    
}
