<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="agreementsEnabled" settingName="agreements-enabled"/>

<%-- Create a Map containing the constants defined in AttributeConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | <c:if test="${assetEntityForm.entity.id>0}">Edit</c:if><c:if test="${assetEntityForm.entity.id<=0}">Add</c:if> Asset Type</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="assetEntity"/>
	<bean:define id="pagetitle" value="Asset Type"/>
	<bean:define id="helpsection" value="asset-entity-relationships"/>
	
	<script type="text/javascript">
	//<!--

	function showHide(ctrl,elementId)
	{
		if(ctrl.checked)
		{
			document.getElementById(elementId).style.display='inline';
		}
		else
		{
			document.getElementById(elementId).style.display='none';
		}
	}

	// give the name field the focus once the dom is ready
	function loadPanels ()
	{
		showHide(document.getElementById('allowChildren'),"children"); 
		showHide(document.getElementById('allowPeers'),"peers");
	}

	$j(function() {
		// When DOM ready
		loadPanels();

		$j('#content .advanced').hide();
		
		$j('a.advancedToggle').toggle(
			function() {
				var $thisLink = $j(this);
				$thisLink.text('Hide advanced options');
				$thisLink.prev('table').find('.advanced').show();
			},
			function() {
				var $thisLink = $j(this);
				$thisLink.text('Show advanced options');
				$thisLink.prev('table').find('.advanced').hide();
			
			}
			
		);

		<c:if test='${assetEntityForm.entityIsAdvanced}'>
			$j('a.advancedToggle').click();
		</c:if>	
	});
		
	// -->
	</script>	

