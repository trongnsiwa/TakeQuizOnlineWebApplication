/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trongns.question.QuestionDAO;

/**
 *
 * @author TrongNS
 */
public class DeleteQuestionServlet extends HttpServlet {

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
        
        String searchedQuestion = request.getParameter("txtSearchQuestion");
        String subjectId = request.getParameter("id");
        String currentPage = request.getParameter("pageNo");
        String status = request.getParameter("cbStatus");

        String url = "SearchQuestionServlet";
        
        try {
            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }
            
            String questionId = request.getParameter("txtQuestionId");
            
            QuestionDAO dao = new QuestionDAO();
            boolean result = dao.deleteQuestionById(questionId);
            if (result) {
                request.setAttribute("DELETE_QUEST_SUCCESS", "Delete question " + questionId + " successfully");
            } else {
                 request.setAttribute("DELETE_QUEST_FAIL", "Delete question " + questionId +"failed");
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at DeleteQuestionServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at DeleteQuestionServlet _ SQLException : " + msg);
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
