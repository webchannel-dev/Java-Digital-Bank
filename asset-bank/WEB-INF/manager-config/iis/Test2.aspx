<%@ Page Language="vb" %>
<html>
   <head>
      <script runat="server">
  Sub Page_Load()
            Message.Text = "User.Identity.IsAuthenticated?  " _
         & Context.User.Identity.IsAuthenticated & "<br/>"
      If Context.User.Identity.IsAuthenticated then
          Message.Text = Message.Text & "User.Identity.Name?  " _
         & Context.User.Identity.Name & "<br/>"
      end if
  End Sub
      </script>
   </head>
<body>
   <asp:label id="Message" runat="server"/>
</body>
</html>

