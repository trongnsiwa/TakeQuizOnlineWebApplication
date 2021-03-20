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
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class CreateSubjectServlet extends HttpServlet {

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

        String url = "createSubject.jsp";

        try {
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getAllSubjects();
            ArrayList<SubjectDTO> subjectList = subjectDAO.getListSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);

            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }

            String subjectId = request.getParameter("txtSubjectId");
            subjectId = subjectId.toUpperCase();
            String subjectName = request.getParameter("txtSubjectName");
            boolean valid = true;

            subjectDAO.getSubjectById(subjectId);
            SubjectDTO checkedSubject = subjectDAO.getSubjectChosen();
            if (checkedSubject != null) {
                valid = false;
                request.setAttribute("CREATE_SUBJECT_ERROR", "Duplicate subject id");
            }

            if (valid) {
                boolean result = subjectDAO.checkExistedSubject(subjectName);
                if (result) {
                    valid = false;
                    request.setAttribute("CREATE_SUBJECT_ERROR", "This subject is already existed. Please try again");
                }
            }
            boolean result = false;
            if (valid) {
                result = subjectDAO.createSubject(subjectId, subjectName);
                if (result) {
                    request.getParameterMap().clear();
                    request.setAttribute("CREATE_SUBJECT_SUCCESS", "Create subject " + subjectId + " successfully");
                }
            }

            if (!result) {
                request.setAttribute("CREATE_SUBJECT_FAIL", "Create subject " + subjectId + " failed");
            }
            
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at CreateSubjectServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at CreateSubjectServlet _ SQLException : " + msg);
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
