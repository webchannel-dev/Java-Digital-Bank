<%@page import="java.util.Date"%>
<%@page import="com.bright.framework.util.BrightDateFormat"%>
<%@page import="com.bright.assetbank.custom.bluebooks.batchrelease.plugin.ECNameService"%>

<%--
	Form start include for bluebooks engineering change name plugin.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bean:define id="basicName" value="true" />

<c:if test="${batchReleaseForm.id <= 0}">
	<bean:define id="basicName" value="false" />
</c:if>

<logic:notEmpty name="batchReleaseForm" property="ext(ecname_year)">
	<bean:define id="basicName" value="false" />
</logic:notEmpty>

<logic:equal name="basicName" value="false" >
	<c:choose>
		<c:when test="${batchReleaseForm.id <= 0}">
			<input type="hidden" name="name" value="[TEMP]" />
		</c:when>
		<c:otherwise>
			<html:hidden name="batchReleaseForm" property="name" />
		</c:otherwise>
	</c:choose>

	<c:if test="${batchReleaseForm.id <= 0}">
		<%
			//setup the variables we need for adding...
			String sYear = (new BrightDateFormat("yy")).format(new Date());
			String sSequence = (new ECNameService()).getNextSequenceNo();
			request.setAttribute("preSelectYear", sYear);
			request.setAttribute("sequence", sSequence);
		%>
	</c:if>

	<logic:notEmpty name="batchReleaseForm" property="ext(ecname_year)">
		<bean:define id="preSelectYear" name="batchReleaseForm" property="ext(ecname_year)" />
	</logic:notEmpty>

	<logic:notEmpty name="batchReleaseForm" property="ext(ecname_sequence)">
		<bean:define id="sequence" name="batchReleaseForm" property="ext(ecname_sequence)" />
	</logic:notEmpty>
	<input type="hidden" name="mandatory_ext(ecname_producttype)" value="Please provide a product type" />
	<input type="hidden" name="mandatory_ext(ecname_year)" value="Please provide a year" />

	<label>Prefix:</label> KW<br />
	<label for="ext_ecname_year">Year:<span class="mandatory">*</span></label> <html:text styleClass="small text" name="batchReleaseForm" styleId="ext_ecname_year" property="ext(ecname_year)" value="${preSelectYear}" maxlength="2"/><br />
	<label for="ext_ecname_producttype">Product Type:<span class="mandatory">*</span></label> <html:text styleClass="small text" name="batchReleaseForm" styleId="ext_ecname_producttype" property="ext(ecname_producttype)" maxlength="2"/><br />
	<label for="ext_ecname_type">Type:</label> <html:select name="batchReleaseForm" styleId="ext_ecname_type" property="ext(ecname_type)" size="1"><option value="1" <logic:equal name="batchReleaseForm" property="ext(ecname_type)" value="1">selected</logic:equal>>New</option><option value="5" <logic:equal name="batchReleaseForm" property="ext(ecname_type)" value="5">selected</logic:equal>>Change</option></html:select><br />
	<label for="ext_ecname_sequence">Sequence:</label> <html:hidden name="batchReleaseForm" styleId="ext_ecname_sequence" property="ext(ecname_sequence)" value="${sequence}" /><bean:write name='sequence' /><br /><br />
</logic:equal>

<logic:equal name="basicName" value="true" >
	<label for="name"><bright:cmsWrite identifier="label-name" filter="false" /></label> <html:text name="batchReleaseForm" property="name" /><br />
</logic:equal>