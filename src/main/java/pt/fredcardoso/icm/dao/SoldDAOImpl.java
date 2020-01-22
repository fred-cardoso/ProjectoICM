package pt.fredcardoso.icm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import pt.fredcardoso.icm.model.Sold;

public class SoldDAOImpl implements SoldDAO {

	private JdbcTemplate jdbcTemplate;

	public SoldDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Sold create(final Sold sold) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(
						"INSERT INTO sold (buyerid, productid, sellerid) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				statement.setLong(1, sold.getBuyerId());
				statement.setLong(2, sold.getProductId());
				statement.setLong(3, sold.getSellerId());
				return statement;
			}
		}, holder);

		long primaryKey = holder.getKey().longValue();

		return this.read(primaryKey).get(0);
	}

	public List<Sold> read(long soldId) {
		String sql = "SELECT * FROM sold";
		Object[] obj = null;

		if (soldId > 0) {
			sql = "SELECT * FROM sold WHERE id = ?";
			obj = new Object[] { soldId };
		}

		List<Sold> solds = jdbcTemplate.query(sql, obj, new SoldMapper());

		if (solds.size() < 1) {
			return null;
		}

		return solds;
	}

	public void update(Sold sold) {
		// TODO Auto-generated method stub

	}

	public void delete(long soldId) {
		jdbcTemplate.update("DELETE FROM sold WHERE id = ?", new Object[] { soldId });
	}

	class SoldMapper implements RowMapper<Sold> {

		public Sold mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sold sold = new Sold();

			sold.setBuyerId(rs.getLong("sellerId"));
			sold.setProductId(rs.getLong("productId"));
			sold.setSellerId(rs.getLong("sellerId"));

			return sold;
		}

	}

}
