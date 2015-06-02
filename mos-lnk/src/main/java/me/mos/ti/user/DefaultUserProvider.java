package me.mos.ti.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import me.mos.ti.database.JdbcTemplateProvider;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月2日 下午10:09:05
 */
public class DefaultUserProvider implements UserProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class UserProviderHolder {
		private static final UserProvider USER_PROVIDER = new DefaultUserProvider();
	}

	private DefaultUserProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static UserProvider getInstance() {
		return UserProviderHolder.USER_PROVIDER;
	}

	@Override
	public User query(long mid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		return jdbcTemplate.queryForObject("", paramMap, new UserMapper());
	}

	@Override
	public long save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update("", new BeanPropertySqlParameterSource(user), keyHolder, new String[] { "mid" });
		return keyHolder.getKey().longValue();
	}

	private class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			long mid = rs.getLong("mid");
			if (mid <= 0L) {
				return null;
			}
			User user = new User();
			user.setMid(mid);
			user.setAddress(rs.getString("address"));
			user.setAvatar(rs.getString("avatar"));
			user.setEmail(rs.getString("email"));
			user.setExtend(rs.getString("extend"));
			user.setGmt_created(rs.getDate("gmt_created"));
			user.setGmt_modified(rs.getDate("gmt_modified"));
			user.setIp(rs.getString("ip"));
			user.setLat(rs.getDouble("lat"));
			user.setLng(rs.getDouble("lng"));
			user.setNick(rs.getString("nick"));
			user.setParty_id(rs.getString("party_id"));
			user.setPasswd(rs.getString("passwd"));
			user.setPhone(rs.getString("phone"));
			user.setQq(rs.getString("qq"));
			user.setStatus(rs.getString("status"));
			user.setTelephone(rs.getString("telephone"));
			user.setWeixin(rs.getString("weixin"));
			return user;
		}
	}
}