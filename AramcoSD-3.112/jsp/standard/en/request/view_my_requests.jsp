<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-my-requests" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="user-requests"/>
	
	<script type="text/javascript" charset="utf-8">
		$j(function () {
			$j('#addRequestFields').dialog({
				autoOpen: false,
				height: 380,
				width: 400,
				modal: true,
				buttons: {
					"Submit": function() {
						$j(this).parent().prependTo($j('#bottomOfForm'));	//move fields back into main form so fields get included.
						$j('#addRequestForm').submit();						//submit form
					},
					Cancel: function() {
						$j(this).dialog( "close" );
					}
				}
			});	
			
			$j('#addRequestLink').click(function() {
				$j("#addRequestFields").dialog("open");
			})
			
		});

	</script>
	
</head>

<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-requests" /></h1> 
	
	<bean:define id="tabId" value="my-requests" />
	<%@include file="inc_request_tabs.jsp"%>
	 
	
	<logic:present name="requestForm">
		<logic:equal name="requestForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="requestForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<div class="toolbar">
		<h3>
			<logic:empty name="requests">
				<bright:cmsWrite identifier="snippet-no-requests" filter="false" />
			</logic:empty>

			<logic:notEmpty name="requests">
				<bean:size id="noOfRequests" name="requests"/>
				<bean:define id="showStatus" value="true" />
				<bright:cmsWrite identifier="snippet-no-of-requests" filter="false" replaceVariables="true" />
			</logic:notEmpty>
			
		</h3>
		<div class="group">
			<a href="#newRequestForm" id="addRequestLink" class="addLink"><bright:cmsWrite identifier="link-make-new-request" /></a>
		</div>	
		<div class="clearing"></div>	
		
	</div>

	
	<logic:notEmpty name="requests">
		<%@include file="inc_request_list.jsp"%>
	</logic:notEmpty>
	
	
	
	<html:form action="saveRequest" method="post" styleClass="form" styleId="addRequestForm">
		<div id="addRequestFields" title="<bright:cmsWrite identifier="link-make-new-request" />">
			<p><bright:cmsWrite identifier="snippet-describe-request" filter="false" />:</p>
			<textarea rows="10" cols="50" name="description"></textarea>
			<div class="info"><span class="required">*</span><bright:cmsWrite identifier="snippet-request-instructions" filter="false" /></div>
		</div>
		<input class="button flush js-enabled-hide" type="submit" value="<bright:cmsWrite identifier="button-submit" filter="false" />" />
		<div id="bottomOfForm"></div>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>