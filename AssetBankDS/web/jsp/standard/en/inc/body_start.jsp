<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
      
		<%@include file="../inc/head.jsp"%>
		<div class="pageWrapper">
		 
      <div id="content">
			      
         <div id="mainCol" class="clearfix">
         	
			<c:if test="${assetBoxErrorMessage==null}">
				<c:set var="assetBoxErrorMessage" value="${param.assetBoxErrorMessage}"/>
			</c:if>
			<c:if test="${assetBoxErrorMessage!=null}">
				<div class="error" style="margin-bottom: 14px; font-weight: bold;"><c:out value="${assetBoxErrorMessage}" escapeXml="false"/></div>	
			</c:if>