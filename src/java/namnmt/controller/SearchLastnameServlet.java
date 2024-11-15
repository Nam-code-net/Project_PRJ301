/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnmt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import namnmt.registration.RegistrationDAO;
import namnmt.registration.RegistrationDTO;

/**
 *
 * @author VICTUS
 */
@WebServlet(name = "SearchLastnameServlet", urlPatterns = {"/SearchLastnameServlet"})
public class SearchLastnameServlet extends HttpServlet {
    private final String SEARCH_PAGE = "search.html";
    private final String SEARCH_RESULT_PAGE = "search.jsp";
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
        //1.Lấy toàn bộ thông tin người dùng ngoại trừ nút lệnh
        String searchValue = request.getParameter("txtSearchValue");
        String url = SEARCH_PAGE;
        try  {
            if (!searchValue.trim().isEmpty()){
                //2. call method of Model
                //2.1 new DAO object
                RegistrationDAO dao = new RegistrationDAO();
                //2.2 call method of DAO object
                dao.searchLastName(searchValue);
                //3. Xử lí kết quả
                List<RegistrationDTO> result = dao.getAccounts();
                url = SEARCH_RESULT_PAGE;
                request.setAttribute("SEARCH_RESULT", result);
                // Sự tương tác người dùng làm kết quả thay đổi nên xài view động
                
            }//user input giá trị hiệu lực
            
                
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchLastnameServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchLastnameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url); //xài requestdispatcher kh xài send redirect do stateless sẽ mất req object ( request scope)
            rd.forward(request, response);
            
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
