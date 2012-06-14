<%

    Set HTMLConverter = server.CreateObject("HtmlShell.HtmlShell")
    
    HTMLConverter.HTMLConverter "http://www.google.com", "C:/out.pdf", ""
    
    Set HTMLConverter = Nothing

%>