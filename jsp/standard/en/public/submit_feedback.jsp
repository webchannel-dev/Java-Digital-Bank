<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		12-Aug-2008		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	
<bean:define id="displayCase" value="lower"/>
<c:set var="type">
	<%@include file="inc_feedback_snippet.jsp"%>
</c:set>
<bright:applicationSetting id="maxRating" settingName="max-rating"/>
	
<head>
	
	<title><bright:cmsWrite identifier="title-submit-feedback" filter="false" replaceVariables="true"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>	

	<script type="text/javascript">
		<!--
			var bClearStars = true;
			var iRating = <c:out value='${feedbackForm.feedback.rating}'/>;

			function set_stars (higlightStar)
			{
				var iCount;
				for (var i=1; i<=higlightStar; i++)
				{
					document.getElementById("star"+i).src = "../images/standard/icon/star.gif";
					iCount = i;
				}

				for (var i=iCount+1; i<=<c:out value='${maxRating}'/>; i++)
				{
					document.getElementById("star"+i).src = "../images/standard/icon/star_off.gif";
				}
				 
			}

			function clear_stars ()
			{
				if (bClearStars)
				{
					//make sure rating is set to 0 if we have a negative value...
					if (iRating <= 0)
					{
						iRating = 0;
					}
					
					//set the on stars...
					for (var i=1; i<=iRating; i++)
					{
						document.getElementById("star"+i).src = "../images/standard/icon/star.gif";
					}
					
					//set the off stars...
					for (var i=iRating+1; i<=<c:out value='${maxRating}'/>; i++)
					{
						document.getElementById("star"+i).src = "../images/standard/icon/star_off.gif";
					}
					document.getElementById('feedback.rating').value = iRating;
				}
				bClearStars = true;
			}

			function set_value (ratingValue)
			{
				bClearStars = false;
				document.getElementById('feedback.rating').value = ratingValue;
			}
		-->
	</script>
</head>

<body id="detailsPage" onload="document.getElementById('starsDiv').style.display = 'block';">

	<%@include file="../inc/body_start.jsp"%>

	<bright:applicationSetting id="comments" settingName="comments"/>
	<bright:applicationSetting id="ratings" settingName="ratings"/>
	<bright:applicationSetting id="ratings-show-subject" settingName="ratings-show-subject"/>
	<bright:applicationSetting id="ratings-comments-mandatory" settingName="ratings-comments-mandatory"/>	
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-submit-feedback" filter="false" replaceVariables="true"/></h1>

	<c:choose>
		<c:when test="${comments && !ratings}">
			<p><bright:cmsWrite identifier="submit-feedback-comment" filter="false" replaceVariables="true"/></p>
		</c:when>
		<c:when test="${ratings && !comments}">
			<p><bright:cmsWrite identifier="submit-feedback-rating" filter="false" replaceVariables="true"/></p>
		</c:when>
		<c:otherwise>
			<p><bright:cmsWrite identifier="submit-feedback-main" filter="false" replaceVariables="true"/></p>
		</c:otherwise>
	</c:choose>

	<logic:equal name="feedbackForm" property="hasErrors" value="true">
		<br/>
		<div class="error">
			<logic:iterate name="feedbackForm" property="errors" id="error">
				<bean:write name='error'/><br/>
			</logic:iterate>
		</div>
	</logic:equal>

	<bean:parameter id="assetId" name="id" value="0"/>
	<bean:parameter id="queryString" name="queryString" value=""/>

	<form name="feedbackForm" action="submitFeedback" method="get" class="floated clearfix" style="margin-bottom:0;">
		<html:hidden name="feedbackForm" property="feedback.assetId"/>
		<html:hidden name="feedbackForm" property="feedback.id"/>
		<html:hidden name="feedbackForm" property="feedback.userId"/>
		<logic:notEmpty name="queryString">
			<input type="hidden" name="queryString" value="<bean:write name='queryString'/>"/>
		</logic:notEmpty>
		<logic:empty name="queryString">
			<input type="hidden" name="queryString" value="<%= request.getQueryString() %>"/>
		</logic:empty>
		
		<c:choose>
			<c:when test="${ratings}">
				<label for="feedback.rating"><bright:cmsWrite identifier="label-rating" filter="false"/><span class="required">*</span></label>
				<noscript>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<c:forEach begin="1" end="${maxRating}" var="value"><td><input type="radio" name="feedback.rating" class="checkbox" value="<c:out value='${value}'/>" <c:if test="${value == feedbackForm.feedback.rating}">checked</c:if>/></td></c:forEach>
						</tr>
						<tr>
							<c:forEach begin="1" end="${maxRating}" var="value"><td style="text-align: center"><c:out value="${value}"/></td></c:forEach>
						</tr>
					</table>
				</noscript>
				<div id="starsDiv" style="display: none;">
					<html:hidden name="feedbackForm" property="feedback.rating" styleId="feedback.rating"/>
					<c:forEach begin="1" end="${maxRating}" var="value"><img src="../images/standard/icon/star<c:if test='${value > feedbackForm.feedback.rating}'>_off</c:if>.gif" alt="" id="star<bean:write name='value'/>" onmouseover="set_stars(<c:out value='${value}'/>);" onmouseout="clear_stars();" onclick="set_value(<c:out value='${value}'/>);" style="cursor: pointer"/></c:forEach>
				</div><br/>
			</c:when>
			<c:otherwise>
				<html:hidden name="feedbackForm" property="feedback.rating"/>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${comments}">

				<logic:equal name="ratings-show-subject" value="true">
					<bright:refDataList id="feedbackList" componentName="ListManager" methodName="getList" argumentValue="feedback_subject"/>
					<label for="feedback.subject"><bright:cmsWrite identifier="label-subject" filter="false"/></label>
					<logic:notEmpty name="feedbackList" property="items">
						<html:select name="feedbackForm" property="feedback.subject" styleId="feedback.subject" size="1">
							<logic:iterate name="feedbackList" property="items" id="item">
								<option value="<bean:write name='item' property='body'/>" <c:if test="${item.body == feedbackForm.feedback.subject}">selected</c:if>><bean:write name='item' property='body'/></option>
							</logic:iterate>
						</html:select>
						<br />
					</logic:notEmpty>
					<logic:empty name="feedbackList" property="items">
						<html:text name="feedbackForm" property="feedback.subject" styleId="feedback.subject"/><br />
					</logic:empty>
				</logic:equal>
				<label for="feedback.comments"><bright:cmsWrite identifier="label-comments" filter="false"/><logic:equal name="ratings-comments-mandatory" value="true"><span class="required">*</span></logic:equal></label>
				<html:textarea name="feedbackForm" property="feedback.comments" styleId="feedback.comments" rows="10" cols="50"></html:textarea><br />
			</c:when>
			<c:otherwise>
				<html:hidden name="feedbackForm" property="feedback.comments"/>
			</c:otherwise>
		</c:choose>

		<div class="hr"></div>
		<input type="submit" class="button flush floated" id="submitButton"  value="<bright:cmsWrite identifier="button-submit" />" /> 
		<!--input type="submit" name="cancelButton"  class="button floated"   value="<bright:cmsWrite identifier="button-cancel" />" style="margin-left:10px;" /-->
		<a href="viewAsset?id=<bean:write name='feedbackForm' property='feedback.assetId'/>&<bean:write name='queryString'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	
	</form>
	

	 
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>