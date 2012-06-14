<%

dim WSH,script
script = "C:\htmltools.exe http://www.verypdf.com C:\VeryPDF.pdf"
Set WSH=CreateObject("WScript.Shell")
WSH.Run(script)
set WSH =nothing

%>