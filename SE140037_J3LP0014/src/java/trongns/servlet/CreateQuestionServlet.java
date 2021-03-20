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
import trongns.question.CreateQuestionError;
import trongns.question.QuestionDAO;
import trongns.question.QuestionDTO;
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class CreateQuestionServlet extends HttpServlet {

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

        String url = "createQuestion.jsp";

        try {
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getAllSubjects();
            ArrayList<SubjectDTO> subjectList = subjectDAO.getListSubject();
            request.setAttribute("SUBJECT_LIST", subjectList);

            if (request.getParameterMap() == null || request.getParameterMap().isEmpty()) {
                return;
            }

            String questionId = request.getParameter("txtQuestionId");
            questionId = questionId.toUpperCase();
            String content = request.getParameter("txtQuestionContent");
            String answer1 = request.getParameter("txtAnswer1");
            String answer2 = request.getParameter("txtAnswer2");
            String answer3 = request.getParameter("txtAnswer3");
            String answer4 = request.getParameter("txtAnswer4");
            String subjectId = request.getParameter("cbSubject");
            String correctAnswer = request.getParameter("rdCorrectAnswer");
            String status = "Activate";
            boolean valid = true;

            CreateQuestionError error = new CreateQuestionError();

            if (subjectId.trim().isEmpty()) {
                valid = false;
                error.setEmptySubject("Please choose a subject to create question");
            }
            
            if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
                valid = false;
                error.setEmptyCorrectAnswer("Please choose a correct answer");
            }

            QuestionDAO questDAO = new QuestionDAO();
            boolean result = questDAO.checkExistQuestionId(questionId);
            if (result) {
                valid = false;
                error.setDuplicateQuestion("Duplicate question id");
            }

            if (valid) {
                result = questDAO.checkExistQuestion(content, subjectId);
                if (result) {
                    valid = false;
                    error.setDuplicateQuestion("This question is already existed. Please try again");
                }
            }

            if (valid) {
                QuestionDTO dto = new QuestionDTO(questionId, content, subjectId, status);
                AnswerDTO ans1 = new AnswerDTO("A", questionId, answer1, false);
                AnswerDTO ans2 = new AnswerDTO("B", questionId, answer2, false);
                AnswerDTO ans3 = new AnswerDTO("C", questionId, answer3, false);
                AnswerDTO ans4 = new AnswerDTO("D", questionId, answer4, false);

                switch (correctAnswer) {
                    case "answer1":
                        ans1.setCorrect(true);
                        break;
                    case "answer2":
                        ans2.setCorrect(true);
                        break;
                    case "answer3":
                        ans3.setCorrect(true);
                        break;
                    case "answer4":
                        ans4.setCorrect(true);
                        break;
                }

                ArrayList<AnswerDTO> answerList = new ArrayList<>();
                answerList.add(ans1);
                answerList.add(ans2);
                answerList.add(ans3);
                answerList.add(ans4);

                dto.setAnswerList(answerList);

                result = questDAO.insertQuestion(dto);
                if (result) {
                    AnswerDAO ansDAO = new AnswerDAO();
                    for (AnswerDTO ans : answerList) {
                        result = ansDAO.insertAnswer(ans);
                    }

                    if (result) {
                        subjectDAO.getTotalQuestionyById(subjectId);
                        int oldTotal = subjectDAO.getTotalQuestion();
                        
                        questDAO.countAllQuestionsBySubjectId(subjectId);
                        int totalQuestions = questDAO.getTotalQuestion();
                        
                        int newTotal = oldTotal + 1;
                        
                        if (totalQuestions < oldTotal) {
                            newTotal = totalQuestions;
                        }
                        
                        subjectDAO.updateTotalQuestionById(subjectId, newTotal);
                        
                        request.getParameterMap().clear();
                        request.setAttribute("CREATE_QUEST_SUCCESS", "Create question " + questionId + " for subject " + subjectId + " successfully");
                    }
                }

                if (!result) {
                    request.setAttribute("CREATE_QUEST_FAIL", "Create question " + questionId + " failed");
                }
            } else {
                request.setAttribute("CREATE_QUEST_ERROR", error);
            }
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at CreateQuestionServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at CreateQuestionServlet _ SQLException : " + msg);
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
