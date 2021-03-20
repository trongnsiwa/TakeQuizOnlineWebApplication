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
public class SearchHistoryServlet extends HttpServlet {

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

        try {
            String searchedSubmit = request.getParameter("txtSearchSubmit");
            String currentPage = request.getParameter("pageNo");
            String spageSize = request.getParameter("pageSize");

            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("USER");

            url = "ShowHistoryServlet";

            int pageNo = 0;
            int pageSize = 0;

            if (currentPage == null) {
                pageNo = 1;
            } else {
                pageNo = Integer.parseInt(currentPage);
            }

            String action = request.getParameter("action");

            if ("Prev".equals(action)) {
                if (pageNo > 1) {
                    pageNo--;
                }
            } else if ("Next".equals(action)) {
                pageNo++;
            }

            if ((searchedSubmit != null && !searchedSubmit.trim().isEmpty())
                    || (action != null && !action.trim().isEmpty())) {
                url = "showHistory.jsp";
                SubmitDAO dao = new SubmitDAO();

                dao.countAllSubmitsByUserId(user);
                int totalSubmits = dao.getTotalSubmit();

                if (!"all".equals(spageSize) && spageSize != null && !spageSize.trim().isEmpty()) {
                    pageSize = Integer.parseInt(spageSize);
                } else {
                    pageSize = totalSubmits;
                }

                dao.getListSubmitQuizWithConditions(user, searchedSubmit, pageNo, pageSize);
                ArrayList<SubmitDTO> submitQuizList = dao.getListSubmitQuiz();

                if (submitQuizList != null) {
                    SubjectDAO subjectDAO = new SubjectDAO();

                    for (SubmitDTO submit : submitQuizList) {
                        subjectDAO.getSubjectById(submit.getSubjectId());
                        SubjectDTO subject = subjectDAO.getSubjectChosen();
                        submit.setSubjectName(subject.getSubjectName());
                    }

                    dao.countSubmitsWithConditions(user, searchedSubmit);
                    int totalSearchedSubmits = dao.getTotalSearchedSubmits();

                    request.setAttribute("SUBMIT_QUIZ_LIST", submitQuizList);
                    if (((pageSize * pageNo) >= totalSubmits) || submitQuizList.size() < pageSize || totalSearchedSubmits == pageSize || ((pageSize * pageNo) >= totalSearchedSubmits)) {
                        request.setAttribute("LAST_LIST", "last");
                    }
                }
            }

            request.setAttribute("PAGE_NO", pageNo);
            request.setAttribute("PAGE_SIZE", pageSize);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at SearchHistoryServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at SearchHistoryServlet _ SQLException : " + msg);
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
