/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.studentquizdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import trongns.util.MyConnection;

/**
 *
 * @author TrongNS
 */
public class StudentQuizDetailDAO implements Serializable {

    public boolean insertStudentQuizDetail(StudentQuizDetailDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO StudentQuizDetail(Id, QuestionId, QuestionContent, Answer1, Answer2, Answer3, Answer4, AnswerCorrect) "
                        + "VALUES(?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getId());
                stm.setString(2, dto.getQuestionId());
                stm.setString(3, dto.getQuestionContent());
                stm.setString(4, dto.getAnswer1());
                stm.setString(5, dto.getAnswer2());
                stm.setString(6, dto.getAnswer3());
                stm.setString(7, dto.getAnswer4());
                stm.setInt(8, dto.getAnswerCorrect());

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

    private ArrayList<StudentQuizDetailDTO> listQuizDetail = null;

    /**
     * @return the listQuizDetail
     */
    public ArrayList<StudentQuizDetailDTO> getListQuizDetail() {
        return listQuizDetail;
    }

    public void getQuizDetailsById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT QuestionId, QuestionContent, Answer1, Answer2, Answer3, Answer4, AnswerCorrect "
                        + "FROM StudentQuizDetail "
                        + "WHERE Id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);

                rs = stm.executeQuery();

                if (this.listQuizDetail == null) {
                    this.listQuizDetail = new ArrayList<>();
                }
                
                String questionId, questionContent, answer1, answer2, answer3, answer4;
                int answerCorrect;

                while (rs.next()) {
                    questionId = rs.getString("QuestionId");
                    questionContent = rs.getString("QuestionContent");
                    answer1 = rs.getString("Answer1");
                    answer2 = rs.getString("Answer2");
                    answer3 = rs.getString("Answer3");
                    answer4 = rs.getString("Answer4");
                    answerCorrect = rs.getInt("AnswerCorrect");
                    this.listQuizDetail.add(new StudentQuizDetailDTO(id, questionId, questionContent, answer1, answer2, answer3, answer4, answerCorrect));
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

    public boolean deleteStudentQuizDetailById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM StudentQuizDetail "
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
    
    private int totalQuestion = 0;

    /**
     * @return the totalQuestion
     */
    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void countAllQuestionsById(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(QuestionId) AS total "
                        + "FROM StudentQuizDetail "
                        + "WHERE Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
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
}
