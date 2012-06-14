<%-- History:
	d1	Francis Devereux	20-Aug-2009		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="maxAutocompleteResults" settingName="auto-complete-max-results" />

[
	<bean:size id="numMatches" name="highlightedMatches"/>
	<c:forEach var="match" items="${highlightedMatches}" varStatus="status">
		{
			"value" : "<c:out value="${match.valueJS}" escapeXml="false"/>",
			<%-- escapeXml="false" in the line below does not introduce an XSS vulnerability because the keywords are passed through ResponseUtils.filter() in HighlightedACMatch.getHighlightedLabelHtml() --%>
			"label" : "<c:out value="${match.highlightedLabelHtml}" escapeXml="false"/>"
		}<c:if test="${not status.last || numMatches >= maxAutocompleteResults}">,</c:if>
		
	</c:forEach>
	<c:if test="${numMatches >= maxAutocompleteResults}">
		{
			"value":"", 
			"label":"<em><bright:cmsWrite identifier="snippet-autocomplete-prompt" filter="false"/></em>"
		}
	</c:if>
]


