package pt.fredcardoso.icm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pt.fredcardoso.icm.model.Product;
import pt.fredcardoso.icm.services.MultimediaService;

public class ProductDAOImpl implements ProductDAO {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private MultimediaService multimediaService; 
	
	private JdbcTemplate jdbcTemplate;
	

	public ProductDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Product create(Product product) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String sql = "INSERT INTO product (name, description, minimum_sale_price, auction_period, award_mode, userID) VALUES (?, ?, ?, ?, ?, ?)";
		if (jdbcTemplate.update(sql, product.getName(),  product.getDescription(), product.getMinimumSalePrice(), dateFormat.format(product.getAuctionPeriod()), product.getAwardMode(), product.getUser().getId()) > 0) {
			System.out.println("criouuu");
			return this.read(product.getId()).get(0);
		} else {
			System.out.println("get rekt");
			return null;
		}
	}

	public List<Product> read(int productId) {
		String sql = "SELECT * FROM product";
		Object[] obj = null;

		if (productId > 0) {
			sql = "SELECT * FROM product WHERE id = ?";
			obj = new Object[] { productId };
		}

		List<Product> products = jdbcTemplate.query(sql, obj, new ProductMapper());

		if (products.size() < 1) {
			return null;
		}

		return products;
	}

	public void update(Product product) {
		// TODO Auto-generated method stub

	}

	public void delete(int productId) {
		jdbcTemplate.update("DELETE FROM product WHERE id = ?", new Object[] { productId });
	}

	class ProductMapper implements RowMapper<Product> {

		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();

			product.setId(rs.getInt("id"));
			product.setName(rs.getString("name"));
			product.setDescription(rs.getString("description"));
			product.setMinimumSalePrice(rs.getDouble("minimum_sale_price"));
			product.setAuctionPeriod(rs.getDate("auction_period"));
			product.setAwardMode(rs.getString("award_mode"));
			product.setUser(userDAO.read(rs.getInt("userId")).get(0));
			product.setMultimedia(multimediaService.getMultimediaOfProduct(product.getId()));

			return product;
		}

	}

}
