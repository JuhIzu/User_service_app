package user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class UserController {
	

  	private static String getUsername(int id) {
		Connection c = null;
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Users.db");
			c.setAutoCommit(false);
			
			System.out.println("Opened database successfully");
			String sql = "SELECT Name FROM Users WHERE Id = ?";			  
			PreparedStatement pstmt  = c.prepareStatement(sql);

		        // set the value
		        pstmt.setInt(1,id);
            
            		ResultSet rs  = pstmt.executeQuery();
			
			String name ="";
            
            // loop through the result set
            while (rs.next()) {
                name = rs.getString("Name");
            }

			return name;

        }	
	catch ( Exception e ) {
		return e.toString(); //This would be a security issue in a production system
	}
   }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping("/user")
    public User user(@RequestParam(value = "id", required = false) String idString) {

		int uid = Integer.parseInt(idString);
        String username = getUsername(uid);
        return new User(uid, username);
    }
}
