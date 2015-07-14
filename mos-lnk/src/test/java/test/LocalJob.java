package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年7月14日 下午8:39:12
 */
public class LocalJob {

	private static NamedParameterJdbcTemplate jdbcTemplate;

	static {
		try {
			String url = "jdbc:mysql://10.146.30.209:3307/ti_dap_db?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useServerPrepStmts=true&useCursorFetch=true";
			jdbcTemplate = new NamedParameterJdbcTemplate(createDataSource(url, "dap", "meandpay123"));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	static String sql0 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.AgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql1 = "SELECT id FROM ti_dap_db.AgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql2 = "DELETE FROM ti_dap_db.AgentT0StlAgreement where id = :id;";

	static String sql3 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.SecAgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql4 = "SELECT id FROM ti_dap_db.SecAgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql5 = "DELETE FROM ti_dap_db.SecAgentT0StlAgreement where id = :id;";

	static String sql6 = "SELECT agentPartyGrpId, gmt_date FROM ti_dap_db.ThirdAgentT0StlAgreement group by agentPartyGrpId, gmt_date order by gmt_date desc";

	static String sql7 = "SELECT id FROM ti_dap_db.ThirdAgentT0StlAgreement where agentPartyGrpId = :agentPartyGrpId and gmt_date = :gmt_date;";

	static String sql8 = "DELETE FROM ti_dap_db.ThirdAgentT0StlAgreement where id = :id;";

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.execute(new Runnable() {
			@Override
			public void run() {
				s(sql0, sql1, sql2);
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				s(sql3, sql4, sql5);
			}
		});
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				s(sql6, sql7, sql8);
			}
		});
	}

	private static void s(String sql0, String sql1, String sql2) {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql0, new HashMap<String, Object>());
		System.out.println(list.size());
		for (Map<String, Object> map : list) {
			try {
				List<Map<String, Object>> el = jdbcTemplate.queryForList(sql1, map);
				if (CollectionUtils.isEmpty(el)) {
					continue;
				}
				System.out.println(el.size());
				if (el.size() >= 2) {
					System.out.println("del " + el.get(0).get("id"));
					jdbcTemplate.update(sql2, el.get(0));
				}
			} catch (Throwable e) {
				e.printStackTrace(System.err);
			}
		}
		System.out.println("success for SQL " + sql0);
	}

	static DriverManagerDataSource createDataSource(String url, String username, String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
}
