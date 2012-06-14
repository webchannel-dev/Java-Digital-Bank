<bright:applicationSetting id="enableSlideshowRepurposing" settingName="slideshow-repurposing-enabled"/>
<logic:notPresent name="slideshow">
	<bean:define id="slideshow" value="false"/>
</logic:notPresent>
<logic:notPresent name="close">
	<bean:define id="close" value="false"/>
</logic:notPresent>
<bean:parameter id="returnUrl" name="returnUrl" value="viewHome"/>
<div class="leftShadow">
	<div class="rightShadow">
	   <div id="comparePanel">
		   <div class="header">
				<c:choose>
					<c:when test="${slideshow}">
						<a href="..<c:out value='${returnUrl}'/>" class="close js-enabled-show">Close slideshow</a>
						<a class="help-popup" href="../action/viewHelp?helpsection=<bean:write name='helpsection'/>" target="_blank" title="View help text in new window"><bright:cmsWrite identifier="menu-help" filter="false"/></a>
						
						<c:if test="${enableSlideshowRepurposing && userprofile.isAdmin}">
							<a href="viewRepurposeSlideshow?type=<bean:write name='type'/>&page=<bean:write name='pageNumber'/>&pageSize=<bean:write name='pageSize'/>&captionIds=<bean:write name='captionIds'/>&displayTime=<bean:write name='controlPanelForm' property='displayTime'/>&labels=<bean:write name='controlPanelForm' property='showAttributeLabels'/>" class="js-enabled-show embed">Create embeddable version</a>
						</c:if>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="window.print();" class="print js-enabled-show">Print</a>
					</c:otherwise>
				</c:choose>
				<c:if test="${slideshow}"></c:if>
				<a href="#" class="collapse_white" onclick="toggleControls(); return false;" id="controlsToggle"><bright:cmsWrite identifier="subhead-options" filter="false"/></a>
		   </div>
		   <div class="inner">	

			<form action="<bean:write name='action'/>" >
				 <c:if test="${slideshow}">
					<input type="hidden" name="type" value="<c:out value='${type}'/>"/>
				 	<input type="hidden" name="page" value="<c:out value='${pageNumber}'/>"/>
				 	<input type="hidden" name="pageSize" value="<c:out value='${pageSize}'/>"/>
				 	<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}'/>"/>
				 </c:if>
				 <div class="standard"  id="changeAttributes" style="margin:0; border-bottom: 1px solid #aaa;">
					 <input type="hidden" name="attributesSelected" value="true"/>
						
					 <div class="toolbar">
					 	
						<h3><bright:cmsWrite identifier="subhead-select-attributes-display" filter="false"/></h3>
						<c:if test="${!slideshow}">
						<div class="group js-enabled-show" >
  						<bright:cmsWrite identifier="snippet-select" filter="false"/>: <a href="#"  onclick="selectCheckboxes('all', '#changeAttributes input.checkbox'); return false;"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a>, <a href="#" onclick="selectCheckboxes('none', '#changeAttributes input.checkbox'); return false;"><bright:cmsWrite identifier="snippet-none" filter="false" case="mixed"/></a>.
						</div>
						</c:if>
				   </div>
		
				   <logic:iterate name="controlPanelForm" property="attributeList" id="attribute">
					  <c:if test="${attribute.fieldName!='file'}">
						 <c:if test="${(attribute.fieldName != 'price' || ecommerce) && attribute.isVisible}">
							   <label for="requiredAttribute_<bean:write name='attribute' property='id'/>" class="after"><input type="checkbox" class="checkbox" name="requiredAttribute_<bean:write name='attribute' property='id'/>" value="true" id="requiredAttribute_<bean:write name='attribute' property='id'/>" <c:if test="${attribute.selected}">checked</c:if>/> <bean:write name="attribute" property="label"/></label>

						 </c:if>
					  </c:if>
				   </logic:iterate>
				   <div class="clearing"></div>
			   </div>
	   
			
	    <div class="standard"   style="margin-top:1em;">
			 <c:choose><c:when test="${!slideshow}"><label for="maxwidth"><bright:cmsWrite identifier="snippet-insert-page-breaks" filter="false"/> <html:text name="controlPanelForm" property="assetsPerPage" size="4" style="width:2em" styleId="maxwidth" /> <bright:cmsWrite identifier="items" filter="false"/>.</label>&nbsp;&nbsp;</c:when><c:otherwise><label for="refreshrate"><bright:cmsWrite identifier="snippet-display-time" filter="false"/> <html:text name="controlPanelForm" property="displayTime" size="4" style="width:2em" styleId="maxwidth" /></label><br/><label for="labels"><bright:cmsWrite identifier="snippet-show-attribute-labels" filter="false"/> <html:checkbox name="controlPanelForm" property="showAttributeLabels" styleId="labels" value="true" /></label><br/><br/></c:otherwise></c:choose><input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-apply" filter="false"/>" />

			      <c:if test="${attributeSelected}">
					   <input type="hidden" name="attributesSelected" value="true"/>
			      </c:if>
		     </div>
			 
			</form> 
			 
		   </div>
	   </div>
	</div>
</div>

<c:if test="${close}">
	<script type="text/javascript">
		<!--
			toggleControls();
		-->
	</script>
</c:if>