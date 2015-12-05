table="${value.collectionTable.quotedName}"
<#if value.collectionTable.catalog?exists && ((clazz.table.catalog?exists && clazz.table.catalog!=value.collectionTable.catalog) || (!clazz.table.catalog?exists && value.collectionTable.catalog?exists)) >
catalog="${value.collectionTable.catalog}"
</#if>
