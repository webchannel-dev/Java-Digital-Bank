/*    */ package net.opengis.gml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRef;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="SolidArrayPropertyType", propOrder={"solid"})
/*    */ public class SolidArrayPropertyType
/*    */ {
/*    */ 
/*    */   @XmlElementRef(name="_Solid", namespace="http://www.opengis.net/gml", type=JAXBElement.class)
/*    */   protected List<JAXBElement<? extends AbstractSolidType>> solid;
/*    */ 
/*    */   public List<JAXBElement<? extends AbstractSolidType>> get_Solid()
/*    */   {
/* 74 */     if (this.solid == null) {
/* 75 */       this.solid = new ArrayList();
/*    */     }
/* 77 */     return this.solid;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.SolidArrayPropertyType
 * JD-Core Version:    0.6.0
 */