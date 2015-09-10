package spdbTracker;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import spdbTracker.view.DBInfo;

@RunWith(JMockit.class)
public class GetDBConnectionTests {

	@Test
	public void testGettingConnectionIP() {
		System.out.println(DBInfo.getDBInfoText());
	}

}
