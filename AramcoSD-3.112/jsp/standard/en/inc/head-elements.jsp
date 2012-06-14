<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>	

<bright:applicationSetting id="autoCompleteQuickSearchEnabled" settingName="auto-complete-quick-search-enabled"/>

	<%-- force struts to output xhtml	--%>
	<html:xhtml/>
	
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	
	<bright:applicationSetting id="categoryExplorerSetting" settingName="useCategoryExplorer"/>
	<c:set var="categoryExplorerType" value="${categoryExplorerSetting}" scope="request" />
	<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
	<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
	<bright:applicationSetting id="jqueryDateFormat" settingName="jquery-standard-date-format" />
	<bright:applicationSetting id="jqueryYearMonthFormat" settingName="jquery-year-and-month-date-format" />

   <!-- Link to default stylesheet -->
   <link href="../css/standard/default.css" rel="stylesheet" type="text/css" media="all" />

	<logic:equal name="section" value="home">
		<link href="../css/standard/home.css" rel="stylesheet" type="text/css" media="all" />
	</logic:equal>
		

   <!-- Link to stylesheets for IE/Win -->
	<!--[if IE 5]>
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-5.css" />
   <![endif]-->
	<!--[if IE 6]>
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-6.css" />
   <![endif]-->
	<!--[if IE 7]> 
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-7.css" />
	<![endif]-->
	<!--[if IE 8]> 
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-8.css" />
	<![endif]-->
	
	<!-- Link to print styesheet -->
	<link href="../css/standard/print.css" rel="stylesheet" type="text/css" media="print" />	
	
	<%-- Colour scheme --%>
	<c:choose>
		<c:when test="${useBrands && userprofile.brand.id gt 0}">
			<c:set var="cssPath" value="../css/standard/${userprofile.brand.cssFile}" />
			<link href="<c:out value='${cssPath}' />" rel="stylesheet" type="text/css" media="all" /> 
		</c:when>
		<c:otherwise>
			<link href="../css/standard/colour-scheme.css" rel="stylesheet" type="text/css" media="all" /> 
		</c:otherwise>
	</c:choose>
	
	<!-- Stylesheet to sort out display issues of right to left text --> 
	<c:if test="${dirValue == 'rtl'}"><link href="../css/standard/rtl.css" rel="stylesheet" type="text/css" media="all" /></c:if>


	<script type="text/javascript" src="../js/lib/jquery-1.5.1.min.js"></script>
	<script type="text/javascript">$j = jQuery.noConflict();</script>
	<script type="text/javascript" src="../js/lib/jquery-ui-1.8.13.custom.min.js"></script>
	<link type="text/css" href="../css/jquery-ui/jquery-ui-1.8.13.custom.css" rel="Stylesheet" />
	<script type="text/javascript" src="../js/lib/prototype.js"></script>	
	<script type="text/javascript" src="../js/lib/scriptaculous.js"></script>
	<script type="text/javascript" src="../js/lib/swfobject.js"></script>
	<script type="text/javascript" src="../js/lib/date.js"></script>
	<script type="text/javascript" src="../js/bright-util.js"></script>
	

	<%-- global javascript stuff that needs jsp code --%>
   	<script type="text/javascript">

		// Global variable to store date format from app setting (used in datepicker javascript)
		var jqDateFormats = {
			'standard' : '<bean:write name='jqueryDateFormat' />',
			'yearMonth' : '<bean:write name='jqueryYearMonthFormat' />'
		}
		
		<%-- suppress javascript errors when in edit mode for the time being --%>
		<logic:equal name='userprofile' property='CMSEditMode' value="true">

			window.onerror = function(message, url, lineNumber) 
			{
				return true; // prevents browser error messages
			};

		</logic:equal>
		
		
		// when dom ready
		$j(function() {
			// BB moved popup links listener to bright_util.js

			<c:if test="${autoCompleteQuickSearchEnabled}">
				//initialise quick search autocomplete - BB-AutoComplete - hide this if quicksearch autocomplete disabled!
				if ($j('#searchKeywords').length>0) {
					//initialise jquery autocompleter on search field:
					initJQAutocompleter($j('#searchKeywords'), "");
				}	
			</c:if>
      	
		});

	</script>
		
	<!-- Meta tags -->
	<%@include file="../customisation/meta_tags.jsp"%>

