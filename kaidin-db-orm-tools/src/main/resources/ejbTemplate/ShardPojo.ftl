${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

<#assign classbody>
<#include "ShardPojoTypeDeclaration.ftl"/> {

<#if !pojo.isInterface()>
	<#include "ShardPojoFields.ftl"/>
   
	<#include "ShardPojoConstructors.ftl"/>
	
	<#if is_shard>
		
	<#else>   
		<#include "PojoPropertyAccessors.ftl"/>
		
		<#include "PojoToString.ftl"/>
		
		<#include "PojoEqualsHashcode.ftl"/>
		
		<#include "PojoGetConstraintSet.ftl"/>
		
	</#if>
<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>

</#if>
<#include "PojoExtraClassCode.ftl"/>

}
</#assign>

${pojo.generateImports()}

${classbody}

