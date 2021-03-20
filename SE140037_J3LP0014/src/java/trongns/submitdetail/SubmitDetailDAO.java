/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.submitdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import trongns.util.MyConnection;

/**
 *
 * @author TrongNS
 */
public class SubmitDetailDAO implements Serializable {
    public boolean insertSubmitDetail(SubmitDetailDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = MyConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO SubmitDetail(SubmitId, QuestionId, AnswerChoice, IsCorrect, Point) "
                        + "VALUES(?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getSubmitId());
                stm.setString(2, dto.getQuestionId());
                stm.setString(3, dto.getAnswerChoice());
                stm.setBoolean(4, dto.isCorrect());
                stm.setFloat(5, dto.getPoint());

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
}
