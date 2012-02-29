<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		15-Dec-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>

	<bean:define id="section" value="repurpose"/>
	<bean:define id="helpsection" value="repurposedSlideshows"/>
	
	<title><bright:cmsWrite identifier="title-repurposed-slideshow" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	

</head>
<bright:applicationSetting id="refreshPeriod" settingName="repurposed-slideshow-refresh-period"/>

<body>

	<%@include file="../inc/body_start.jsp"%> 
	
	<bean:parameter id="editing" name="editing" value="0"/>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-slideshow" filter="false" /></h1> 
	
	
	<c:if test="${editing != 1}">
		<div class="confirm">Your embeddable slideshow has been created. <br /><a href="previewRepurposedSlideshow?id=<bean:write name='repurposingForm' property='repurposedVersion.id'/>" target="_blank" class="help-popup">Preview this slideshow</a> | <a href="downloadRepurposedSlideshow?id=<bean:write name='repurposingForm' property='repurposedVersion.id'/>">Download this slideshow as a zip</a> | <a href="viewRepurposedSlideshows">Manage your slideshows</a><br />
	
		</div>
	</c:if>

	<logic:notEmpty name="repurposingForm" property="repurposedVersion.searchCriteria"><div class="info">This is a refreshing embeddable slideshow. Every <c:if test="${refreshPeriod > 1}"><bean:write name='refreshPeriod' /> </c:if>hour<c:if test="${refreshPeriod > 1}">s</c:if> the search will be re-run and the slideshow regenerated to match the results of the search.</div></logic:notEmpty>
	
	<h3>Embeddable HTML<logic:notEmpty name='repurposingForm' property='repurposedVersion.description'> - <em><bean:write name='repurposingForm' property='repurposedVersion.description' /></em></logic:notEmpty></h3>

	
	<p>To embed this slideshow on another website copy the HTML snippet below into the page you want to show the slideshow on:</p>
	

	<div><!-- wrap textarea in div to prevent IE6/7 bug where it inherits a left-hand margin -->
		<html:textarea name='repurposingForm' property='repurposedVersion.embeddableHtml' styleClass="embedCode" onclick="select();" />
	</div>
	<br />
	
	
	<c:if test="${editing == 1}">
		<p>
			<a href="previewRepurposedSlideshow?id=<bean:write name='repurposingForm' property='repurposedVersion.id'/>" target="_blank" class="help-popup">Preview this slideshow</a> 
			&nbsp;|&nbsp; 
			<a href="downloadRepurposedSlideshow?id=<bean:write name='repurposingForm' property='repurposedVersion.id'/>">Download this slideshow as a zip</a>
			
		</p>
		<div class="hr"></div>	
	</c:if>





	<p><a href="viewRepurposedSlideshows">&laquo; Manage your slideshows</a></p>

	<!-- end of left col -->
	<div class="clearing">&nbsp;</div>

   	  
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
