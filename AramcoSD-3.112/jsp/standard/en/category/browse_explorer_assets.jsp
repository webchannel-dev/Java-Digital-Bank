<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		30-Nov-2006		Created
	 d2		Matt Stevenson		01-Dec-2006		Work on presentation
	 d3		Matt Stevenson		04-Dec-2006		Further build work
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:present parameter="page">
	<bean:parameter id="p_page" name="page"/>
</logic:present>
<logic:notPresent parameter="page">
	<bean:define id="p_page" value="0"/>
</logic:notPresent>

<bean:parameter id="categoryTypeId" name="categoryTypeId" value="1" />


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="titleMaxLength" settingName="browse-title-max-length"/>
<bright:applicationSetting id="descriptionMaxLength" settingName="browse-description-max-length"/>

<bright:applicationSetting id="showCategoryItemCounts" settingName="showCategoryItemCounts"/>
<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>


<bean:define id="section" value="browse"/>
<bean:define id="pagetitle" value="Browse Categories"/>
<bean:define id="browseableName" value="category" />
<bean:define id="browseablesName" value="categories" />
<c:set var="displayAttributeGroup" value="2" scope="request" />
							
<c:set var="explorer" value="true" />
<div class="panelWrapper">
	<%@include file="inc_panelised_assets.jsp"%>
</div>