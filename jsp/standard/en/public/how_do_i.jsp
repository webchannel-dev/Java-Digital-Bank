<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />



<head>
	
	<title>Asset Bank | Bright Interactive</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="howdoi"/>
	<bean:define id="pagetitle" value="How Do I &hellip;?"/>

</head>

<body id="howdoi">
<%@include file="../inc/head.jsp"%>

<h2 class="hidden">How do I..?</h2>

<div class="copy">

<div class="rule" style="padding-top:0;margin-bottom:12px;"></div>

<p><a href="#search">Use the Search facility</a></p>
<p><a href="#download">Download Images</a></p>
<p><a href="#lightbox">Use the Lightbox</a></p>
<logic:present name="userprofile">
	<logic:equal name="userprofile" property="canUpdateAssets" value="true">
		<p><a href="#upload">Upload Images</a></p>
	</logic:equal>
</logic:present>

<div class="rule"></div>

<a name="search"></a>
<h2>Use the Search facility</h2>

<p>If you are looking for a specific type of image, you can search the Asset Bank by a keyword, by an author's surname and by category.</p>
<ul>
	<li>Select "Search" from the left-hand menu.</li>
	<li>The Search window contains fields for Keywords, Author, Usage rights and Date created. Complete as few or as many fields as you want and click on Search.</li>
</ul>

<h3>Keywords</h3>

<p>Search all words</p>
<ul>
	<li>To search for more than one word, list the words with AND (in capitals) between them. The search will look only for images that are described using all of these words.</li>
	<li>For example, to find an image of pills in a bottle, key in "pills AND bottle".</li>
</ul>

<p>Search any words</p>
<ul>
	<li>For a broader search, list the keywords with a space or comma between them. The search will look for all images containing any of the words.</li>
	<li>For example, to find all images of microscopes or laboratory technicians, key in "microscope technician".</li>
</ul>

<h3>Author</h3>

<p>Names of authors or organisations which have submitted images will appear in the pull-down Author list.</p>
<ul>
	<li>Alternatively, to search for a particular author, insert the surname in the free-text field.</li>
</ul>

<h3>Usage rights</h3>

<p>To search for images that you may use without charge, select "Free use" from this field.</p>
<p>Some images may have restricted use, and will be marked "Restricted". Specific restrictions will be stated on the Image Detail view. For example, certain images may only be used with text that describes specific research.</p>

<h3>Date Created</h3>

<p>To refine your search to images created in a given time period, key in the start and end dates in the format "<c:out value='${dateFormatHelpString}' />"</p>
<ul>
	<li>To find all images created after a particular date, insert the start date and leave the end date blank.</li>
	<li>To find all images created before a particular date, insert the end date and leave the start date blank.</li>
</ul>

<h3>Refine search by category</h3>

<p>To perform a more restricted search, select appropriate categories from the pull-down category list, and Add them to the category window. 
If you want to search the entire image bank, do not select a category. The default [All] will be shown in the category window.</p>

<h3>Browse by category</h3>

<p>To browse all images within a named category, Add the category to the category window and leave the other fields blank.</p>

<h3>Results page</h3>

<p>On the results page, click the thumbnail or the image title to see a larger version of the image and the image details.<br />
To view a selection of images, add images to the Lightbox, and view them using My Lightbox.</p>

<div class="rule"></div>

<a name="download"></a>

<h2>Download Images</h2>

<p>To download more than one image</p>
<ul>
	<li>Select the images that you want to download by adding them to your Lightbox.</li>
	<li>On the Lightbox page, click "Download All"</li>
	<li>The Download page will be displayed.</li>
	<li>NB. All images will be downloaded as a Zip file.</li>
</ul>

<p>To download one image</p>
<ul>
	<li>From an Image Details view, select "Download".</li>
	<li>The Download page will be displayed.</li>
</ul>

<p>Download page</p>
<ul>
	<li>Indicate how you intend to use the images by selecting from the pull-down Use list, or entering the details in the free-text field.</li>
	<li>You will need to confirm that you have read the Conditions of Use.</li>
</ul>

<p class="note">NB. You will need to complete the Use field and confirm you have read the Conditions of Use before you can download any images.</p>

<ul>
	<li>Choose which format you want the images as:
		<ul>
			<li>for print purposes, download the originals;</li>
			<li>to use in a Powerpoint presentation, or on a website, select the appropriate option from the Convert &amp; Download list.</li>
		</ul>
	</li>
	<li>Select Download Originals or Download. You will be prompted to select a location to which to save your images.</li>
</ul>

<div class="rule"></div>

<a name="lightbox"></a>

<h2>Use the Lightbox</h2>

<p>During your visit to the Asset Bank, you can move selected images in and out of the Lightbox. It is a place to store and view images before you decide which ones to download.</p>
	
<ul>
	<li>You can add images to the Lightbox from the Search Results list or from an Image Detail view.</li>
	<li>You can delete images from the Lightbox to refine your selection before downloading them.</li>
</ul>

<p class="note">NB. Images you leave in the Lightbox when you log out will still there next time you log in.</p>

<logic:present name="userprofile">
	<logic:equal name="userprofile" property="canUpdateAssets" value="true">

	<a name="upload"></a>

	<div class="rule"></div>

	<h2>Upload Images</h2>

	<p>If you would like to contribute images to the Asset Image Bank you can upload them online. Select "Upload" from the left-hand menu.</p>

	<p>You will need to complete the following fields for each image:</p>

	<ul>
		<li>Title</li>
		<li>Author - i.e. the person and/or unit who created the image.</li>
		<li>Usage rights - indicate if there are any restrictions to use. If there are none, select "Free use".</li>
		<li>Description - provide a few sentences about the image. Please try to include words that may help searching for this type of image.</li>
		<li>Credit - give the exact wording that should be cited each time the image is used.</li>
		<li>Date created</li>
	</ul>

	<p>It would also be useful if you could select at least one appropriate category for each image.</p>

	</logic:equal>
</logic:present>

</div>
									
<%@include file="../inc/foot.jsp"%>	
</body>
</html>