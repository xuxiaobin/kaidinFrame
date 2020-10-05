<#foreach property1 in pojo.getPropertiesForFullConstructor()>
	<#if !property1.getValue().isSimpleValue()>
	
	/**
	 * getConstraintSet
	 * @return Set<BaseEntity>
	 */
    /*
	@${pojo.importType("javax.persistence.Transient")}
	public Set<BaseEntity> getConstraintSet() {
		Set<BaseEntity> result = new HashSet<>();
		
<#foreach property2 in pojo.getPropertiesForFullConstructor()>
	<#if !property2.getValue().isSimpleValue()>
		if (null != ${property2.getName()}) {
			result.addAll(${property2.getName()});
		}
	</#if>
</#foreach>
		
		return result;
	}
	*/
		<#break>
	</#if>
</#foreach>