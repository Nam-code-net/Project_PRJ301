/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnmt.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import namnmt.util.DBHelper;

/**
 *
 * @author VICTUS
 */

//query là các phương thức của DAO
// 3 lưu ý khi làm việc với JDBC API
//+ Phải khai báo biến cho nó
//+ Phải đóng nó bằng mọi cách
//+ Mới được dùng nó
public class RegistrationDAO implements Serializable {
    public RegistrationDTO  checkLogin(String username, String password)
    throws SQLException, ClassNotFoundException {
        RegistrationDTO result = null;
        
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;
            
            try{
        //1. get connection object and check connection object khac null
        con = DBHelper.getConnection();
        if (con != null) {
        //2. write sql String
            // mỗi mệnh đề phải viết trên một dòng và xuống dòng phải chèn thêm 1 khoảng trắng
            String sql = "Select lastname, isAdmin " 
                    + "From Registration "
                    + "Where username = ? "
                    + "And password = ?";
                // câu lệnh này là câu lệnh repare statement( nhận diện bằng dấu ?) dấu chấm hỏi trước xác định
                //từ trái qua phải từ trên xuống dưới vị trí thiết lập là số 1         
        //3. call create statement from connection object để load câu lệnh sql string vào trong bộ nhớ ==> Statement object
            // câu lệnh statement là câu lệnh không có mệnh đề where và không có truyền tham số
            // b1 là phải khai báo
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
        //4. call excute query from the statement object to excute và trả về Result
            rs = stm.executeQuery();
        //5. process Result
            // Khi viết câu lệnh truy vấn phải xác định nó trả ra 1 dòng hay nhiều dòng 1 dòng dùng if nhiều dòng dùng while
            if (rs.next()){
              String fullName = rs.getString("lastname");
              boolean role = rs.getBoolean("isAdmin");
              result = new RegistrationDTO(username, null, fullName, role);
              
            }// user name và password có tồn tại
        } //con is available
            
        
                
            }finally{
                // closed is required
                if (rs != null){
                    rs.close();
                }
                if (stm != null){
                    stm.close();
                }
                if (con != null){
                    con.close();
                }
            }
            
        
        return result;
    }
    private List<RegistrationDTO> accounts; //tạo ra để chứa nhiều dòng DTO

    public List<RegistrationDTO> getAccounts() { //getA control space
        return accounts;
    }
    
    public void searchLastName(String searchValue)
            throws SQLException, ClassNotFoundException{
       
        boolean result = false;
        
            Connection con = null;
            PreparedStatement stm = null;
            ResultSet rs = null;
            
         try{
        //1. get connection object and check connection object khac null
        con = DBHelper.getConnection();
        if (con != null) {
        //2. write sql String
            // mỗi mệnh đề phải viết trên một dòng và xuống dòng phải chèn thêm 1 khoảng trắng
            String sql = "Select username, password, lastname, isAdmin "
                    + "From Registration "
                    + "Where lastname Like ?";
                // câu lệnh này là câu lệnh repare statement( nhận diện bằng dấu ?) dấu chấm hỏi trước xác định
                //từ trái qua phải từ trên xuống dưới vị trí thiết lập là số 1         
        //3. call create statement from connection object để load câu lệnh sql string vào trong bộ nhớ ==> Statement object
            // câu lệnh statement là câu lệnh không có mệnh đề where và không có truyền tham số
            // b1 là phải khai báo
         stm = con.prepareStatement(sql);  
         stm.setString(1, "%" + searchValue + "%");
           
        //4. call excute query from the statement object to excute và trả về Result
         rs = stm.executeQuery();
        //5. process Result
            // Khi viết câu lệnh truy vấn phải xác định nó trả ra 1 dòng hay nhiều dòng 1 dòng dùng if nhiều dòng dùng while
         while(rs.next()){
             //map Data
             //5.1 get data from the Result Set
            String username = rs.getString("username");
            String password = rs.getString("password");
            String fullname = rs.getString("lastname");
            boolean role = rs.getBoolean("isAdmin");
             //5.2 set data to DTO object
            //a.new DTO object 
            RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
                
            //b.set data into DTO object
            if (this.accounts == null){
                this.accounts = new ArrayList<>();
            }//accounts are NOT exist 
            this.accounts.add(dto);
         } //traverse each row of table
           
    } //con is available
            
        
                
        }finally{
                // closed is required
                if (rs != null){
                    rs.close();
                }
                if (stm != null){
                    stm.close();
                }
                if (con != null){
                    con.close();
                }
            }
            
    }
    
