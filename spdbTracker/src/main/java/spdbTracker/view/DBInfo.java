package spdbTracker.view;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInfo {

	private static final String SPDB_USERNAME = "remote";
	private static final String SPDB_PW = "bbbbbbbbb";

	public static String getDBInfoText() {
		try {
			System.out.println("Getting outbound ip....");
			String jdbcUrl = "http://sert-yapi.com";
			;
			// URL whatismyip = new URL("http://checkip.amazonaws.com");
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(whatismyip.openStream()));
			// String myExternalIp = in.readLine(); // you get the IP as a
			// String
			// System.out.println("Outbound ip:" + myExternalIp);
			// System.out.println("Getting official server outbound ip....");
			// URL getExternalIp = new
			// URL("http://mobile-gourmet.com/getExternalIp.do");
			// BufferedReader in2 = new BufferedReader(new
			// InputStreamReader(getExternalIp.openStream()));
			// String externalIp = in2.readLine(); // you get the IP as a String
			// System.out.println("Server Outbound IP:" + externalIp);
			// System.out.println("Getting official server local ip....");
			// URL getServerUrl = new
			// URL("http://mobile-gourmet.com/getLocalIp.do");
			// BufferedReader in3 = new BufferedReader(new
			// InputStreamReader(getServerUrl.openStream()));
			// String serverIp = in3.readLine(); // you get the IP as a String
			// System.out.println("Server Local IP:" + serverIp);
			// if (myExternalIp.equals(externalIp))
			// jdbcUrl = serverIp.replace("/serviceProvider/", "");
			// else
			// jdbcUrl = "http://sert-yapi.com";
			jdbcUrl = constructJdbcFromServerHttpUrl(jdbcUrl);
			jdbcUrl = constructCredentials(jdbcUrl);
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			return getTableData(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getTableData(Connection connection) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			System.out.println("Running sql query to fetch DB table size...");
			st = connection.createStatement();
			String sql = "SELECT table_name \"Table\", ( data_length + index_length ) / 1024 / 1024 \"Size\" FROM information_schema.TABLES where table_schema=\"serviceprovider\" order by Size desc";
			rs = st.executeQuery(sql);
			StringBuilder sb = new StringBuilder();
			if (rs != null) {
				// sb.append(rs.getString("Table") + "===>" +
				// rs.getString("Size") + String.format("%n"));
				while (rs.next()) {
					String TableName = rs.getString("Table");
					String tableSize = rs.getString("Size");
					BigDecimal bd = new BigDecimal(tableSize);
					BigDecimal gigabyte = new BigDecimal("1024");
					BigDecimal div = bd.divide(gigabyte);
					BigDecimal one = new BigDecimal("1");
					if (div.compareTo(one) == 1) {
						div = div.setScale(2, BigDecimal.ROUND_HALF_UP);
						tableSize = div.toPlainString() + " GB";
					} else {
						bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						tableSize = bd.toPlainString() + " MB";
					}
					sb.append(TableName + "===>" + tableSize + String.format("%n"));
				}
				return sb.toString();
			} else {
				return null;
			}
		} finally {
			rs.close();
			st.close();
		}
	}

	private static String constructCredentials(String jdbcUrl) {
		jdbcUrl = jdbcUrl + "user=" + SPDB_USERNAME + "&password=" + SPDB_PW;
		return jdbcUrl;
	}

	private static String constructJdbcFromServerHttpUrl(String jdbcUrl) {
		String tmp = jdbcUrl.replace("http://", "jdbc:mysql://");
		if (tmp.contains("sert-yapi"))
			tmp = tmp + ":2018/serviceprovider?";
		else
			tmp = tmp + ":3306/serviceprovider?";
		return tmp;
	}

}
