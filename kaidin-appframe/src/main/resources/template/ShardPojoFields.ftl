<#-- // Fields -->
 public static final String ENTITY_NAME = "com.certus.isa.entity.<#if is_shard>${pojo.getDeclarationName()}";<#else>${entity_name}";
 
<#foreach property in pojo.getAllPropertiesIterator()>
    public static final String P_${pojo.getPropertyName(property)} = "${property.name}"; 
</#foreach>
</#if>

<#if is_shard>
<#else>
<#foreach field in pojo.getAllPropertiesIterator()>
<#if pojo.getMetaAttribAsBool(field, "gen-property", true)> 
	<#if pojo.hasMetaAttribute(field, "field-description")>    
	/**
     *${pojo.getFieldJavaDoc(field, 0)}
     */
   </#if>    
   	protected ${pojo.getJavaTypeName(field, true)} ${field.name}<#if pojo.hasFieldInitializor(field, true)>= ${pojo.getFieldInitialization(field, true)}</#if>;
</#if>
</#foreach>
</#if>