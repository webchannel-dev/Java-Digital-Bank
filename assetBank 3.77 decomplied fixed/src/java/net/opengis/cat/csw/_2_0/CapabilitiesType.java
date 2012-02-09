/*    */ package net.opengis.cat.csw._2_0;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import net.opengis.ogc.Filter_Capabilities;
/*    */ import net.opengis.ows.CapabilitiesBaseType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="CapabilitiesType", propOrder={"filterCapabilities"})
/*    */ @XmlRootElement(name="Capabilities")
/*    */ public class CapabilitiesType extends CapabilitiesBaseType
/*    */ {
/*    */ 
/*    */   @XmlElement(name="Filter_Capabilities", namespace="http://www.opengis.net/ogc", required=true)
/*    */   protected Filter_Capabilities filterCapabilities;
/*    */ 
/*    */   public Filter_Capabilities getFilter_Capabilities()
/*    */   {
/* 64 */     return this.filterCapabilities;
/*    */   }
/*    */ 
/*    */   public void setFilter_Capabilities(Filter_Capabilities value)
/*    */   {
/* 76 */     this.filterCapabilities = value;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\cat.zip
 * Qualified Name:     cat.csw._2_0.CapabilitiesType
 * JD-Core Version:    0.6.0
 */