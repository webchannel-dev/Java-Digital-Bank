/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAnyElement;
/*    */ import javax.xml.bind.annotation.XmlMixed;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="LiteralType", propOrder={"content"})
/*    */ public class LiteralType
/*    */ {
/*    */ 
/*    */   @XmlMixed
/*    */   @XmlAnyElement(lax=true)
/*    */   protected List<Object> content;
/*    */ 
/*    */   public List<Object> getContent()
/*    */   {
/* 73 */     if (this.content == null) {
/* 74 */       this.content = new ArrayList();
/*    */     }
/* 76 */     return this.content;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.LiteralType
 * JD-Core Version:    0.6.0
 */