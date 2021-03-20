/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.submit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import trongns.submitdetail.SubmitDetailDTO;

/**
 *
 * @author TrongNS
 */
public class SubmitDTO implements Serializable {
    private String submitId;
    private String subjectId;
    private String userId;
    private int numberOfCorrect;
    private float grade;
    private ArrayList<SubmitDetailDTO> submitDetails;
    private Date submitDate;
    private String subjectName;

    public SubmitDTO() {
    }

    public SubmitDTO(String submitId, String subjectId, String userId, int numberOfCorrect, float grade) {
        this.submitId = submitId;
        this.subjectId = subjectId;
        this.userId = userId;
        this.numberOfCorrect = numberOfCorrect;
        this.grade = grade;
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
     * @return the numberOfCorrect
     */
    public int getNumberOfCorrect() {
        return numberOfCorrect;
    }

    /**
     * @param numberOfCorrect the numberOfCorrect to set
     */
    public void setNumberOfCorrect(int numberOfCorrect) {
        this.numberOfCorrect = numberOfCorrect;
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
     * @return the submitDate
     */
    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * @param submitDate the submitDate to set
     */
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * @return the submitDetails
     */
    public ArrayList<SubmitDetailDTO> getSubmitDetails() {
        return submitDetails;
    }

    /**
     * @param submitDetails the submitDetails to set
     */
    public void setSubmitDetails(ArrayList<SubmitDetailDTO> submitDetails) {
        this.submitDetails = submitDetails;
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
}
