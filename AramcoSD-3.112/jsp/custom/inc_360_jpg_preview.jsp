<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<c:set var="fileUrl" scope="request"><%= ((String)request.getAttribute("fileUrl")).replace(".jpg","") %></c:set>
<c:set var="fileUrl" scope="request"><%= ((String)request.getAttribute("fileUrl")).replaceFirst("\\.\\./","") %></c:set>
<c:set var="fileUrl" scope="request"><%= ((String)request.getAttribute("fileUrl")).replace("?file=","/") %></c:set>

<script type="text/javascript" src="../flash/custom/swfobject.js"></script>		

<div id="flashcontent" class="panoPreview" style="
	<c:if test="${assetForm.asset.format.previewWidth>0}">width:<c:out value="${assetForm.asset.format.previewWidth}"/>px; </c:if>
	<c:if test="${assetForm.asset.format.previewHeight>0}">height:<c:out value="${assetForm.asset.format.previewHeight}"/>px; </c:if>
">This content requires <a href="http://www.adobe.com/go/getflashplayer/">Adobe Flash Player</a> and a browser with JavaScript enabled.<br />
Built on <a href="http://flashpanoramas.com/player/">Flash Panorama Player</a>.</div> 
    <script type="text/javascript">
	  var so = new SWFObject("../flash/custom/pano/Main.swf", "cylinder", "100%", "100%", "9", "#282828"); 
	  so.addVariable("xml_text", "<?xml version = '1.0'?><panorama><parameters> panoName=<c:out value="${fileUrl}"/>; layer_1=../flash/custom/pano/files/fps.swf; layer_6=../flash/custom/pano/files/cylConverter.swf; panoType=cylinder; panHome=80; layer_5 = ../flash/custom/pano/files/openFullscreen.swf; zoomhome=0.0; segments=40; layer_2=../flash/custom/pano/files/hotspots.swf; layer_10=../flash/custom/pano/files/autorotator.swf;</parameters><hotspots><global><spot id='8' pan='150' tilt='-70' url='../flash/custom/pano/files/logo/tripod.png' smoothing='1' scaleable='5' onClick=' openUrl(http://www.photint.com/)'/></global></hotspots></panorama>");
	  so.addVariable("redirect", window.location);
	  so.addParam("allowFullScreen","true");
	  so.addParam("allowScriptAccess","sameDomain");
	  so.write("flashcontent");
    </script>