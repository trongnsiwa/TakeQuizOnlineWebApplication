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
import javax.servlet.http.HttpSession;
import trongns.user.UserDAO;
import trongns.user.UserDTO;
import trongns.util.MyUtility;

/**
 *
 * @author TrongNS
 */
public class LoginServlet extends HttpServlet {

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

        String url = "login.jsp";

        boolean isSuccess = false;
        boolean valid = true;

        try {
            if (request.getParameterMap().isEmpty() || request.getParameterMap() == null) {
                return;
            }

            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");

            String registerStatus = request.getParameter("registerStatus");
            if (registerStatus != null && !registerStatus.trim().isEmpty()) {
                request.setAttribute("CREATE_ACCOUNT_SUCCESS", "Register successfully. Please login!");
                return;
            }

            if (!email.matches("^[\\w!#$%&'*+=?`{|}~^-]+(?:\\.[\\w!#$%&'*+=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                request.setAttribute("LOGIN_ERROR", "Invalid email.");
                valid = false;
            }

            if (valid) {
                String hashedPassword = MyUtility.getEncryptedPassword(password);

                UserDAO dao = new UserDAO();
                dao.checkLogin(email, hashedPassword);
                UserDTO dto = dao.getLoginUser();

                HttpSession session = request.getSession();
                if (dto != null) {
                    isSuccess = true;
                    url = "home";

                    String role = "Student";
                    if (dto.isRole()) {
                        role = "Admin";
                    }

                    session.setAttribute("USER", dto.getEmail());
                    session.setAttribute("FULLNAME", dto.getFullname());
                    session.setAttribute("ROLE", role);
                } else {
                    request.setAttribute("LOGIN_ERROR", "Invalid email or password. Please try again.");
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            String msg = ex.getMessage();
            log("Error at LoginServlet _ NoSuchAlgorithmException : " + msg);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at LoginServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at LoginServlet _ SQLException : " + msg);
        } finally {
            if (isSuccess) {
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
