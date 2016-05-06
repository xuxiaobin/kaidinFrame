package com.kaidin.db.entity;
// Generated 2015-12-7 17:00:26 by Hibernate Tools 3.3.0.GA
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kaidin.appframe.entity.BaseEntity;
/**
 * EntityCfgMenu generated by hbm2java
 */
@Entity
@Table(name="cfg_menu"
	,catalog="kaidin"
)
public class EntityCfgMenu extends BaseEntity
 {
	private static final long serialVersionUID = 0126L;
	public static final String ENTITY_NAME = "com.kaidin.db.entity.EntityCfgMenu";
	public static final String P_Id	= "id";
	public static final String P_Href	= "href";
	public static final String P_Name	= "name";
	public static final String P_Alias	= "alias";
	public static final String P_CssClass	= "cssClass";
	public static final String P_CssStyle	= "cssStyle";
	public static final String P_OtherProperties	= "otherProperties";
	public static final String P_Level	= "level";
	public static final String P_ParentId	= "parentId";
	public static final String P_Code	= "code";
	public static final String P_Sort	= "sort";
	public static final String P_Status	= "status";

	private long id;
	// 菜单的连接地址
	private String href;
	// 菜单名称
	private String name;
	// 菜单显示名称
	private String alias;
	// 菜单的css样式类
	private String cssClass;
	// 另外添加的css样式
	private String cssStyle;
	// 其他属性
	private String otherProperties;
	// 菜单等级
	private Short level;
	// 父级菜单id
	private long parentId;
	// 菜单编码
	private String code;
	// 控制菜单从小到大显示顺序，
	private Short sort;
	// 菜单状态
	private short status;

	// default constructor
	public EntityCfgMenu() {
	}
	public EntityCfgMenu(String href, String name, long parentId, short status) {
		this.href = href;
		this.name = name;
		this.parentId = parentId;
		this.status = status;
	}
	public EntityCfgMenu(String href, String name, String alias, String cssClass, String cssStyle, String otherProperties, Short level, long parentId, String code, Short sort, short status) {
		this.href = href;
		this.name = name;
		this.alias = alias;
		this.cssClass = cssClass;
		this.cssStyle = cssStyle;
		this.otherProperties = otherProperties;
		this.level = level;
		this.parentId = parentId;
		this.code = code;
		this.sort = sort;
		this.status = status;
	}

    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
    
    @Column(name="href", nullable=false, length=128)
	public String getHref() {
		return this.href;
	}
	public void setHref(String href) {
		this.href = href;
	}
    
    @Column(name="name", nullable=false, length=128)
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    @Column(name="alias", length=128)
	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
    
    @Column(name="css_class", length=128)
	public String getCssClass() {
		return this.cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
    
    @Column(name="css_style", length=128)
	public String getCssStyle() {
		return this.cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
    
    @Column(name="other_properties", length=128)
	public String getOtherProperties() {
		return this.otherProperties;
	}
	public void setOtherProperties(String otherProperties) {
		this.otherProperties = otherProperties;
	}
    
    @Column(name="level")
	public Short getLevel() {
		return this.level;
	}
	public void setLevel(Short level) {
		this.level = level;
	}
    
    @Column(name="parent_id", nullable=false)
	public long getParentId() {
		return this.parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
    
    @Column(name="code", length=16)
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
    
    @Column(name="sort")
	public Short getSort() {
		return this.sort;
	}
	public void setSort(Short sort) {
		this.sort = sort;
	}
    
    @Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}
	public void setStatus(short status) {
		this.status = status;
	}


	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (!(other instanceof EntityCfgMenu)) return false;
		EntityCfgMenu castOther = (EntityCfgMenu) other;
		return this.getId() == castOther.getId();
	}
	
	public int hashCode() {
		String code = ENTITY_NAME + ":" + this.getId();
		return code.hashCode();
	}
}