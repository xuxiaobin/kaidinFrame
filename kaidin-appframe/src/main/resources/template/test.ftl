<#include "Ejb3TypeDeclaration.ftl"/>
${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

<#assign classbody>
${pojo.getClassModifiers()} interface I${pojo.getDeclarationName()}Entity {

 public static final String ENTITY_NAME = "${pojo.getDeclarationName()}";


<#foreach property in pojo.getAllPropertiesIterator()>
	<#if pojo.getMetaAttribAsBool(property, "gen-property", true)>   
/**
 *  ${c2j.toJavaDoc(c2j.getMetaAsString(property, "field-description"), 4)} 
 */
   public static final String P_${pojo.getPropertyName(property)} = "${property.name}"; 
   ${pojo.getPropertyGetModifiers(property)} ${pojo.getJavaTypeName(property, true)} ${pojo.getGetterSignature(property)}();
   ${pojo.getPropertySetModifiers(property)} void set${pojo.getPropertyName(property)}(${pojo.getJavaTypeName(property, true)} ${property.name});
   
</#if>
</#foreach>

</#assign>

${pojo.generateImports()}
${classbody}
}