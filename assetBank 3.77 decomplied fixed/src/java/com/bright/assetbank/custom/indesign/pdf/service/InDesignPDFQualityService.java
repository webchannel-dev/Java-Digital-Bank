/*    */ package com.bright.assetbank.custom.indesign.pdf.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.custom.indesign.pdf.bean.InDesignPDFQuality;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.database.exception.SQLStatementException;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignPDFQualityService
/*    */ {
/*    */   public List<InDesignPDFQuality> getAll(DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 38 */     String sSQL = "SELECT idpq.Id AS idpqId, idpq.Name FROM InDesignPDFQuality idpq ORDER BY idpq.Id";
/*    */     List pdfQualities;
/*    */     try
/*    */     {
/* 45 */       Connection con = a_transaction.getConnection();
/* 46 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 47 */       ResultSet rs = psql.executeQuery();
/*    */ 
/* 49 */       pdfQualities = new ArrayList();
/* 50 */       while (rs.next())
/*    */       {
/* 52 */         pdfQualities.add(InDesignPDFQualityDBUtil.createInDesignPDFQualityFromRS(rs));
/*    */       }
/*    */ 
/* 55 */       psql.close();
/*    */     }
/*    */     catch (SQLException e)
/*    */     {
/* 59 */       throw new SQLStatementException(sSQL, e);
/*    */     }
/*    */ 
/* 62 */     return pdfQualities;
/*    */   }
/*    */ 
/*    */   public InDesignPDFQuality get(DBTransaction a_transaction, int a_pdfQualityId)
/*    */     throws Bn2Exception
/*    */   {
/* 69 */     String sSQL = "SELECT idpq.Id AS idpqId, idpq.Name FROM InDesignPDFQuality idpq WHERE Id=?";
/*    */     InDesignPDFQuality pdfQuality;
/*    */     try
/*    */     {
/* 76 */       Connection con = a_transaction.getConnection();
/* 77 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 78 */       psql.setInt(1, a_pdfQualityId);
/* 79 */       ResultSet rs = psql.executeQuery();
/*    */      // lity pdfQuality;
/* 81 */       if (rs.next())
/*    */       {
/* 83 */         pdfQuality = InDesignPDFQualityDBUtil.createInDesignPDFQualityFromRS(rs);
/*    */       }
/*    */       else
/*    */       {
/* 87 */         pdfQuality = null;
/*    */       }
/*    */ 
/* 90 */       psql.close();
/*    */     }
/*    */     catch (SQLException e)
/*    */     {
/* 94 */       throw new SQLStatementException(sSQL, e);
/*    */     }
/*    */ 
/* 97 */     return pdfQuality;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFQualityService
 * JD-Core Version:    0.6.0
 */