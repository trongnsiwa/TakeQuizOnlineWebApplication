/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trongns.answer.AnswerDAO;
import trongns.studentquiz.StudentQuizDAO;
import trongns.studentquizdetail.StudentQuizDetailDAO;
import trongns.subject.SubjectDAO;
import trongns.submit.SubmitDAO;
import trongns.submit.SubmitDTO;
import trongns.submitdetail.SubmitDetailDAO;
import trongns.submitdetail.SubmitDetailDTO;

/**
 *
 * @author TrongNS
 */
public class SubmitQuizServlet extends HttpServlet {

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
        boolean success = false;
        boolean haveError = false;

        try {
            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }

            String subjectId = request.getParameter("txtSubjectId");
            String subjectName = request.getParameter("txtSubjectName");

            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getQuizEndDateById(subjectId);
            String sgrade = request.getParameter("txtGrade");
            String squestionQuantity = request.getParameter("txtQuestionQuantity");

            HttpSession session = request.getSession(false);
            String user = (String) session.getAttribute("USER");

            StudentQuizDAO studentQuizDAO = new StudentQuizDAO();
            boolean existed = studentQuizDAO.checkExistedStudentQuizId(user + subjectId);

            if (!existed) {
                url = "submitError.jsp";
                haveError = true;
                request.setAttribute("SUBMIT_QUIZ_ERROR", "This attempt has already been finished");
                request.setAttribute("SUBJECT", subjectId);
            } else {
                String submitId = null;
                float quizGrade = Float.parseFloat(sgrade);
                float studentGrade = 0;
                int questionQuantity = Integer.parseInt(squestionQuantity);
                float pointOfAnswer = 0;
                int numberOfCorrect = 0;

                SubmitDTO submit = null;
                ArrayList<SubmitDetailDTO> submitDetails = new ArrayList<>();

                SubmitDAO submitDAO = new SubmitDAO();

                if (submitId == null) {
                    submitId = createSubmitId(15, subjectId, user);
                    boolean duplicated = submitDAO.checkDuplicatedSubmitId(submitId);
                    while (duplicated) {
                        submitId = createSubmitId(15, user, subjectId);
                        duplicated = submitDAO.checkDuplicatedSubmitId(submitId);
                    }
                }

                submit = new SubmitDTO(submitId, subjectId, user, 0, 0);

                pointOfAnswer = quizGrade / questionQuantity;

                AnswerDAO ansDAO = new AnswerDAO();
                String[] questions = request.getParameterValues("txtQuestionId");

                for (String questionId : questions) {
                    String answerChoice = request.getParameter("rdAnswerChoice_" + questionId);

                    String answerId = null;
                    String answerCorrect = null;
                    if (answerChoice != null && !answerChoice.trim().isEmpty()) {
                        String[] arr = answerChoice.split("_");
                        answerId = arr[0];
                        answerCorrect = arr[1];
                    }

                    boolean correct = "true".equals(answerCorrect) ? true : false;

                    if (correct) {
                        numberOfCorrect += 1;
                    }

                    float point = 0;
                    if (correct) {
                        point = pointOfAnswer;
                    }

                    SubmitDetailDTO detail = new SubmitDetailDTO(submitId, questionId, answerId, correct, point);
                    submitDetails.add(detail);
                }

                studentGrade = numberOfCorrect * pointOfAnswer;
                studentGrade = (float) (Math.round(studentGrade * 10) / 10.0);

                submit.setNumberOfCorrect(numberOfCorrect);
                submit.setGrade(studentGrade);

                boolean result = false;
                result = submitDAO.insertSubmit(submit);

                SubmitDetailDAO submitDetailDAO = new SubmitDetailDAO();

                if (result) {
                    for (SubmitDetailDTO detail : submitDetails) {
                        result = submitDetailDAO.insertSubmitDetail(detail);
                    }
                }

                if (result) {
                    url = "showResult.jsp";
                    success = true;

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                    String now = dateFormat.format(new Date()).toString();
                    
                    request.setAttribute("SUBMIT_TIME", now);
                    request.setAttribute("SUBJECT_ID", subjectId);
                    request.setAttribute("SUBJECT_NAME", subjectName);
                    request.setAttribute("NUMBER_OF_CORRECT", numberOfCorrect);
                    request.setAttribute("QUESTION_QUANTITY", questionQuantity);
                    request.setAttribute("STUDENT_GRADE", studentGrade);
                }

                StudentQuizDetailDAO quizDetailDAO = new StudentQuizDetailDAO();
                quizDetailDAO.deleteStudentQuizDetailById(user + subjectId);

                studentQuizDAO.deleteStudentQuizById(user + subjectId);
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at SubmitQuizServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at SubmitQuizServlet _ SQLException : " + msg);
        } finally {
            if (success || haveError) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                response.sendRedirect(url);
            }
            out.close();
        }
    }

    private String createSubmitId(int size, String userId, String subjectId) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String date = dateFormat.format(now);

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size - 10; i++) {
            int index = (int) (letters.length() * Math.random());
            sb.append(letters.charAt(index));
        }

        return userId.substring(0, 1) + subjectId.substring(0, 1) + date + sb.toString();
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
