/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import trongns.answer.AnswerDTO;

/**
 *
 * @author TrongNS
 */
public class QuestionDTO implements Serializable {
    private String questionId;
    private String content;
    private ArrayList<AnswerDTO> answerList;
    private String subjectId;
    private Date createdDate;
    private String status;

    public QuestionDTO() {
    }

    public QuestionDTO(String questionId, String content, String subjectId, String status) {
        this.questionId = questionId;
        this.content = content;
        this.subjectId = subjectId;
        this.status = status;
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
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the answerList
     */
    public ArrayList<AnswerDTO> getAnswerList() {
        return answerList;
    }

    /**
     * @param answerList the answerList to set
     */
    public void setAnswerList(ArrayList<AnswerDTO> answerList) {
        this.answerList = answerList;
    }
    
    
}
