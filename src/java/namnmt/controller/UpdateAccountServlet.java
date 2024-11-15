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

/**
 *
 * @author VICTUS
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {
    private final String UPDATE = "update.jsp"; //đường dẫn đến trang Update 
    private final String SEARCH_CONTROLLER = "SearchLastnameServlet";  // Đường dẫn đến servlet tìm kiếm
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
        String username = request.getParameter("txtUsername");
        String newPassword = request.getParameter("txtPassword");
        String searchValue = request.getParameter("lastSearchValue");
        String chkAdmin = request.getParameter("chkAdmin");
        boolean isAdmin = "ON".equals(chkAdmin);
        String url = UPDATE;
         boolean foundError = false;
        RegistrationCreateError errors = new RegistrationCreateError();
        try  {
            // Kiểm tra độ dài của mật khẩu
            if (newPassword.trim().length() < 6 || newPassword.trim().length() > 30) {
                foundError = true;
                errors.setPasswordLengthErr("Password must be between 6 and 30 characters");
            }
            // Nếu có lỗi, lưu lỗi vào request
            if (foundError) {
                request.setAttribute("UPDATE_ERROR", errors);
            } else {
                // Nếu không có lỗi, thực hiện cập nhật tài khoản
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.updateAccount(username, newPassword, isAdmin);            
                if (result) {
                // Nếu cập nhật thành công, chuyển hướng về trang tìm kiếm với giá trị đã tìm trước đó
                url = "DispatchServlet?btAction=Search&txtSearchValue=" + searchValue;
                }
            }
         
        } catch (SQLException ex) {
          log("UpdateAccountServlet _ SQL: " + ex.getMessage());     
        } catch (ClassNotFoundException ex) {
            log("UpdateAccountServlet _ ClassNotFound: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url); // Tạo RequestDispatcher
            rd.forward(request, response); // Chuyển hướng đến URL đã xác định
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