</head>
<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<h2>Edit <bean:write name="assetEntityForm" property="entity.name"/> Relationships</h2> 

	<logic:equal name="assetEntityForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="assetEntityForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>

	<p>Select the types of relationships that this asset type can have from the options below. Selecting a relationship type will expand the configurations options for the relationship:</p>
	
	<html:form action="saveAssetEntityRelationships" styleId="assetRelationshipsForm" method="post">
		<html:hidden name="assetEntityForm" property="entity.id"/>
		<logic:notEmpty name="assetEntityForm" property="entity.translations">
			<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.id"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.name"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].name"/>
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
		<div class="hr"></div>
		

			<p><label class="wrapping"><html:checkbox name="assetEntityForm" property="entity.allowPeers" styleClass="checkbox" styleId="allowPeers" onclick="showHide(this,'peers'); showHide(this,'peersOfType');"/> Can have <strong>peers</strong> <span id="peersOfType">of type:</span></label> </p>

		
		<div id="peers">
			
			<bean:define id="relationshipTableType" value="peer"/>
			<%@include file="inc_relationship_table.jsp"%>
			
			<br />
			
			<table cellspacing="0" class="form" style="width:auto">
				<tr>
					<th><label for="peerRelToName">Term for Peer / Peers:</label></th>
					<td><html:text name="assetEntityForm" property="entity.peerRelationshipToName" styleId="peerRelToName" styleClass="text" size="20" maxlength="60"/> / <html:text name="assetEntityForm" property="entity.peerRelationshipToNamePlural" styleId="peerRelToNamePlural" styleClass="text" size="20" maxlength="60"/></td>
				</tr>
				
				<logic:notEmpty name="assetEntityForm" property="entity.translations">
					<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
						<tr>
							<logic:greaterThan name="translation" property="language.id" value="0">
								<td><label for="peerRelToName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label></td>
								<td>
									<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].peerRelationshipToName" size="20" maxlength="60" id="peerRelToName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="peerRelationshipToName" />"/> / <input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].peerRelationshipToNamePlural" size="20" maxlength="60" id="peerRelToNamePlural<bean:write name='tIndex'/>" value="<bean:write name="translation" property="peerRelationshipToNamePlural" />"/>
								</td>
							</logic:greaterThan>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				
			</table>
			
		

		</div>

		<div class="hr"></div>
		
		<p><label class="wrapping"><html:checkbox name="assetEntityForm" property="entity.allowChildren" styleClass="checkbox" styleId="allowChildren" onclick="showHide(this,'children'); showHide(this,'childrenOfType');"/> Can have <strong>children</strong> <span id="childrenOfType">of type:</span></label> </p>
		

		<div id="children">
			<bean:define id="entities" name="assetEntityForm" property="entities"/>
			<bean:define id="relationshipTableType" value="child"/>
			<%@include file="inc_relationship_table.jsp"%>
			<br />
			<table cellspacing="0" class="form" style="width:auto">
				<tr>
					<th><label id="childToTermLabel">Term for Child / Children:</label></th>
					<td>
						<html:text name="assetEntityForm" property="entity.childRelationshipToName" styleId="childRelToName" styleClass="text" size="16" maxlength="60"/> /
						<html:text name="assetEntityForm" property="entity.childRelationshipToNamePlural" styleId="childRelToNamePlural" styleClass="text" size="16" maxlength="60"/>
					</td>
				</tr>		
			
				<logic:notEmpty name="assetEntityForm" property="entity.translations">
					<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<td><label for="childRelToName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label></td>
								<td>
								<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].childRelationshipToName" maxlength="60" id="childRelToName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="childRelationshipToName" />"/> /
								<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].childRelationshipToNamePlural" maxlength="60" id="childRelToNamePlural<bean:write name='tIndex'/>" value="<bean:write name="translation" property="childRelationshipToNamePlural" />"/>
								</td>
							</tr>	
						</logic:greaterThan>
					</logic:iterate>
				</logic:notEmpty>
			</table>
		
		</div>

		<div class="hr"></div>
		
		<div class="children">
		
			<p>If this asset type will be a child of any other asset type then please complete the following fields...</p>

			
			<table cellspacing="0" class="form">
				<tr>
					<th>Must have parent?</th>
					<td><label class="wrapping"><html:checkbox name="assetEntityForm" property="entity.mustHaveParent" styleId="mustHaveParent" styleClass="checkbox"/> This asset type must have a parent (i.e. it cannot be added to the system via a standard upload)</label></td>
				</tr>
				<tr>
					<th><label for="childRelFromName">Term for Parent / Parents:</label></th>
					<td>
						<html:text name="assetEntityForm" property="entity.childRelationshipFromName" styleId="childRelFromName" styleClass="text" size="16" maxlength="60"/> /
						<html:text name="assetEntityForm" property="entity.childRelationshipFromNamePlural" styleId="childRelFromNamePlural" styleClass="text" size="16" maxlength="60"/>
					</td>
				</tr>		
			
				<logic:notEmpty name="assetEntityForm" property="entity.translations">
					<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
								<tr>
									<td><label for="childRelFromName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label></td>
									<td>
										<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].childRelationshipFromName" maxlength="60" id="childRelFromName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="childRelationshipFromName" />"/> /
										<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].childRelationshipFromNamePlural" maxlength="60" id="childRelFromNamePlural<bean:write name='tIndex'/>" value="<bean:write name="translation" property="childRelationshipFromNamePlural" />"/>
									</td>
								</tr>	
							</logic:greaterThan>
					</logic:iterate>
				</logic:notEmpty>
	
				<tr>
					<th><label for="termForSibling">Term for Sibling / Siblings:</label></th>
					<td>
						<html:text name="assetEntityForm" property="entity.termForSibling" styleId="termForSibling" styleClass="text" size="16" maxlength="60"/> /
						<html:text name="assetEntityForm" property="entity.termForSiblings" styleId="termForSiblings" styleClass="text" size="16" maxlength="60"/>
					</td>
				</tr>	
				<logic:notEmpty name="assetEntityForm" property="entity.translations">
					<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<td><label for="termForSiblings<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label></td>
								<td>
									<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].termForSibling" maxlength="60" value="<bean:write name="translation" property="termForSibling" />"/> /
									<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].termForSiblings" maxlength="60" value="<bean:write name="translation" property="termForSiblings" />"/>
								</td>	
							</tr>	
						</logic:greaterThan>
					</logic:iterate>
				</logic:notEmpty>
			</table>	
		</div>

		<div class="hr"> </div>
	
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="viewAssetEntities" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>