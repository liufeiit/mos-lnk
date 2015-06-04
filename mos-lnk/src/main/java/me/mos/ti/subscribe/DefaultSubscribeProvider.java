package me.mos.ti.subscribe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
 * @since 2015年6月3日 下午11:49:19
 */
public class DefaultSubscribeProvider implements SubscribeProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static class SubscribeProviderHolder {
		private static final SubscribeProvider SUBSCRIBE_PROVIDER = new DefaultSubscribeProvider();
	}
	
	public static SubscribeProvider getInstance() {
		return SubscribeProviderHolder.SUBSCRIBE_PROVIDER;
	}
	
	private static final String ADD_SUB_SQL = "INSERT INTO `mos-lnk`.`lnk-subscribe` (`mid`, `smid`, `party_id`, `nick`, `avatar`) VALUES (:mid, :smid, :party_id, :nick, :avatar);";

	private static final String DEL_SUB_SQL = "DELETE FROM `mos-lnk`.`lnk-subscribe` WHERE `mos-lnk`.`mid` = :mid AND `mos-lnk`.`smid` = :smid;";
	
	private static final String QUERY_SUB_SQL = "SELECT "
			+ "`lnk-subscribe`.`id`, "
			+ "`lnk-subscribe`.`mid`, "
			+ "`lnk-subscribe`.`smid`, "
			+ "`lnk-subscribe`.`party_id`, "
			+ "`lnk-subscribe`.`nick`, "
			+ "`lnk-subscribe`.`avatar` "
			+ "FROM `mos-lnk`.`lnk-subscribe` WHERE `lnk-subscribe`.`mid` = :mid;";
	
	private DefaultSubscribeProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	@Override
	public long save(Subscribe subscribe) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(ADD_SUB_SQL, new BeanPropertySqlParameterSource(subscribe), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

	@Override
	public List<Subscribe> queryMessageList(long mid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		return jdbcTemplate.query(QUERY_SUB_SQL, paramMap, new SubscribeMapper());
	}

	@Override
	public int delete(long mid, long smid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mid", mid);
		paramMap.put("smid", smid);
		return jdbcTemplate.update(DEL_SUB_SQL, paramMap);
	}
	
	private class SubscribeMapper implements RowMapper<Subscribe> {
		@Override
		public Subscribe mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("id");
			if(id <= 0L) {
				return null;
			}
			Subscribe subscribe = new Subscribe();
			subscribe.setId(id);
			subscribe.setAvatar(rs.getString("avatar"));
			subscribe.setMid(rs.getLong("mid"));
			subscribe.setNick(rs.getString("nick"));
			subscribe.setParty_id(rs.getString("party_id"));
			subscribe.setSmid(rs.getLong("smid"));
			return subscribe;
		}
	}
}