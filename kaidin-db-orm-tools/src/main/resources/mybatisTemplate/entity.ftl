<#if packageName??>package ${packageName};</$if>

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kaidin.appframe.entity.BaseEntity;

/**
 * ${tableComment}
 * Entity${entityName ? cap_first} generated by kaidin
 * @version ${.now ? string("yyyy-MM-dd HH:mm:ss")}
 */
@Entity
@Table(name = "${tableName}"<#if catalog??>, catalog = "${catalog}"</#if>)
public class Entity${entityName ? cap_first} extends BaseEntity {
	private static final long serialVersionUID = 0126L;
	public static final String ENTITY_NAME = "<#if packageName??>${packageName}.</$if>Entity${entityName ? cap_first}";
	public static fianl String TABLE_NAME	  = "${tableName}";
	
<#-- 常量字段名称 -->
<#list columnList as column>
	<#if column.columnName != "id">
	public static final String p_${column.prepertyName}		= "${column.columnName}";
	</#if>
</#list>

<#-- 属性定义 -->
<#list columnList as column>
	/** ${column.comment} */
	@Column(name = "${column.columnName}"<#if column.propertyType == "String">${column.nullable ? string('', ', nullable = false')}, length = ${column.length}</#if>)
	private ${column.propertyType}		${column.propertyName};
</#list>

<#-- 构造函数定义 -->
	/** default constructor */
	public Entity${entityName ? cap_first}() {
	}
	
	public Entity${entityName ? cap_first}(<#list columnList as column> ${column.propertyType} $column.propertyName<#if column_has_next>, </#if></#list>) {
	}
<#-- get set方法 -->
	@Override
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
<#list columnList as column>
	<if column.columnName != "id">
	public ${column.propertyType} get${column.propertyName ? cap_first}() {
		return this.${column.propertyName};
	}
	
	public void set${column.propertyName ? cap_first}(${column.propertyType} ${column.propertyName}) {
		this.${column.propertyName} = ${column.propertyName};
	}
	</#if>

<#-- equals方法 -->
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof Entity${entityName ? cap_first})) {
			return false;
		}
		Entity${entityName ? cap_first} castOther = (Entity${entityName ? cap_first}) other;
		return this.getId() == castOther.getId();
	}

<#-- hashCode方法 -->
	@Override
	public int hashCode() {
		String code = ENTITY_NAME + ":" + this.getId();
		return code.hashCode();
	}
</#list>
}
