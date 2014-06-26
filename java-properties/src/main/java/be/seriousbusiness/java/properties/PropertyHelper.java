package be.seriousbusiness.java.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Properties helper.</br>
 * Can be used to store and load property data.</br>
 * </br>
 * source: <a href="http://www.mkyong.com/java/java-properties-file-examples/">http://www.mkyong.com/java/java-properties-file-examples/</a>
 * @author seriousbusiness
 * @author Stefan Borghys
 *
 */
public class PropertyHelper {
	private static final Logger LOGGER=LoggerFactory.getLogger(PropertyHelper.class);
	private static final Properties properties=new Properties();
	public static final String CONFIG_PROPERTIES="config.properties";
	
	/**
	 * Store a properties file in the root folder.
	 * @param fileName the file name
	 * @param propertiesMap a map containing the property name as key and it's value
	 * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
	 */
	public synchronized static final void store(final String fileName,final Map<String,String> propertiesMap) throws IllegalArgumentException {
		if(fileName==null || fileName.isEmpty()){
			throw new IllegalArgumentException("The file name is null or empty");
		}
		try(final OutputStream outputStream=new FileOutputStream(fileName)) {
			if(propertiesMap!=null){
				for(Map.Entry<String,String> property : propertiesMap.entrySet()){
					properties.setProperty(property.getKey(),property.getValue());
				}
			}
			try {
				properties.store(outputStream,null);
			} catch (final IOException e) {
				LOGGER.error("Properties could not be written to file {}",fileName,e);
			}
			
		} catch (final FileNotFoundException e) {
			LOGGER.error("Properties file {} could not be found",fileName,e);
		} catch (final IOException e) {
			LOGGER.error("OutputStream could not be closed",e);
		}
	}
	
	/**
	 * Loads Properties into a map containing the property name as key and it's value.
	 * @return an empty map when no properties are found.
	 */
	private synchronized static final Map<String,String> load(){
		final Enumeration<?> propertyNames=properties.propertyNames();
		Map<String,String> propertiesMap=new HashMap<String,String>();
		while(propertyNames.hasMoreElements()){
			final String propertyName=(String)propertyNames.nextElement(),
					propertyValue=properties.getProperty(propertyName);
			propertiesMap.put(propertyName,propertyValue);
		}
		return propertiesMap;
	}
	
	/**
	 * Load a properties file data from the root folder.
	 * @param fileName the file name
	 * @return a map containing the property name as key and it's value 
	 * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
	 */
	public synchronized static final Map<String,String> load(final String fileName) throws IllegalArgumentException {
		if(fileName==null || fileName.isEmpty()){
			throw new IllegalArgumentException("The file name is null or empty");
		}
		try(final InputStream inputStream=new FileInputStream(fileName)) {
			properties.load(inputStream);
		} catch (final FileNotFoundException e) {
			LOGGER.error("Properties file {} could not be found",fileName,e);
		} catch (final IOException e) {
			LOGGER.error("InputStream could not be closed",e);
		}
		return load();
	}
	
	/**
	 * Load a properties file data from the classpath.
	 * @param fileName the file name
	 * @return a map containing the property name as key and it's value
	 * @throws IllegalArgumentException when the file name is <code>null</code> or empty.
	 */
	public synchronized static final Map<String,String> loadFromClassPath(final String fileName) throws IllegalArgumentException {
		if(fileName==null || fileName.isEmpty()){
			throw new IllegalArgumentException("The file name is null or empty");
		}
		try(final InputStream inputStream=PropertyHelper.class.getClassLoader().getResourceAsStream(fileName)) {
			properties.load(inputStream);
		} catch (final FileNotFoundException e) {
			LOGGER.error("Properties file {} could not be found",fileName,e);
		} catch (final IOException e) {
			LOGGER.error("InputStream could not be closed",e);
		}
		return load();
	}

}
