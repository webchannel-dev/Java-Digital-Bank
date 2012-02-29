<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Incorporated categories
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	19-May-2005		Added javascript for selecting parent categories
	 d5		Matt Stevenson	14-Jun-2005		Modified parent category permission setting
	 d6     Steve Bryan		29-Dec-2005    Updated for org units
	 d7		Ben Browning	21-Feb-2006		HTML/CSS tidy up
	 d8		James Home		29-Jan-2007		Permissions moved to separate page
	 d9		Matt Stevenson	16-Apr-2007		Added DiscountPercentage
	 d10	Matt Woollard	24-Aug-2009		Added option to restrict view larger size
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="notUsingActiveDirectory" settingName="suspend-ad-authentication"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
<bright:applicationSetting id="automaticBrandRegistration" settingName="automatic-brand-registration"/>
<bright:applicationSetting id="useIPMapping" settingName="users-have-IP"/>
<bright:applicationSetting id="useURLMapping" settingName="users-have-URL"/>
<bright:applicationSetting id="canEmailAssets" settingName="images-are-emailable"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
<bright:applicationSetting id="dlFromFileSystemEnabled" settingName="download-from-filesystem"/>
<bright:applicationSetting id="userInvitations" settingName="user-invitations-enabled"/>
<bright:applicationSetting id="allowEmptyAssets" settingName="allow-empty-assets"/>
<bright:applicationSetting id="advancedViewingEnabled" settingName="advanced-viewing-enabled"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Bright Interactive</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/groups.js"></script>
	<bean:define id="section" value="groups"/>
	
	<logic:equal name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Add Group"/>
	</logic:equal>
	<logic:notEqual name="groupForm" property="group.id" value="0">	
		<bean:define id="pagetitle" value="Edit Group"/>
	</logic:notEqual>
	<script type="text/JavaScript">
		// give the username field the focus once the dom is ready
		$j(function () {
  			$j('#name').focus();
		});
	</script>	

