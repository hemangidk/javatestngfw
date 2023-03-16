package com.orangehrm.pageFactory.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class propertyUtil
{
    public static Properties LoadProperties(final String fileName)  {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            try {
                assert fis != null;
                fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return prop;
    }
   
    public static String getProperty(String filename, final String Key)  {
//        final Properties prop = this.LoadProperties(String.valueOf(new File("./").getAbsolutePath()) + "\\config\\env.properties");
    	   final Properties prop = LoadProperties(filename);
    	return prop.getProperty(Key);
    }
    
    public static void setProperty(String filename, final String Key, final String Value) {
//        final Properties prop = this.LoadProperties(String.valueOf(new File("./").getAbsolutePath()) + "\\config\\env.properties");
    	  final Properties prop = LoadProperties(filename);
        prop.setProperty(Key, Value);
    }
}