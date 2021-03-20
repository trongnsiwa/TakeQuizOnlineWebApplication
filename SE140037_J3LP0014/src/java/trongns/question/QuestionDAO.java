/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.question;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import trongns.util.MyConnection;

/**
 *
 * @author TrongNS
 */
public class QuestionDAO implements Serializable {

    private ArrayList<QuestionDTO> listQuestion = null;

    /**
     * @return the listQuestion
     */
    public ArrayList<QuestionDTO> getListQuestion() {
        return listQuestion;
    }

    public void getQuestionListWithCondition(String subjectId, String keyword, String status, int pageSize, int pageNo) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int offset = pageSize * (pageNo - 1);

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT QuestionId, QuestionContent, CreatedDate, [Status] "
                        + "FROM Question "
                        + "WHERE SubjectId = ? ";
                if (keyword != null && !keyword.trim().isEmpty()) {
                    sql += "AND QuestionContent LIKE ? ";
                }
                if (status != null && !status.trim().isEmpty()) {
                    sql += "AND Status = ? ";
                }
                sql += "ORDER BY QuestionContent ASC "
                        + "OFFSET " + offset + " ROWS "
                        + "FETCH NEXT " + pageSize + " ROWS ONLY";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                if (keyword != null && !keyword.trim().isEmpty()) {
                    stm.setString(2, "%" + keyword + "%");
                    if (status != null && !status.trim().isEmpty()) {
                        stm.setString(3, status);
                    }
                } else {
                    if (status != null && !status.trim().isEmpty()) {
                        stm.setString(2, status);
                    }
                }

                rs = stm.executeQuery();

                String questionId, questionContent, statusOfQuestion;
                Date createdDate;

                if (this.listQuestion == null) {
                    this.listQuestion = new ArrayList<>();
                }

                while (rs.next()) {
                    questionId = rs.getString("QuestionId");
                    questionContent = rs.getString("QuestionContent");
                    statusOfQuestion = rs.getString("Status");
                    createdDate = rs.getTimestamp("CreatedDate");

                    QuestionDTO dto = new QuestionDTO(questionId, questionContent, subjectId, statusOfQuestion);
                    dto.setCreatedDate(createdDate);
                    this.listQuestion.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private int totalQuestion = 0;

    /**
     * @return the totalQuestion
     */
    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void countAllQuestionsBySubjectId(String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(QuestionId) AS total "
                        + "FROM Question "
                        + "WHERE SubjectId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    this.totalQuestion = rs.getInt("total");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    private int totalActivateQuestion = 0;

    /**
     * @return the totalActivateQuestion
     */
    public int getTotalActivateQuestion() {
        return totalActivateQuestion;
    }
    
    public void countAllActivateQuestionsBySubjectId(String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(QuestionId) AS total "
                        + "FROM Question "
                        + "WHERE SubjectId = ? AND Status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, "Activate");
                rs = stm.executeQuery();
                if (rs.next()) {
                    this.totalActivateQuestion = rs.getInt("total");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private int totalSearchedQuestions = 0;

    /**
     * @return the totalSearchedQuestions
     */
    public int getTotalSearchedQuestions() {
        return totalSearchedQuestions;
    }

    public void countQuestionsWithConditions(String subjectId, String keyword, String status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(QuestionId) AS total "
                        + "FROM Question "
                        + "WHERE SubjectId = ? ";
                if (keyword != null && !keyword.trim().isEmpty()) {
                    sql += "AND QuestionContent LIKE ? ";
                }
                if (status != null && !status.trim().isEmpty()) {
                    sql += "AND Status = ? ";
                }
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                if (keyword != null && !keyword.trim().isEmpty()) {
                    stm.setString(2, "%" + keyword + "%");
                    if (status != null && !status.trim().isEmpty()) {
                        stm.setString(3, status);
                    }
                } else {
                    if (status != null && !status.trim().isEmpty()) {
                        stm.setString(2, status);
                    }
                }

                rs = stm.executeQuery();
                if (rs.next()) {
                    this.totalSearchedQuestions = rs.getInt("total");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean checkExistQuestionId(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT QuestionContent "
                        + "FROM Question "
                        + "WHERE QuestionId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean checkExistQuestion(String content, String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT QuestionId "
                        + "FROM Question "
                        + "WHERE QuestionContent = ? AND SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, content);
                stm.setString(2, subjectId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean insertQuestion(QuestionDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Question(QuestionId, QuestionContent, SubjectId, CreatedDate, Status) "
                        + "VALUES(?,?,?,GETDATE(),?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getQuestionId());
                stm.setString(2, dto.getContent());
                stm.setString(3, dto.getSubjectId());
                stm.setString(4, dto.getStatus());

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateQuestion(QuestionDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Question "
                        + "SET QuestionContent = ?, Status = ? "
                        + "WHERE QuestionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getContent());
                stm.setString(2, dto.getStatus());
                stm.setString(3, dto.getQuestionId());

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean deleteQuestionById(String questionId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Question "
                        + "SET Status = ? "
                        + "WHERE QuestionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Deactivate");
                stm.setString(2, questionId);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    private ArrayList<QuestionDTO> listQuizQuestions = null;

    /**
     * @return the listQuizQuestions
     */
    public ArrayList<QuestionDTO> getListQuizQuestions() {
        return listQuizQuestions;
    }

    public void getQuizQuestionListWithConditions(String subjectId, int questionQuantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP (?) QuestionId, QuestionContent "
                        + "FROM Question "
                        + "WHERE SubjectId = ? AND Status = ? "
                        + "ORDER BY NEWID()";
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionQuantity);
                stm.setString(2, subjectId);
                stm.setString(3, "Activate");

                rs = stm.executeQuery();

                String questionId, questionContent;

                if (this.listQuizQuestions == null) {
                    this.listQuizQuestions = new ArrayList<>();
                }

                while (rs.next()) {
                    questionId = rs.getString("QuestionId");
                    questionContent = rs.getString("QuestionContent");

                    QuestionDTO dto = new QuestionDTO(questionId, questionContent, subjectId, "Activate");
                    this.listQuizQuestions.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private QuestionDTO studentQuizQuestion = null;

    /**
     * @return the studentQuizQuestion
     */
    public QuestionDTO getStudentQuizQuestion() {
        return studentQuizQuestion;
    }

    public void getQuestionByQuestionId(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT QuestionContent, SubjectId, CreatedDate "
                        + "FROM Question "
                        + "WHERE QuestionId = ? AND Status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                stm.setString(2, "Activate");

                rs = stm.executeQuery();

                String questionContent, subjectId;
                Date createdDate;

                if (rs.next()) {
                    questionContent = rs.getString("QuestionContent");
                    subjectId = rs.getString("SubjectId");
                    createdDate = rs.getTimestamp("CreatedDate");

                    QuestionDTO dto = new QuestionDTO(id, questionContent, subjectId, "Activate");
                    dto.setCreatedDate(createdDate);
                    this.studentQuizQuestion = dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
