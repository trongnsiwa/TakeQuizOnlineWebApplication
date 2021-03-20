/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.subject;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author TrongNS
 */
public class SubjectDTO implements Serializable {
    private String subjectId;
    private String subjectName;
    private int totalQuestion;
    private int timeLimit;
    private int quizQuestionQuantity;
    private float grade;
    private Date startDate;
    private Date endDate;
    private boolean attempting;

    public SubjectDTO() {
    }

    public SubjectDTO(String subjectId, String subjectName, int totalQuestion, int timeLimit, int quizQuestionQuantity, float grade, Date startDate, Date endDate) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.totalQuestion = totalQuestion;
        this.timeLimit = timeLimit;
        this.quizQuestionQuantity = quizQuestionQuantity;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * @return the totalQuestion
     */
    public int getTotalQuestion() {
        return totalQuestion;
    }

    /**
     * @param totalQuestion the totalQuestion to set
     */
    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    /**
     * @return the timeLimit
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * @param timeLimit the timeLimit to set
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * @return the quizQuestionQuantity
     */
    public int getQuizQuestionQuantity() {
        return quizQuestionQuantity;
    }

    /**
     * @param quizQuestionQuantity the quizQuestionQuantity to set
     */
    public void setQuizQuestionQuantity(int quizQuestionQuantity) {
        this.quizQuestionQuantity = quizQuestionQuantity;
    }

    /**
     * @return the grade
     */
    public float getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the attempting
     */
    public boolean isAttempting() {
        return attempting;
    }

    /**
     * @param attempting the attempting to set
     */
    public void setAttempting(boolean attempting) {
        this.attempting = attempting;
    }
}
