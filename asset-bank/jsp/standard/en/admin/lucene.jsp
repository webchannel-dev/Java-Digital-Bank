<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<html>
	<head>
		<title>Debug Page: Lucene Fields</title> 
	</head>

	<body>
		<bean:parameter name="id" id="id" value="0" />
		<h1>Document <bean:write name='id' /> Lucene Fields:</h1>
		
		<bean:write name="fields" filter="false" />
	
	</body>
</html>