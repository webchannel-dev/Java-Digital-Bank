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
/*    */ @XmlType(name="PropertyNameType")
/*    */ public class PropertyNameType extends ExpressionType
/*    */ {
/*    */ 
/*    */   @XmlMixed
/*    */   @XmlAnyElement(lax=true)
/*    */   protected List<Object> content;
/*    */ 
/*    */   public List<Object> getContent()
/*    */   {
/* 70 */     if (this.content == null) {
/* 71 */       this.content = new ArrayList();
/*    */     }
/* 73 */     return this.content;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.PropertyNameType
 * JD-Core Version:    0.6.0
 */