/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.apache.axis.components.encoding.AbstractXMLEncoder;
/*    */ import org.apache.axis.i18n.Messages;
/*    */ 
/*    */ public class CompactUTF8XmlEncoder extends AbstractXMLEncoder
/*    */ {
/*    */   public String getEncoding()
/*    */   {
/* 16 */     return "UTF-8";
/*    */   }
/*    */ 
/*    */   public void writeEncoded(Writer writer, String xmlString) throws IOException
/*    */   {
/* 21 */     if (xmlString == null)
/*    */     {
/* 23 */       return;
/*    */     }
/*    */ 
/* 26 */     int length = xmlString.length();
/*    */ 
/* 28 */     for (int i = 0; i < length; i++)
/*    */     {
/* 30 */       char character = xmlString.charAt(i);
/* 31 */       switch (character)
/*    */       {
/*    */       case '&':
/* 35 */         writer.write("&amp;");
/* 36 */         break;
/*    */       case '"':
/* 38 */         writer.write("&quot;");
/* 39 */         break;
/*    */       case '<':
/* 41 */         writer.write("&lt;");
/* 42 */         break;
/*    */       case '>':
/* 44 */         writer.write("&gt;");
/* 45 */         break;
/*    */       case '\n':
/* 47 */         writer.write("\n");
/* 48 */         break;
/*    */       case '\r':
/* 50 */         writer.write("\r");
/* 51 */         break;
/*    */       case '\t':
/* 53 */         writer.write("\t");
/* 54 */         break;
/*    */       default:
/* 56 */         if (character < ' ')
/*    */         {
/* 58 */           throw new IllegalArgumentException(Messages.getMessage("invalidXmlCharacter00", Integer.toHexString(character), xmlString.substring(0, i)));
/*    */         }
/*    */ 
/* 63 */         writer.write(character);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.CompactUTF8XmlEncoder
 * JD-Core Version:    0.6.0
 */