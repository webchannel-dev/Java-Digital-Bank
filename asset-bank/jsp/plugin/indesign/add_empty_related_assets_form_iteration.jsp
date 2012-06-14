<%@ page import="com.bright.framework.common.bean.IdValueBean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%
	IdValueBean relationship = (IdValueBean)(request.getAttribute("relationship"));
	pageContext.setAttribute("optionsCollection", request.getAttribute("indesign_templateAssets_"+relationship.getId()));
%>

<logic:present name="optionsCollection">
	<logic:notEmpty name="optionsCollection">
		<input type="hidden" name="ext(indesign_pdfQuality_<c:out value='${relationship.id}' />)" value="1" />
		<bean:size name="optionsCollection" id="collectionCount" />
		<c:choose>
			<c:when test="${collectionCount == 1}">
				<logic:iterate name="optionsCollection" id="option">
					<input type="hidden" name="ext(indesign_templateAssetId_<c:out value='${relationship.id}' />)" value="<c:out value='${option.id}' />" />
					<c:out value="${option.searchName}" />
				</logic:iterate>
			</c:when>
			<c:otherwise>
				<select class="text" name="ext(indesign_templateAssetId_<c:out value='${relationship.id}' />)" id="ext_indesign_templateAssetId__<c:out value='${relationship.id}' />">
					<logic:iterate name="optionsCollection" id="option">
						<option value="<c:out value='${option.id}' />"><c:out value="${option.searchName}" /></option>
					</logic:iterate>
				</select>
			</c:otherwise>
		</c:choose>
	</logic:notEmpty>
</logic:present>
