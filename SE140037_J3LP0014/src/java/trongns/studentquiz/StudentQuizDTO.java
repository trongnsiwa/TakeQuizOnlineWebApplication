/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.studentquiz;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author TrongNS
 */
public class StudentQuizDTO implements Serializable{
    private String id;
    private String userId;
    private String subjectId;
    private int timeLimit;
    private float grade;
    private Date startDate;
    private Date endDate;

    public StudentQuizDTO() {
    }

    public StudentQuizDTO(String userId, String subjectId, int timeLimit, float grade, Date startDate, Date endDate) {
        this.id = userId + subjectId;
        this.userId = userId;
        this.subjectId = subjectId;
        this.timeLimit = timeLimit;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
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
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
    
    
}
