<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


	<%-- Forward to the correct place depending on breadcrumb. Use relative URLs --%>

	<%-- default to home --%>
	<c:set var="redirectURL" scope="page" value="../action/viewHome" />
	
	<%-- Browser breadcrumbs - get the url for the last link --%>
	<logic:notEmpty name="breadcrumbTrail">
		<logic:iterate name="breadcrumbTrail" id="entry">
			<c:set var="redirectURL" scope="page" value="${entry.value}" />
		</logic:iterate>  		 				
	</logic:notEmpty>
		
	<%-- Check for return url - add leading .. --%>
	<logic:empty name="breadcrumbTrail">
		<logic:present name="imageDetailReturnUrl">	
			<c:set var="redirectURL" scope="page" value="..${imageDetailReturnUrl}" />
		</logic:present>
	</logic:empty>

	<%-- Scriptlet does a browser redirect to remove browser refresh problems --%>
	<% 
		String sUrl = (String) pageContext.getAttribute("redirectURL");
		
		
		//out.println(sUrl); 
		response.sendRedirect(sUrl); 
	%>

