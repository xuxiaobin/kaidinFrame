<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC 
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<!-- Generated ${date} by Hibernate Tools ${version} -->
<#if is_shard>
 <hibernate-mapping>
    <union-subclass  name="${c2h.getClassName(clazz)}" table="${clazz.table.quotedName}" extends="com.certus.isa.entity.${entity_name}"  
    <#if clazz.table.catalog?exists>
    	catalog="${clazz.table.catalog}"
	</#if>
	>
    </union-subclass>
</hibernate-mapping>
<#else>
</#if>
 