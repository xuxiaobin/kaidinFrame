package com.kaidin.appframe.service;

public class ServiceFactoryHelper {
	/**
	 * IXxxDao转换成XxxDaoImpl
	 * @param aInterfaceName
	 * @return
	 */
	public static String convertClassName(String aInterfaceName) {
		String result = null;
		
		String packageName = aInterfaceName.substring(0, aInterfaceName.lastIndexOf(".interfaces."));
		String interfaceName = aInterfaceName.substring(aInterfaceName.lastIndexOf('.') + 1);
		
		String impl_java_name_tmp = interfaceName.substring(1, interfaceName.length());
		if (impl_java_name_tmp.endsWith("Local")) {
			impl_java_name_tmp = impl_java_name_tmp.substring(0, impl_java_name_tmp.indexOf("Local"));
		} else if (impl_java_name_tmp.endsWith("Remote")) {
			impl_java_name_tmp = impl_java_name_tmp.substring(0, impl_java_name_tmp.indexOf("Remote"));
		}
		String impl_java_name = impl_java_name_tmp;
		if (0 < impl_java_name_tmp.indexOf("Dao")) {
			impl_java_name += "Impl";
		}
		result = packageName + ".impl." + impl_java_name;
		
		return result;
	}
}
