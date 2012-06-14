<%-- 
*
*	Sets a page attribute called 'thisUrl' with the URL of the current page.
*
*	d1	26-Oct-2005	 Martin Wilson	Created
*
*
--%>
		
		<%-- Get the URL of this page, so we can redirect here if user logs in --%>
		<%

			String sThisUrl = null;

			String sUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
			StringBuffer sbUrl = null;

			if ((sUrl == null) || (sUrl.equals("")))
			{
				//valid for up to tomcat 5.0
				sbUrl = request.getRequestURL();
			}
			else
			{
				if (!sUrl.startsWith("/action/") && !sUrl.startsWith("/jsp/"))
				{
					// if the name of the webapp will be included, remove it:
					sUrl = "/" + sUrl.split("/", 3)[2];
				}

				//use the attribute value (valid for tomcat 5.5 onwards)
				sbUrl = new StringBuffer(sUrl);
			}

			// Add the query string:
			if (request.getQueryString() != null)
			{
				sbUrl.append("?" + request.getQueryString());
			}

			request.setAttribute("thisUrl", sbUrl.toString());
			request.setAttribute("thisUrlForGet",  (sbUrl.toString()).replaceAll("&", "%26").replaceAll("\\?", "%3F"));
		%>