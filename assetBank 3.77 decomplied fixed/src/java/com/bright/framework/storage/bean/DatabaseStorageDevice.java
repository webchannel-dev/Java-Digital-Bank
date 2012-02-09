/*     */ package com.bright.framework.storage.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.database.sql.ApplicationSql;
/*     */ import com.bright.framework.database.sql.SQLGenerator;
/*     */ import com.bright.framework.io.InputStreamConsumer;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.storage.exception.UnsupportedStoredFileTypeException;
/*     */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DatabaseStorageDevice extends BaseStorageDevice
/*     */   implements CachedStorageDevice
/*     */ {
/*     */   private DBTransactionManager m_transactionManager;
/*     */ 
/*     */   public DatabaseStorageDevice(String a_sFactoryClassName, String a_sFactoryDisplayName, DBTransactionManager a_transactionManager)
/*     */   {
/*  60 */     super(a_sFactoryClassName, a_sFactoryDisplayName);
/*     */ 
/*  62 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   protected void prepareFileForLocalAccess(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     String sFullFilePath = buildFullLocalPath(a_sRelativePath);
/*     */ 
/*  71 */     final File file = new File(sFullFilePath);
/*     */ 
/*  73 */     if (!file.exists())
/*     */     {
/*  76 */       DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*  77 */       ApplicationSql appSql = SQLGenerator.getInstance();
/*     */       try
/*     */       {
/*  81 */         Connection con = transaction.getConnection();
/*     */ 
/*  83 */         String sSql = "SELECT FileData FROM AssetFile WHERE " + appSql.getLowerCaseFunction("Uri") + " = " + appSql.getLowerCaseFunction("?");
/*  84 */         Object[] args = { a_sRelativePath };
/*  85 */         int[] argTypes = { 12 };
/*     */ 
/*  87 */         InputStreamConsumer consumer = new InputStreamConsumer()
/*     */         {
/*     */           public void consume(InputStream a_is) throws IOException {
/*  90 */             OutputStream os = null;
/*     */             try
/*     */             {
/*  93 */               os = new BufferedOutputStream(new FileOutputStream(file));
/*  94 */               IOUtils.copy(a_is, os);
/*     */             }
/*     */             finally
/*     */             {
/*  98 */               IOUtils.closeQuietly(os);
/*     */             }
/*     */           }
/*     */         };
/* 103 */         appSql.readBlob(con, sSql, args, argTypes, "FileData", consumer);
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 107 */         this.m_logger.error(getClass().getSimpleName() + ".getAbsolutePath : SQL Exception : " + e);
/* 108 */         throw new Bn2Exception(getClass().getSimpleName() + ".getAbsolutePath : SQL Exception : " + e, e);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 112 */         this.m_logger.error(getClass().getSimpleName() + ".getAbsolutePath : IO Exception : " + e);
/* 113 */         throw new Bn2Exception(getClass().getSimpleName() + ".getAbsolutePath : IO Exception : " + e, e);
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 119 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 123 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String generateUniqueFilename(String a_sDirectory, String a_sFilename, StoredFileType a_fileType)
/*     */     throws Bn2Exception
/*     */   {
/* 132 */     String sFilename = null;
/*     */ 
/* 134 */     String sRelativePath = a_sDirectory + "/" + a_sFilename;
/* 135 */     Connection con = null;
/* 136 */     String sSql = null;
/* 137 */     PreparedStatement psql = null;
/* 138 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/* 139 */     boolean bIsUnique = false;
/* 140 */     int iCount = 1;
/* 141 */     ApplicationSql sqlGenerator = SQLGenerator.getInstance();
/*     */     try
/*     */     {
/* 145 */       con = transaction.getConnection();
/*     */ 
/* 147 */       sSql = "SELECT Uri FROM AssetFile WHERE " + sqlGenerator.getLowerCaseFunction("Uri") + "=" + sqlGenerator.getLowerCaseFunction("?");
/*     */ 
/* 149 */       while (!bIsUnique)
/*     */       {
/* 151 */         psql = con.prepareStatement(sSql);
/* 152 */         psql.setString(1, sRelativePath);
/* 153 */         ResultSet rs = psql.executeQuery();
/*     */ 
/* 155 */         if (!rs.next())
/*     */         {
/* 157 */           bIsUnique = true;
/*     */         }
/*     */         else
/*     */         {
/* 161 */           sRelativePath = a_sDirectory + "/" + FileUtil.getFilepathWithoutSuffix(a_sFilename) + iCount++;
/* 162 */           String sSuffix = FileUtil.getSuffix(a_sFilename);
/*     */ 
/* 164 */           if (StringUtils.isNotEmpty(sSuffix))
/*     */           {
/* 166 */             sRelativePath = sRelativePath + "." + FileUtil.getSuffix(a_sFilename);
/*     */           }
/*     */         }
/*     */ 
/* 170 */         psql.close();
/*     */       }
/*     */ 
/* 173 */       sFilename = FileUtil.getFilename(sRelativePath);
/*     */ 
/* 176 */       createAssetFileRow(sRelativePath, con);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 180 */       this.m_logger.error(getClass().getSimpleName() + ".generateUniqueFilename : SQL Exception : " + e);
/* 181 */       throw new Bn2Exception(getClass().getSimpleName() + ".generateUniqueFilename : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 187 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 191 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 195 */     return sFilename;
/*     */   }
/*     */ 
/*     */   private void createAssetFileRow(String sRelativePath, Connection con)
/*     */     throws SQLException, Bn2Exception
/*     */   {
/* 210 */     String sSql = "INSERT INTO AssetFile (Uri,DateCreated,FileData) VALUES (?,?," + SQLGenerator.getInstance().getInitialEmptyBlobValue() + ")";
/* 211 */     PreparedStatement psql = con.prepareStatement(sSql);
/*     */     try
/*     */     {
/* 215 */       psql.setString(1, sRelativePath);
/* 216 */       psql.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
/*     */ 
/* 218 */       if (psql.executeUpdate() <= 0)
/*     */       {
/* 220 */         this.m_logger.error("FileStoreManager.createAssetFileRow() : Could not create empty AssetFile row for file: " + sRelativePath);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 225 */       if (psql != null)
/*     */       {
/* 227 */         psql.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void storeFileInDatabase(String a_sRelativePath)
/*     */     throws Bn2Exception
/*     */   {
/* 239 */     Connection con = null;
/* 240 */     String sSql = null;
/* 241 */     PreparedStatement psql = null;
/* 242 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/* 243 */     BufferedInputStream bis = null;
/* 244 */     ApplicationSql appSql = SQLGenerator.getInstance();
/*     */     try
/*     */     {
/* 248 */       File file = new File(getFullLocalPath(a_sRelativePath));
/* 249 */       bis = new BufferedInputStream(new FileInputStream(file));
/*     */ 
/* 251 */       con = transaction.getConnection();
/*     */ 
/* 254 */       sSql = "SELECT Uri FROM AssetFile WHERE " + appSql.getLowerCaseFunction("Uri") + "=" + appSql.getLowerCaseFunction("?");
/* 255 */       psql = con.prepareStatement(sSql);
/* 256 */       psql.setString(1, a_sRelativePath);
/* 257 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 259 */       if (!rs.next())
/*     */       {
/* 261 */         createAssetFileRow(a_sRelativePath, con);
/*     */       }
/*     */ 
/* 264 */       psql.close();
/*     */ 
/* 266 */       sSql = "UPDATE AssetFile SET FileData=? WHERE Uri=?";
/*     */ 
/* 268 */       psql = con.prepareStatement(sSql);
/* 269 */       appSql.setBlobData(psql, bis, "AssetFile", "FileData", "Uri", a_sRelativePath, 1, file.length());
/* 270 */       psql.setString(2, a_sRelativePath);
/* 271 */       psql.execute();
/* 272 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 276 */       this.m_logger.error(getClass().getSimpleName() + ".storeFileInDatabase : SQL Exception : " + e);
/* 277 */       throw new Bn2Exception(getClass().getSimpleName() + ".storeFileInDatabase : SQL Exception : " + e, e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 281 */       this.m_logger.error(getClass().getSimpleName() + ".storeFileInDatabase : IO Exception : " + e);
/* 282 */       throw new Bn2Exception(getClass().getSimpleName() + ".storeFileInDatabase : IO Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 286 */       IOUtils.closeQuietly(bis);
/*     */       try
/*     */       {
/* 290 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 294 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cleanDatabaseFileStore(int a_iAssetAgeInMinutes)
/*     */     throws Bn2Exception
/*     */   {
/* 305 */     Connection con = null;
/* 306 */     String sSql = null;
/* 307 */     PreparedStatement psql = null;
/* 308 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 313 */       con = transaction.getConnection();
/*     */ 
/* 315 */       sSql = "DELETE FROM AssetFile WHERE " + SQLGenerator.getInstance().getNullCheckStatement("FileData") + " AND DateCreated < ?";
/*     */ 
/* 317 */       psql = con.prepareStatement(sSql);
/* 318 */       psql.setTimestamp(1, new Timestamp(System.currentTimeMillis() - a_iAssetAgeInMinutes * 1000 * 60));
/*     */ 
/* 320 */       int iNumDeleted = psql.executeUpdate();
/*     */ 
/* 322 */       psql.close();
/*     */ 
/* 324 */       this.m_logger.debug("FileStoreManager.cleanDatabaseFileStore() : Deleted " + iNumDeleted + " empty AssetFile records.");
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 328 */       this.m_logger.error(getClass().getSimpleName() + ".cleanDatabaseFileStore : SQL Exception : " + e);
/* 329 */       throw new Bn2Exception(getClass().getSimpleName() + ".cleanDatabaseFileStore : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 335 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 339 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getHttpUrl(String a_sRelativePath, boolean a_bTestFileExists)
/*     */     throws FileNotFoundException, IOException, Bn2Exception
/*     */   {
/* 356 */     synchronized (this.m_aiStorageAccessCount)
/*     */     {
/* 358 */       this.m_aiStorageAccessCount.incrementAndGet();
/*     */     }
                File file;
/*     */ 
/*     */     try
/*     */     {
/* 365 */       prepareFileForLocalAccess(a_sRelativePath);
/*     */ 
/* 367 */       if (a_bTestFileExists)
/*     */       {
/* 369 */         String sFullFilePath = getFullLocalPath(a_sRelativePath);
/*     */ 
/* 371 */         file = new File(sFullFilePath);
/*     */ 
/* 373 */         if (!((File)file).exists())
/*     */         {
/* 375 */           throw new FileNotFoundException("File not found in database file store: " + a_sRelativePath);
/*     */         }
/*     */       }
/*     */ 
/* 379 */       String sPrefix = getHttpBaseUrl() + '/';
/*     */ 
/* 381 */       String sfile = sPrefix + (StringUtils.isNotEmpty(getSubPath()) ? getSubPath() + '/' : "") + StorageDeviceUtil.removeDevicePrefix(a_sRelativePath);
                return sfile;
/*     */     }
/*     */     finally
/*     */     {
/*     */       //Object file;
/* 387 */       this.m_alStorageLastAccess.set(System.currentTimeMillis());
/* 388 */       this.m_aiStorageAccessCount.decrementAndGet();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isLocalFileStorage()
/*     */   {
/* 394 */     return false;
/*     */   }
/*     */ 
/*     */   public void doStoreExistingFile(String a_sRelativePath) throws FileNotFoundException, IOException, Bn2Exception
/*     */   {
/* 399 */     storeFileInDatabase(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public void doStoreNewFile(String a_sRelativePath, StoredFileType a_type)
/*     */     throws UnsupportedStoredFileTypeException, Bn2Exception
/*     */   {
/* 405 */     storeFileInDatabase(a_sRelativePath);
/*     */   }
/*     */ 
/*     */   public void doCleanFileStore()
/*     */     throws Bn2Exception
/*     */   {
/* 411 */     cleanDatabaseFileStore(AssetBankSettings.getDeleteStoredFilesOlderThanMins());
/*     */   }
/*     */ 
/*     */   public void doRunUsageScan() throws Bn2Exception
/*     */   {
/* 416 */     Connection con = null;
/* 417 */     String sSql = null;
/* 418 */     PreparedStatement psql = null;
/* 419 */     DBTransaction transaction = this.m_transactionManager.getNewTransaction();
/*     */     try
/*     */     {
/* 424 */       con = transaction.getConnection();
/*     */ 
/* 426 */       ApplicationSql appSql = SQLGenerator.getInstance();
/*     */ 
/* 428 */       sSql = "SELECT SUM(" + appSql.getBlobSizeStatement("FileData") + ") TotalBytes " + " FROM AssetFile WHERE Uri LIKE ?";
/*     */ 
/* 431 */       psql = con.prepareStatement(sSql);
/* 432 */       psql.setString(1, getId() + ":%");
/*     */ 
/* 434 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 436 */       if (rs.next())
/*     */       {
/* 438 */         setUsedSpaceInBytes(rs.getLong("TotalBytes"));
/*     */       }
/*     */ 
/* 441 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 445 */       this.m_logger.error(getClass().getSimpleName() + ".doRunUsageScan : SQL Exception : " + e);
/* 446 */       throw new Bn2Exception(getClass().getSimpleName() + ".doRunUsageScan : SQL Exception : " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 452 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/* 456 */         this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.bean.DatabaseStorageDevice
 * JD-Core Version:    0.6.0
 */