<#if pojo.needsToString()>
	/**
	 * toString
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Entity:").append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
<#foreach property in pojo.getPropertiesForFullConstructor()>
	<#if property.getValue().isSimpleValue()>
		buffer.append("${property.name}").append("='").append(${pojo.getGetterSignature(property)}()).append("' ");
	<#else>
		buffer.append("${property.name}").append("='").append("${property.getType().getName()}").append("' ");
	</#if>		
</#foreach>
		buffer.append("]");
      
		return buffer.toString();
	}
</#if>