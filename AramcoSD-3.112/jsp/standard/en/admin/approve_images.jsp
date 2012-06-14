<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
            "http://www.w3.org/TR/html4/loose.dtd">
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Tamora James	05-Sep-2005		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>
<bright:applicationSetting id="companyName" settingName="companyName"/>
<head>
	<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
	<title><bean:write name="companyName" /> | Approve Images</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="pagetitle" value="Approve Images"/>
</head>

<body id="adminPage">
<%@include file="../inc/head.jsp"%>
<p class="rule">
Lightbox request from Ben Golder on 01/09/2005.
</p>
<div class="rule" style="padding-top:0;"><!--  --></div>
<form>
	<ul class="results">
		<li class="was.clearfix">		
			<%--p style="padding-bottom: 4px;"><strong>ID: <a href="viewAsset?id="><bean:write name="asset" property="id"/></a></strong></p--%>
			<%--c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if--%>
	
			<a href="../action/viewAsset?id="><img class="icon" src="../images-mrc/tmp/recent1.gif" alt="" width="58" height="58" border="0"><!--img class="icon" src="../servlet/display?file=" alt=""--></a>
			
			<select class="action">
				<option>Unapproved</option>
				<option>Pending</option>
				<option>Approved</option>
			</select>
			
			<h3><a href="viewAsset?id=">23: Consequat vel dolor duis qui ipsum<%--bean:write name="asset" property="name"/--%></a></h3>
			<p>
				<%--bean:write name="asset" property="description"/--%>
				Delenit qui feugait sit ex minim aliquam ullamcorper nostrud at commodo duis eu, ex, commodo facilisis. Veniam tation wisi adipiscing consequat et wisi praesent dignissim autem vel nulla facilisis te vel, et.
			</p>
			
			<textarea class="admin" rows="4" cols="35">Notes from admin</textarea>
		</li>
		<li class="was.clearfix">		
			<%--p style="padding-bottom: 4px;"><strong>ID: <a href="viewAsset?id="><bean:write name="asset" property="id"/></a></strong></p--%>
			<%--c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if--%>
	
			<a href="../action/viewAsset?id="><img class="icon" src="../images-mrc/tmp/recent1.gif" alt="" width="58" height="58" border="0"><!--img class="icon" src="../servlet/display?file=" alt=""--></a>
			
			<select class="action">
				<option>Unapproved</option>
				<option>Pending</option>
				<option>Approved</option>
			</select>
			
			<h3><a href="viewAsset?id=">44: Consequat vel dolor duis qui ipsum<%--bean:write name="asset" property="name"/--%></a></h3>
			<p>
				<%--bean:write name="asset" property="description"/--%>
				Delenit qui feugait sit ex minim aliquam ullamcorper nostrud at commodo duis eu, ex, commodo facilisis. Veniam tation wisi adipiscing consequat et wisi praesent dignissim autem vel nulla facilisis te vel, et.
			</p>
			
			<textarea class="admin" rows="4" cols="35">Notes from admin</textarea>
		</li>
	</ul>

	<div class="panel greyPanel">

		<table cellspacing="0" class="form" summary="Form for user permissions">
			<tr>
				<th style="width: 60px;">Approve?:</th>
				<td>
					<select name="approved">
						<option value="1" selected="true">Approve</option>
						<option value="0">Reject</option>
					</select>
				</td>
			</tr>
		</table>	
		<div class="bottom" ></div>
	</div>
	<div class="rule" style="padding-top:0; margin-bottom: 16px;">
	<!--  -->
	</div>
	<div class="buttonHolder">
		<input type="submit" class="button" value="Submit"> 
	</div>
</form>

<form name="cancelForm" action="viewApproval" method="get">
	<input type="submit" name="cancel" value="Cancel" class="button" id="cancelButton">
</form>
<%@include file="../inc/foot.jsp"%>
</body>
</html>