</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="groupForm">
		<logic:equal name="groupForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="groupForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<html:form styleId="group_edit" action="saveGroup" method="post">
		<html:hidden name="groupForm" property="group.isDefaultGroup"/>
		<html:hidden name="groupForm" property="group.id"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
		<c:if test="${!ecommerce || groupForm.group.isDefaultGroup}">
			<input type="hidden" name="discountString" value="0"/>
		</c:if>
		<table cellspacing="0" class="form" summary="Form for group details">
			<tr>
				<th>
					<label for="name">Name: <span class="required">*</span></label>
				</th>
				<td colspan="2">
					<html:text styleClass="text" name="groupForm" property="group.name" styleId="name" size="30" maxlength="60"/>
				</td>													
			</tr>
			<tr>
				<th>
					<label for="description">Description:</label>
				</th>
				<td colspan="2">
					<html:text styleClass="text" name="groupForm" property="group.description" styleId="description" size="30" maxlength="255"/>
				</td>													
			</tr>	
			<tr>
				<th>
					<label for="maxDownloads">Maximum daily downloads:</label>
				</th>
				<td colspan="2">
					<html:text styleClass="text" name="groupForm" property="group.maxDownloads" styleId="maxDownloads" size="5" maxlength="255"/>
					<span class="formHelp">Leave blank or set to 0 for unlimited daily downloads</span>
				</td>													
			</tr>
			<tr>
				<th>
					<label for="maxHeight">Max download height (pixels):</label>
				</th>
				<td colspan="2">
					<input type="text" class="text" name="group.maxDownloadHeight" size="5" id="maxHeight" maxlength="255" value="<c:if test='${groupForm.group.maxDownloadHeight > 0}'><bean:write name='groupForm' property='group.maxDownloadHeight'/></c:if>"/>
					<span class="formHelp">Leave blank for an unlimited download height</span>
				</td>													
			</tr>
			<tr>
				<th>
					<label for="maxWidth">Max download width (pixels):</label>
				</th>
				<td colspan="2">
					<input type="text" class="text" name="group.maxDownloadWidth" id="maxWidth" size="5" maxlength="255" value="<c:if test='${groupForm.group.maxDownloadWidth > 0}'><bean:write name='groupForm' property='group.maxDownloadWidth'/></c:if>"/>
					<span class="formHelp">Leave blank for an unlimited download width</span>
				</td>													
			</tr>
			<c:if test="${canEmailAssets}">	
				<tr>
					<th>
						<label for="userCanEmailAssets">Users can email assets:</label>
					</th>
					<td colspan="2">
						<html:checkbox styleClass="checkbox" styleId="userCanEmailAssets" name="groupForm" property="group.userCanEmailAssets" />
					</td>													
				</tr>
			</c:if>
			<c:if test="${assetRepurposingEnabled}">	
				<tr>
					<th>
						<label for="userCanRepurposeAssets">Users can embed assets:</label>
					</th>
					<td colspan="2">
						<html:checkbox styleClass="checkbox" styleId="userCanRepurposeAssets" name="groupForm" property="group.userCanRepurposeAssets" />
					</td>													
				</tr>
			</c:if>
			
			<tr>
				<th>
					<label for="usersCanViewLargerSize">Users can view larger size:</label>
				</th>
				<td colspan="2">
					<html:checkbox styleClass="checkbox" styleId="usersCanViewLargerSize" name="groupForm" property="group.usersCanViewLargerSize" />
				</td>													
			</tr>

			<c:if test="${!notUsingActiveDirectory}">
				<tr>
					<th>
						<label for="remoteGroups">Remote Group(s):</label>
					</th>
					<td colspan="2">
						<html:textarea styleClass="textarea" styleId="remoteGroups" name="groupForm" property="group.mapping" rows="4" cols="40"/>
					</td>													
				</tr>
			</c:if>
			<c:if test="${!groupForm.group.isDefaultGroup}">
				<tr>
					<th>
						<label for="userCanOnlyEditOwnFiles">Editors can only edit their own files:</label>
					</th>
					<td colspan="2">
						<html:checkbox styleClass="checkbox" name="groupForm" styleId="userCanOnlyEditOwnFiles" property="group.userCanOnlyEditOwnFiles" />
						<em>Note that if a user is in more than one group then they can edit other users' files if any group has this unchecked.</em>
					</td>													
				</tr>
				
				<c:if test="${allowEmptyAssets}">	
					<tr>
						<th>
							<label for="userCanAddEmptyAssets">Users can add empty assets:</label>
						</th>
						<td colspan="2">
							<html:checkbox styleClass="checkbox" styleId="userCanAddEmptyAssets" name="groupForm" property="group.usersCanAddEmptyAssets" />
							<em>This applies if users in the group can upload assets.</em>
						</td>													
					</tr>
				</c:if>
				
			</c:if>
			<c:if test="${dlFromFileSystemEnabled}">
				<tr>
					<th>
						<label for="usersCanSeeSourcePath">Users can see source path:</label> 
					</th>
					<td colspan="2">
						<html:checkbox styleClass="checkbox" name="groupForm" styleId="usersCanSeeSourcePath" property="group.usersCanSeeSourcePath" />
						<em>Users can see the path to the image on the server</em>
					</td>													
				</tr>
			</c:if>
			<c:if test="${useIPMapping}">
				<tr>
					<th>
						<label for="ipMapping">IP Mapping(s):</label>
					</th>
					<td>
						<html:textarea styleClass="textarea" styleId="ipMapping" name="groupForm" property="group.ipMapping" rows="4" cols="40"/>
					</td>				
					<td style="padding-left:10px">
						One or more IP address ranges (one per line) either as a single address <br/>e.g. <em>192.168.1.1</em><br/> or in the form <em>x1.x2.x3.x4-y1.y2.y3.y4</em><br/>e.g. <em>192.168.1.1-192.168.1.10</em>
					</td>									
				</tr>
			</c:if>
			<c:if test="${useURLMapping}">
				<tr>
					<th>
						<label for="urlMapping">URL Mapping(s):</label>
					</th>
					<td>
						<html:textarea styleClass="textarea" name="groupForm" styleId="urlMapping" property="group.urlMapping" rows="4" cols="40"/>
					</td>									
					<td style="padding-left:10px">
						One or more values for group-identifier (one per line), so that when a user logs in using the URL [image library domain]/group-identifier, then they will be assigned the rights for the group. (Please note that Apache configuration is required for this to work, otherwise the URL [image library domain]?group=group-identifier can be used).
					</td>													
				</tr>
			</c:if>
			<c:if test="${ecommerce && !groupForm.group.isDefaultGroup}">
				<tr>
					<th>
						<label for="discountString">Discount (%):</label>
					</th>
					<td>
						<c:choose>
							<c:when test="${groupForm.hasErrors}">
								<html:text styleClass="text" name="groupForm" styleId="discountString" property="discountString" size="30" maxlength="255"/>
							</c:when>
							<c:otherwise>
								<input type="text" class="text" id="discountString" name="discountString" value="<bean:write name='groupForm' property='group.discountPercentage'/>" size="30" maxlength="255"/>
							</c:otherwise>
						</c:choose>
					</td>									
					<td style="padding-left:10px">
						Discount to be applied to purchases made by members of this group. Discount is a whole number percentage.	
					</td>													
				</tr>
			</c:if>

			<%-- Don't show org unit for public or logged-in users groups, or if no org units --%>
			<bean:define name="groupForm" property="orgUnitList" id="orgUnits" />
			<bean:size id="numOrgUnits" name="orgUnits" />
			<c:if test="${numOrgUnits > 0 && groupForm.group.id != 1 && groupForm.group.id != 2}">		
				<tr>
					<th>
						<label for="orgUnitReference">Organisational Unit:</label>
					</th>
					
					<%-- For existing groups already attached to an org unit, cannot change the org unit --%>
					<c:choose>
							
						<c:when test="${groupForm.group.orgUnitReference.id > 0}">
							<td class="padded">
								<bean:write name="groupForm" property="group.orgUnitReference.name" />
								<html:hidden name="groupForm" property="group.orgUnitReference.id" />
						
								<c:if test="${groupForm.group.orgUnitAdminGroup}"> <em>(This is the org unit admin group)</em></c:if>
							</td>
						</c:when>
						<c:otherwise>
	
							<c:choose>
								<%-- Admin can attach to none or any org unit --%>
								<c:when test="${userprofile.isAdmin}">
									<td>
										<html:select name="groupForm" property="group.orgUnitReference.id" styleId="orgUnitReference">
											<option value="0">[any]</option>
											<html:options collection="orgUnits" property="id" labelProperty="category.name" />
										</html:select>	
									</td>
								</c:when>
								
								<%-- Org unit admin can attach new group to any one of their org units - if one then give no choice --%>
								<c:otherwise>
									<c:choose>
										<c:when test="${numOrgUnits > 1}">
											<td>
												<html:select name="groupForm" property="group.orgUnitReference.id" styleId="orgUnitReference">
													<html:options collection="orgUnits" property="id" labelProperty="category.name" />
												</html:select>
											</td>
										</c:when>
										<c:otherwise>
											<td class="padded">
												<logic:iterate name="orgUnits" id="ou">
													<bean:write name="ou" property="category.name" />							
													<input type="hidden" name="group.orgUnitReference.id" value="<c:out value='${ou.id}' />" />
												</logic:iterate>
											</td>	
										</c:otherwise>													
									</c:choose>
								</c:otherwise>
							</c:choose>
								
							</c:otherwise>
						</c:choose>
						
					</td>													
				</tr>	
				<tr>
					<th></th>
					<td>
						<em>Please note that once a group has been allocated to an organisational unit, it cannot be moved to a different one.</em>
					</td>
				</tr>
			</c:if>		

			<%-- Select brand if enabled --%>														
			<c:if test="${useBrands}">
				<tr>
					<th>
						<label for="brandReference">Brand:</label>
					</th>
					<td>
						<bean:define name="groupForm" property="brandList" id="brand"/>
						<html:select name="groupForm" styleId="brandReference" property="group.brandReference.id">
							<option value="0">[none]</option>
							<html:options collection="brand" property="id" labelProperty="name"/>
						</html:select>							
					</td>													
				</tr>	
				<c:if test="${automaticBrandRegistration}">
					<tr>
						<th>
							<label for="brandReference">Automatic brand registration:</label>
						</th>
						<td>
							<html:checkbox name="groupForm" property="group.automaticBrandRegister" value="true" styleClass="checkbox" />
						</td>													
					</tr>
				</c:if>
			</c:if>		
			
			
			<%-- User invitations --%>														
			<c:if test="${userInvitations}">
				<tr>
					<th>
						<label for="usersCanInviteUsers">Allow invitations:</label>
					</th>
					<td>
						<html:checkbox styleClass="checkbox" styleId="usersCanInviteUsers" name="groupForm" property="group.usersCanInviteUsers" />						
					</td>													
				</tr>				
			</c:if>		
			
			<%-- advanced viewing --%>														
			<c:if test="${advancedViewingEnabled}">
				<tr>
					<th>
						<label for="advancedViewing">Advanced viewing of unnapproved assets:</label>
					</th>
					<td>
						<html:checkbox styleClass="checkbox" styleId="advancedViewing" name="groupForm" property="group.advancedViewing" />						
					</td>													
				</tr>				
			</c:if>	
			
		</table>	
		
		<div class="hr"></div>
		
		<input type="submit" class="button floated flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>	
		
	</html:form>
		
		

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>