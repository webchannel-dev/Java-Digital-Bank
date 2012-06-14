<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>

	<bean:define id="section" value="repurpose"/>
	<bean:define id="helpsection" value="repurposeSlideshow"/>
	
	<title><bright:cmsWrite identifier="title-repurpose-slideshow" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript">
		$j(function() {
			$j('#defaultOnHomepage').click(function(){ 
				if ($j(this).attr('checked')) {
					$j('#showInListOnHomepage').attr('checked',true);
				}
			});		
		});
		$j(function() {
			$j('#showInListOnHomepage').click(function(){ 
				if (!$j(this).attr('checked')) {
					$j('#defaultOnHomepage').attr('checked',false);
				}
			});		
		});
	</script>		

</head>

<body>
		
	<%@include file="../inc/body_start.jsp"%> 

	<h1 class="underline"><bright:cmsWrite identifier="heading-repurpose-slideshow" filter="false" /></h1> 
	
	<logic:notEmpty name="repurposeSlideshowForm" property="errors">
		<div class="error">
			<logic:iterate name="repurposeSlideshowForm" property="errors" id="error">
				- <bean:write name="error" /><br />
			</logic:iterate>
		</div>
	</logic:notEmpty>
	

	<form action="updateSlideshow" method="get" id="usageForm" class="floated">
		<html:hidden name="repurposeSlideshowForm" property="id"/>
		<label for='description'>Name:</label><html:text styleClass="description" name="repurposeSlideshowForm" property="description"/><br/>
		<label for="showInListOnHomepage">Show in list on homepage:</label><html:checkbox name="repurposeSlideshowForm" property="showInListOnHomepage" value="true" styleId="showInListOnHomepage" styleClass="checkbox" /><br/>
		<label for="defaultOnHomepage">Default on homepage:</label><html:checkbox name="repurposeSlideshowForm" property="defaultOnHomepage" value="true" styleId="defaultOnHomepage" styleClass="checkbox" /><br/> 
		
		<div class="hr"></div>
		<input type="submit" name="submitButton" value="Save" class="button flush"/>
		<a href="viewRepurposedSlideshows" class="cancelLink">Cancel</a>  

	</form>
	

   	  
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
