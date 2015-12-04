<#foreach property1 in pojo.getPropertiesForFullConstructor()>
	<#if !property1.getValue().isSimpleValue()>
	
	/**
	 * getConstraintSet
	 * @return Set<BaseEntity>
	 */
	public Set<BaseEntity> getConstraintSet() {
		Set<BaseEntity> result = new HashSet<BaseEntity>();
		
<#foreach property2 in pojo.getPropertiesForFullConstructor()>
	<#if !property2.getValue().isSimpleValue()>
		result.addAll(this.${property2.getName()});
	</#if>
</#foreach>
		
		return result;
	}
		<#break>
	</#if>
</#foreach>