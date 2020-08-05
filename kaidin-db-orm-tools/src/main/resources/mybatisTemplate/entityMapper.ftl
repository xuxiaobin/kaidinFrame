<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="<#if packageName??>${packageName}.</#if>IEntity${entityName ? cap_first}Dao">
	<!-- 
	<resultMap id="entity${entityName ? cap_first}" type="${packageName}.Entity${entityName ? cap_first}">
	<#list columnList as column>
		<#if column.columnName == "id"><id <#else><result </#if>column="${column.columnName}" property="${column.propertyName}" jdbcType="$column.columnType ? upper_case}" javaType="$column.propertyType}"/>
	</#list>
	-->
	
	<insert id="save" parameterType="<#if packageName??>${packageName}.</$if>Entity${entityName ? cap_first}">
		insert into ${tableName} (
			<#list columnList as column><#if column.column != "id">${column.columnName}<#if column_has_nest>,<#if (column_index + 1) % 5 == 0>
			<#else> </#if></#if></#if><#list>
		) values (
			<#list columnList as column><#if column.column != "id">${r'#{'}${column.columnName}}<#if column_has_nest>,<#if (column_index + 1) % 5 == 0>
			<#else> </#if></#if></#if><#list>
		)
		<selectKey resultType="long" keyProperty="id">
			select last_insert_id()
		</selectKey>
	</insert>
	
	<delete id="delete" parameterType="<#if packageName??>${packageName}.</$if>Entity${entityName ? cap_first}">
		delete
		from ${tableName}
		where id = ${r'#{id}'}
	</delete>
	<delete id="deleteById" parameterType="long">
		delete
		from ${tableName}
		where id = ${r'#{id}'}
	</delete>
	<delete id="deleteEntities">
		delete
		from ${tableName}
		where ${r'${value}'}
	</delete>
	
	<update id="update" parameterType="<#if packageName??>${packageName}.</$if>Entity${entityName ? cap_first}">
		update ${tableName}
		set <#list columnList as column><#rt>
			<#if column.columnName != "id">${column.columnName} = ${r'#{column.propertyName}'}<#if column_has_next>,</#if></#if>
			</#list>
		where id = ${r'#{id}'}
	</update>
	
	<sql id="entity${entityName ? cap_first}_columns>
		<#list columnList as column>${column.columnName}<#if column_has_next>,<#if (column_index + 1) % 5 == 0>
		<#else> </#if></#if></#list>
	</sql>
	
	<!-- 查询 -->
	<select id="queryEntities" resultType="<#if packageName??>${packageName}.</$if>Entity${entityName ? cap_first}">
		select <include refid="entity${entityName ? cap_first}_columns" />
		from ${tableName}
		where ${r'${value}'}
	</select>
	<select id="queryEntities" resultType="int">
		select count(1)
		from ${tableName}
		where ${r'${value}'}
	</select>
</mapper>
