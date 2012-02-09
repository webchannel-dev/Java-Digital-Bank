/*    */ package com.bright.assetbank.custom.indesign.pdf.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.database.bean.TransactionListener;
/*    */ import com.bright.framework.database.exception.SQLStatementException;
/*    */ import com.bright.framework.util.ClassUtil;
/*    */ import com.bright.framework.util.LogUtil;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import javax.annotation.Resource;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignPDFJobService
/*    */ {
/* 38 */   private Log m_logger = LogUtil.getLog(ClassUtil.currentClassName());
/*    */ 
/*    */   @Resource
/*    */   private InDesignPDFJobRunner m_jobRunner;
/*    */ 
/*    */   public void enqueue(DBTransaction a_transaction, long a_assetId)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     this.m_logger.info("Enqueuing PDF regeneration job for asset " + a_assetId);
/*    */ 
/* 50 */     String sSQL = "INSERT INTO InDesignPDFJob (AssetId, JobStatusId) VALUES (?,?)";
/*    */     try
/*    */     {
/* 54 */       Connection con = a_transaction.getConnection();
/* 55 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 56 */       psql.setLong(1, a_assetId);
/* 57 */       psql.setInt(2, 1);
/* 58 */       psql.executeUpdate();
/* 59 */       psql.close();
/*    */     }
/*    */     catch (SQLException e)
/*    */     {
/* 63 */       throw new SQLStatementException(sSQL, e);
/*    */     }
/*    */ 
/* 69 */     a_transaction.addListener(new TransactionListener()
/*    */     {
/*    */       public void doAfterCommit()
/*    */       {
/* 74 */         InDesignPDFJobService.this.m_jobRunner.runPendingJobs();
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFJobService
 * JD-Core Version:    0.6.0
 */