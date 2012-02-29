<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="defaultSearchOperator" settingName="default-search-operator"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Search Help</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="help">

<div class="copy">
	
<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="keyword-help"/>	
<c:choose>
		
<c:when test="${!empty copyItem.body}">
	<bean:write name="copyItem" property="body" filter="false"/>					     
</c:when>
<c:otherwise>

	<h2>Keyword Search Help</h2>

	<h3>Basic searching</h3>

	
	<p>You may enter one or more words into the keyword search field in order to find the images/files you are looking for.</p>
	
	<p>If you enter more than one word into the keyword search field then the search will find items that contain
		<c:choose>
			<c:when test="${defaultSearchOperator == 'AND'}">
				all of
			</c:when>
			<c:otherwise>
				at least one of
			</c:otherwise>
		</c:choose> 
		 the words you entered.</p>
	
	<p>The search is not case sensitive, with the exception of the boolean operators described below.</p>

	<h3>Searching for phrases</h3>

	<p>You can search for a phrase of two or more words by enclosing the words in quotation marks. E.g. searching for<br /><br />
	<code class="searchString">"the pink panther"</code><br /><br />
	will find only items containing this complete phrase.</p>

	<a name="boolean"></a>
	<h3>Boolean operators</h3>

	<h4>AND</h4>
	<p>Placing the operator <code>AND</code> (in upper-case) between two words or phrases will find only those items that contain both words or phrases. E.g. searching for <br /><br /><code class="searchString">return AND "pink panther"</code><br /><br /> will find only those items containing both the word "return" and the phrase "pink panther".</p>
	<c:if test="${defaultSearchOperator == 'AND'}">
		<p>The <code>AND</code> operator is optional; leaving a space between 2 words or phrases has the same effect.</p>
	</c:if>
	
	<h4>OR</h4>
	<p>Placing the operator <code>OR</code> (in upper-case) between two words or phrases will find items that contain either of the words or phrases. E.g. searching for <br /><br /><code class="searchString">return OR "pink panther"</code><br /><br /> will find items containing either the word "return" or the phrase "pink panther".</p>
	<c:if test="${defaultSearchOperator == 'OR'}">
		<p>The <code>OR</code> operator is optional; leaving a space between 2 words or phrases has the same effect.</p>
	</c:if>
	

	<h4>NOT</h4>

	<p>Placing the operator <code>NOT</code> between two words or phrases will find only those items that contain the first word or phrase but <em>do not</em> contin the second. E.g. searching for <br /><br /><code class="searchString">pink NOT red</code><br /><br /> will match items that contain the word "pink" but do not also contain the word "red". Note that there must be a word or phrase on either side of the <code>NOT</code> operator.</p>

	<h3>Grouping</h3>

	<p>Parentheses can be used to group search clauses into sub-queries. This can be very useful for controlling the boolean logic of a search. For example, the search <br /><br /><code class="searchString">red AND pink OR blue</code><br /><br /> is logically ambiguous, whereas the meaning of <br /><br /><code class="searchString">red AND (pink OR blue)</code><br /><br /> or <br /><br /><code class="searchString">(red AND pink) OR blue</code><br /><br /> is clear.</p>

	<h3>Wildcard searches</h3>

	<p>Wildcards can be used to match against any character or characters that appear somewhere in a word. Note, however, that neither of the wildcards described below can appear at the beginning of a word - at least the first character of the word must be specified.</p>

	<h4>Single character wildcard</h4>

	<p>A question mark (<code>?</code>) can be used in a word to match against any single character. For instance,<br /><br /><code class="searchString">p?nk</code><br /><br /> will find "pink" and "punk".</p>

	<h4>Multiple character wildcard</h4>

	<p>An asterisk (<code>*</code>) can be used to match any 0 or more characters in a word. For instance,<br /><br /><code class="searchString">pin*</code><br /><br /> will match "pin", "pink", "pints", etc. and <br /><br /><code class="searchString">p*t</code><br /><br /> will match all word beginning in "p" and ending in "t".</p>
	
	<c:if test="${userprofile.isAdmin}">
		<h3>Special Keywords</h3>
		<p>You may use the special keyword "isempty" in any text search fields to find assets with empty metadata fields, or 
			"untranslated" to find assets for which there is data that has not yet been translated into a particular language.</p>
	</c:if>
</c:otherwise>		
</c:choose>
			
	<br />

	<div style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>
		
</div>

</body>
</html>