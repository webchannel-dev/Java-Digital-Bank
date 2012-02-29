<%-- 
	Set up javascript for autocomplete on multiple language fields
	
	Pass in:
	searchOrEdit - should be 'search' for search pages and 'edit' for upload/edit pages..
	
	History:
		d1      Ben Browning   01-Oct-2009    Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

	<c:if test="${attribute.autoComplete && attribute.isVisible}">
		if ($j('#field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>').length>0) {
			<c:choose>
				<c:when test="${attribute.tokenDelimitersJS != null}">
					// get defined delimiter(s)
					var delimiters = [
						<c:forEach var="delimiter" items="${attribute.tokenDelimitersJS}" varStatus="delimiterStatus">
							"<c:out value="${delimiter}" escapeXml="false"/>"<c:if test="${!delimiterStatus.last}">,</c:if>								</c:forEach>					
						];
				</c:when>
				<c:otherwise>
					// default delimiter to nothing
					var delimiters = "";
				</c:otherwise>
			</c:choose>	
			initAutocompleter("field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>", "autocomplete_field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>",delimiters,"<bean:write name='attribute' property='id'/>","<c:out value="${searchOrEdit}"/>","<bean:write name="translation" property="language.code"/>");           
		}
	</c:if>

