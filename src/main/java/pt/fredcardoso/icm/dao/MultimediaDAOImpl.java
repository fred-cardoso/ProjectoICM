package pt.fredcardoso.icm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pt.fredcardoso.icm.model.Multimedia;

public class MultimediaDAOImpl implements MultimediaDAO {

	private JdbcTemplate jdbcTemplate;

	public MultimediaDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Multimedia create(final Multimedia multimedia) {
		String sql = "INSERT INTO multimedia (path, productid) VALUES (?, ?)";
		
		if (jdbcTemplate.update(sql, multimedia.getPath(), multimedia.getProductid()) > 0) {
			return this.read(multimedia.getPath()).get(0);
		} else {
			return null;
		}
	}

	public List<Multimedia> read(String multimediaPath) {
		String sql = "SELECT * FROM multimedia";
		Object[] obj = null;

		if (multimediaPath != null) {
			sql = "SELECT * FROM multimedia WHERE path = ?";
			obj = new Object[] { multimediaPath };
		}

		List<Multimedia> multimedia = jdbcTemplate.query(sql, obj, new MultimediaMapper());

		if (multimedia.size() < 1) {
			return null;
		}

		return multimedia;
	}

	public void update(Multimedia multimedia) {
		// TODO Auto-generated method stub

	}

	public void delete(String multimediaPath) {
		jdbcTemplate.update("DELETE FROM multimedia WHERE path = ?", new Object[] { multimediaPath });
	}

	class MultimediaMapper implements RowMapper<Multimedia> {

		public Multimedia mapRow(ResultSet rs, int rowNum) throws SQLException {
			Multimedia multimedia = new Multimedia();

			multimedia.setPath(rs.getString("path"));
			multimedia.setProductid(rs.getInt("productid"));

			return multimedia;
		}

	}

}
