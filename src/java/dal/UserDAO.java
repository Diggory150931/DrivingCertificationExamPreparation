/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.User;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Windows 10
 */
public class UserDAO extends DBContext {

    public User authenticateUser(String username, String password) throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM UserTable WHERE username = ? AND password = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(username);
                user.setPassword(password);
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getInt("status"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean registerUser(User user) throws SQLException {
        String query = "INSERT INTO [UserTable] (username, password, role, email, dob, address, phoneNumber) VALUES (?, ?, 'USER', ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            java.util.Date utilDate = user.getDob();
            if (utilDate != null) {
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                stmt.setDate(4, sqlDate);
            } else {
                stmt.setDate(4, null);
            }

            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPhoneNumber());
            return stmt.executeUpdate() > 0;
        }
    }

    public User getUserByUsername(String savedUsername) throws SQLException {
        String query = "SELECT * FROM [UserTable] WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, savedUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("userId"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getInt("role"));
                    return user;
                }
            }
        }
        return null;
    }

    public Vector<User> getAll() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Vector<User> users = new Vector<>();
        String sql = "select * from [UserTable]";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int userid = rs.getInt("userId");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phoneNumber");
                Date dob = rs.getDate("dob");
                String address = rs.getString("address");
                int role = rs.getInt("role");

                User u = new User(userid, username, password, role, email, dob, address, phone, role);
                users.add(u);
            }
            return users;

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void insert(User user) {
        PreparedStatement stm = null;

        String sql = "INSERT INTO [dbo].[UserTable] "
                + "([username], [password], [role], [email], [dob], [address], [phoneNumber]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setInt(3, user.getRole());
            stm.setString(4, user.getEmail());

            java.util.Date utilDate = user.getDob();
            if (utilDate != null) {
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                stm.setDate(5, sqlDate);
            } else {
                stm.setDate(5, null);
            }

            stm.setString(6, user.getAddress());
            stm.setString(7, user.getPhoneNumber());
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT * FROM UserTable WHERE UserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setDob(rs.getDate("Dob"));
                user.setAddress(rs.getString("Address"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Vector<User> getAllAccount() {
        PreparedStatement stm = null;
        ResultSet rs = null;

        Vector<User> userList = new Vector<>();
        String sql = "select * from [UserTable] where role = 2";
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getInt("status"));
                userList.add(user);
            }
        } catch (SQLException e) {
        }
        return userList;
    }

    public boolean update(User user) {
        PreparedStatement stm = null;
        String sql = "UPDATE [dbo].[UserTable] SET [username] = ?, [password] = ?, [email] = ?, [dob] = ?, [address] = ?, [phoneNumber] = ? WHERE [userId] = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getEmail());

            java.util.Date utilDate = user.getDob();
            if (utilDate != null) {
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                stm.setDate(4, sqlDate);
            } else {
                stm.setDate(4, null);
            }

            stm.setString(5, user.getAddress());
            stm.setString(6, user.getPhoneNumber());
            stm.setInt(7, user.getUserId());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateUserStatus(int userId, int status) {
        String checkRoleSql = "SELECT role FROM [UserTable] WHERE userId = ?";
        String updateSql = "UPDATE [UserTable] SET status = ? WHERE userId = ?";

        try (PreparedStatement checkRoleStmt = connection.prepareStatement(checkRoleSql)) {
            checkRoleStmt.setInt(1, userId);
            ResultSet rs = checkRoleStmt.executeQuery();

            if (rs.next()) {
                int role = rs.getInt("role");
                if (role == 1) {
                    System.out.println("Cannot ban an admin user.");
                    return;
                }
            }

            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setInt(1, status);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserByIdd(int userId) {
        String sql = "SELECT * FROM UserTable WHERE userId = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getInt("status"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vector<User> searchUsers(String search) {
        Vector<User> userList = new Vector<>();
        try {
            // Kết nối đến cơ sở dữ liệu và thực hiện truy vấn
            String sql = "SELECT * FROM UserTable WHERE username LIKE ? OR email LIKE ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setEmail(rs.getString("email"));
                user.setStatus(rs.getInt("status"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

//    public static void main(String[] args) {
//        String jdbcUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProjectA1Certificate;user=sa;password=123456;";
//        Connection connection = null;
//
//        try {
//            // Thiết lập kết nối đến cơ sở dữ liệu
//            connection = DriverManager.getConnection(jdbcUrl);
//            
//            // Tạo đối tượng User để cập nhật
//            User userToUpdate = new User();
//            userToUpdate.setUserId(1); // ID người dùng cần cập nhật
//            userToUpdate.setUsername("newUsername");
//            userToUpdate.setPassword("newPassword");
//            userToUpdate.setEmail("newemail@example.com");
//            userToUpdate.setDob(new java.util.Date()); // Ngày sinh, bạn có thể thay đổi theo ý muốn
//            userToUpdate.setAddress("New Address");
//            userToUpdate.setPhoneNumber("1234567890");
//            
//            // Tạo đối tượng UserDAO và gọi hàm update
//            UserDAO userDAO = new UserDAO();
//            boolean updateSuccess = userDAO.update(userToUpdate);
//            
//            // Kiểm tra kết quả cập nhật
//            if (updateSuccess) {
//                System.out.println("Cập nhật người dùng thành công.");
//            } else {
//                System.out.println("Cập nhật người dùng không thành công.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Đảm bảo đóng kết nối nếu có
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//
//        // Thay thế userId bằng giá trị hợp lệ trong cơ sở dữ liệu của bạn
//        int userId = 3; // Giả sử bạn muốn lấy thông tin người dùng có ID là 1
//        User user = userDAO.getUserById(userId);
//
//        if (user != null) {
//            System.out.println("User ID: " + user.getUserId());
//            System.out.println("Username: " + user.getUsername());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Date of Birth: " + user.getDob());
//            System.out.println("Address: " + user.getAddress());
//            System.out.println("Phone Number: " + user.getPhoneNumber());
//        } else {
//            System.out.println("User not found.");
//        }
//    }
//
//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//
//        // Thay thế userId bằng giá trị hợp lệ trong cơ sở dữ liệu của bạn
//        int userId = 4; // Giả sử bạn muốn lấy thông tin người dùng có ID là 3
//        User user = userDAO.getUserById(userId);
//
//        if (user != null) {
//            System.out.println("Before Update:");
//            System.out.println("User ID: " + user.getUserId());
//            System.out.println("Username: " + user.getUsername());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Phone Number: " + user.getPhoneNumber());
//            System.out.println("Status: " + (user.getStatus() == 1 ? "Active" : "Banned"));
//
//            // Cập nhật trạng thái người dùng
//            int newStatus = (user.getStatus() == 1) ? 2 : 1; // Đảo ngược trạng thái
//            userDAO.updateUserStatus(userId, newStatus);
//
//            // Lấy lại thông tin người dùng sau khi cập nhật
//            user = userDAO.getUserById(userId);
//            System.out.println("After Update:");
//            System.out.println("User ID: " + user.getUserId());
//            System.out.println("Username: " + user.getUsername());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Phone Number: " + user.getPhoneNumber());
//            System.out.println("Status: " + (user.getStatus() == 1 ? "Active" : "Banned"));
//        } else {
//            System.out.println("User not found.");
//        }
//    }
//    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        
//        String searchQuery = "xoai";
//        Vector<User> result = userDAO.searchUsers(searchQuery);
//        if (result.isEmpty()) {
//            System.out.println("No users found for the search query: " + searchQuery);
//        } else {
//            System.out.println("Found users for the search query: " + searchQuery);
//            for (User user : result) {
//                System.out.println("User ID: " + user.getUserId());
//                System.out.println("Username: " + user.getUsername());
//                System.out.println("Phone Number: " + user.getPhoneNumber());
//                System.out.println("Email: " + user.getEmail());
//                System.out.println("Status: " + (user.getStatus() == 1 ? "Active" : "Banned"));
//                System.out.println("------");
//            }
//        }
//    }
}
