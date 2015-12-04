<#-- // Property accessors -->
<#foreach property in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>
	<#if pojo.hasFieldJavaDoc(property)>
	/**
	 * ${pojo.getFieldJavaDoc(property, 4)}
	 */
	</#if>
	<#include "GetPropertyAnnotation.ftl"/>
	${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, true)} ${pojo.getGetterSignature(property)}() {
		return this.${property.name};
	}
	<#if "Id" == pojo.getPropertyName(property)>
	${pojo.getPropertyGetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, true)} ${property.name}) {
		this.${property.name} = ${property.name};
	}
	<#else>
	${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, true)} ${property.name}) {
		this.${property.name} = ${property.name};
	}
	</#if>
</#if>
</#foreach>
