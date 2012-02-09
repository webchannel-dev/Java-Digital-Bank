/*    */ package net.opengis.ows;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElementRefs;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="", propOrder={"getOrPost"})
/*    */ @XmlRootElement(name="HTTP")
/*    */ public class HTTP
/*    */ {
/*    */ 
/*    */   @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="Post", namespace="http://www.opengis.net/ows", type=JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="Get", namespace="http://www.opengis.net/ows", type=JAXBElement.class)})
/*    */   protected List<JAXBElement<RequestMethodType>> getOrPost;
/*    */ 
/*    */   public List<JAXBElement<RequestMethodType>> getGetOrPost()
/*    */   {
/* 79 */     if (this.getOrPost == null) {
/* 80 */       this.getOrPost = new ArrayList();
/*    */     }
/* 82 */     return this.getOrPost;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ows.zip
 * Qualified Name:     ows.HTTP
 * JD-Core Version:    0.6.0
 */