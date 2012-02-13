/*    */ package com.bright.assetbank.custom.indesign.application.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.service.CategoryDiskUsageExtension;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.database.exception.SQLStatementException;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignCategoryDiskUsageExtension
/*    */   implements CategoryDiskUsageExtension
/*    */ {
/*    */   public long getAdditionalDiskUsageForCategoryAssets(DBTransaction a_transaction, long[] a_categoryIds, long a_lCategoryTypeId)
/*    */     throws Bn2Exception
/*    */   {
/* 39 */     Connection con = a_transaction.getConnection();
/* 40 */     String sSQL = "SELECT SUM(inddFileSizeInBytes) diskUsageInBytes FROM (SELECT indd.FileSizeInBytes inddFileSizeInBytes FROM InDesignDocument indd, InDesignAsset inda, CM_ItemInCategory iic, CM_Category c WHERE indd.Id = inda.InDesignDocumentId AND inda.AssetId=iic.ItemId AND iic.CategoryId IN (" + StringUtil.convertNumbersToString(a_categoryIds, ",") + ") " + "AND c.Id=iic.CategoryId " + "AND c.CategoryTypeId=? " + "GROUP BY indd.FileSizeInBytes) DummyAlias";
/*    */     long result;
/*    */     try
/*    */     {
/* 53 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 54 */       psql.setLong(1, a_lCategoryTypeId);
/* 55 */       ResultSet rs = psql.executeQuery();
/*    */       //long result;
/* 57 */       if (rs.next())
/*    */       {
/* 59 */         result = rs.getLong("diskUsageInBytes");
/*    */       }
/*    */       else
/*    */       {
/* 63 */         result = 0L;
/*    */       }
/*    */ 
/* 66 */       psql.close();
/*    */     }
/*    */     catch (SQLException e)
/*    */     {
/* 70 */       throw new SQLStatementException(sSQL, e);
/*    */     }
/*    */ 
/* 73 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.application.abplugin.InDesignCategoryDiskUsageExtension
 * JD-Core Version:    0.6.0
 */