
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (!(other instanceof ${pojo.getDeclarationName()})) return false;
		${pojo.getDeclarationName()} castOther = (${pojo.getDeclarationName()}) other;
		return this.getId() == castOther.getId();
	}
	
	public int hashCode() {
		String code = ENTITY_NAME + ":" + this.getId();
		return code.hashCode();
	}
