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
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;
import trongns.submit.SubmitDAO;
import trongns.submit.SubmitDTO;

/**
 *
 * @author TrongNS
 */
public class ShowHistoryServlet extends HttpServlet {

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
        boolean haveList = false;
        
        try {
            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("USER");

            int pageSize = 10;
            int pageNo = 1;

            SubmitDAO dao = new SubmitDAO();
            dao.getListSubmitQuizWithConditions(user, null, pageNo, pageSize);
            ArrayList<SubmitDTO> submitQuizList = dao.getListSubmitQuiz();

            if (submitQuizList != null) {
                haveList = true;
                url = "showHistory.jsp";
                SubjectDAO subjectDAO = new SubjectDAO();
                for (SubmitDTO submit : submitQuizList) {
                    subjectDAO.getSubjectById(submit.getSubjectId());
                    SubjectDTO subject = subjectDAO.getSubjectChosen();
                    submit.setSubjectName(subject.getSubjectName());
                }

                dao.countAllSubmitsByUserId(user);
                int totalSubmits = dao.getTotalSubmit();

                if (((pageSize * pageNo) >= totalSubmits) || submitQuizList.size() < pageSize) {
                    request.setAttribute("LAST_LIST", "last");
                }

                request.setAttribute("SUBMIT_QUIZ_LIST", submitQuizList);
                request.setAttribute("PAGE_NO", pageNo);
                request.setAttribute("PAGE_SIZE", pageSize);
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at ShowHistoryServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at ShowHistoryServlet _ SQLException : " + msg);
        } finally {
            if (haveList) {
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
