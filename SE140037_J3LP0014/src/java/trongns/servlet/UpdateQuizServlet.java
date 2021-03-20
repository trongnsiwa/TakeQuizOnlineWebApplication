/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trongns.question.QuestionDAO;
import trongns.subject.UpdateQuizError;
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class UpdateQuizServlet extends HttpServlet {

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
            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }

            haveId = true;
            url = "updateQuiz.jsp";

            SubjectDAO subjectDAO = new SubjectDAO();
            String id = request.getParameter("id");

            if (id != null && !id.trim().isEmpty()) {
                subjectDAO.getSubjectById(id);
                SubjectDTO dto = subjectDAO.getSubjectChosen();

                QuestionDAO questionDAO = new QuestionDAO();
                questionDAO.countAllActivateQuestionsBySubjectId(id);
                int totalQuestion = questionDAO.getTotalActivateQuestion();

                if (totalQuestion > 0) {
                    if (totalQuestion != dto.getTotalQuestion()) {
                        subjectDAO.updateTotalQuestionById(id, totalQuestion);
                        dto.setTotalQuestion(totalQuestion);
                    }
                    if (totalQuestion < dto.getQuizQuestionQuantity()) {
                        subjectDAO.updateQuizQuestionQuantityById(id, totalQuestion);
                        dto.setQuizQuestionQuantity(totalQuestion);
                    }
                    request.setAttribute("SUBJECT_INFO", dto);
                } else {
                    request.setAttribute("SUBJECT_INFO", "");
                }
            } else {
                String subjectId = request.getParameter("txtSubjectId");
                String subjectName = request.getParameter("txtSubjectName");
                String stotalQuestion = request.getParameter("txtTotalQuestion");
                String stimeLimit = request.getParameter("txtTimeLimit");
                String squestQuantity = request.getParameter("txtQuizQuestionQuantity");
                String sgrade = request.getParameter("txtGrade");
                String sstartDate = request.getParameter("txtStartDate");
                String sendDate = request.getParameter("txtEndDate");

                boolean valid = true;

                UpdateQuizError error = new UpdateQuizError();

                if (Integer.parseInt(stimeLimit) == 0) {
                    valid = false;
                    error.setZeroMinute("Not allow zero minute for quiz");
                }

                if (Integer.parseInt(squestQuantity) == 0) {
                    valid = false;
                    error.setZeroQuestionQuantity("Not allow zero question quantity");
                }
                
                if (Integer.parseInt(stotalQuestion) == 0) {
                    valid = false;
                    error.setZeroTotalQuestion("Not allow zero total question");
                }

                if (Integer.parseInt(squestQuantity) > Integer.parseInt(stotalQuestion)) {
                    valid = false;
                    error.setNotEnoughQuestion("Not enough question to make quiz");
                }

                if (sstartDate == null || sstartDate.trim().isEmpty()) {
                    valid = false;
                    error.setEmptyStartDate("Please choose start quiz date");
                }

                if (sendDate == null || sendDate.trim().isEmpty()) {
                    valid = false;
                    error.setEmptyEndDate("Please choose end quiz date");
                }

                Date startDate = null;
                Date endDate = null;
                
                if (valid) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    startDate = sdf.parse(sstartDate);
                    endDate = sdf.parse(sendDate);

                    if (startDate.compareTo(endDate) > 0) {
                        valid = false;
                        error.setStartDateAfterEndDate("Start Date must be before or equal End Date");
                    }
                }

                if (valid) {
                    int totalQuestion = Integer.parseInt(stotalQuestion);
                    int timeLimit = Integer.parseInt(stimeLimit);
                    int questQuantity = Integer.parseInt(squestQuantity);
                    float grade = Float.parseFloat(sgrade);

                    boolean result = subjectDAO.updateQuizForSubject(subjectId, subjectName, totalQuestion, timeLimit, questQuantity, grade, startDate, endDate);

                    if (result) {
                        request.getParameterMap().clear();
                        request.setAttribute("UPDATE_QUIZ_SUCCESS", "Make quiz for subject " + subjectId + " successfully");
                    } else {
                        request.setAttribute("UPDATE_QUIZ_FAIL", "Make quiz for subject " + subjectId + " failed");
                    }
                } else {
                    request.setAttribute("UPDATE_QUIZ_ERROR", error);
                }

                subjectDAO.getSubjectById(subjectId);
                SubjectDTO dto = subjectDAO.getSubjectChosen();
                request.setAttribute("SUBJECT_INFO", dto);
            }

            subjectDAO.getAllSubjects();
            ArrayList<SubjectDTO> subjectList = subjectDAO.getListSubject();

            request.setAttribute("SUBJECT_LIST", subjectList);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at UpdateQuizServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at UpdateQuizServlet _ SQLException : " + msg);
        } catch (ParseException ex) {
            String msg = ex.getMessage();
            log("Error at UpdateQuizServlet _ ParseException : " + msg);
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
