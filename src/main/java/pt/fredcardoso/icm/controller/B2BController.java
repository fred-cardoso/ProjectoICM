package pt.fredcardoso.icm.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.model.User;
import pt.fredcardoso.icm.model.form.LoginForm;
@Controller
@RequestMapping("/b2bservice")
public class B2BController {

	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void getDataSouce() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://home.fredcardoso.pt//icm");
		dataSource.setUsername("sa");
		dataSource.setPassword("pass");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String doNothing() {
		System.out.println("ola!!!");
		return "b2bservice/index.html";
	}

	@RequestMapping(value = { "/Login" }, method = RequestMethod.POST)
	public String Login(HttpServletRequest request, HttpServletResponse response) {
		getDataSouce();
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		;
		LoginForm login = new LoginForm();
		login.setEmail(username);
		login.setPassword(password);

		String sql = "SELECT * FROM user where email ='" + username + "'";
		Object[] obj = null;
		List<User> users = jdbcTemplate.query(sql, obj, new UserMapper());

		if (!users.isEmpty()) {
			if (passwordEncoder.matches(login.getPassword(), users.get(0).getPassword())) {
				response.setHeader("token", users.get(0).getPassword());
				response.setHeader("userId", "" + users.get(0).getId());
			} else {
				response.setHeader("token", "");

				response.setHeader("userId", "");
			}
		} else {
			response.setHeader("token", "");

			response.setHeader("userId", "");
		}
		return "b2bservice/index.html";
	}

	@RequestMapping(value = { "/insertProducts" }, method = RequestMethod.GET)
	public String InsertProducts() {
		System.out.println("insert Products!!!");
		return "b2bservice/index.html";
	}

	@RequestMapping(value = { "/viewProducts" }, method = RequestMethod.GET)
	public String viewProducts(HttpServletRequest request, HttpServletResponse response) {
		getDataSouce();
		String sql = "SELECT * FROM product where userId = '" + request.getHeader("userId") + "'";
		Object[] obj = null;
		List<Product> products = jdbcTemplate.query(sql, obj, new ProductMapper());
 
		String JsonResponse = "{ \"products\": [ ";

		for (int index = 0; index < products.size(); index++) {
			Product product = products.get(index);
			String jsonProduct = "";
			if (index != products.size() - 1) {
				jsonProduct = "{\"product\" : { \"id\" : " + product.getId() + ", \"name\" : \"" + product.getName()
						+ "\"}},";
			} else {
				jsonProduct = "{\"product\" : { \"id\" : " + product.getId() + ", \"name\" : \"" + product.getName()
						+ "\"}}";
			}
			JsonResponse += jsonProduct;
		}
		response.setHeader("response", "response");
		JsonResponse += "] }";
		response.setHeader("response", JsonResponse);
		return "b2bservice/index.html";
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
	
	class ProductMapper implements RowMapper<Product> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();

			product.setId(rs.getInt("id"));
			product.setName(rs.getString("name"));
			product.setDescription(rs.getString("description"));
			product.setMinimumSalePrice(rs.getDouble("minimum_sale_price"));
			product.setAuctionPeriod(new Date(rs.getTimestamp("auction_period").getTime()));
			product.setAwardMode(rs.getString("award_mode"));
			
			return product;
		}

	}



}