    public boolean deleteAccount(String username) 
            throws ClassNotFoundException, SQLException{
        
        boolean result = false;
        
            Connection con = null;
            PreparedStatement stm = null;
            
            
            try{
        //1. get connection object and check connection object khac null
        con = DBHelper.getConnection();
        if (con != null) {
        //2. write sql String
            // mỗi mệnh đề phải viết trên một dòng và xuống dòng phải chèn thêm 1 khoảng trắng
            String sql = "Delete From Registration "
                    + "Where username = ?";
                // câu lệnh này là câu lệnh repare statement( nhận diện bằng dấu ?) dấu chấm hỏi trước xác định
                //từ trái qua phải từ trên xuống dưới vị trí thiết lập là số 1         
        //3. call create statement from connection object để load câu lệnh sql string vào trong bộ nhớ ==> Statement object
            // câu lệnh statement là câu lệnh không có mệnh đề where và không có truyền tham số
            // b1 là phải khai báo
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
        //4. call excute query from the statement object to excute và trả về Result
            int affectedRows = stm.executeUpdate();
            // insert delete update đều là executeUpdate
        //5. process Result
            // Khi viết câu lệnh truy vấn phải xác định nó trả ra 1 dòng hay nhiều dòng 1 dòng dùng if nhiều dòng dùng while
            if (affectedRows > 0){
                result = true;
            }//delete action has completed
        } //con is available
            
        
                
        }finally{
                // closed is required
                
                if (stm != null){
                    stm.close();
                }
                if (con != null){
                    con.close();
                }
            }
            
        
        return result;
    }
    public void updateAccount(String username)
            throws SQLException, ClassNotFoundException {
        //12. connect DB
        //3 điều lưu ý để sử dụng các thành phần JDBC API
        //*Khai báo biến
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try { //***Dùng
            //1. phải có connection object và check connection object khác null
            con = DBHelper.getConnection();

            //13. query DB
            if (con != null) {
                //2. viết câu lệnh SQL String
                String sql = "Delete From Registration "
                        + "Where username = ?";   //mỗi mệnh đề viết trên 1 dòng | trước khi xuống dòng phải cách 1 khoảng trắng rồi enter xuống dòng
                //3. gọi phương thức createStatement() từ connection object để load câu lệnh SQL String vào bên trong bộ nhớ, tạo ra statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. gọi phương thức executeQuery() từ statement object để thực thi và trả ra Result
                int affectedRows = stm.executeUpdate(); //INSERT DELETE UPDATE ĐỀU LÀ executeUpdate()
                //5. xử lý Result
                //khi xử lý mệnh đề if phải biết nó trả ra 1 hay nhiều dòng
                //1 dòng dùng if, nhiều dòng dùng while
                
                //14. map

            } //con is available
        } finally {
            //**Đóng bằng mọi cách | đóng theo chiều ngược
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public boolean createAccount(RegistrationDTO account) throws SQLException, ClassNotFoundException{
              
        
        boolean result = false;
        
            Connection con = null;
            PreparedStatement stm = null;
            
            
            try{
        //1. get connection object and check connection object khac null
        con = DBHelper.getConnection();
        if (con != null) {
        //2. write sql String
            // mỗi mệnh đề phải viết trên một dòng và xuống dòng phải chèn thêm 1 khoảng trắng
            String sql = "Insert Into Registration("
                    + "username, password, lastname,isAdmin"
                    + ") Values("
                    + "?,?,?,?"
                    + ")";
                // câu lệnh này là câu lệnh repare statement( nhận diện bằng dấu ?) dấu chấm hỏi trước xác định
                //từ trái qua phải từ trên xuống dưới vị trí thiết lập là số 1         
        //3. call create statement from connection object để load câu lệnh sql string vào trong bộ nhớ ==> Statement object
            // câu lệnh statement là câu lệnh không có mệnh đề where và không có truyền tham số
            // b1 là phải khai báo
            stm = con.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            stm.setString(2, account.getPassword());
            stm.setString(3, account.getFullName());
            stm.setBoolean(4, account.isRole());
           
        //4. call excute query from the statement object to excute và trả về Result
            int affectedRows = stm.executeUpdate();
            // insert delete update đều là executeUpdate
        //5. process Result
            // Khi viết câu lệnh truy vấn phải xác định nó trả ra 1 dòng hay nhiều dòng 1 dòng dùng if nhiều dòng dùng while
            if (affectedRows > 0){
                result = true;
            }//delete action has completed
        } //con is available
            
        
                
        }finally{
                // closed is required
                
                if (stm != null){
                    stm.close();
                }
                if (con != null){
                    con.close();
                }
            }
            
        
        return result;
    }
    public boolean updateAccount(String username, String newPassword, boolean isAdmin)
            throws SQLException, ClassNotFoundException{
           boolean result = false;
        Connection connection = null;//declaration
        PreparedStatement stm = null;       
        try{
        //1. get connection object, check not null
            connection = DBHelper.getConnection();
            if (connection != null){
        //2. write SQL String xoa 1 chuyen trong registration voi dieu kien name = name trong bang
            String sql = "UPDATE Registration "
                       + "SET password = ?, isAdmin = ? "
                       + "WHERE username = ?";
        //3. create statement from connection object --> statement object
        stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setBoolean(2, isAdmin);
            stm.setString(3, username);
        //4. excute statement from statement object --> result
        int affectedRow = stm.executeUpdate(); //insert, delete, update dung executeUpdate
        //5. process Result
            if (affectedRow > 0) {
                result = true;
            }//delete action is successfully
            }//end connection is available
        }finally{
            //close required

            if(stm != null){
                stm.close();
            }
            if (connection != null){
                connection.close();
            }
        }
        
        
        return result;
    
    }
    
}
