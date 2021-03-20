/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.studentquiz;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import trongns.util.MyConnection;

/**
 *
 * @author TrongNS
 */
public class StudentQuizDAO implements Serializable {
    public boolean insertStudentQuiz(StudentQuizDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO StudentQuiz(Id, UserId, SubjectId, TimeLimit, Grade, StartDate, EndDate) "
                        + "VALUES(?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getId());
                stm.setString(2, dto.getUserId());
                stm.setString(3, dto.getSubjectId());
                stm.setInt(4, dto.getTimeLimit());
                stm.setFloat(5, dto.getGrade());
                stm.setTimestamp(6, new Timestamp(dto.getStartDate().getTime()));
                stm.setTimestamp(7, new Timestamp(dto.getEndDate().getTime()));

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
    
    public boolean checkExistedStudentQuizId(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT UserId "
                        + "FROM StudentQuiz "
                        + "WHERE Id = ?";
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
    
    public boolean deleteStudentQuizById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM StudentQuiz "
                        + "WHERE Id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

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
    
    private StudentQuizDTO studentQuiz = null;
    
     /**
     * @return the studentQuiz
     */
    public StudentQuizDTO getStudentQuiz() {
        return studentQuiz;
    }
    
    public void getStudentQuizById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT TimeLimit, Grade, StartDate, EndDate "
                        + "FROM StudentQuiz "
                        + "WHERE Id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();
                
                int timeLimit;
                float grade;
                Date startDate, endDate;

                if (rs.next()) {
                    timeLimit = rs.getInt("TimeLimit");
                    grade = rs.getFloat("Grade");
                    startDate = rs.getTimestamp("StartDate");
                    endDate = rs.getTimestamp("EndDate");
                    
                    StudentQuizDTO dto = new StudentQuizDTO();
                    dto.setTimeLimit(timeLimit);
                    dto.setGrade(grade);
                    dto.setStartDate(startDate);
                    dto.setEndDate(endDate);
                    this.studentQuiz = dto;
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
