/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnmt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import namnmt.registration.RegistrationDAO;
import namnmt.registration.RegistrationDTO;

/**
 *
 * @author VICTUS
 */
public class LoginServlet extends HttpServlet {
    private final String SEARCH_PAGE = "search.jsp";
    private final String INVALID_PAGE = "invalid.html";
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
        //1. Lấy toàn bộ thông tin người dùng ngoại trừ nút lệnh
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
//        String button = request.getParameter("btAction");
        String url = INVALID_PAGE;
        try  {
//            if ("Login".equals(button)){
                //2. call method of model
             //2.1 new DAO object
                RegistrationDAO dao = new RegistrationDAO();
             //2.2 Gọi phương thức của DAO object đã tạo
                RegistrationDTO result = dao.checkLogin(username, password);
                // chỗ này cấm throw
             //3. process result
             if (result != null){
                 url = SEARCH_PAGE;
                 Cookie cookie = new Cookie(username,password);
                 cookie.setMaxAge(15);
                 response.addCookie(cookie);
                 //store sessions
                 HttpSession session = request.getSession();
                 session.setAttribute("USERNAME", username);
                 session.setAttribute("USER", result);
                
             }//username and password are authenticated
                
//            }//end user clicked Login button
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //sendRedirect sẽ kh sercuriry và thấy thằng cuối cùng
//           response.sendRedirect(url); 
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
