<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		19-Jan-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<c:set var="pagetitle" value="System" />		



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="tabId" value="update"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<p><bright:cmsWrite identifier="app-name" filter="false"/> does not have the required permissions to perform the update. Please correct the issues below and restart the update.</p>
	
	<p>
		<ul>		
			<c:if test="${applicationUpdateForm.updatePermissionDetails.insufficientDatabasePermissions}">
				<li>
					The <bright:cmsWrite identifier="app-name" filter="false"/> database user does not have the required privileges.  The error given was
					'<c:out value="${applicationUpdateForm.updatePermissionDetails.databasePermissionError}" />'.
					<br/>
					<br/>  					
					<em>Please ensure the database user has full privileges on the <bright:cmsWrite identifier="app-name" filter="false"/> database.</em>					
				</li>
			</c:if>
			<br/>
			<c:if test="${applicationUpdateForm.updatePermissionDetails.insufficientFileSystemPermissions}">
				<li>
					The following files/directories could not be read from/written to.
					<ul>
						<c:forEach var="filename" items="${applicationUpdateForm.updatePermissionDetails.insufficientPermissionFiles}">
							<li>
								<c:out value="${filename}"/>
							</li>
						</c:forEach>
					</ul> 
					<br/>
					<em>Please ensure the tomcat user has read/write permission on the <bright:cmsWrite identifier="app-name" filter="false"/> application directory.</em>
				</li>
			</c:if>
		</ul>
	</p>
	<br/>
					
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>