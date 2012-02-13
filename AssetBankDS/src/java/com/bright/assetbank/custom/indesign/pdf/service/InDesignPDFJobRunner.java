/*     */ package com.bright.assetbank.custom.indesign.pdf.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetService;
/*     */ import com.bright.assetbank.custom.indesign.pdf.bean.InDesignPDFJob;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionCallback;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.util.ClassUtil;
/*     */ import com.bright.framework.util.LogUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignPDFJobRunner
/*     */ {
/*  45 */   private static final String c_ksClassName = ClassUtil.currentClassName();
/*     */   private Log m_logger;
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetService m_inDesignAssetService;
/*     */ 
/*     */   @Resource
/*     */   private InDesignPDFGenerator m_pdfGenerator;
/*     */ 
/*     */   @Resource
/*     */   private DBTransactionManager m_transactionManager;
/*     */   ExecutorService m_executor;
/*     */ 
/*     */   public InDesignPDFJobRunner()
/*     */   {
/*  46 */     this.m_logger = LogUtil.getLog(c_ksClassName);
/*     */ 
/*  56 */     this.m_executor = Executors.newSingleThreadExecutor(new ThreadFactory()
/*     */     {
/*     */       public Thread newThread(Runnable r)
/*     */       {
/*  60 */         return new Thread(r, "InDesignPDFJobRunner");
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void runPendingJobs() {
/*  67 */     this.m_executor.execute(new PendingJobsRunner());
/*     */   }
/*     */ 
/*     */   private InDesignPDFJob claimNextPendingJob()
/*     */     throws Bn2Exception
/*     */   {
/* 126 */     return (InDesignPDFJob)getTransactionManager().executeInNewTransaction(new DBTransactionCallback()
/*     */     {
/*     */       public InDesignPDFJob doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */       {
/* 130 */         String sSQL = "SELECT Id, AssetId, JobStatusId FROM InDesignPDFJob WHERE JobStatusId = ? ORDER BY Id";
/*     */ 
/* 135 */         Connection con = a_transaction.getConnection();
/* 136 */         PreparedStatement psql = con.prepareStatement(sSQL);
/* 137 */         psql.setInt(1, 1);
/* 138 */         ResultSet rs = psql.executeQuery();
/*     */         InDesignPDFJob nextJob;
/* 141 */         if (rs.next())
/*     */         {
/* 143 */           nextJob = new InDesignPDFJob();
/* 144 */           nextJob.setId(rs.getLong("Id"));
/* 145 */           nextJob.setAssetId(rs.getLong("AssetId"));
/* 146 */           nextJob.setJobStatusId(rs.getInt("JobStatusId"));
/*     */         }
/*     */         else
/*     */         {
/* 150 */           nextJob = null;
/*     */         }
/* 152 */         psql.close();
/*     */ 
/* 154 */         if (nextJob != null)
/*     */         {
/* 156 */           sSQL = "UPDATE InDesignPDFJob SET JobStatusId = ? WHERE Id = ?";
/*     */ 
/* 159 */           psql = con.prepareStatement(sSQL);
/* 160 */           psql.setInt(1, 2);
/* 161 */           psql.setLong(2, nextJob.getId());
/* 162 */           psql.executeUpdate();
/* 163 */           psql.close();
/*     */         }
/*     */ 
/* 166 */         return nextJob;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void deleteJob(DBTransaction a_transaction, long a_jobId) throws SQLException, Bn2Exception
/*     */   {
/* 174 */     String sSQL = "DELETE FROM InDesignPDFJob WHERE Id = ?";
/*     */ 
/* 177 */     Connection con = a_transaction.getConnection();
/* 178 */     PreparedStatement psql = con.prepareStatement(sSQL);
/* 179 */     psql.setLong(1, a_jobId);
/* 180 */     psql.executeUpdate();
/* 181 */     psql.close();
/*     */   }
/*     */ 
/*     */   private InDesignAssetService getInDesignAssetService()
/*     */   {
/* 187 */     return this.m_inDesignAssetService;
/*     */   }
/*     */ 
/*     */   private InDesignPDFGenerator getPDFGenerator()
/*     */   {
/* 192 */     return this.m_pdfGenerator;
/*     */   }
/*     */ 
/*     */   private DBTransactionManager getTransactionManager()
/*     */   {
/* 197 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   private class PendingJobsRunner
/*     */     implements Runnable
/*     */   {
/*     */     private PendingJobsRunner()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       try
/*     */       {
/*     */         while (true)
/*     */         {
/*  81 */           final InDesignPDFJob job = InDesignPDFJobRunner.this.claimNextPendingJob();
/*  82 */           if (job == null)
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/*  88 */           boolean success = false;
/*     */           try
/*     */           {
/*  91 */             InDesignPDFJobRunner.this.getPDFGenerator().generatePDFForAsset(job.getAssetId());
/*  92 */             success = true;
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*  96 */             InDesignPDFJobRunner.this.m_logger.error("Exception whilst running PDF generation job " + job.getAssetId() + " for asset " + job.getAssetId(), e);
/*     */           }
/*     */ 
/* 100 */           final int newStatus = success ? 4 : 3;
/*     */ 
/* 103 */           InDesignPDFJobRunner.this.getTransactionManager().executeInNewTransaction(new DBTransactionCallback()
/*     */           {
/*     */             public Object doInTransaction(DBTransaction a_transaction) throws SQLException, Bn2Exception
/*     */             {
/* 107 */               InDesignPDFJobRunner.this.getInDesignAssetService().updatePDFStatus(a_transaction, job.getAssetId(), newStatus);
/* 108 */               InDesignPDFJobRunner.this.deleteJob(a_transaction, job.getId());
/*     */ 
/* 110 */               return null;
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 117 */         InDesignPDFJobRunner.this.m_logger.error("Exception whilst running pending jobs", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.service.InDesignPDFJobRunner
 * JD-Core Version:    0.6.0
 */