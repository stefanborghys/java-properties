package be.seriousbusiness.java.properties;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class PropertyHelperTest {
	
	@Test
	public void testPropertyHelperStoreLoad(){
		final String DATABASE="database",DBUSER="dbuser",DBPASSWORD="dbpassword";
		final String TEST_DATABASE="database"+String.valueOf(System.currentTimeMillis());
		final String TEST_DBUSER="dbuser"+String.valueOf(System.currentTimeMillis());
		final String TEST_DBPASSWORD="dbpassword"+String.valueOf(System.currentTimeMillis());
		final Map<String,String> propertiesMap=new HashMap<String,String>();
		propertiesMap.put(DATABASE,TEST_DATABASE);
		propertiesMap.put(DBUSER,TEST_DBUSER);
		propertiesMap.put(DBPASSWORD,TEST_DBPASSWORD);
		PropertyHelper.store(PropertyHelper.CONFIG_PROPERTIES, propertiesMap);
		final Map<String,String> retrievedPropertiesMap=PropertyHelper.load(PropertyHelper.CONFIG_PROPERTIES);
		Assert.assertEquals("The number of stored properties should be equal to the ones retrieved",propertiesMap.size(),retrievedPropertiesMap.size());
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DATABASE,retrievedPropertiesMap.get(DATABASE));
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DBUSER,retrievedPropertiesMap.get(DBUSER));
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DBPASSWORD,retrievedPropertiesMap.get(DBPASSWORD));
	}

}
