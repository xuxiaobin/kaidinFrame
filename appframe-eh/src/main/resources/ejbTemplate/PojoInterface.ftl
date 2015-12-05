// Generated ${date} by Hibernate Tools ${version}
${pojo.getPackageDeclaration()}

<#assign classbody>
${pojo.getClassModifiers()} interface I${pojo.getDeclarationName()}Entity extends com.certus.isa.entity.interfaces.IEntity {

 public static final String ENTITY_NAME = "com.certus.isa.entity.${pojo.getDeclarationName()}";


<#foreach property in pojo.getAllPropertiesIterator()>
	<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>   
/**
 *  ${c2j.toJavaDoc(c2j.getMetaAsString(property, "field-description"), 4)} 
 */
   public static final String P_${pojo.getPropertyName(property)} = "${property.name}"; 
   ${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, false)} ${pojo.getGetterSignature(property)}();
   ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, false)} ${property.name});
   
</#if>
</#foreach>

</#assign>

${pojo.generateImports()}
${classbody}
}