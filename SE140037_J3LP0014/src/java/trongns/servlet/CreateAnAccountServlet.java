/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trongns.user.CreateAnAccountError;
import trongns.user.UserDAO;
import trongns.user.UserDTO;
import trongns.util.MyUtility;

/**
 *
 * @author TrongNS
 */
public class CreateAnAccountServlet extends HttpServlet {

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

        String url = "register.jsp";

        boolean success = false;

        try {
            if (request.getParameterMap().isEmpty() || request.getParameterMap() == null) {
                return;
            }
            
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            String fullname = request.getParameter("txtFullname");
            String role = request.getParameter("rdRole");
            boolean valid = true;
            String status = "New";
            
            CreateAnAccountError error = new CreateAnAccountError();
            
            if (!email.matches("^[\\w!#$%&'*+=?`{|}~^-]+(?:\\.[\\w!#$%&'*+=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                error.setInvalidEmail("Invalid email.");
                valid = false;
            }

            if (!confirm.equals(password)) {
                error.setConfirmNotMatchPassword("Confirm not match password.");
                valid = false;
            }

            UserDAO dao = new UserDAO();
            boolean result = dao.checkExistAccount(email);
            if (result) {
                error.setDuplicateEmail("Email is already existed.");
                valid = false;
            }

            if (valid) {
                String hashedPassword = MyUtility.getEncryptedPassword(password);

                boolean isAdmin = false;
                if ("Teacher".equals(role)) {
                    isAdmin = true;
                }

                UserDTO dto = new UserDTO(email, hashedPassword, fullname, isAdmin, status);
                result = dao.createNewAccount(dto);
                
                if (result) {
                    url = "login"
                            + "?registerStatus=success";
                    success = true;
                } else {
                    request.setAttribute("CREATE_ACCOUNT_FAIL", "Sorry. Account registered fail.");
                }
            } else {
                request.setAttribute("CREATE_ACCOUNT_ERROR", error);
            }
        } catch (NoSuchAlgorithmException ex) {
            String msg = ex.getMessage();
            log("Error at CreateAnAccountServlet _ NoSuchAlgorithmException : " + msg);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at CreateAnAccountServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at CreateAnAccountServlet _ SQLException : " + msg);
        } finally {
            if (success) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
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
