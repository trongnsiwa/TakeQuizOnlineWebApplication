/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.subject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import trongns.util.MyConnection;

/**
 *
 * @author TrongNS
 */
public class SubjectDAO implements Serializable {

    private ArrayList<SubjectDTO> listSubject = null;

    /**
     * @return the listSubject
     */
    public ArrayList<SubjectDTO> getListSubject() {
        return listSubject;
    }

    public void getAllSubjects() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT SubjectId, SubjectName, TotalQuestion, TimeLimit, QuizQuestionQuantity, Grade, StartDate, EndDate "
                        + "FROM Subject ";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();

                String subjectId, subjectName;
                int totalQuestion, timeLimit, quizQuestionQuantity;
                float grade;
                Date startDate, endDate;

                if (this.listSubject == null) {
                    this.listSubject = new ArrayList<>();
                }

                while (rs.next()) {
                    subjectId = rs.getString("SubjectId");
                    subjectName = rs.getString("SubjectName");
                    totalQuestion = rs.getInt("TotalQuestion");
                    timeLimit = rs.getInt("TimeLimit");
                    quizQuestionQuantity = rs.getInt("QuizQuestionQuantity");
                    grade = rs.getFloat("Grade");
                    startDate = rs.getTimestamp("StartDate");
                    endDate = rs.getTimestamp("EndDate");

                    SubjectDTO dto = new SubjectDTO(subjectId, subjectName, totalQuestion, timeLimit, quizQuestionQuantity, grade, startDate, endDate);
                    this.listSubject.add(dto);
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

    private SubjectDTO subjectChosen = null;

    /**
     * @return the subjectChosen
     */
    public SubjectDTO getSubjectChosen() {
        return subjectChosen;
    }

    public void getSubjectById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT SubjectName, TotalQuestion, TimeLimit, QuizQuestionQuantity, Grade, StartDate, EndDate "
                        + "FROM Subject "
                        + "WHERE SubjectId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();

                String subjectName;
                int totalQuestion, timeLimit, quizQuestionQuantity;
                float grade;
                Date startDate, endDate;

                if (rs.next()) {
                    subjectName = rs.getString("SubjectName");
                    totalQuestion = rs.getInt("totalQuestion");
                    timeLimit = rs.getInt("TimeLimit");
                    quizQuestionQuantity = rs.getInt("QuizQuestionQuantity");
                    grade = rs.getFloat("Grade");
                    startDate = rs.getTimestamp("StartDate");
                    endDate = rs.getTimestamp("EndDate");

                    SubjectDTO dto = new SubjectDTO(id, subjectName, totalQuestion, timeLimit, quizQuestionQuantity, grade, startDate, endDate);
                    dto.setAttempting(false);
                    this.subjectChosen = dto;
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

    public boolean updateQuizForSubject(String id, String name, int totalQuestion, int timeLimit, int questionQuantity, float grade, Date startDate, Date endDate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Subject "
                        + "SET SubjectName = ?, TotalQuestion = ?, TimeLimit = ?, QuizQuestionQuantity = ?, Grade = ?, StartDate = ?, EndDate = ? "
                        + "WHERE SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.setInt(2, totalQuestion);
                stm.setInt(3, timeLimit);
                stm.setInt(4, questionQuantity);
                stm.setFloat(5, grade);
                stm.setTimestamp(6, new Timestamp(startDate.getTime()));
                stm.setTimestamp(7, new Timestamp(endDate.getTime()));
                stm.setString(8, id);

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

    private Date quizEndDate = null;

    /**
     * @return the quizEndDate
     */
    public Date getQuizEndDate() {
        return quizEndDate;
    }

    public void getQuizEndDateById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT EndDate "
                        + "FROM Subject "
                        + "WHERE SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();

                if (rs.next()) {
                    this.quizEndDate = rs.getTimestamp("EndDate");
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

    public void getTotalQuestionyById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT TotalQuestion "
                        + "FROM Subject "
                        + "WHERE SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();

                if (rs.next()) {
                    this.totalQuestion = rs.getInt("TotalQuestion");
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

    public boolean updateTotalQuestionById(String id, int total) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Subject "
                        + "SET TotalQuestion = ? "
                        + "WHERE SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, total);
                stm.setString(2, id);

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
    
    public boolean updateQuizQuestionQuantityById(String id, int quantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Subject "
                        + "SET QuizQuestionQuantity = ? "
                        + "WHERE SubjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, id);

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
    
    public boolean createSubject(String subjectId, String subjectName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Subject(SubjectId, SubjectName) "
                        + "VALUES(?,?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, subjectName);

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
    
    public boolean checkExistedSubject(String name) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT SubjectId "
                        + "FROM Subject "
                        + "WHERE SubjectName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);

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
}
