/*    */ package com.bright.assetbank.plugin.iface;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class DefaultABModelMod
/*    */   implements ABModelMod
/*    */ {
/*    */   public List<Serializable> get(DBTransaction a_transaction, List<?> a_coreObjects)
/*    */     throws Bn2Exception
/*    */   {
/* 36 */     List extensionDatas = new ArrayList(a_coreObjects.size());
/* 37 */     for (Iterator i$ = a_coreObjects.iterator(); i$.hasNext(); ) { Object coreObject = i$.next();
/*    */ 
/* 39 */       extensionDatas.add(get(a_transaction, coreObject));
/*    */     }
/* 41 */     return extensionDatas;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.iface.DefaultABModelMod
 * JD-Core Version:    0.6.0
 */