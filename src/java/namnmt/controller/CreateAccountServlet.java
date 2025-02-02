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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import namnmt.registration.RegistrationCreateError;
import namnmt.registration.RegistrationDAO;
import namnmt.registration.RegistrationDTO;

/**
 *
 * @author VICTUS
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String ERROR_PAGE = "createAccount.jsp";
    private final String LOGIN_PAGE = "login.html";
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
        //1. get all users information
        //1. validate all user`s errors
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            String fullname = request.getParameter("txtFullname");
            
            boolean foundError = false;
            RegistrationCreateError errors = new RegistrationCreateError();
            String url = ERROR_PAGE;
        try {
            if(username.trim().length() < 6
                    || username.trim().length() > 20) {
                foundError = true;
                errors.setUsernameLengthErr("username is required to type from the 6 top 20 characters");
                
            }
            if(password.trim().length() < 6
                    || password.trim().length() > 30) {
                foundError = true;
                errors.setPasswordLengthErr("password is required to type from the 6 top 30 characters");
                
            }else if (!confirm.trim().equals(password.trim())){ //confirm not matched
                foundError = true;
                errors.setConfirmNotMatched("Confirm must matched password");
            }
            if(fullname.trim().length() < 2
                    || fullname.trim().length() > 50) {
                foundError = true;
                errors.setFullnameLengthErr("fullname is required to type from the 6 top 50 characters");
                
            }
            if(foundError) { //has atleast one error
                request.setAttribute("CREATE_ERROR", errors);
            }else {//noerror
                //2. call method of model
                RegistrationDAO  dao = new RegistrationDAO();
                RegistrationDTO dto = new RegistrationDTO(username, password, fullname, false);
                
                boolean result = dao.createAccount(dto);
               //3. process result
               if(result){
                url = LOGIN_PAGE;
                }//end create account
            }//end process no errors
            
            
            
            
        } catch (ClassNotFoundException ex) {
            
              log("CreateAccountServlet _ ClassnotFound: "+ ex.getMessage());
        } catch (SQLException ex) {
                String errMsg = ex.getMessage();
        log ("RegisterServlet _ SQL: " + errMsg);
        if (errMsg.contains("duplicate")){//username is existed
            errors.setUsernameIsExisted(username + "is existed");
            request.setAttribute("CREATE_ERROR", errors);
        }
        
            
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request,response);
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
