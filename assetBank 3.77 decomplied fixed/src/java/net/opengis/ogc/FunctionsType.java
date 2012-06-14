/*    */ package net.opengis.ogc;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="FunctionsType", propOrder={"functionNames"})
/*    */ public class FunctionsType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="FunctionNames", required=true)
/*    */   protected FunctionNamesType functionNames;
/*    */ 
/*    */   public FunctionNamesType getFunctionNames()
/*    */   {
/* 54 */     return this.functionNames;
/*    */   }
/*    */ 
/*    */   public void setFunctionNames(FunctionNamesType value)
/*    */   {
/* 66 */     this.functionNames = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.FunctionsType
 * JD-Core Version:    0.6.0
 */