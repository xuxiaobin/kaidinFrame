${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}
<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {
<#if !pojo.isInterface()>
	private static final long serialVersionUID = 359986L;
	<#include "PojoFields.ftl"/>

	<#include "PojoConstructors.ftl"/>

	<#include "PojoPropertyAccessors.ftl"/>
	<#include "PojoToString.ftl"/>

	<#include "PojoEqualsHashcode.ftl"/>
	<#include "PojoGetConstraintSet.ftl"/>
<#else>
	<#include "PojoInterfacePropertyAccessors.ftl"/>
</#if>
<#include "PojoExtraClassCode.ftl"/>
}
</#assign>
<#-- 
<#foreach property in pojo.getPropertiesForFullConstructor()>
	<#if !property.getValue().isSimpleValue()>
import java.util.HashSet;
import java.util.Set;
	<#break>
	</#if>
</#foreach>
 -->
${pojo.generateImports()}
import com.kaidin.common.appframe.entity.BaseEntity;
${classbody}
