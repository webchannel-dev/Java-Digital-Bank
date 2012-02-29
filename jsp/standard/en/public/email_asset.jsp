<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1	Adam Bones		25-Oct-2006		Created.
	d2  Matt Woollard   02-May-2008     Added setting to attach assets only
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<bean:define id="section" value="email"/>

<bright:applicationSetting id="emailAsAttachmentOnly" settingName="email-as-attachment-only"/>

<head>
	
	<title><bright:cmsWrite identifier="title-email-item" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/JavaScript">
		// give the recipients field the focus once the dom is ready
		$j(function () {
  			$j('#recipients').focus();
 		});
	</script>		

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-email-item" filter="false" /></h1> 
	
	<c:choose>
		<c:when test="${emailForm.isLightbox}">
			<div class="head">
		    	<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a>
		    </div>
		</c:when>
		<c:otherwise>
			<div class="head">
				<bean:parameter name="returnUrl" id="returnUrl" value=""/>
				<%-- Note, we don't just want to see if the parameter is present, but if it actually has a value --%>
				<c:if test="${not empty returnUrl}">
					<a href="<bean:write name="returnUrl"/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
				</c:if>
				<c:if test="${empty returnUrl}">
		    		<a href="../action/viewAsset?id=<bean:write name='emailForm' property='asset.id'/>&returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
		    	</c:if>
		    </div>
		</c:otherwise>
	</c:choose>
	
	<logic:equal name="emailForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="emailForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<bright:cmsWrite identifier="snippet-email-asset-intro" filter="false" />
	
	<c:choose>
		<c:when test="${!emailForm.assetIsAttachable && emailAsAttachmentOnly}">
			<bright:cmsWrite identifier="snippet-file-too-big" filter="false"/>
		</c:when>
		<c:otherwise>
			<html:form enctype="multipart/form-data" action="emailAsset" method="post">
				<table class="form" cellspacing="0" cellpadding="0">
					<tr>
						<th><label for="recipient"><bright:cmsWrite identifier="label-to" filter="false" /></label></th>
						<td>
							<html:text name="emailForm" property="recipients" styleId="recipients" />
							<c:set var="separator" value=";"/>
							<legend><bright:cmsWrite identifier="snippet-email-addresses-comment" replaceVariables="true" filter="false"/></legend>
						</td>
					</tr>
					<tr>
						<th><label for="message"><bright:cmsWrite identifier="label-message" filter="false" /></label></th>
						<td><html:textarea name="emailForm" property="message"/></td>
					</tr>
					<tr>
						<th><label for="filename"><bright:cmsWrite identifier="label-filename" filter="false" /></label></th>
						<td class="padded"><bean:write name="emailForm" property="filename"/></td>
						<html:hidden name="emailForm" property="fileId"/>
						<html:hidden name="emailForm" property="asset.id"/>
						<html:hidden name="emailForm" property="assetIsAttachable"/>
					</tr>
					<c:choose>
						<c:when test="${emailForm.assetIsAttachable}">
							<tr>
								<th><label for="method"><bright:cmsWrite identifier="label-method" filter="false" /></label></th>
								<td>
									<html:radio styleClass="radio" name="emailForm" property="assetLinkedNotAttached" value="false" styleId="emailattach" /> <label for="emailattach"><bright:cmsWrite identifier="snippet-add-as-attachment" filter="false" /></label><br/>
									<c:if test="${!emailAsAttachmentOnly}">
										<html:radio styleClass="radio" name="emailForm" property="assetLinkedNotAttached" value="true" styleId="emaillink" /> <label for="emaillink"><bright:cmsWrite identifier="snippet-link-to-item" filter="false" /></label>
									</c:if>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>&nbsp;</td>
								<td><bright:cmsWrite identifier="email-linked-text" filter="false"/></td>	
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th>&nbsp;</th>
						<td><input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-send" filter="false" />" /></td>
					</tr>
				</table>
			</html:form>
		</c:otherwise>
	</c:choose>
								
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>