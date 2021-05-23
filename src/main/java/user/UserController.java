package user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> user(@RequestParam(value = "id", required = false) String idString) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		int uid = Integer.parseInt(idString);
        String username = getUsername(uid);
		User user = new User(uid, username);
        return new ResponseEntity<User>(user, httpHeaders, HttpStatus.OK);
    }
}
