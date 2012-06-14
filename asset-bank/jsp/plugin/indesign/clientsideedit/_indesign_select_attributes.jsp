
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<p>
<bright:cmsWrite identifier="snippet-attributes-for-indesign-document"/>
<p>
	
<div class="attrCols">	

<c:forEach var="attribute" items="${availableAttributes}">
	<label class="wrapping">
		<html:checkbox name="clientSideEditPrepForm" property="ext(indesign_att_${attribute.id})"/>
		<c:out value="${attribute.label}"/>
	</label>
</c:forEach>

</div>

<label for=""><bright:cmsWrite identifier="label-number-of-symbols"/></label> <html:text styleClass="text" name="clientSideEditPrepForm" styleId="ext_indesign__numSymbols" property="ext(indesign_numSymbols)" maxlength="5"/>
<br />
<label><bright:cmsWrite identifier="label-include-lightbox-contents"/></label>	
<label class="wrapping"><html:checkbox name="clientSideEditPrepForm" property="ext(indesign_download_lightbox)"/> <bright:cmsWrite identifier="snippet-include-lightbox-contents"/> </label><br />