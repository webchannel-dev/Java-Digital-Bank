/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ListOfValuesType", propOrder={"value"})
/*    */ public class ListOfValuesType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Value", required=true)
/*    */   protected List<Object> value;
/*    */ 
/*    */   public List<Object> getValue()
/*    */   {
/* 70 */     if (this.value == null) {
/* 71 */       this.value = new ArrayList();
/*    */     }
/* 73 */     return this.value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.ListOfValuesType
 * JD-Core Version:    0.6.0
 */