<%-- History:

	 d1		Matt Woollard		03-Apr-2008		Created.
	 d2		James Home			13-Jan-2010		Updated to be served as text/xml
	 -------------------------------------------------
	 Note, content is served as text/xml, so includes no leading whitespace

--%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %><%--
--%><%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %><%--
--%><%@ page contentType="text/xml; charset=UTF-8" %><%--
--%><logic:iterate name="assetAuditForm" property="assetAuditLog" id="entry"><bean:write name="entry" property="log" filter="false"/></logic:iterate>	