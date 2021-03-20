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
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trongns.answer.AnswerDAO;
import trongns.answer.AnswerDTO;
import trongns.question.QuestionDAO;
import trongns.question.QuestionDTO;
import trongns.studentquiz.StudentQuizDAO;
import trongns.studentquiz.StudentQuizDTO;
import trongns.studentquizdetail.StudentQuizDetailDAO;
import trongns.studentquizdetail.StudentQuizDetailDTO;
import trongns.subject.SubjectDAO;
import trongns.subject.SubjectDTO;

/**
 *
 * @author TrongNS
 */
public class AttemptQuizServlet extends HttpServlet {

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

        String url = "LoadHomeByRoleServlet";
        boolean valid = true;

        try {
            if (request.getParameterMap().isEmpty() || request.getParameterMap() == null) {
                return;
            }

            String subjectId = request.getParameter("id");

            if (subjectId == null || subjectId.trim().isEmpty()) {
                return;
            }

            url = "ViewSubjectServlet";

            String attempting = request.getParameter("attempting");

            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("USER");

            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.getSubjectById(subjectId);
            SubjectDTO subject = subjectDAO.getSubjectChosen();

            StudentQuizDAO studentQuizDAO = new StudentQuizDAO();
            StudentQuizDetailDAO quizDetailDAO = new StudentQuizDetailDAO();

            boolean isAttempt = false;

            if (attempting == null) {
                isAttempt = studentQuizDAO.checkExistedStudentQuizId(user + subjectId);
            }
            subject.setAttempting(isAttempt);

            if (isAttempt || attempting != null) {
                studentQuizDAO.getStudentQuizById(user + subjectId);
                StudentQuizDTO studentQuiz = studentQuizDAO.getStudentQuiz();

                quizDetailDAO.getQuizDetailsById(user + subjectId);
                ArrayList<StudentQuizDetailDTO> quizDetailList = quizDetailDAO.getListQuizDetail();

                ArrayList<QuestionDTO> questStudentQuizList = new ArrayList<>();
                int quizQuestQuantity = 0;
                if (quizDetailList != null) {
                    for (StudentQuizDetailDTO detail : quizDetailList) {
                        QuestionDTO question = new QuestionDTO();
                        question.setQuestionId(detail.getQuestionId());
                        question.setContent(detail.getQuestionContent());
                        AnswerDTO ans1 = new AnswerDTO("A", detail.getQuestionId(), detail.getAnswer1(), false);
                        AnswerDTO ans2 = new AnswerDTO("B", detail.getQuestionId(), detail.getAnswer2(), false);
                        AnswerDTO ans3 = new AnswerDTO("C", detail.getQuestionId(), detail.getAnswer3(), false);
                        AnswerDTO ans4 = new AnswerDTO("D", detail.getQuestionId(), detail.getAnswer4(), false);

                        switch (detail.getAnswerCorrect()) {
                            case 1:
                                ans1.setCorrect(true);
                                break;
                            case 2:
                                ans2.setCorrect(true);
                                break;
                            case 3:
                                ans3.setCorrect(true);
                                break;
                            case 4:
                                ans4.setCorrect(true);
                                break;
                        }

                        ArrayList<AnswerDTO> answerList = new ArrayList<>();
                        answerList.add(ans1);
                        answerList.add(ans2);
                        answerList.add(ans3);
                        answerList.add(ans4);

                        question.setAnswerList(answerList);
                        questStudentQuizList.add(question);
                        quizQuestQuantity += 1;
                    }
                }

                if (questStudentQuizList.size() > 0) {

                    url = "attemptQuiz.jsp";

                    subject.setTimeLimit(studentQuiz.getTimeLimit());
                    subject.setGrade(studentQuiz.getGrade());
                    subject.setStartDate(studentQuiz.getStartDate());
                    subject.setEndDate(studentQuiz.getEndDate());
                    subject.setQuizQuestionQuantity(quizQuestQuantity);

                    request.setAttribute("SUBJECT_INFO", subject);
                    request.setAttribute("QUESTION_LIST", questStudentQuizList);
                }
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formatStartDate = dateFormat.format(subject.getStartDate());
                Date startDate = dateFormat.parse(formatStartDate);

                String formatEndDate = dateFormat.format(subject.getEndDate());
                Date endDate = dateFormat.parse(formatEndDate);

                Date todayDate = dateFormat.parse(dateFormat.format(new Date()));

                if (todayDate.compareTo(startDate) < 0) {
                    valid = false;
                    request.setAttribute("START_QUIZ_ERROR", "You cannot start quiz of subject " + subject.getSubjectName() + " now");
                }

                if (todayDate.compareTo(endDate) > 0) {
                    valid = false;
                    request.setAttribute("START_QUIZ_ERROR", "This quiz of subject " + subject.getSubjectName() + " is end");
                }

                if (valid) {
                    int questionQuantity = subject.getQuizQuestionQuantity();

                    QuestionDAO questDAO = new QuestionDAO();
                    questDAO.getQuizQuestionListWithConditions(subjectId, questionQuantity);
                    ArrayList<QuestionDTO> questionList = questDAO.getListQuizQuestions();

                    if (questionList != null && questionList.size() > 0) {
                        studentQuizDAO.insertStudentQuiz(new StudentQuizDTO(user, subjectId, subject.getTimeLimit(), subject.getGrade(), subject.getStartDate(), subject.getEndDate()));

                        AnswerDAO ansDAO = new AnswerDAO();

                        for (QuestionDTO quest : questionList) {
                            ansDAO.getAnswerListByQuestionId(quest.getQuestionId());
                            ArrayList<AnswerDTO> answerList = ansDAO.getListAnswer();
                            quest.setAnswerList(answerList);

                            int correct = 0;
                            for (int i = 0; i < answerList.size(); i++) {
                                if (answerList.get(i).isCorrect()) {
                                    correct = i + 1;
                                }
                            }

                            quizDetailDAO.insertStudentQuizDetail(new StudentQuizDetailDTO(user + subjectId,
                                    quest.getQuestionId(),
                                    quest.getContent(),
                                    answerList.get(0).getContent(),
                                    answerList.get(1).getContent(),
                                    answerList.get(2).getContent(),
                                    answerList.get(3).getContent(),
                                    correct));
                        }

                        url = "attemptQuiz.jsp";

                        request.setAttribute("min", subject.getTimeLimit());
                        request.setAttribute("sec", 0);
                        request.setAttribute("QUESTION_LIST", questionList);
                        request.setAttribute("SUBJECT_INFO", subject);
                    } else {
                        return;
                    }
                }
            }

        } catch (ParseException ex) {
            String msg = ex.getMessage();
            log("Error at AttemptQuizServlet _ ParseException : " + msg);
        } catch (NamingException ex) {
            String msg = ex.getMessage();
            log("Error at AttemptQuizServlet _ NamingException : " + msg);
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("Error at AttemptQuizServlet _ SQLException : " + msg);
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
