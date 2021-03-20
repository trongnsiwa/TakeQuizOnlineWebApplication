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
import javax.servlet.http.HttpSession;
import trongns.question.QuestionDAO;
import trongns.studentquiz.StudentQuizDAO;
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class ViewSubjectServlet extends HttpServlet {

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

        String url = "home";
        boolean haveId = false;

        try {
            String subjectId = request.getParameter("id");
            if (subjectId != null && !subjectId.trim().isEmpty()) {
                haveId = true;
                url = "viewSubject.jsp";

                HttpSession session = request.getSession(false);
                String user = (String) session.getAttribute("USER");

                SubjectDAO subjectDAO = new SubjectDAO();
                subjectDAO.getAllSubjects();
                ArrayList<SubjectDTO> subjectList = subjectDAO.getListSubject();

                subjectDAO.getSubjectById(subjectId);
                SubjectDTO subject = subjectDAO.getSubjectChosen();
                
                QuestionDAO questDAO = new QuestionDAO();
                questDAO.countAllActivateQuestionsBySubjectId(subjectId);
                int totalQuestions = questDAO.getTotalActivateQuestion();
                
                if (totalQuestions < subject.getQuizQuestionQuantity()) {
                    subject.setQuizQuestionQuantity(0);
                }

                StudentQuizDAO studentQuizDAO = new StudentQuizDAO();

                boolean attempting = studentQuizDAO.checkExistedStudentQuizId(user + subject.getSubjectId());
                subject.setAttempting(attempting);

                request.setAttribute("SUBJECT_LIST", subjectList);
                request.setAttribute("SUBJECT_INFO", subject);
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at ViewSubjectServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at ViewSubjectServlet _ SQLException : " + msg);
        } finally {
            if (haveId) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
