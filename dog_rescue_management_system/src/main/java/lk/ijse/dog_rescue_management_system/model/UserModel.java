package lk.ijse.dog_rescue_management_system.model;

import lk.ijse.dog_rescue_management_system.db.DBConnection;
import lk.ijse.dog_rescue_management_system.dto.UserDto;
import lk.ijse.dog_rescue_management_system.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    /**
     * Authenticates a user by checking credentials against the database
     * The role is determined from the database based on username and password
     *
     * @param username The username to check
     * @param password The password to check
     * @return UserDto if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     * @throws ClassNotFoundException if driver not found
     */
    public UserDto authenticateUser(String username, String password)
            throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        ResultSet resultSet = CrudUtil.execute(sql, username, password);

        if (resultSet.next()) {
            // User exists with given credentials
            UserDto user = new UserDto();
            user.setUserId(resultSet.getString("user_id"));
            user.setUserName(resultSet.getString("name"));
            // Don't set password for security reasons
            user.setUserRole(resultSet.getString("role"));
            // Set other user properties as needed

            return user;
        }

        return null; // Authentication failed
    }

    /**
     * Legacy method for checking login, kept for backward compatibility
     * @param userDto Contains user credentials
     * @return true if authentication successful, false otherwise
     * @throws SQLException if database error occurs
     * @throws ClassNotFoundException if driver not found
     */
    public boolean checkLogin(UserDto userDto) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        ResultSet resultSet = CrudUtil.execute(sql, userDto.getUserName(), userDto.getUserPassword());

        return resultSet.next(); // Returns true if a matching user is found
    }

    /**
     * Retrieves user by ID
     * @param userId The user ID to retrieve
     * @return UserDto if user found, null otherwise
     * @throws SQLException if database error occurs
     * @throws ClassNotFoundException if driver not found
     */
    public UserDto getUserById(String userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, userId);

        if (resultSet.next()) {
            UserDto user = new UserDto();
            user.setUserId(resultSet.getString("user_id"));
            user.setUserName(resultSet.getString("name"));
            user.setUserRole(resultSet.getString("role"));
            // Set other properties as needed

            return user;
        }

        return null;
    }

    /**
     * Updates user password for password reset functionality
     * @param username The username to update
     * @param newPassword The new password
     * @return true if password updated successfully, false otherwise
     * @throws SQLException if database error occurs
     * @throws ClassNotFoundException if driver not found
     */
    public boolean updatePassword(String username, String newPassword)
            throws SQLException, ClassNotFoundException {

        String sql = "UPDATE users SET password = ? WHERE name = ?";
        int rowsAffected = CrudUtil.execute(sql, newPassword, username) ? 1 : 0;

        return rowsAffected > 0;
    }

    /**
     * Gets a list of all users
     * @return List of UserDto objects
     * @throws SQLException if database error occurs
     * @throws ClassNotFoundException if driver not found
     */
    public List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users";
        ResultSet resultSet = CrudUtil.execute(sql);

        List<UserDto> users = new ArrayList<>();
        while (resultSet.next()) {
            UserDto user = new UserDto();
            user.setUserId(resultSet.getString("user_id"));
            user.setUserName(resultSet.getString("name"));
            user.setUserRole(resultSet.getString("role"));
            // Set other properties as needed

            users.add(user);
        }

        return users;
    }
}