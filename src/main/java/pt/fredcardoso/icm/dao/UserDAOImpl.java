package pt.fredcardoso.icm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pt.fredcardoso.icm.model.User;

public class UserDAOImpl implements UserDAO {

	private JdbcTemplate jdbcTemplate;

	public UserDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User create(User user) {
		String sql = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
		if (jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword()) > 0) {
			return this.read(user.getEmail()).get(0);
		} else {
			return null;
		}
	}

	public List<User> read(int userId) {
		String sql = "SELECT * FROM user";
		Object[] obj = null;

		if (userId > 0) {
			sql = "SELECT * FROM user WHERE id = ?";
			obj = new Object[] { userId };
		}

		List<User> users = jdbcTemplate.query(sql, obj, new UserMapper());

		if (users.size() < 1) {
			return null;
		}

		return users;
	}

	public List<User> read(String email) {
		if (email == null || email.equals("")) {
			return new ArrayList<User>();
		}

		List<User> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email },
				new UserMapper());

		if (users.size() < 1) {
			return null;
		}

		return users;
	}

	public void update(User user) {
		// TODO Auto-generated method stub

	}

	public void delete(int userId) {
		jdbcTemplate.update("DELETE FROM user WHERE id = ?", new Object[] { userId });
	}

	class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();

			user.setId(rs.getInt("id"));
			user.setEmail(rs.getString("email"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));

			return user;
		}

	}

}
