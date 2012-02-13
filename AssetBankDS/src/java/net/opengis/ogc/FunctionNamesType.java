/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="FunctionNamesType", propOrder={"functionName"})
/*    */ public class FunctionNamesType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="FunctionName", required=true)
/*    */   protected List<FunctionNameType> functionName;
/*    */ 
/*    */   public List<FunctionNameType> getFunctionName()
/*    */   {
/* 70 */     if (this.functionName == null) {
/* 71 */       this.functionName = new ArrayList();
/*    */     }
/* 73 */     return this.functionName;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FunctionNamesType
 * JD-Core Version:    0.6.0
 */