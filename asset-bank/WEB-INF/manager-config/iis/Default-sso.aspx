<%@ Page Language="vb" %>
<%@ Import Namespace="System.Security.Cryptography" %>

<html>
   <head>
      <script runat="server">
  Sub Page_Load()
  
		' Check if user is authenticated  	
   	If Context.User.Identity.IsAuthenticated Then
   	' If True Then
        
        	' Get the authenticated username
        	Dim sUsername As String
  	
        	sUsername = Context.User.Identity.Name
        	' sUsername = "Domain\Admin"
        	
        	' Strip off the domain if there is one
        	Dim iPos As Integer
        	iPos = sUsername.IndexOf("\")
        	If iPos >= 0 Then
        		sUsername = sUsername.Substring(iPos + 1)
        	End If
        	
        	' Now create an MD5 hash of the username + secret
        	Dim sKey As String

			Dim sInput As String
			sInput = sUsername & "rty67pk$"
		
			'The array of bytes that will contain the encrypted value 
			Dim bytes as Byte()   
	
			'The encoder class used to convert strPlainText to an array of bytes
			Dim encoder as New UTF8Encoding()
	
			'Create an instance of the MD5CryptoServiceProvider class
			Dim md5Hasher as New MD5CryptoServiceProvider()
	
			'Call ComputeHash, passing in the plain-text string as an array of bytes
			'The return value is the encrypted value, as an array of bytes
			bytes = md5Hasher.ComputeHash(encoder.GetBytes(sInput))
	        	
	     	' Convert to a hex string
			Dim hexString as New StringBuilder(bytes.Length)
		
			Dim i As Integer
			For i=0 To bytes.Length - 1
				Dim byteAsHexString As String
				byteAsHexString = bytes(i).ToString("X2") 
				hexString.Append(byteAsHexString)
			Next
			sKey = hexString.ToString()
	        	
	        	' Create a cookie for username + key
	        	Dim sValue = sUsername & "|" & sKey
	        	Dim cookie As New HttpCookie("AssetBankToken", sValue)
			Response.Cookies.Add( cookie )
			
			' Test:
			Message.Text = "Created cookie:" & sValue

			' NOTE: We could examine forwardurl and passthru parameters here
			' But for now we'll just go to homepage
			Dim sLoginUrl As String = "/asset-bank/action/viewHome"
	
				Response.Redirect(sLoginUrl)
	         
		Else
        	' Redirect to failure page
        	Response.Redirect("/asset-bank/action/viewHome")    
     	End If
            
  End Sub
      </script>
   </head>
<body>
	<p>Asset Bank Forwarding Page</p>
	<p><asp:label id="Message" runat="server"/></p>
	
</body>
</html>

