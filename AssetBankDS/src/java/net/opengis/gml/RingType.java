/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="RingType", propOrder={"curveMember"})
/*    */ public class RingType extends AbstractRingType
/*    */ {
/*    */ 
/*    */   @XmlElement(required=true)
/*    */   protected List<CurvePropertyType> curveMember;
/*    */ 
/*    */   public List<CurvePropertyType> getCurveMember()
/*    */   {
/* 77 */     if (this.curveMember == null) {
/* 78 */       this.curveMember = new ArrayList();
/*    */     }
/* 80 */     return this.curveMember;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.RingType
 * JD-Core Version:    0.6.0
 */