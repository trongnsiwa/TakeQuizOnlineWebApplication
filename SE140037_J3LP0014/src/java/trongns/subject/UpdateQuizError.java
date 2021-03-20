/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.subject;

import java.io.Serializable;

/**
 *
 * @author TrongNS
 */
public class UpdateQuizError implements Serializable{
    private String zeroMinute;
    private String zeroQuestionQuantity;
    private String zeroTotalQuestion;
    private String notEnoughQuestion;
    private String emptyStartDate;
    private String emptyEndDate;
    private String startDateAfterEndDate;

    public UpdateQuizError() {
    }

    public UpdateQuizError(String zeroMinute, String zeroQuestionQuantity, String zeroTotalQuestion, String notEnoughQuestion, String emptyStartDate, String emptyEndDate, String startDateAfterEndDate) {
        this.zeroMinute = zeroMinute;
        this.zeroQuestionQuantity = zeroQuestionQuantity;
        this.zeroTotalQuestion = zeroTotalQuestion;
        this.notEnoughQuestion = notEnoughQuestion;
        this.emptyStartDate = emptyStartDate;
        this.emptyEndDate = emptyEndDate;
        this.startDateAfterEndDate = startDateAfterEndDate;
    }

    /**
     * @return the zeroMinute
     */
    public String getZeroMinute() {
        return zeroMinute;
    }

    /**
     * @param zeroMinute the zeroMinute to set
     */
    public void setZeroMinute(String zeroMinute) {
        this.zeroMinute = zeroMinute;
    }

    /**
     * @return the zeroQuestionQuantity
     */
    public String getZeroQuestionQuantity() {
        return zeroQuestionQuantity;
    }

    /**
     * @param zeroQuestionQuantity the zeroQuestionQuantity to set
     */
    public void setZeroQuestionQuantity(String zeroQuestionQuantity) {
        this.zeroQuestionQuantity = zeroQuestionQuantity;
    }

    /**
     * @return the startDateAfterEndDate
     */
    public String getStartDateAfterEndDate() {
        return startDateAfterEndDate;
    }

    /**
     * @param startDateAfterEndDate the startDateAfterEndDate to set
     */
    public void setStartDateAfterEndDate(String startDateAfterEndDate) {
        this.startDateAfterEndDate = startDateAfterEndDate;
    }

    /**
     * @return the notEnoughQuestion
     */
    public String getNotEnoughQuestion() {
        return notEnoughQuestion;
    }

    /**
     * @param notEnoughQuestion the notEnoughQuestion to set
     */
    public void setNotEnoughQuestion(String notEnoughQuestion) {
        this.notEnoughQuestion = notEnoughQuestion;
    }

    /**
     * @return the emptyStartDate
     */
    public String getEmptyStartDate() {
        return emptyStartDate;
    }

    /**
     * @param emptyStartDate the emptyStartDate to set
     */
    public void setEmptyStartDate(String emptyStartDate) {
        this.emptyStartDate = emptyStartDate;
    }

    /**
     * @return the emptyEndDate
     */
    public String getEmptyEndDate() {
        return emptyEndDate;
    }

    /**
     * @param emptyEndDate the emptyEndDate to set
     */
    public void setEmptyEndDate(String emptyEndDate) {
        this.emptyEndDate = emptyEndDate;
    }

    /**
     * @return the zeroTotalQuestion
     */
    public String getZeroTotalQuestion() {
        return zeroTotalQuestion;
    }

    /**
     * @param zeroTotalQuestion the zeroTotalQuestion to set
     */
    public void setZeroTotalQuestion(String zeroTotalQuestion) {
        this.zeroTotalQuestion = zeroTotalQuestion;
    }
    
    
}
