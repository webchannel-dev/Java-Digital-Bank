<%-- History:
	 d1		Matthew Woollard	18-Sep-2007		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<bright:applicationSetting id="imageManager" settingName="tinymce-image-manager-installed"/>

	<script type="text/javascript" src="../js/tiny_mce/tiny_mce.js"></script>
	<script type="text/javascript">
		//<!-- 
		tinyMCE.init({
			mode : "specific_textareas",
			theme : "advanced",
			<c:if test="${imageManager}">plugins: "imagemanager",</c:if>
			theme_advanced_buttons1 : "bold,italic,underline,forecolor,charmap,separator,justifyleft,justifycenter,justifyright, justifyfull,bullist,numlist,undo,redo,link,unlink",
			theme_advanced_buttons2 : "image,cleanup,removeformat,separator,formatselect,code",
			theme_advanced_blockformats : "p,h1,h2,h3,h4,h5,h6,blockquote,dt,dd,code,samp",
			theme_advanced_buttons3 : "",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_path_location : "bottom",
			content_css : "../css/standard/global.css",
			relative_urls : false,
			convert_urls : false,
			editor_selector : "editor",
			remove_linebreaks : true,
			apply_source_formatting : false
			
		}); 
		//-->
	</script>