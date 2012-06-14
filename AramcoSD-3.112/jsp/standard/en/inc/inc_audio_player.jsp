<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:define id="height" value="70"/>
<bean:define id="width" value="400"/>
<bean:define id="autoplay" value="false"/>
<bean:define id="marginTop" value="0"/>
<div style="<c:if test="${floatFlashPlayerLeft}">float: left;</c:if>">
	<%@include file="../public/inc_flash_player.jsp"%>		
</div>
