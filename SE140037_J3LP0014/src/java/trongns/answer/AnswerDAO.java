/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.answer;

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
public class AnswerDAO implements Serializable {

    private ArrayList<AnswerDTO> listAnswer = null;

    /**
     * @return the listAnswer
     */
    public ArrayList<AnswerDTO> getListAnswer() {
        return listAnswer;
    }

    public void getAnswerListByQuestionId(String questionId) throws NamingException, SQLException {
        this.listAnswer = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT AnswerId, AnswerContent, IsCorrect "
                        + "FROM Answer "
                        + "WHERE QuestionId = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, questionId);

                rs = stm.executeQuery();

                String answerId;
                String answerContent;
                boolean isCorrect;

                if (this.listAnswer == null) {
                    this.listAnswer = new ArrayList<>();
                }

                while (rs.next()) {
                    answerId = rs.getString("AnswerId");
                    answerContent = rs.getString("AnswerContent");
                    isCorrect = rs.getBoolean("IsCorrect");

                    AnswerDTO dto = new AnswerDTO(answerId, questionId, answerContent, isCorrect);
                    this.listAnswer.add(dto);
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

    public boolean insertAnswer(AnswerDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Answer(AnswerId, QuestionId, AnswerContent, IsCorrect) "
                        + "VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getAnswerId());
                stm.setString(2, dto.getQuestionId());
                stm.setString(3, dto.getContent());
                stm.setBoolean(4, dto.isCorrect());

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

    public boolean updateAnswer(AnswerDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE Answer "
                        + "SET AnswerContent = ?, IsCorrect = ? "
                        + "WHERE AnswerId = ? AND QuestionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getContent());
                stm.setBoolean(2, dto.isCorrect());
                stm.setString(3, dto.getAnswerId());
                stm.setString(4, dto.getQuestionId());

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

    private boolean answerCorrect = false;

    /**
     * @return the answerCorrect
     */
    public boolean isAnswerCorrect() {
        return answerCorrect;
    }

    public void checkIsCorrect(String answerId, String questionId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT IsCorrect "
                        + "FROM Answer "
                        + "WHERE AnswerId = ? AND QuestionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, answerId);
                stm.setString(2, questionId);

                rs = stm.executeQuery();

                if (rs.next()) {
                    this.answerCorrect = rs.getBoolean("IsCorrect");
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
    
    public boolean checkExistedAnswerOfQuestion(String answerId, String questionId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT AnswerContent "
                        + "FROM Answer "
                        + "WHERE AnswerId = ? AND QuestionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, answerId);
                stm.setString(2, questionId);

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
