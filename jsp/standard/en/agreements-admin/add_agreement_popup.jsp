<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		07-Jul-2008		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="orgUnitsEnabled" settingName="orgunit-use"/>

<head>
	<title><bright:cmsWrite identifier="title-agreements" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	<bean:define id="section" value="agreements"/>
	
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the agreeTitle field the focus once the dom is ready
		$j(function () {
  			$j('#agreeTitle').focus();
		});
	</script>	
</head>

<c:choose>
	<c:when test="${agreementsForm.isComplete}">
		
		<script type="text/javascript">
			var parentWin = window.opener;

			<c:choose>
				<c:when test="${agreementsForm.isNew}">
					var bNewAgreement = true;
				</c:when>
				<c:otherwise>
					var bNewAgreement = false;
				</c:otherwise>
			</c:choose>
				
			function sendToParent() 
			{
				var selectbox = parentWin.document.getElementById("agreementSelect");
				for(i=selectbox.options.length-1;i>=0;i--)
				{
					if(selectbox.options[i].selected && !bNewAgreement)
					{
						selectbox.remove(i);
					}
				}
				
				selectbox.appendChild(new Option("<bean:write name="agreementsForm" property="agreement.title"/>","<bean:write name="agreementsForm" property="agreement.id"/>"));
				selectbox.selectedIndex = parentWin.document.getElementById("agreementSelect").length-1;
				close();
			}
		</script>
		
		<body onload="sendToParent(); close();">

	</c:when>
	<c:otherwise>
		
		<body id="conditionsPopup">
		
		<h1 class="underline"><bright:cmsWrite identifier="heading-agreements" filter="false"/></h1> 
		<div>
			<logic:present name="agreementsForm">
				<logic:equal name="agreementsForm" property="hasErrors" value="true">
					<logic:iterate name="agreementsForm" property="errors" id="error">
						<div class="error">
							<bean:write name="error" filter="false"/>
						</div>
					</logic:iterate>
				</logic:equal>
			</logic:present>											
			
			<h2><bright:cmsWrite identifier="subhead-edit-agreement" filter="false"/></h2>
			
				<html:form action="saveAgreement" method="post">	
				
					<html:hidden name="agreementsForm" property="agreement.id"/>
					<input type="hidden" name="usePopup" value="true" />
					
					<table cellspacing="0" cellpadding="0" class="form auto" summary="Form for agreement details">
					<tr>
						<th>
							<label for="agreeTitle"><bright:cmsWrite identifier="label-title" filter="false"/></label>
						</th>
						<td>
							<html:text styleClass="text" styleId="agreeTitle" name="agreementsForm" property="agreement.title" size="30" maxlength="60"/>
						</td>
					</tr>
					<tr>
						<th>
							<bright:cmsWrite identifier="label-agreement" filter="false"/>
						</th>
						<td>
							<textarea style="width:80%;" class="editor" name="agreement.body" cols="90" rows="20"/><bean:write name="agreementsForm" property="agreement.body" filter="false"/></textarea>	
						</td>
					</tr>
					<tr>
						<th>
							<bright:cmsWrite identifier="label-expiry" filter="false"/>
						</th>
						<td>
							<html:text styleClass="small text" name="agreementsForm" property="agreement.expiryString" size="20" styleId="agreementexpiry" /> 
				
							<script type="text/javascript">
								document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePickerSupportUS(document.getElementById(\'agreementexpiry\'),true)" width="16" height="15" style="padding-right: 0;" /><\/a>');
							</script>
						</td>
					</tr>
					<c:if test="${orgUnitsEnabled}">
						<c:if test="${!userprofile.isAdmin}">
							<tr>
								<th>
									<bright:cmsWrite identifier="label-shared-with-org-unit" filter="false"/>
								</th>
								<td>
									<html:checkbox styleClass="checkbox" name="agreementsForm" property="agreement.isSharedWithOU" styleId="availableToAll"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<th>
								<bright:cmsWrite identifier="label-shared-between-org-units" filter="false"/>
							</th>
							<td>
								<html:checkbox styleClass="checkbox" name="agreementsForm" property="agreement.isAvailableToAll" styleId="availableToAll"/>
							</td>
						</tr>
					</c:if>
				</table>
					
				<div class="hr"></div>
				
				<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
				<a href="#" onclick="window.close()" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</div>		
			</html:form>

		
		</div>			
		</body>
		
	</c:otherwise>
</c:choose>

</html>