<#--  /** default constructor */ -->
	// default constructor
	public ${pojo.getDeclarationName()}() {
	}
<#if pojo.needsMinimalConstructor() && pojo.getPropertyClosureForSuperclassMinimalConstructor().size() < 20>
	<#-- /** minimal constructor */ -->
	public ${pojo.getDeclarationName()}(${c2j.asParameterList(pojo.getPropertyClosureForMinimalConstructor(), true, pojo)}) {
	<#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassMinimalConstructor().isEmpty()>
		super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassMinimalConstructor())});
	</#if>
	<#foreach field in pojo.getPropertiesForMinimalConstructor()>
		this.${field.name} = ${field.name};
	</#foreach>
	}
</#if>   
<#if pojo.needsFullConstructor() && pojo.getPropertyClosureForFullConstructor().size() <= 100>
	<#-- /** full constructor */ -->
	public ${pojo.getDeclarationName()}(${c2j.asParameterList(pojo.getPropertyClosureForFullConstructor(), true, pojo)}) {
	<#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassFullConstructor().isEmpty()>
		super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassFullConstructor())});        
	</#if>
	<#foreach field in pojo.getPropertiesForFullConstructor()> 
		this.${field.name} = ${field.name};
	</#foreach>
	}
</#if>    
