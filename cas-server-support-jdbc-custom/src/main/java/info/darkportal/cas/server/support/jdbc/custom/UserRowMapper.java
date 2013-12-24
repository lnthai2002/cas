package info.darkportal.cas.server.support.jdbc.custom;
 
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
 
public class UserRowMapper implements RowMapper<User>
{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setEmail(rs.getString("email"));
		user.setSalt(rs.getString("encryption_salt"));
		user.setEncryptedPassword(rs.getString("encrypted_password"));
		return user;
	}
 
}
