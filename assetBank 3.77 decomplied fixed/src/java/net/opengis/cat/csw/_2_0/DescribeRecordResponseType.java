/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="DescribeRecordResponseType", propOrder={"schemaComponent"})
/*    */ @XmlRootElement(name="DescribeRecordResponse")
/*    */ public class DescribeRecordResponseType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="SchemaComponent")
/*    */   protected List<SchemaComponentType> schemaComponent;
/*    */ 
/*    */   public List<SchemaComponentType> getSchemaComponent()
/*    */   {
/* 75 */     if (this.schemaComponent == null) {
/* 76 */       this.schemaComponent = new ArrayList();
/*    */     }
/* 78 */     return this.schemaComponent;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.DescribeRecordResponseType
 * JD-Core Version:    0.6.0
 */