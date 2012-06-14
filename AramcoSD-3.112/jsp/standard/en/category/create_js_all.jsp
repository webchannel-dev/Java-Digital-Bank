<%-- 
	Generates JavaScript called by generateCatJS action

	History:
	 d1		Martin Wilson		01-Jun-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="databaseSupportsUTF8" settingName="databaseSupportsUTF8"/>

<c:if test="${databaseSupportsUTF8}">
	<% response.setContentType("text/JavaScript;charset=utf-8"); %>
</c:if>	

<%-- Sorry about the squashed code below! It's like this because othewise, when there are many categories, the white space makes the page very long and so it takes ages to load.--%>
	
	<logic:iterate name="generateCatJSForm" property="descriptiveCategories" id="category" indexId="catIndex">var catInfo = new Object(); catInfo.id = <bean:write name="category" property="id"/>; catInfo.parentId = <bean:write name="category" property="parentId"/>; catInfo.name = '<bean:write name="category" property="JSUnicodeName" filter="false"/>';	catInfoArray[k_iCategoryIndex] = catInfo; 
			k_iCategoryIndex++;</logic:iterate>	
	<logic:iterate name="generateCatJSForm" property="permissionCategories" id="category" indexId="catIndex">var catInfo = new Object(); catInfo.id = <bean:write name="category" property="id"/>; catInfo.parentId = <bean:write name="category" property="parentId"/>; catInfo.name = '<bean:write name="category" property="JSUnicodeName" filter="false"/>';	catInfoArray[k_iCategoryIndex] = catInfo; 
			k_iCategoryIndex++;</logic:iterate>	
	
