   /**
     * clone
     * @return ${pojo.getDeclarationName()}
     */
     public ${pojo.getDeclarationName()}  clone(final ${entity_name} parent) throws IllegalAccessException, java.lang.reflect.InvocationTargetException {
 		 org.apache.commons.beanutils.BeanUtils.copyProperties(this, parent);
      	return this;
     } 

 