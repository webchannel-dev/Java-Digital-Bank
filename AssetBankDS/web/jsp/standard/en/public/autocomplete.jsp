<%@include file="../inc/doctype_html.jsp" %>
<%-- History:
	d1	Francis Devereux	20-Aug-2009		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<body>
<ul>
	<c:forEach var="keyword" items="${keywords}">
      <%-- escapeXml="false" in the line below does not introduce an XSS vulnerability because the keywords are passed through ResponseUtils.filter() in AutoCompleteAction --%>
		<li><c:out value="${keyword}" escapeXml="false"/></li>
	</c:forEach> 
</ul>
</body>
</html>
