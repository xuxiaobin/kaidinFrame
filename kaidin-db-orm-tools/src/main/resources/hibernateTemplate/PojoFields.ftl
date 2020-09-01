<#-- // Fields -->
	public static final String ENTITY_NAME = "${pojo.packageName}.${pojo.declarationName}";
<#foreach property in pojo.getAllPropertiesIterator()>
	<#if !property.name.equalsIgnoreCase("id")>public static final String P_${pojo.getPropertyName(property)}	= "${property.name}";</#if>
</#foreach>

<#foreach field in pojo.getAllPropertiesIterator()>
<#if !field.name.equalsIgnoreCase("id") && pojo.getMetaAttribAsBool(field, "gen-property", true)>
	<#if pojo.hasMetaAttribute(field, "field-description")>
	/**
	${pojo.getFieldJavaDoc(field, 0)}
	 */
	</#if>
	<#foreach column in field.columnIterator><#if column.comment ? exists && 0 != column.comment?trim?length>     
	// ${column.comment}
	</#if></#foreach>
	${pojo.getFieldModifiers(field)} ${pojo.getJavaTypeName(field, true)} ${field.name}<#if pojo.hasFieldInitializor(field, true) >= ${pojo.getFieldInitialization(field, true)}</#if>;
</#if>
</#foreach>
