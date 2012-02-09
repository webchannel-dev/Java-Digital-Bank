/*    */ package net.opengis.gml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="AngleChoiceType", propOrder={"angle", "dmsAngle"})
/*    */ public class AngleChoiceType
/*    */ {
/*    */   protected MeasureType angle;
/*    */   protected DMSAngleType dmsAngle;
/*    */ 
/*    */   public MeasureType getAngle()
/*    */   {
/* 57 */     return this.angle;
/*    */   }
/*    */ 
/*    */   public void setAngle(MeasureType value)
/*    */   {
/* 69 */     this.angle = value;
/*    */   }
/*    */ 
/*    */   public DMSAngleType getDmsAngle()
/*    */   {
/* 81 */     return this.dmsAngle;
/*    */   }
/*    */ 
/*    */   public void setDmsAngle(DMSAngleType value)
/*    */   {
/* 93 */     this.dmsAngle = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AngleChoiceType
 * JD-Core Version:    0.6.0
 */