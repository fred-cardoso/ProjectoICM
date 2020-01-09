package pt.fredcardoso.icm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pt.fredcardoso.icm.model.Product;

public class ProductDAOImpl implements ProductDAO {

	private JdbcTemplate jdbcTemplate;

	public ProductDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Product create(Product product) {
		String sql = "INSERT INTO product (description, minimum_sale_price, auction_period, award_mode) VALUES (?, ?, ?, ?)";
		if (jdbcTemplate.update(sql, product.getDescription(), product.getMinimumSalePrice(), product.getAuctionPeriod(), product.getAwardMode()) > 0) {
			return this.read(product.getId()).get(0);
		} else {
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
			product.setDescription(rs.getString("description"));
			product.setMinimumSalePrice(rs.getDouble("minimum_sale_price"));
			product.setAuctionPeriod(rs.getDate("auction_period"));
			product.setAwardMode(rs.getString("award_mode"));

			return product;
		}

	}

}
