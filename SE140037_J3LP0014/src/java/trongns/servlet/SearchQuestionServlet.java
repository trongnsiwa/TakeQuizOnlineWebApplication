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
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class SearchQuestionServlet extends HttpServlet {

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

        String url = "ViewQuestionServlet";

        try {
            int pageNo = 0;
            int pageSize = 0;
            String searchedQuestion = request.getParameter("txtSearchQuestion");
            String subjectId = request.getParameter("id");
            String currentPage = request.getParameter("pageNo");
            String spageSize = request.getParameter("pageSize");
            String status = request.getParameter("cbStatus");

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

            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getSubjectById(subjectId);
            SubjectDTO dto = subjectDAO.getSubjectChosen();

            if ((searchedQuestion != null && !searchedQuestion.trim().isEmpty())
                    || (status != null && !status.trim().isEmpty())
                    || (action != null && !action.trim().isEmpty())) {
                url = "viewQuestion.jsp";

                QuestionDAO questDAO = new QuestionDAO();
                questDAO.countAllQuestionsBySubjectId(subjectId);
                int totalQuests = questDAO.getTotalQuestion();

                if (!"all".equals(spageSize) && spageSize != null && !spageSize.trim().isEmpty()) {
                    pageSize = Integer.parseInt(spageSize);
                } else {
                    pageSize = totalQuests;
                }

                questDAO.countQuestionsWithConditions(subjectId, searchedQuestion, status);
                int totalSearchedQuests = questDAO.getTotalSearchedQuestions();
                
                if (totalSearchedQuests <= pageSize * (pageNo - 1)) {
                    pageNo = 1;
                }

                questDAO.getQuestionListWithCondition(subjectId, searchedQuestion, status, pageSize, pageNo);
                ArrayList<QuestionDTO> questionList = questDAO.getListQuestion();

                if (questionList != null) {
                    AnswerDAO ansDAO = new AnswerDAO();
                    for (QuestionDTO quest : questionList) {
                        ansDAO.getAnswerListByQuestionId(quest.getQuestionId());
                        ArrayList<AnswerDTO> answerList = ansDAO.getListAnswer();
                        quest.setAnswerList(answerList);
                    }

                    request.setAttribute("QUESTION_LIST", questionList);
                    if (((pageSize * pageNo) >= totalQuests) || questionList.size() < pageSize || totalSearchedQuests == pageSize || (pageSize * pageNo) >= totalSearchedQuests) {
                        request.setAttribute("LAST_LIST", "last");
                    }
                }
            }

            SubjectDAO dao = new SubjectDAO();
            dao.getAllSubjects();
            ArrayList<SubjectDTO> subjectList = dao.getListSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);
            request.setAttribute("SUBJECT_INFO", dto);

            request.setAttribute("PAGE_NO", pageNo);
            request.setAttribute("PAGE_SIZE", pageSize);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at SearchQuestionServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at SearchQuestionServlet _ SQLException : " + msg);
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
