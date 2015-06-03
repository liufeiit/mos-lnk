package me.mos.ti.message;

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
 * @since 2015年6月3日 上午11:54:08
 */
public class DefaultMessageProvider implements MessageProvider {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static class MessageProviderHolder {
		private static final MessageProvider MESSAGE_PROVIDER = new DefaultMessageProvider();
	}

	private DefaultMessageProvider() {
		super();
		jdbcTemplate = JdbcTemplateProvider.getJdbcTemplate();
	}

	public static MessageProvider getInstance() {
		return MessageProviderHolder.MESSAGE_PROVIDER;
	}

	@Override
	public long save(Message message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update("", new BeanPropertySqlParameterSource(message), keyHolder, new String[] { "id" });
		return keyHolder.getKey().longValue();
	}

	@Override
	public List<Message> queryMessageList(long tid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tid", tid);
		return jdbcTemplate.query("", paramMap, new MessageMapper());
	}

	@Override
	public int delete(long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return jdbcTemplate.update("", paramMap);
	}
	
	private class MessageMapper implements RowMapper<Message> {
		@Override
		public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			return null;
		}
	}
}