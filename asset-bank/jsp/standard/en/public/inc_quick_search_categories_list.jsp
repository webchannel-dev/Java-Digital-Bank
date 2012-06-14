<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<logic:notPresent name="bigStyle">
	<bean:define id="bigStyle" value=""/>
</logic:notPresent>
<logic:notEmpty name="browserCategories">
	<select name="descriptiveCategoryForm.selectedCategories" class="<c:out value="${bigStyle}" /> searchCategoryFilter">
		<option value="">- <bright:cmsWrite identifier="heading-all-cats" filter="false"/> -</option>														
		<logic:iterate name="browserCategories" id="category">
			<c:forEach items="${searchForm.descriptiveCategoryForm.selectedCategories}" var="selectedCategory">
						<c:choose>
							<c:when test="${selectedCategory ==  category.id}">
								<c:set var="categoryIsSelected" value="true" />  
							</c:when>
							<c:otherwise>
								<c:set var="categoryIsSelected" value="false" />
							</c:otherwise>
						</c:choose>
					</c:forEach>						
			<option <c:if test="${categoryIsSelected}"> selected </c:if> value="<c:out value='${category.id}' />"  /><c:out value='${category.name}' /></option>													
		</logic:iterate>
	</select>
</logic:notEmpty>