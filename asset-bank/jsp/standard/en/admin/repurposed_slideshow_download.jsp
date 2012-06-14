<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		17-Feb-2010		Created
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

<body>

	<%@include file="../inc/body_start.jsp"%> 
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-repurposed-slideshow" filter="false" /></h1> 
	
	<div class="confirm">
		Your slideshow has been prepared for download.<br />
		<a href="<bean:write name='repurposingForm' property='downloadUrl'/>">Download your slideshow &raquo;</a>
	</div>
	<p>The download will be a zip file containing the following elements:</p>
	
	<ul>
		<li><b>Image files:</b> Copies of the image files used in the slideshow</li>
		<li><b>XML file:</b> A xml file that specifies the images to use in the slideshow, the order they are presented in and any associated captions</li>
		<li><b>HTML file:</b> An example HTML file showing how to embed the slideshow on a website</li>
		<li><b>Flash and Javascript files:</b> The files that do the actual work of showing the slideshow</li>
	</ul>
	<br/>
	<p>All references to other files in the HTML and XML files contain no path information because everything is in the same directory. You will need to adjust paths if you move the files into separate directories for any reason.</p>
	
	<div class="hr"></div>
	
	<p><a href="viewRepurposedSlideshows">&laquo; Return to embeddable slideshows list</a></p>

	<!-- end of left col -->
	<div class="clearing">&nbsp;</div>

   	  
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
