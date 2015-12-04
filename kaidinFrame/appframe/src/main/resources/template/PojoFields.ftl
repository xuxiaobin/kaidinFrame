<#-- // Fields -->
	public static final String ENTITY_NAME = "com.kaidin.gen.entity.${pojo.getDeclarationName()}";
 
<#foreach property in pojo.getAllPropertiesIterator()>
	public static final String P_${pojo.getPropertyName(property)} = "${property.name}";
</#foreach>

<#foreach field in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(field, "gen-property", true)>
	<#if pojo.hasMetaAttribute(field, "field-description")>    
	/**
     *${pojo.getFieldJavaDoc(field, 0)}
     */
	</#if>    
   	${pojo.getFieldModifiers(field)} ${pojo.getJavaTypeName(field, true)} ${field.name}<#if pojo.hasFieldInitializor(field, true)>= ${pojo.getFieldInitialization(field, true)}</#if>;
</#if>
</#foreach>
