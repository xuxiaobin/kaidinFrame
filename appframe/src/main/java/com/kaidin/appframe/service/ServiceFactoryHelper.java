package com.kaidin.appframe.service;

public class ServiceFactoryHelper {
	/*
	 * IXxxDao转换成XxxDaoImpl
	 */
	public static String convertClassName(String aInterfaceName) {
		String package_name = aInterfaceName.substring(0, aInterfaceName
				.lastIndexOf(".interfaces."));
		String interface_java_name = aInterfaceName.substring(aInterfaceName
				.lastIndexOf('.') + 1);
		String impl_java_name_tmp = interface_java_name.substring(1,
				interface_java_name.length());
		if(impl_java_name_tmp.endsWith("Remote"))
		{
			impl_java_name_tmp = impl_java_name_tmp.substring(0, impl_java_name_tmp.indexOf("Remote"));
		}
		else if(impl_java_name_tmp.endsWith("Local"))
		{
			impl_java_name_tmp = impl_java_name_tmp.substring(0, impl_java_name_tmp.indexOf("Local"));
		}
		String impl_java_name = impl_java_name_tmp;
		if(impl_java_name_tmp.indexOf("Dao") >0 )
		{
			impl_java_name += "Impl";
		}
		String impl_class_name = package_name + ".impl." + impl_java_name;
		return impl_class_name;
	}
}
