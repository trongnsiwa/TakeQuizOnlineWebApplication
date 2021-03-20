/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.question;

import java.io.Serializable;

/**
 *
 * @author TrongNS
 */
public class CreateQuestionError implements Serializable{
    private String duplicateQuestion;
    private String emptySubject;
    private String emptyCorrectAnswer;

    public CreateQuestionError() {
    }

    public CreateQuestionError(String duplicateQuestion, String emptySubject, String emptyCorrectAnswer) {
        this.duplicateQuestion = duplicateQuestion;
        this.emptySubject = emptySubject;
        this.emptyCorrectAnswer = emptyCorrectAnswer;
    }

    /**
     * @return the duplicateQuestion
     */
    public String getDuplicateQuestion() {
        return duplicateQuestion;
    }

    /**
     * @param duplicateQuestion the duplicateQuestion to set
     */
    public void setDuplicateQuestion(String duplicateQuestion) {
        this.duplicateQuestion = duplicateQuestion;
    }

    /**
     * @return the emptySubject
     */
    public String getEmptySubject() {
        return emptySubject;
    }

    /**
     * @param emptySubject the emptySubject to set
     */
    public void setEmptySubject(String emptySubject) {
        this.emptySubject = emptySubject;
    }

    /**
     * @return the emptyCorrectAnswer
     */
    public String getEmptyCorrectAnswer() {
        return emptyCorrectAnswer;
    }

    /**
     * @param emptyCorrectAnswer the emptyCorrectAnswer to set
     */
    public void setEmptyCorrectAnswer(String emptyCorrectAnswer) {
        this.emptyCorrectAnswer = emptyCorrectAnswer;
    }
    
    
}
