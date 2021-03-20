/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.submit;

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
public class SubmitDAO implements Serializable {

    public boolean insertSubmit(SubmitDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Submit(SubmitId, SubjectId, UserId, NumberOfCorrect, Grade, SubmitDate) "
                        + "VALUES(?,?,?,?,?,GETDATE()) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getSubmitId());
                stm.setString(2, dto.getSubjectId());
                stm.setString(3, dto.getUserId());
                stm.setInt(4, dto.getNumberOfCorrect());
                stm.setFloat(5, dto.getGrade());

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

    public boolean checkDuplicatedSubmitId(String submitId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT SubjectId "
                        + "FROM Submit "
                        + "WHERE SubmitId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, submitId);

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

    private ArrayList<SubmitDTO> listSubmitQuiz = null;

    /**
     * @return the listSubmitQuiz
     */
    public ArrayList<SubmitDTO> getListSubmitQuiz() {
        return listSubmitQuiz;
    }

    public void getListSubmitQuizWithConditions(String userId, String keyword, int pageNo, int pageSize) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int offset = pageSize * (pageNo - 1);

        boolean haveKeyword = false;
        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT s.SubmitId, s.SubjectId, s.NumberOfCorrect, s.Grade, s.SubmitDate "
                        + "FROM Submit s ";
                if (keyword != null && !keyword.trim().isEmpty()) {
                    sql += "FULL OUTER JOIN Subject AS su "
                            + "ON s.SubjectId = su.SubjectId ";
                    haveKeyword = true;
                }
                sql += "WHERE s.UserId = ? ";
                if (haveKeyword) {
                    sql += "AND su.SubjectName LIKE ? ";
                }
                sql += "ORDER BY s.SubmitDate DESC "
                        + "OFFSET " + offset + " ROWS "
                        + "FETCH NEXT " + pageSize + " ROWS ONLY";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                if (haveKeyword) {
                    stm.setString(2, "%" + keyword + "%");
                }

                rs = stm.executeQuery();

                String submitId, subjectId;
                int numberOfCorrect;
                float grade;
                Date submitDate;

                if (this.listSubmitQuiz == null) {
                    this.listSubmitQuiz = new ArrayList<>();
                }

                while (rs.next()) {
                    submitId = rs.getString("SubmitId");
                    subjectId = rs.getString("SubjectId");
                    numberOfCorrect = rs.getInt("NumberOfCorrect");
                    grade = rs.getFloat("Grade");
                    submitDate = rs.getTimestamp("SubmitDate");

                    SubmitDTO submit = new SubmitDTO(submitId, subjectId, userId, numberOfCorrect, grade);
                    submit.setSubmitDate(submitDate);
                    this.listSubmitQuiz.add(submit);
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

    private int totalSubmit = 0;

    /**
     * @return the totalSubmit
     */
    public int getTotalSubmit() {
        return totalSubmit;
    }

    public void countAllSubmitsByUserId(String userId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(SubmitId) AS total "
                        + "FROM Submit "
                        + "WHERE UserId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    this.totalSubmit = rs.getInt("total");
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

    private int totalSearchedSubmits = 0;

    /**
     * @return the totalSearchedSubmits
     */
    public int getTotalSearchedSubmits() {
        return totalSearchedSubmits;
    }

    public void countSubmitsWithConditions(String userId, String keyword) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean haveKeyword = false;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(s.SubmitId) AS total "
                        + "FROM Submit s ";
                if (keyword != null && !keyword.trim().isEmpty()) {
                    sql += "FULL OUTER JOIN Subject AS su "
                            + "ON s.SubjectId = su.SubjectId ";
                    haveKeyword = true;
                }
                sql += "WHERE s.UserId = ? ";
                if (haveKeyword) {
                    sql += "AND su.SubjectName LIKE ? ";
                }
                stm = con.prepareStatement(sql);
                stm.setString(1, userId);
                if (haveKeyword) {
                    stm.setString(2, "%" + keyword + "%");
                }

                rs = stm.executeQuery();
                if (rs.next()) {
                    this.totalSearchedSubmits = rs.getInt("total");
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
