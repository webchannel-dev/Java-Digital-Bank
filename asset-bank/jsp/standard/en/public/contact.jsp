<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Matt Stevenson	11-Jan-2006		Added field validation
    d4		Ben Browning	09-Feb-2006		Tidied up html
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="title-contact" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="contact"/>

</head>

<body id="contact">

	<%@include file="../inc/body_start.jsp"%>
   
   <h1 class="underline"><bright:cmsWrite identifier="heading-contact" filter="false" /></h1> 
      
	<!-- The welcome text: this comes from the database (change in the 'Content' area of Admin) -->
	<bright:cmsWrite identifier="contact-details" filter="false" />
   
	<logic:present name="sendEmailForm">
		<logic:equal name="sendEmailForm" property="hasErrors" value="true">
			<div class="error">
		   	<logic:iterate name="sendEmailForm" property="errors" id="error">
		   		<bean:write name="error"/>
		   	</logic:iterate>
			</div>
		 </logic:equal>
   </logic:present>
	
   <form action="../action/sendContactEmail" method="post" name="contactForm" class="floated">
    
   	<bright:refDataList componentName="UserManager" methodName="getAdminEmailAddresses" id="adminEmailAddresses"/>
   	<input type="hidden" name="template" value="contact" />

		<logic:present name="adminEmailAddresses">
	   		<input type="hidden" name="adminEmailAddresses" value="<bean:write name='adminEmailAddresses'/>" />
		</logic:present>
	
   	<input type="hidden" name="mandatory_name" value="You need to provide your name" />
   	<input type="hidden" name="mandatory_email" value="You need to provide an email address" />
   	
		<label for="name"><bright:cmsWrite identifier="label-name" filter="false"/><span class="required">*</span></label>
		<c:choose>
			<c:when test="${not empty sendEmailForm.errors || !userprofile.isLoggedIn}">
				<input type="text" class="text" name="name" id="name" size="20" maxlength="100" value="<bean:write name='sendEmailForm' property='selectedValue(name)'/>" /><br />
			</c:when>
			<c:otherwise>
				<input type="text" class="text" name="name" id="name" size="20" maxlength="100" value="<bean:write name='userprofile' property='user.fullName'/>" /><br />
			</c:otherwise>
		</c:choose>

		<label for="establishment"><bright:cmsWrite identifier="label-organisation" filter="false"/></label>
		<c:choose>
			<c:when test="${not empty sendEmailForm.errors || !userprofile.isLoggedIn}">
				<input type="text" class="text" name="establishment" id="establishment" size="20" maxlength="100" value="<bean:write name='sendEmailForm' property='selectedValue(establishment)'/>" /><br />
			</c:when>
			<c:otherwise>
				<input type="text" class="text" name="establishment" id="establishment" size="20" maxlength="100" value="<bean:write name='userprofile' property='user.organisation'/>" /><br />
			</c:otherwise>
		</c:choose>

		<label for="email"><bright:cmsWrite identifier="label-email" filter="false"/><span class="required">*</span></label>
		<c:choose>
			<c:when test="${not empty sendEmailForm.errors || !userprofile.isLoggedIn}">
				<input type="text" class="text" name="email" id="email" size="20" maxlength="100" value="<bean:write name='sendEmailForm' property='selectedValue(email)'/>" /><br />
			</c:when>
			<c:otherwise>
				<input type="text" class="text" name="email" id="email" size="20" maxlength="100" value="<bean:write name='userprofile' property='user.emailAddress'/>" /><br />
			</c:otherwise>
		</c:choose>

		<label for="telephone"><bright:cmsWrite identifier="label-tel" filter="false"/></label>
		<c:choose>
			<c:when test="${not empty sendEmailForm.errors || !userprofile.isLoggedIn}">
				<input type="text" class="text" name="telephone" id="telephone" size="20" maxlength="100" value="<bean:write name='sendEmailForm' property='selectedValue(telephone)'/>" /><br />
			</c:when>
			<c:otherwise>
				<input type="text" class="text" name="telephone" id="telephone" size="20" maxlength="100" value="" /><br />
			</c:otherwise>
		</c:choose>

		<label for="message"><bright:cmsWrite identifier="label-message" filter="false"/></label>
		<textarea name="message" id="message" rows="8" cols="50"><bean:write name='sendEmailForm' property='selectedValue(message)'/></textarea><br />

		<span class="empty">&nbsp;</span><input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-send" filter="false" />" /><br />

   	
   </form>
   
   <script type="text/javascript" language="JavaScript">
     <!--
     var focusControl = document.forms["contactForm"].elements["name"];
   
     if (focusControl.type != "hidden") {
        focusControl.focus();
     }
     // -->
   </script>							

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>