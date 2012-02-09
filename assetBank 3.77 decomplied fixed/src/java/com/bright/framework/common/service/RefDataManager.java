/*     */ package com.bright.framework.common.service;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.common.bean.DescriptionDataBean;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.common.exception.SettingNotFoundException;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.excalibur.datasource.DataSourceComponent;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class RefDataManager extends Bn2Manager
/*     */ {
/*     */   private static final String c_ksClassName = "RefDataManager";
/*  53 */   protected DataSourceComponent m_dataSource = null;
/*  54 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/*  56 */   private HashMap m_hmCache = new HashMap();
/*     */ 
/*     */   public String getSystemSetting(String a_sId)
/*     */     throws Bn2Exception, SettingNotFoundException
/*     */   {
/*  73 */     String ksMethodName = "getSystemSetting";
/*     */ 
/*  75 */     Connection cCon = null;
/*  76 */     String sValue = null;
/*     */ 
/*  79 */     if (this.m_hmCache.containsKey(a_sId))
/*     */     {
/*  81 */       sValue = (String)this.m_hmCache.get(a_sId);
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/*  88 */         cCon = this.m_dataSource.getConnection();
/*     */ 
/*  90 */         String sSql = "SELECT Value FROM SystemSetting WHERE Id=?";
/*  91 */         PreparedStatement psql = null;
/*  92 */         psql = cCon.prepareStatement(sSql);
/*  93 */         psql.setString(1, a_sId);
/*  94 */         ResultSet rs = psql.executeQuery();
/*     */ 
/*  97 */         if (rs.next())
/*     */         {
/*  99 */           sValue = rs.getString("Value");
/* 100 */           psql.close();
/*     */         }
/*     */         else
/*     */         {
/* 104 */           psql.close();
/* 105 */           throw new SettingNotFoundException("RefDataManager.getSystemSetting: there is no system setting with id " + a_sId);
/*     */         }
/*     */ 
/* 109 */         setCachedValue(a_sId, sValue);
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/*     */         try
/*     */         {
/* 115 */           cCon.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */ 
/* 122 */         throw new Bn2Exception("RefDataManager.getSystemSetting: Exception occurred: " + e.getMessage(), e);
/*     */       }
/*     */       finally
/*     */       {
/* 127 */         if (cCon != null)
/*     */         {
/*     */           try
/*     */           {
/*     */             try
/*     */             {
/* 133 */               cCon.commit();
/*     */             }
/*     */             finally
/*     */             {
/* 137 */               cCon.close();
/*     */             }
/*     */           }
/*     */           catch (SQLException sqle)
/*     */           {
/* 142 */             this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 148 */     return sValue;
/*     */   }
/*     */ 
/*     */   public long getSystemSettingAsLong(String a_sId)
/*     */     throws Bn2Exception, SettingNotFoundException
/*     */   {
/* 166 */     String ksMethodName = "getSystemSettingAsLong";
/* 167 */     String sValue = getSystemSetting(a_sId);
/* 168 */     long lRetValue = 0L;
/*     */     try
/*     */     {
/* 173 */       lRetValue = Long.parseLong(sValue);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 177 */       throw new Bn2Exception("RefDataManager.getSystemSettingAsLong: the system setting " + a_sId + " is not a long", nfe);
/*     */     }
/* 179 */     return lRetValue;
/*     */   }
/*     */ 
/*     */   public void setSystemSetting(DBTransaction a_dbTransaction, String a_sId, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/* 199 */     String ksMethodName = "setSystemSetting";
/* 200 */     Connection cCon = null;
/* 201 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 203 */     if (transaction == null)
/*     */     {
/* 205 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 210 */       cCon = transaction.getConnection();
/*     */ 
/* 212 */       String sSql = "SELECT * FROM SystemSetting WHERE Id=?";
/* 213 */       PreparedStatement psql = null;
/* 214 */       psql = cCon.prepareStatement(sSql);
/* 215 */       psql.setString(1, a_sId);
/* 216 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 218 */       if (rs.next())
/*     */       {
/* 221 */         psql.close();
/* 222 */         updateSystemSetting(a_dbTransaction, a_sId, a_sValue);
/*     */       }
/*     */       else
/*     */       {
/* 227 */         psql.close();
/* 228 */         sSql = "INSERT INTO SystemSetting (Id, Value) VALUES (?,?)";
/* 229 */         psql = cCon.prepareStatement(sSql);
/* 230 */         psql.setString(1, a_sId);
/* 231 */         psql.setString(2, a_sValue);
/* 232 */         psql.executeUpdate();
/* 233 */         psql.close();
/* 234 */         setCachedValue(a_sId, a_sValue);
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 239 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 243 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 247 */           this.m_logger.error("RefDataManager.setSystemSetting() : SQLException whilst rolling back transaction.");
/*     */         }
/*     */       }
/*     */ 
/* 251 */       throw new Bn2Exception("RefDataManager.setSystemSetting: Exception occurred: " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 255 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 259 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 263 */           this.m_logger.error("RefDataManager.setSystemSetting() : SQLException whilst committing transaction.");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateSystemSetting(DBTransaction a_dbTransaction, String a_sId, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/* 286 */     String ksMethodName = "updateSystemSetting";
/* 287 */     Connection cCon = null;
/* 288 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 290 */     if (transaction == null)
/*     */     {
/* 292 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 297 */       cCon = transaction.getConnection();
/*     */ 
/* 299 */       String sSql = "UPDATE SystemSetting SET Value = ? WHERE Id=?";
/* 300 */       PreparedStatement psql = null;
/* 301 */       psql = cCon.prepareStatement(sSql);
/* 302 */       psql.setString(1, a_sValue);
/* 303 */       psql.setString(2, a_sId);
/* 304 */       int iUpdatedRows = psql.executeUpdate();
/*     */ 
/* 306 */       psql.close();
/*     */ 
/* 309 */       if (iUpdatedRows <= 0)
/*     */       {
/* 311 */         throw new Bn2Exception("RefDataManager.updateSystemSetting: there is no system setting with id " + a_sId);
/*     */       }
/*     */ 
/* 315 */       setCachedValue(a_sId, a_sValue);
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 320 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 324 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 328 */           this.m_logger.error("RefDataManager.updateSystemSetting() : SQLException whilst rolling back transaction.");
/*     */         }
/*     */       }
/*     */ 
/* 332 */       throw new Bn2Exception("RefDataManager.updateSystemSetting: Exception occurred: " + e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/* 336 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 340 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 344 */           this.m_logger.error("RefDataManager.updateSystemSetting() : SQLException whilst committing transaction.");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateSystemSettingAsLong(DBTransaction a_dbTransaction, String a_sId, long a_lValue)
/*     */     throws Bn2Exception
/*     */   {
/* 368 */     String sValue = "" + a_lValue;
/*     */ 
/* 370 */     updateSystemSetting(a_dbTransaction, a_sId, sValue);
/*     */   }
/*     */ 
/*     */   public void deleteSystemSetting(DBTransaction a_dbTransaction, String a_sId)
/*     */     throws Bn2Exception
/*     */   {
/* 380 */     String ksMethodName = "deleteSystemSetting";
/* 381 */     Connection cCon = null;
/* 382 */     DBTransaction transaction = a_dbTransaction;
/*     */ 
/* 384 */     if (transaction == null)
/*     */     {
/* 386 */       transaction = this.m_transactionManager.getNewTransaction();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 391 */       cCon = transaction.getConnection();
/*     */ 
/* 393 */       String sSql = "DELETE FROM SystemSetting WHERE Id=?";
/* 394 */       PreparedStatement psql = null;
/* 395 */       psql = cCon.prepareStatement(sSql);
/* 396 */       psql.setString(1, a_sId);
/* 397 */       psql.executeUpdate();
/* 398 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 402 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 406 */           transaction.rollback();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 410 */           this.m_logger.error("RefDataManager.updateSystemSetting() : SQLException whilst rolling back transaction.");
/*     */         }
/*     */       }
/*     */ 
/* 414 */       throw new Bn2Exception("RefDataManager.deleteSystemSetting: Exception occurred: " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 418 */       if ((a_dbTransaction == null) && (transaction != null))
/*     */       {
/*     */         try
/*     */         {
/* 422 */           transaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 426 */           this.m_logger.error("RefDataManager.updateSystemSetting() : SQLException whilst committing transaction.");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setCachedValue(String a_sId, String sValue)
/*     */   {
/* 446 */     synchronized (this.m_hmCache)
/*     */     {
/* 448 */       this.m_hmCache.put(a_sId, sValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Vector getNameList(DataSourceComponent a_dataSource, String a_sSql)
/*     */     throws Bn2Exception
/*     */   {
/* 455 */     String ksMethodName = "getNameList";
/*     */ 
/* 457 */     Connection cCon = null;
/* 458 */     Vector vec = new Vector();
/*     */     try
/*     */     {
/* 462 */       cCon = a_dataSource.getConnection();
/*     */ 
/* 464 */       PreparedStatement psql = null;
/* 465 */       psql = cCon.prepareStatement(a_sSql);
/* 466 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 469 */       while (rs.next())
/*     */       {
/* 472 */         StringDataBean item = new StringDataBean();
/* 473 */         item.setId(rs.getLong("Id"));
/* 474 */         item.setName(rs.getString("Name"));
/*     */ 
/* 476 */         vec.add(item);
/*     */       }
/*     */ 
/* 479 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/* 485 */         cCon.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*     */       }
/*     */ 
/* 492 */       throw new Bn2Exception("RefDataManager.getNameList: Exception occurred: " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 497 */       if (cCon != null)
/*     */       {
/*     */         try
/*     */         {
/* 501 */           cCon.commit();
/* 502 */           cCon.close();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 511 */     return vec;
/*     */   }
/*     */ 
/*     */   public static Vector getDescriptionList(DataSourceComponent a_dataSource, String a_sSql)
/*     */     throws Bn2Exception
/*     */   {
/* 526 */     String ksMethodName = "getDescriptionList";
/*     */ 
/* 528 */     Connection cCon = null;
/* 529 */     Vector vec = new Vector();
/*     */     try
/*     */     {
/* 533 */       cCon = a_dataSource.getConnection();
/*     */ 
/* 535 */       PreparedStatement psql = null;
/* 536 */       psql = cCon.prepareStatement(a_sSql);
/* 537 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 540 */       while (rs.next())
/*     */       {
/* 543 */         DescriptionDataBean item = new DescriptionDataBean();
/* 544 */         item.setId(rs.getLong("Id"));
/* 545 */         item.setName(rs.getString("Name"));
/* 546 */         item.setDescription(rs.getString("Description"));
/*     */ 
/* 548 */         vec.add(item);
/*     */       }
/*     */ 
/* 551 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/* 557 */         cCon.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*     */       }
/*     */ 
/* 564 */       throw new Bn2Exception("RefDataManager.getDescriptionList: Exception occurred: " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 569 */       if (cCon != null)
/*     */       {
/*     */         try
/*     */         {
/* 573 */           cCon.commit();
/* 574 */           cCon.close();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 583 */     return vec;
/*     */   }
/*     */ 
/*     */   public static long getIdForName(DataSourceComponent a_dataSource, String a_sName, String a_sSql)
/*     */     throws Bn2Exception
/*     */   {
/* 590 */     String ksMethodName = "getIdForName";
/*     */ 
/* 592 */     long lId = -1L;
/* 593 */     Connection cCon = null;
/*     */     try
/*     */     {
/* 597 */       cCon = a_dataSource.getConnection();
/*     */ 
/* 599 */       PreparedStatement psql = null;
/* 600 */       psql = cCon.prepareStatement(a_sSql);
/* 601 */       psql.setString(1, a_sName);
/* 602 */       ResultSet rs = psql.executeQuery();
/*     */ 
/* 604 */       if (rs.next())
/*     */       {
/* 606 */         lId = rs.getLong("Id");
/*     */       }
/* 608 */       psql.close();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/*     */       try
/*     */       {
/* 614 */         cCon.rollback();
/*     */       }
/*     */       catch (SQLException sqle)
/*     */       {
/*     */       }
/*     */ 
/* 621 */       throw new Bn2Exception("RefDataManager.getIdForName: Exception occurred: " + e, e);
/*     */     }
/*     */     finally
/*     */     {
/* 626 */       if (cCon != null)
/*     */       {
/*     */         try
/*     */         {
/* 630 */           cCon.commit();
/* 631 */           cCon.close();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 640 */     return lId;
/*     */   }
/*     */ 
/*     */   public Vector getListApplicationSetting(String a_sSettingName)
/*     */   {
/* 646 */     String sResult = FrameworkSettings.getInstance().getStringSetting(a_sSettingName);
/* 647 */     if (sResult != null)
/*     */     {
/* 649 */       return StringUtil.convertToVector(sResult, ",");
/*     */     }
/* 651 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDataSourceComponent(DataSourceComponent a_datasource)
/*     */   {
/* 657 */     this.m_dataSource = a_datasource;
/*     */   }
/*     */ 
/*     */   public DBTransactionManager getTransactionManager()
/*     */   {
/* 663 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*     */   {
/* 669 */     this.m_transactionManager = a_sTransactionManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.service.RefDataManager
 * JD-Core Version:    0.6.0
 */