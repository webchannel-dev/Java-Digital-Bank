/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ArrayType", propOrder={"members"})
/*    */ public class ArrayType extends AbstractGMLType
/*    */ {
/*    */   protected ArrayAssociationType members;
/*    */ 
/*    */   public ArrayAssociationType getMembers()
/*    */   {
/* 56 */     return this.members;
/*    */   }
/*    */ 
/*    */   public void setMembers(ArrayAssociationType value)
/*    */   {
/* 68 */     this.members = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ArrayType
 * JD-Core Version:    0.6.0
 */