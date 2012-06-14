<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		28-Jan-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/abplugin.tld" prefix="abplugin" %>

<bean:parameter name="search" id="search" value="false" />
<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bright:cmsWrite identifier="batch-release" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="batchReleases"/>
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			
			// click event on user search
			$j('#findBtn').click(function(event){
				event.preventDefault();
				var paramStr = $j('#userFields input').serialize();			// convert form fields to parameter string
				

				$j.ajax({
					url: "../action/findUserAjax",
					data: paramStr,
					success: function(data) {
						$j('#userResults').html(data);
					}
				})

			});
			
			$j('#userResults a.addUser').live('click', function(){
				//alert('added');
				$this = $j(this);
				
				if (!$this.hasClass('disabled')) {
					var name = $this.attr('rel');				// get username
					var checkId = $this.prev().attr('value');	// get user id
					//build duplicate checkbox
					var str = '<label class="wrapping"><input type="checkbox" name="selectedUsers" value="'+checkId+'"  class="checkbox" checked="checked" /> '+name+'</label><br/>';

					var $holder = $j('#selectedUsers .holder');
					$j(str).appendTo($holder).effect("highlight", {}, 1500);

				} 
				
				// set flag to say that this user has already been added.
				$this.addClass('disabled');
				return false;								
			});
			
		})
	</script>

</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="batch-release" filter="false" case="mixed" /></h1> 

	<logic:present  name="batchReleaseForm">
		<logic:equal name="batchReleaseForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="batchReleaseForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	
	
	<html:form action="actionOnBatchRelease" method="post" styleClass="floated">
		<html:hidden name="batchReleaseForm" property="id" />
		<input type="hidden" name="search" value="<bean:write name='search' />" />
		
		<%-- Include form start JSPs from extensions --%>
		<abplugin:include-form-extensions extensibleEntity="batchRelease" verb="edit" position="start" replacement="true">
			<label for="name"><bright:cmsWrite identifier="label-name" filter="false" /></label> <html:text name="batchReleaseForm" property="name" /><br />
		</abplugin:include-form-extensions>

		<label for="description"><bright:cmsWrite identifier="label-description" filter="false" />:</label> <html:textarea name="batchReleaseForm" property="description" rows="10" cols="60" /><br />
		
		<label for="notes"><bright:cmsWrite identifier="label-batch-release-notes" filter="false" /></label> <html:textarea name="batchReleaseForm" property="notes" rows="10" cols="60" /><br />
		
		<div id="selectedUsers">
		
			<label><bright:cmsWrite identifier="subhead-approval-users" filter="false" />:</label> 
		
			<div class="holder holderWide">
				<c:if test="${batchReleaseForm.id > 0}">
					<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager"  methodName="getApprovalUsersForBatchRelease" argumentValue="${batchReleaseForm.id}" id="approvalUsers"/>
		
					<logic:notEmpty name='approvalUsers'>
				
				
						<c:choose>
							<c:when test="${batchReleaseForm.currentState.name != 'approved'}">
								<logic:iterate name='approvalUsers' id='approval'>
									<c:set var="identifier">selectedUser<bean:write name='approval' property='user.id' /></c:set>
									<label for="<c:out value='${identifier}' />" class="wrapping"><input type="checkbox" name="selectedUsers" class="checkbox" id="<c:out value='${identifier}' />" value="<bean:write name='approval' property='user.id' />" checked="true" /> <bean:write name='approval' property='user.username' /> (<a href="mailto:<bean:write name='approval' property='user.emailAddress' />"><bean:write name='approval' property='user.emailAddress' /></a>)</label><br />
								</logic:iterate>
							</c:when>
							<c:otherwise>
								<logic:iterate name='approvalUsers' id='approval'>
									<input type="hidden" name="selectedUsers" value="<bean:write name='approval' property='user.id' />" />
									<bean:write name='approval' property='user.username' /> (<a href="mailto:<bean:write name='approval' property='user.emailAddress' />"><bean:write name='approval' property='user.emailAddress' /></a>)<br />
								</logic:iterate>									
							</c:otherwise>
						</c:choose>

					</logic:notEmpty>
				</c:if>
			</div>

		</div>

		<c:if test="${batchReleaseForm.currentState.name != 'approved'}">
		
			<div class="clearing"></div>

			<div id="userFields">
				<c:set var="findUsersForm" value="${batchReleaseForm}" />
				<%@include file="../inc/find-users-form.jsp" %>
				<html:hidden name="findUsersForm" property="searchCriteria.includeAdminWithSelectedGroups" value="true" />
				<input type="hidden" name="ajaxVersion" value="true"/>
			</div>
			
			<input type="submit" name="find" value="Find" class="button flush" id="findBtn" />
			
			<div class="clearing"></div>
			
			<div id="userResults">
				<logic:notEmpty name="batchReleaseForm" property="users">
					<c:set var="reorderingAction" value="" />
					<%@include file="../inc/find-users-results.jsp" %>
				</logic:notEmpty>
			</div>

		</c:if>
		

		<br />
		
		<div class="hr"></div>

		<input type="submit" name="save" value="Save" class="button flush" />
		<a href="<c:choose><c:when test='${search}'>batchReleaseSearch?cachedCriteria=1</c:when><c:otherwise>viewManageBatchReleases</c:otherwise></c:choose>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" case="sentence" /></a>

	</html:form>

	<br /><p></p>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>
