/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="MultiLineStringType", propOrder={"lineStringMember"})
/*    */ public class MultiLineStringType extends AbstractGeometricAggregateType
/*    */ {
/*    */   protected List<LineStringPropertyType> lineStringMember;
/*    */ 
/*    */   public List<LineStringPropertyType> getLineStringMember()
/*    */   {
/* 72 */     if (this.lineStringMember == null) {
/* 73 */       this.lineStringMember = new ArrayList();
/*    */     }
/* 75 */     return this.lineStringMember;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.MultiLineStringType
 * JD-Core Version:    0.6.0
 */