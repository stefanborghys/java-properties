package be.seriousbusiness.java.properties;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test the PropertyHelper implementation.
 * @author seriousbusiness
 * @author Stefan Borghys
 */
public class PropertyHelperTest {
	private static final Logger LOGGER=LoggerFactory.getLogger(PropertyHelperTest.class);
	private File folder;
	
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	/**
	 * Creates a temporary folder which can be used to store .properties files.</br>
	 * Afterwards they are automaticly deleted.
	 * @throws IOException
	 */
	@Before
	public void before() throws IOException{
		folder=temporaryFolder.newFolder();
		LOGGER.info("Temporary test folder {}",folder.getPath());
	}
	
	/**
	 * Test the creation and retrieval of property data.</br>
	 * A new config.properties file is stored in a temporary folder.</br>
	 * The stored values are loaded and compared against each other.</br>
	 * Afterwards the temporary folder and it's content is removed.
	 * @throws IOException
	 */
	@Test
	public void testPropertyHelperStoreLoad() throws IOException{
		final String DATABASE="database",DBUSER="dbuser",DBPASSWORD="dbpassword";
		final String TEST_DATABASE="database"+String.valueOf(System.currentTimeMillis());
		final String TEST_DBUSER="dbuser"+String.valueOf(System.currentTimeMillis());
		final String TEST_DBPASSWORD="dbpassword"+String.valueOf(System.currentTimeMillis());
		final Map<String,String> propertiesMap=new HashMap<String,String>();
		propertiesMap.put(DATABASE,TEST_DATABASE);
		propertiesMap.put(DBUSER,TEST_DBUSER);
		propertiesMap.put(DBPASSWORD,TEST_DBPASSWORD);
		PropertyHelper.store(folder.getPath()+"/"+PropertyHelper.CONFIG_PROPERTIES, propertiesMap);
		final Map<String,String> retrievedPropertiesMap=PropertyHelper.load(folder.getPath()+"/"+PropertyHelper.CONFIG_PROPERTIES);
		Assert.assertEquals("The number of stored properties should be equal to the ones retrieved",propertiesMap.size(),retrievedPropertiesMap.size());
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DATABASE,retrievedPropertiesMap.get(DATABASE));
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DBUSER,retrievedPropertiesMap.get(DBUSER));
		Assert.assertEquals("The retrieved property value should be equal to the one stored",TEST_DBPASSWORD,retrievedPropertiesMap.get(DBPASSWORD));
	}

}
