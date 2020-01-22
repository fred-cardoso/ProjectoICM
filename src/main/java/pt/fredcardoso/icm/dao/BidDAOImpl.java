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

import pt.fredcardoso.icm.model.Bid;

public class BidDAOImpl implements BidDAO {

	private JdbcTemplate jdbcTemplate;

	public BidDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Bid create(final Bid bid) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(
						"INSERT INTO bid (datetime, value, userid, productid) VALUES (?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				statement.setDate(1, new java.sql.Date(bid.getDatetime().getTime()));
				statement.setFloat(2, bid.getValue());
				statement.setLong(3, bid.getUserId());
				statement.setLong(4, bid.getProductId());
				return statement;
			}
		}, holder);

		long primaryKey = holder.getKey().longValue();

		return this.read(primaryKey).get(0);
	}

	public List<Bid> read(long bidId) {
		String sql = "SELECT * FROM bid";
		Object[] obj = null;

		if (bidId > 0) {
			sql = "SELECT * FROM bid WHERE id = ?";
			obj = new Object[] { bidId };
		}

		List<Bid> bids = jdbcTemplate.query(sql, obj, new BidMapper());

		if (bids.size() < 1) {
			return null;
		}

		return bids;
	}

	public void update(Bid bid) {
		// TODO Auto-generated method stub

	}

	public void delete(long bidId) {
		jdbcTemplate.update("DELETE FROM bid WHERE id = ?", new Object[] { bidId });
	}

	class BidMapper implements RowMapper<Bid> {

		public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
			Bid bid = new Bid();

			bid.setId(rs.getInt("id"));
			bid.setDatetime(rs.getDate("datetime"));
			bid.setValue(rs.getFloat("value"));
			bid.setUserId(rs.getLong("userId"));
			bid.setProductId(rs.getLong("productId"));

			return bid;
		}

	}

}
