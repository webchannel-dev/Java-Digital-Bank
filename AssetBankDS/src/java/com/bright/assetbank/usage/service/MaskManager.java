/*     */ package com.bright.assetbank.usage.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.bean.ImageFileInfo;
/*     */ import com.bright.assetbank.application.exception.CannotDeleteReferredToRowException;
/*     */ import com.bright.assetbank.application.util.ABImageMagick;
/*     */ import com.bright.assetbank.database.AssetBankSql;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.bean.Mask;
/*     */ import com.bright.assetbank.usage.util.MaskDBUtil;
/*     */ import com.bright.assetbank.usage.util.MaskUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.SQLStatementException;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MaskManager extends Bn2Manager
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*  57 */   private UsageManager m_usageManager = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     super.startup();
/*     */ 
/*  67 */     File maskDir = MaskUtil.getMaskDirPathAsFile();
/*  68 */     if (!maskDir.exists())
/*     */     {
/*  70 */       if (!maskDir.mkdirs())
/*     */       {
/*  72 */         this.m_logger.error("Could not create mask directory " + maskDir);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addMask(String a_sName, String a_sImageFilename, InputStream a_isImage)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     File maskDir = MaskUtil.getMaskDirPathAsFile();
/*  88 */     String sImageExtension = FilenameUtils.getExtension(a_sImageFilename);
/*     */ 
/*  90 */     boolean bRollback = false;
/*     */ 
/*  92 */     Collection<File> createdFiles = new ArrayList();
/*     */ 
/*  97 */     DBTransaction transaction = getTransactionManager().getNewTransaction();
/*     */     try
/*     */     {
/* 101 */       File tempImageFile = File.createTempFile("tmp", "." + sImageExtension, maskDir);
/* 102 */       createdFiles.add(tempImageFile);
/*     */ 
/* 104 */       IOException fileCloseException = null;
/* 105 */       OutputStream os = new BufferedOutputStream(new FileOutputStream(tempImageFile));
/*     */       try
/*     */       {
/* 108 */         IOUtils.copy(a_isImage, os);
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 114 */           os.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 118 */           fileCloseException = e;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 123 */       if (fileCloseException != null)
/*     */       {
/* 125 */         throw new Bn2Exception("Error closing " + tempImageFile, fileCloseException);
/*     */       }
/*     */ 
/* 129 */       ImageFileInfo imageInfo = ABImageMagick.getInfo(tempImageFile.getPath());
/*     */ 
/* 132 */       Mask m = new Mask();
/* 133 */       m.setWidth(imageInfo.getWidth());
/* 134 */       m.setHeight(imageInfo.getHeight());
/* 135 */       m.setName(a_sName);
/*     */ 
/* 137 */       insertMask(transaction, m);
/*     */ 
/* 141 */       String sFilename = String.valueOf(m.getId());
/* 142 */       if (StringUtils.isNotEmpty(sImageExtension))
/*     */       {
/* 144 */         sFilename = sFilename + "." + sImageExtension;
/*     */       }
/* 146 */       m.setFilename(sFilename);
/* 147 */       updateMask(transaction, m);
/*     */ 
/* 150 */       File imageFile = new File(maskDir, sFilename);
/* 151 */       if (!tempImageFile.renameTo(imageFile))
/*     */       {
/* 153 */         throw new Bn2Exception("Couldn't rename " + tempImageFile + " to " + imageFile);
/*     */       }
/*     */ 
/* 157 */       createdFiles.remove(tempImageFile);
/* 158 */       createdFiles.add(imageFile);
/*     */ 
/* 161 */       String sRgbColorProfile = this.m_usageManager.getColorSpace(transaction, 1).getFileLocation();
/* 162 */       String sCmykColorProfile = this.m_usageManager.getColorSpace(transaction, 2).getFileLocation();
/*     */ 
/* 165 */       ABImageMagick.resizeToRGB(imageFile.getPath(), MaskUtil.getThumbnailPath(m.getId()), 40, 40, sRgbColorProfile, sCmykColorProfile);
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/* 173 */       bRollback = true;
/* 174 */       throw e;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 178 */       bRollback = true;
/* 179 */       throw new Bn2Exception("Error adding mask", e);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 183 */       bRollback = true;
/* 184 */       throw e;
/*     */     }
/*     */     finally
/*     */     {
/* 189 */       if (bRollback)
/*     */       {
/*     */         try
/*     */         {
/* 193 */           transaction.rollback();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 197 */           this.m_logger.error("Error rolling back database transaction", e);
/*     */         }
/*     */ 
/* 200 */         for (File file : createdFiles)
/*     */         {
/*     */           try
/*     */           {
/* 204 */             if (!file.delete())
/*     */             {
/* 206 */               this.m_logger.error("Couldn't roll back creation of file " + file);
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 211 */             this.m_logger.error("Couldn't roll back creation of file " + file, e);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 220 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 224 */         this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteMask(DBTransaction a_transaction, long a_lMaskId)
/*     */     throws Bn2Exception
/*     */   {
/* 234 */     DBTransaction transaction = a_transaction;
/* 235 */     if (transaction == null)
/*     */     {
/* 237 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 242 */       Mask mask = getMaskById(transaction, a_lMaskId);
/*     */ 
/* 244 */       if (maskIsReferredTo(transaction, a_lMaskId))
/*     */       {
/* 246 */         throw new CannotDeleteReferredToRowException("Mask", a_lMaskId);
/*     */       }
/*     */ 
/* 249 */       deleteMaskFromDB(transaction, a_lMaskId);
/*     */ 
/* 251 */       File maskDir = MaskUtil.getMaskDirPathAsFile();
/* 252 */       File imageFile = new File(maskDir, mask.getFilename());
/* 253 */       File thumbnailFile = MaskUtil.getThumbnailPathAsFile(a_lMaskId);
/*     */ 
/* 255 */       tryToDelete(imageFile);
/* 256 */       tryToDelete(thumbnailFile);
/*     */     }
/*     */     finally
/*     */     {
/* 260 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 264 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 268 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void tryToDelete(File a_file)
/*     */   {
/* 276 */     if (!a_file.delete())
/*     */     {
/* 278 */       this.m_logger.error("Failed to delete " + a_file);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<Mask> getMasks(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 287 */     if (!MaskUtil.masksAllowedBySettings())
/*     */     {
/* 292 */       return Collections.emptyList();
/*     */     }
/*     */ 
/* 295 */     String sSQL = "SELECT m.id AS maskId, m.name, m.filename, m.width, m.height FROM Mask m ORDER BY m.name";
/*     */ 
/* 300 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 302 */     if (transaction == null)
/*     */     {
/* 304 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 309 */       Connection con = transaction.getConnection();
/* 310 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 311 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 313 */       List masks = new ArrayList();
/* 314 */       while (rs.next())
/*     */       {
/* 316 */         masks.add(MaskDBUtil.createMaskFromRS(rs));
/*     */       }
/*     */ 
/* 319 */       psql.close();
/*     */ 
/* 321 */       //localList1 = masks;
                return masks;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       //List localList1;
/* 325 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 329 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 333 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 337 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean haveMasks(DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 348 */     if (!MaskUtil.masksAllowedBySettings())
/*     */     {
/* 353 */       return false;
/*     */     }
/*     */ 
/* 356 */     String sSQL = "SELECT 1 FROM Mask";
/*     */ 
/* 358 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 360 */     if (transaction == null)
/*     */     {
/* 362 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 367 */       Connection con = transaction.getConnection();
/* 368 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 369 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 371 */       boolean haveMask = rs.next();
/*     */ 
/* 373 */       psql.close();
                return haveMask;
/*     */       //boolean bool1;
/* 375 */       //bool1 = haveMask;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       boolean bool1;
/* 379 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 383 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 387 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 391 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Mask getMaskById(DBTransaction a_transaction, long a_lMaskId)
/*     */     throws Bn2Exception
/*     */   {
/* 402 */     String sSQL = "SELECT m.id AS maskId, m.name, m.filename, m.width, m.height FROM Mask m WHERE m.Id=?";
/*     */ 
/* 407 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 409 */     if (transaction == null)
/*     */     {
/* 411 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 416 */       Connection con = transaction.getConnection();
/* 417 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 418 */       psql.setLong(1, a_lMaskId);
/*     */ 
/* 420 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 422 */       Mask mask = null;
/* 423 */       if (rs.next())
/*     */       {
/* 425 */         mask = MaskDBUtil.createMaskFromRS(rs);
/*     */       }
/*     */ 
/* 428 */       psql.close();
/*     */       //Mask localMask1;
/* 430 */       //localMask1 = mask;
                return mask;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       
/* 434 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 438 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 442 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 446 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insertMask(DBTransaction a_transaction, Mask a_mask)
/*     */     throws Bn2Exception
/*     */   {
/* 458 */     StringBuilder sbSQL = new StringBuilder();
/*     */ 
/* 460 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 462 */     if (transaction == null)
/*     */     {
/* 464 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 469 */       Connection con = transaction.getConnection();
/* 470 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*     */ 
/* 472 */       long lNewId = 0L;
/* 473 */       sbSQL.append("INSERT INTO Mask (");
/* 474 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 476 */         lNewId = sqlGenerator.getUniqueId(con, "MaskSequence");
/* 477 */         sbSQL.append("Id, ");
/*     */       }
/* 479 */       sbSQL.append("Name, Filename, Width, Height) ");
/*     */ 
/* 481 */       sbSQL.append("VALUES (");
/* 482 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 484 */         sbSQL.append("?, ");
/*     */       }
/* 486 */       sbSQL.append("?, ?, ?, ?)");
/*     */ 
/* 488 */       PreparedStatement psql = con.prepareStatement(sbSQL.toString());
/* 489 */       int iParam = 1;
/* 490 */       if (!sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 492 */         psql.setLong(iParam++, lNewId);
/*     */       }
/*     */ 
/* 495 */       iParam = MaskDBUtil.prepareMaskStatement(a_mask, psql, iParam);
/*     */ 
/* 497 */       psql.executeUpdate();
/* 498 */       psql.close();
/* 499 */       if (sqlGenerator.usesAutoincrementFields())
/*     */       {
/* 501 */         lNewId = sqlGenerator.getUniqueId(con, "MaskSequence");
/*     */       }
/*     */ 
/* 504 */       a_mask.setId(lNewId);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 508 */       throw new SQLStatementException(sbSQL.toString(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 512 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 516 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 520 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateMask(DBTransaction a_transaction, Mask a_mask)
/*     */     throws Bn2Exception
/*     */   {
/* 529 */     String sSQL = null;
/*     */ 
/* 531 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 533 */     if (transaction == null)
/*     */     {
/* 535 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 540 */       Connection con = transaction.getConnection();
/*     */ 
/* 542 */       sSQL = "UPDATE Mask SET Name=?,Filename=?,Width=?,Height=? WHERE Id=?";
/*     */ 
/* 549 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 550 */       int iParam = 1;
/* 551 */       iParam = MaskDBUtil.prepareMaskStatement(a_mask, psql, iParam);
/*     */ 
/* 553 */       psql.setLong(iParam++, a_mask.getId());
/*     */ 
/* 555 */       psql.executeUpdate();
/* 556 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 560 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 564 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 568 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 572 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean maskIsReferredTo(DBTransaction a_transaction, long a_lMaskId) throws Bn2Exception
/*     */   {
/* 583 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */ 
/* 585 */     String sSQL = sqlGenerator.getAsSelectForUpdate("SELECT 1 FROM UsageTypeFormat WHERE PresetMaskId=?");
/*     */     boolean isReferredTo;
/*     */     try {
/* 590 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 592 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 593 */       psql.setLong(1, a_lMaskId);
/*     */ 
/* 595 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 598 */       isReferredTo = rs.next();
/*     */ 
/* 600 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 604 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */ 
/* 607 */     return isReferredTo;
/*     */   }
/*     */ 
/*     */   private void deleteMaskFromDB(DBTransaction a_transaction, long a_lMaskId)
/*     */     throws Bn2Exception
/*     */   {
/* 613 */     String sSQL = "DELETE FROM Mask WHERE Id=?";
/*     */ 
/* 615 */     DBTransaction transaction = a_transaction;
/*     */ 
/* 617 */     if (transaction == null)
/*     */     {
/* 619 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 624 */       Connection con = transaction.getConnection();
/*     */ 
/* 626 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 627 */       psql.setLong(1, a_lMaskId);
/*     */ 
/* 629 */       psql.executeUpdate();
/* 630 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 634 */       throw new SQLStatementException(sSQL, e);
/*     */     }
/*     */     finally
/*     */     {
/* 638 */       if ((a_transaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 642 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 646 */           this.m_logger.error("SQLException whilst trying to commit transaction", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 657 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 662 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 667 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.service.MaskManager
 * JD-Core Version:    0.6.0
 */