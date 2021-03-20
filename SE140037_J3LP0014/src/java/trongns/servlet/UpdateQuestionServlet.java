/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trongns.answer.AnswerDAO;
import trongns.answer.AnswerDTO;
import trongns.question.QuestionDAO;
import trongns.question.QuestionDTO;

/**
 *
 * @author TrongNS
 */
public class UpdateQuestionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String subjectId = request.getParameter("id");

        String url = "SearchQuestionServlet";

        try {
            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }
            
            String questionId = request.getParameter("txtQuestionId");
            String content = request.getParameter("txtQuestionContent_" + questionId);
            String answer1 = request.getParameter("txtAnswer1_" + questionId);
            String answer2 = request.getParameter("txtAnswer2_" + questionId);
            String answer3 = request.getParameter("txtAnswer3_" + questionId);
            String answer4 = request.getParameter("txtAnswer4_" + questionId);
            String correctAnswer = request.getParameter("rdCorrectAnswer_" + questionId);
            String statusCheck = request.getParameter("chkStatus");
            String questStatus = "Deactivate";
            if (statusCheck != null) {
                questStatus = "Activate";
            }
            boolean valid = true;
            
            if (valid) {
                QuestionDTO dto = new QuestionDTO(questionId, content, subjectId, questStatus);
                AnswerDTO ans1 = new AnswerDTO("A", questionId, answer1, false);
                AnswerDTO ans2 = new AnswerDTO("B", questionId, answer2, false);
                AnswerDTO ans3 = new AnswerDTO("C", questionId, answer3, false);
                AnswerDTO ans4 = new AnswerDTO("D", questionId, answer4, false);
                
                if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
                    valid = false;
                    request.setAttribute("UPDATE_QUEST_ERROR", "Please select the correct answer");
                    return;
                }
                
                switch (correctAnswer) {
                    case "answer1":
                        ans1.setCorrect(true);
                        break;
                    case "answer2":
                        ans2.setCorrect(true);
                        break;
                    case "answer3":
                        ans3.setCorrect(true);
                        break;
                    case "answer4":
                        ans4.setCorrect(true);
                        break;
                }
                
                ArrayList<AnswerDTO> answerList = new ArrayList<>();
                answerList.add(ans1);
                answerList.add(ans2);
                answerList.add(ans3);
                answerList.add(ans4);
                
                dto.setAnswerList(answerList);
                QuestionDAO questDAO = new QuestionDAO();
                boolean result = questDAO.updateQuestion(dto);
                if (result) {
                    AnswerDAO ansDAO = new AnswerDAO();
                    for (AnswerDTO ans : answerList) {
                        if (ansDAO.checkExistedAnswerOfQuestion(ans.getAnswerId(), questionId)) {
                            result = ansDAO.updateAnswer(ans);
                        } else {
                            result = ansDAO.insertAnswer(ans);
                        }
                    }
                    
                    if (result) {
                        request.setAttribute("UPDATE_QUEST_SUCCESS", "Update question " + questionId + " successfully");
                    }
                }
                
                if (!result){
                    request.setAttribute("UPDATE_QUEST_FAIL", "Update question " + questionId + " failed");
                }
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at UpdateQuestionServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at UpdateQuestionServlet _ SQLException : " + msg);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
