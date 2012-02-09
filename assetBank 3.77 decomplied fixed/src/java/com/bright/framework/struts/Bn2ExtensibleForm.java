/*    */ package com.bright.framework.struts;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Bn2ExtensibleForm extends Bn2Form
/*    */ {
/* 32 */   private Map<String, Object> m_ext = new HashMap();
/*    */ 
/*    */   public void setExt(String key, Object extProp)
/*    */   {
/* 40 */     this.m_ext.put(key, extProp);
/*    */   }
/*    */ 
/*    */   public Object getExt(String key)
/*    */   {
/* 49 */     return this.m_ext.get(key);
/*    */   }
/*    */ 
/*    */   public int getExtInt(String key)
/*    */   {
/* 58 */     Object extProp = getExt(key);
/* 59 */     if ((extProp == null) || (extProp.toString().equals("")))
/*    */     {
/* 61 */       return -1;
/*    */     }
/*    */ 
/* 65 */     return Integer.valueOf(extProp.toString()).intValue();
/*    */   }
/*    */ 
/*    */   public long getExtLong(String key)
/*    */   {
/* 75 */     Object extProp = getExt(key);
/* 76 */     if ((extProp == null) || (extProp.toString().equals("")))
/*    */     {
/* 78 */       return -1L;
/*    */     }
/*    */ 
/* 82 */     return Long.valueOf(extProp.toString()).longValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.struts.Bn2ExtensibleForm
 * JD-Core Version:    0.6.0
 */