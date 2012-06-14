<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


	
	<%-- get and show the browse list view display attributes --%>
	<c:set var="displayAttributeGroup" value="11" scope="request" />
	<bean:define id="descriptions" name="item" property='<%= "descriptions(" + String.valueOf(request.getAttribute("displayAttributeGroup")) + ")" %>' />

	<logic:notEmpty name="descriptions">

		<logic:iterate name="descriptions" id="description" indexId="index">
			<c:if test="${(not empty description)}" >
				<td>
					<c:if test="${firstIsHeader && index==0}"><h2></c:if>
					
					<c:if test="${not empty description.iconFile}"><span class="<c:out value='${description.classIdentifier}'/>"></c:if>
					<logic:equal name="description" property="isLink" value="true"><a href="viewAsset?<c:out value='${viewUrlParams}' />" title="View asset details"></logic:equal>
					
					<bean:write name="description" property="description" filter="false"/><c:if test="${not empty description.iconFile}">&nbsp;<img src="../servlet/display?file=${description.iconFile}" /></c:if>
					<logic:equal name="description" property="isLink" value="true"></a></logic:equal><c:choose><c:when test="${firstIsHeader && index==0}"></h2></c:when><c:otherwise></c:otherwise></c:choose>
					
					<c:if test="${not empty description.iconFile}"></span></c:if>
				&nbsp;
				</td>
			</c:if>
		</logic:iterate>
		

		<bean:define id="type" value="table" />
		<bean:define id="showLabel" value="false" />
		<%@include file="../inc/result_extra_descriptions.jsp"%>

	</logic:notEmpty>	

	<logic:notPresent name="ratingIsInDisplay">
		<bright:refDataList id="ratingIsInDisplay" componentName="DisplayAttributeManager" methodName="isRatingDisplayAttribute" argumentValue="${displayAttributeGroup}"/>
	</logic:notPresent>

	<c:if test="${showViews || showDownloads || ratingIsInDisplay}">	
		<td>
			<%@include file="../inc/inc_result_views_ratings.jsp"%>
			&nbsp;
		</td>
	</c:if>