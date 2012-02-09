/*      */ package com.bright.framework.storage.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bright.framework.common.service.ScheduleManager;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.storage.bean.FileSystemStorageDevice;
/*      */ import com.bright.framework.storage.bean.StorageDevice;
/*      */ import com.bright.framework.storage.constant.StorageDeviceConstants;
/*      */ import com.bright.framework.storage.constant.StorageDeviceType;
/*      */ import com.bright.framework.storage.constant.StoredFileType;
/*      */ import com.bright.framework.storage.exception.NoAvailableStorageException;
/*      */ import com.bright.framework.storage.util.StorageDeviceUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.TimerTask;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class StorageDeviceManager extends Bn2Manager
/*      */   implements StorageDeviceConstants
/*      */ {
/*   62 */   private static final String c_ksClassName = StorageDeviceManager.class.getSimpleName();
/*      */ 
/*   65 */   private Vector<StorageDevice> m_vDevices = null;
/*      */ 
/*   68 */   private Map<String, StorageDeviceFactory<StorageDevice>> m_mFactories = new LinkedHashMap();
/*      */ 
/*   70 */   private AtomicBoolean m_boolRunningUpdateTask = new AtomicBoolean(false);
/*   71 */   private boolean m_bCacheInvalidated = true;
/*      */ 
/*   73 */   private ScheduleManager m_scheduleManager = null;
/*   74 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   86 */     super.startup();
/*      */ 
/*   89 */     for (StorageDeviceFactory factory : this.m_mFactories.values())
/*      */     {
/*   92 */       if ((factory instanceof TransactionStorageDeviceFactory))
/*      */       {
/*   94 */         ((TransactionStorageDeviceFactory)factory).setTransactionManager(this.m_transactionManager);
/*      */       }
/*      */     }
/*      */ 
/*   98 */     initialiseStorage();
/*      */ 
/*  100 */     Vector<StorageDevice> devices = getAllDevices(null);
/*      */ 
/*  103 */     for (StorageDevice storageDevice : devices)
/*      */     {
/*  105 */       storageDevice.cleanFileStore();
/*      */     }
/*      */ 
/*  108 */     int iUpdatePeriod = FrameworkSettings.getStorageDeviceUsageUpdatePeriod();
/*      */ 
/*  111 */     TimerTask task = new TimerTask()
/*      */     {
/*      */       public void run()
/*      */       {
/*  116 */         if (StorageDeviceManager.this.m_boolRunningUpdateTask.compareAndSet(false, true))
/*      */         {
/*      */           try
/*      */           {
/*  120 */             StorageDeviceManager.this.refreshDeviceUsageData();
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*  124 */             StorageDeviceManager.this.m_logger.error("Could not update storage device usage data in scheduled task due to exception: " + e.getLocalizedMessage(), e);
/*      */           }
/*      */           finally
/*      */           {
/*  128 */             StorageDeviceManager.this.m_boolRunningUpdateTask.set(false);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  133 */           StorageDeviceManager.this.m_logger.debug("Skipping storage device usage update task - task already running.");
/*      */         }
/*      */       }
/*      */     };
/*  139 */     this.m_logger.info("Scheduling storage device usage data update task to run every " + iUpdatePeriod + " minutes");
/*      */ 
/*  141 */     this.m_scheduleManager.schedule(task, 60000L, iUpdatePeriod * 60L * 1000L, false);
/*      */   }
/*      */ 
/*      */   private void initialiseStorage() throws Bn2Exception
/*      */   {
/*  146 */     Vector vDevices = getAllDevices(null);
/*      */ 
/*  149 */     for (Iterator itDevices = vDevices.iterator(); itDevices.hasNext(); )
/*      */     {
/*  151 */       StorageDevice device = (StorageDevice)itDevices.next();
/*  152 */       device.initialiseStorage();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addStorageDeviceFactory(StorageDeviceFactory<StorageDevice> a_factory)
/*      */   {
/*  162 */     this.m_mFactories.put(a_factory.getFactoryClassName(), a_factory);
/*      */   }
/*      */ 
/*      */   public List<StorageDeviceFactory<StorageDevice>> getFactories()
/*      */   {
/*  170 */     return new ArrayList(this.m_mFactories.values());
/*      */   }
/*      */ 
/*      */   public StorageDeviceFactory<StorageDevice> getFactory(String a_sClassName)
/*      */   {
/*  180 */     return (StorageDeviceFactory)this.m_mFactories.get(a_sClassName);
/*      */   }
/*      */ 
/*      */   private StorageDeviceFactory<StorageDevice> getFactoryForDevice(StorageDevice a_device)
/*      */   {
/*  190 */     if (StringUtils.isNotEmpty(a_device.getFactoryClassName()))
/*      */     {
/*  192 */       return (StorageDeviceFactory)this.m_mFactories.get(a_device.getFactoryClassName());
/*      */     }
/*  194 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean getDeviceUsageUpdateIsRunning()
/*      */   {
/*  199 */     return this.m_boolRunningUpdateTask.get();
/*      */   }
/*      */ 
/*      */   public StorageDevice getDeviceFromDatabase(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  212 */     return getDeviceFromDatabase(a_dbTransaction, a_lId, false);
/*      */   }
/*      */ 
/*      */   public StorageDevice getDeviceFromDatabase(DBTransaction a_dbTransaction, long a_lId, boolean a_bGetAssetUse)
/*      */     throws Bn2Exception
/*      */   {
/*  226 */     StorageDevice device = getDevice(a_dbTransaction, a_lId);
/*      */ 
/*  229 */     StorageDeviceFactory factory = getFactoryForDevice(device);
/*  230 */     return factory.loadStorageDevice(a_dbTransaction, device.getId(), a_bGetAssetUse);
/*      */   }
/*      */ 
/*      */   public Vector<StorageDevice> getAllDevices(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  242 */     String ksMethodName = "getAllDevices";
/*      */ 
/*  244 */     if ((!this.m_bCacheInvalidated) && (this.m_vDevices != null))
/*      */     {
/*  246 */       return this.m_vDevices;
/*      */     }
/*      */ 
/*  249 */     Vector<StorageDevice> vDevices = new Vector();
/*  250 */     Connection con = null;
/*  251 */     String sSql = null;
/*  252 */     PreparedStatement psql = null;
/*  253 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  255 */     if (transaction == null)
/*      */     {
/*  257 */       transaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */     }
                StorageDeviceFactory<StorageDevice> factory;
/*      */ 
/*      */     try
/*      */     {
/*  262 */       con = transaction.getConnection();
/*      */ 
/*  264 */       sSql = "SELECT Id, FactoryClassName FROM StorageDevice ORDER BY SequenceNumber";
/*      */ 
/*  266 */       psql = con.prepareStatement(sSql);
/*      */ 
/*  268 */       ResultSet rs = psql.executeQuery();
/*      */       StorageDevice device;
/*      */       Iterator iterator;
/*  270 */       while (rs.next())
/*      */       {
/*  272 */         factory = getFactory(rs.getString("FactoryClassName"));
/*  273 */         device = factory.loadStorageDevice(transaction, rs.getLong("Id"), false);
/*  274 */         vDevices.add(device);
/*      */ 
/*  276 */         if (this.m_vDevices != null)
/*      */         {
/*  279 */           for (iterator = this.m_vDevices.iterator(); iterator.hasNext(); )
/*      */           {
/*  281 */             StorageDevice cachedDevice = (StorageDevice)iterator.next();
/*      */ 
/*  283 */             if ((cachedDevice.getId() == device.getId()) && (cachedDevice.getLocalBasePath().equalsIgnoreCase(device.getLocalBasePath())))
/*      */             {
/*  285 */               device.setNextStorageSubdirectory(cachedDevice.getNextStorageSubdirectory());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  291 */       psql.close();
/*      */ 
/*  293 */       this.m_vDevices = vDevices;
/*  294 */       this.m_bCacheInvalidated = false;
/*      */ 
/*  296 */       //factory = vDevices;
/*      */      // return factory;
                return vDevices;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  300 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  304 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  308 */           this.m_logger.error(c_ksClassName + "." + "getAllDevices" + " : SQL Exception while rolling back : " + e);
/*      */         }
/*      */       }
/*  311 */       this.m_logger.error(c_ksClassName + "." + "getAllDevices" + " : SQL Exception : " + e);
/*  312 */       throw new Bn2Exception(c_ksClassName + "." + "getAllDevices" + " : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  317 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  321 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  325 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                     throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
/*  326 */     //throw localObject;}
/*      */   }
/*      */ }
/*      */   public List<String> validateDevice(StorageDevice a_device)
/*      */     throws Bn2Exception
/*      */   {
/*  340 */     StorageDeviceFactory factory = getFactoryForDevice(a_device);
/*      */ 
/*  342 */     return factory.validateStorageDevice(a_device);
/*      */   }
/*      */ 
/*      */   public List<String> validateOverallStorage(DBTransaction a_dbTransaction, StorageDevice a_deviceToSave)
/*      */     throws Bn2Exception
/*      */   {
/*  357 */     List messages = new ArrayList();
/*      */ 
/*  359 */     Vector devices = getAllDevices(a_dbTransaction);
/*      */ 
/*  361 */     boolean bFoundAssetStorage = false;
/*  362 */     boolean bFoundSystemStorage = false;
/*  363 */     boolean bFoundThumbnailStorage = false;
/*      */ 
/*  365 */     if (a_deviceToSave.getId() == 0L)
/*      */     {
/*  367 */       bFoundAssetStorage = a_deviceToSave.isAvailableForStorage(StorageDeviceType.ASSETS);
/*  368 */       bFoundSystemStorage = a_deviceToSave.isAvailableForStorage(StorageDeviceType.SYSTEM);
/*  369 */       bFoundThumbnailStorage = a_deviceToSave.isAvailableForStorage(StorageDeviceType.THUMBNAILS);
/*      */     }
/*      */     Iterator itDevices;
/*  372 */     if ((!bFoundAssetStorage) || (!bFoundSystemStorage))
/*      */     {
/*  374 */       for (itDevices = devices.iterator(); itDevices.hasNext(); )
/*      */       {
/*  376 */         StorageDevice existingDevice = (StorageDevice)itDevices.next();
/*      */ 
/*  378 */         if (existingDevice.getId() == a_deviceToSave.getId())
/*      */         {
/*  383 */           if ((a_deviceToSave.getType().equals(existingDevice.getType())) && (((a_deviceToSave.getMaxSpaceInMb() >= existingDevice.getMaxSpaceInMb()) && (existingDevice.getMaxSpaceInMb() > 0L)) || ((a_deviceToSave.getMaxSpaceInMb() == 0L) && ((!a_deviceToSave.isLocked()) || (existingDevice.isLocked())))))
/*      */           {
/*  388 */             return Collections.emptyList();
/*      */           }
/*      */ 
/*  391 */           a_deviceToSave.setUsedSpaceInBytes(existingDevice.getUsedSpaceInBytes());
/*      */ 
/*  393 */           bFoundAssetStorage |= a_deviceToSave.isAvailableForStorage(StorageDeviceType.ASSETS);
/*  394 */           bFoundSystemStorage |= a_deviceToSave.isAvailableForStorage(StorageDeviceType.SYSTEM);
/*  395 */           bFoundThumbnailStorage |= a_deviceToSave.isAvailableForStorage(StorageDeviceType.THUMBNAILS);
/*      */         }
/*      */         else
/*      */         {
/*  399 */           bFoundAssetStorage |= existingDevice.isAvailableForStorage(StorageDeviceType.ASSETS);
/*  400 */           bFoundSystemStorage |= existingDevice.isAvailableForStorage(StorageDeviceType.SYSTEM);
/*  401 */           bFoundThumbnailStorage |= existingDevice.isAvailableForStorage(StorageDeviceType.THUMBNAILS);
/*      */         }
/*      */ 
/*  405 */         if ((bFoundAssetStorage) && (bFoundSystemStorage) && (bFoundThumbnailStorage))
/*      */         {
/*  407 */           return Collections.emptyList();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  413 */     if ((!bFoundAssetStorage) || (!bFoundSystemStorage) || (!bFoundThumbnailStorage))
/*      */     {
/*  415 */       messages.add("You cannot save this device because it would leave the system without available " + StringUtil.getAsReadableList(false, new String[] { bFoundAssetStorage ? "" : "asset", bFoundThumbnailStorage ? "" : "thumbnail", bFoundSystemStorage ? "" : "system" }) + " storage.");
/*      */     }
/*      */ 
/*  420 */     return messages;
/*      */   }
/*      */ 
/*      */   public void saveDevice(DBTransaction a_dbTransaction, StorageDevice a_device)
/*      */     throws Bn2Exception
/*      */   {
/*  432 */     StorageDeviceFactory factory = getFactoryForDevice(a_device);
/*      */ 
/*  434 */     factory.saveStorageDevice(a_dbTransaction, a_device);
/*      */ 
/*  436 */     a_device.initialiseStorage();
/*      */ 
/*  438 */     this.m_bCacheInvalidated = true;
/*      */   }
/*      */ 
/*      */   private void updateDeviceUsageData(DBTransaction a_dbTransaction, long a_lDeviceId, long a_lBytesUsed, long a_lLocalBytesUsed, Date a_dtLastScanTime)
/*      */     throws Bn2Exception
/*      */   {
/*  456 */     String ksMethodName = "updateDeviceUsageData";
/*      */ 
/*  458 */     Connection con = null;
/*  459 */     String sSql = null;
/*  460 */     PreparedStatement psql = null;
/*  461 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  463 */     if (transaction == null)
/*      */     {
/*  465 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  470 */       con = transaction.getConnection();
/*      */ 
/*  472 */       sSql = "UPDATE StorageDevice SET UsedSpaceInBytes=?, UsedLocalSpaceInBytes=?, LastScanTime=? WHERE Id=?";
/*      */ 
/*  474 */       psql = con.prepareStatement(sSql);
/*      */ 
/*  476 */       int iCol = 1;
/*  477 */       psql.setLong(iCol++, a_lBytesUsed);
/*  478 */       psql.setLong(iCol++, a_lLocalBytesUsed);
/*  479 */       psql.setTimestamp(iCol++, new Timestamp(a_dtLastScanTime.getTime()));
/*  480 */       psql.setLong(iCol++, a_lDeviceId);
/*      */ 
/*  482 */       psql.executeUpdate();
/*      */ 
/*  484 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  488 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  492 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  496 */           this.m_logger.error(c_ksClassName + "." + "updateDeviceUsageData" + " : SQL Exception while rolling back : " + e);
/*      */         }
/*      */       }
/*  499 */       this.m_logger.error(c_ksClassName + "." + "updateDeviceUsageData" + " : SQL Exception : " + e);
/*  500 */       throw new Bn2Exception(c_ksClassName + "." + "updateDeviceUsageData" + " : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  505 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  509 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  513 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getTotalFreeSpace(DBTransaction a_dbTransaction, StorageDeviceType a_type, long a_lExcludeDeviceId)
/*      */     throws Bn2Exception
/*      */   {
/*  528 */     Vector vDevices = getAllDevices(a_dbTransaction);
/*  529 */     long lTotal = 0L;
/*      */ 
/*  531 */     for (Iterator itDevices = vDevices.iterator(); itDevices.hasNext(); )
/*      */     {
/*  533 */       StorageDevice device = (StorageDevice)itDevices.next();
/*      */ 
/*  535 */       if ((device.getId() != a_lExcludeDeviceId) && (device.isStorageFor(a_type)) && (!device.isLocked()) && (device.getHasFreeSpace()))
/*      */       {
/*  541 */         if (device.getMaxSpaceInMb() <= 0L)
/*      */         {
/*  543 */           return -1L;
/*      */         }
/*      */ 
/*  547 */         lTotal += device.getFreeSpaceInBytes();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  552 */     return lTotal;
/*      */   }
/*      */ 
/*      */   public Vector<StorageDevice> deleteDevice(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/*  566 */     String ksMethodName = "deleteDevice";
/*      */ 
/*  568 */     Connection con = null;
/*  569 */     String sSql = null;
/*  570 */     PreparedStatement psql = null;
/*  571 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/*  573 */     if (transaction == null)
/*      */     {
/*  575 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  580 */       con = transaction.getConnection();
/*      */ 
/*  582 */       sSql = "DELETE FROM StorageDevice WHERE Id=?";
/*  583 */       psql = con.prepareStatement(sSql);
/*  584 */       psql.setLong(1, a_lId);
/*  585 */       psql.executeUpdate();
/*      */ 
/*  587 */       psql.close();
/*      */ 
/*  589 */       invalidateCache();
/*      */ 
/*  591 */       Vector localVector = getAllDevices(transaction);
/*      */       return localVector;
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  595 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  599 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  603 */           this.m_logger.error(c_ksClassName + "." + "deleteDevice" + " : SQL Exception while rolling back : " + e);
/*      */         }
/*      */       }
/*  606 */       this.m_logger.error(c_ksClassName + "." + "deleteDevice" + " : SQL Exception : " + e);
/*  607 */       throw new Bn2Exception(c_ksClassName + "." + "deleteDevice" + " : SQL Exception : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/*  612 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/*  616 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/*  620 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
                    throw new Bn2Exception(sqle.getMessage());
/*      */         }
/*      */       }
/*  621 */     }//throw localObject;
/*      */   }
/*      */ 
/*      */   public Vector<StorageDevice> reorderDevice(DBTransaction a_dbTransaction, long a_lId, boolean a_bUp)
/*      */     throws Bn2Exception
/*      */   {
/*  636 */     String ksMethodName = "reorderDevices";
/*      */ 
/*  638 */     Connection con = null;
/*  639 */     String sSql = null;
/*  640 */     PreparedStatement psql = null;
/*  641 */     DBTransaction transaction = a_dbTransaction;
/*  642 */     boolean bChangedOrder = false;
/*      */ 
/*  644 */     Vector vDevices = getAllDevices(a_dbTransaction);
/*      */ 
/*  646 */     if (transaction == null)
/*      */     {
/*  648 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*  651 */     synchronized (vDevices)
/*      */     {
/*  653 */       int increment = a_bUp ? -1 : 1;
/*  654 */       for (int i = 0; i < vDevices.size(); i++)
/*      */       {
/*  656 */         StorageDevice device = (StorageDevice)vDevices.get(i);
/*  657 */         if ((device.getId() != a_lId) || (i + increment < 0) || (i + increment >= vDevices.size()))
/*      */           continue;
/*  659 */         Collections.swap(vDevices, i, i + increment);
/*  660 */         bChangedOrder = true;
/*  661 */         break;
/*      */       }
/*      */ 
/*  665 */       if (bChangedOrder)
/*      */       {
/*      */         try
/*      */         {
/*  669 */           con = transaction.getConnection();
/*      */ 
/*  671 */           sSql = "UPDATE StorageDevice SET SequenceNumber=? WHERE Id=?";
/*  672 */           psql = con.prepareStatement(sSql);
/*      */ 
/*  674 */           for (int i = 0; i < vDevices.size(); i++)
/*      */           {
/*  676 */             psql.setInt(1, i + 1);
/*  677 */             psql.setLong(2, ((StorageDevice)vDevices.get(i)).getId());
/*  678 */             psql.executeUpdate();
/*      */           }
/*  680 */           psql.close();
/*      */ 
/*  682 */           invalidateCache();
/*      */         }
/*      */         catch (SQLException e)
/*      */         {
/*  686 */           if ((a_dbTransaction == null) && (transaction != null))
/*      */           {
/*      */             try
/*      */             {
/*  690 */               transaction.rollback();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/*  694 */               this.m_logger.error(c_ksClassName + "." + "reorderDevices" + " : SQL Exception while rolling back : " + e);
/*      */             }
/*      */           }
/*  697 */           this.m_logger.error(c_ksClassName + "." + "reorderDevices" + " : SQL Exception : " + e);
/*  698 */           throw new Bn2Exception(c_ksClassName + "." + "reorderDevices" + " : SQL Exception : " + e, e);
/*      */         }
/*      */         finally
/*      */         {
/*  703 */           if ((a_dbTransaction == null) && (transaction != null))
/*      */           {
/*      */             try
/*      */             {
/*  707 */               transaction.commit();
/*      */             }
/*      */             catch (SQLException sqle)
/*      */             {
/*  711 */               this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  717 */       return getAllDevices(a_dbTransaction);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StorageDevice getDeviceForNewFile(DBTransaction a_dbTransaction, long a_lSuggestedDeviceId, StorageDeviceType a_type)
/*      */     throws Bn2Exception, NoAvailableStorageException
/*      */   {
/*  733 */     Vector vDevices = getAllDevices(a_dbTransaction);
/*      */ 
/*  736 */     if (a_lSuggestedDeviceId > 0L)
/*      */     {
/*      */       try
/*      */       {
/*  740 */         StorageDevice device = getDevice(a_dbTransaction, a_lSuggestedDeviceId);
/*      */ 
/*  742 */         if ((device != null) && (device.isAvailableForStorage(a_type)))
/*      */         {
/*  744 */           return device;
/*      */         }
/*      */       }
/*      */       catch (Throwable t)
/*      */       {
/*      */       }
/*      */     }
/*  751 */     for (Iterator iterator = vDevices.iterator(); iterator.hasNext(); )
/*      */     {
/*  753 */       StorageDevice device = (StorageDevice)iterator.next();
/*      */ 
/*  755 */       if (device.isAvailableForStorage(a_type))
/*      */       {
/*  757 */         return device;
/*      */       }
/*      */     }
/*      */ 
/*  761 */     throw new NoAvailableStorageException();
/*      */   }
/*      */ 
/*      */   public StorageDevice getDeviceForNewFile(DBTransaction a_dbTransaction, StorageDeviceType a_type)
/*      */     throws Bn2Exception, NoAvailableStorageException
/*      */   {
/*  775 */     return getDeviceForNewFile(a_dbTransaction, 0L, a_type);
/*      */   }
/*      */ 
/*      */   public String getPathForDeviceId(DBTransaction a_dbTransaction, long a_lDeviceId)
/*      */     throws Bn2Exception
/*      */   {
/*  787 */     Vector vDevices = getAllDevices(a_dbTransaction);
/*      */ 
/*  789 */     for (int i = 0; i < vDevices.size(); i++)
/*      */     {
/*  791 */       FileSystemStorageDevice device = (FileSystemStorageDevice)vDevices.get(i);
/*      */ 
/*  793 */       if (device.getId() == a_lDeviceId)
/*      */       {
/*  795 */         return device.getLocalBasePath();
/*      */       }
/*      */     }
/*  798 */     throw new IllegalArgumentException(c_ksClassName + ".getPathForDeviceId() : No storage device found for id " + a_lDeviceId);
/*      */   }
/*      */ 
/*      */   public String getFullPathForRelativePath(String a_sRelativePath)
/*      */     throws Bn2Exception
/*      */   {
/*  811 */     if (a_sRelativePath.length() == 0)
/*      */     {
/*  813 */       throw new IllegalArgumentException("Empty relative path passed to getFullPathForRelativePath()");
/*      */     }
/*      */ 
/*  816 */     StorageDevice device = getDeviceForRelativePath(a_sRelativePath);
/*  817 */     String sFullPath = device.getFullLocalPath(a_sRelativePath);
/*      */ 
/*  820 */     String sBasePath = getFullBasePathForRelativePath(a_sRelativePath);
/*  821 */     String sRelativePath = stripDeviceId(a_sRelativePath);
/*      */     try
/*      */     {
/*  828 */       File canonicalBasePath = new File(sBasePath).getCanonicalFile();
/*  829 */       File canonicalFullPath = new File(sFullPath).getCanonicalFile();
/*  830 */       File canonicalFullPathAncestor = canonicalFullPath;
/*  831 */       boolean fullIsInBase = false;
/*  832 */       while ((!fullIsInBase) && (canonicalFullPathAncestor != null))
/*      */       {
/*  834 */         canonicalFullPathAncestor = canonicalFullPathAncestor.getParentFile();
/*  835 */         fullIsInBase = canonicalBasePath.equals(canonicalFullPathAncestor);
/*      */       }
/*      */ 
/*  838 */       if (!fullIsInBase)
/*      */       {
/*  840 */         this.m_logger.error("Potential directory traversal attack detected and rejected. Relative path passed = \"" + a_sRelativePath + "\", canonicalBasePath = \"" + canonicalBasePath + "\", canonicalFullPath = \"" + canonicalFullPath);
/*      */ 
/*  845 */         throw new Bn2Exception("Relative path contains characters used to traverse directories.");
/*      */       }
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  850 */       throw new Bn2Exception("IOException canonicalising path", e);
/*      */     }
/*      */ 
/*  853 */     return sFullPath;
/*      */   }
/*      */ 
/*      */   private String stripDeviceId(String a_sRelativePathWithOptionalDeviceId)
/*      */   {
/*  861 */     int iDelimiterIndex = a_sRelativePathWithOptionalDeviceId.indexOf(':');
/*      */     String sRelativePath;
/*      */     //String sRelativePath;
/*  864 */     if (iDelimiterIndex > 0)
/*      */     {
/*  866 */       sRelativePath = a_sRelativePathWithOptionalDeviceId.substring(iDelimiterIndex + 1);
/*      */     }
/*      */     else
/*      */     {
/*  870 */       sRelativePath = a_sRelativePathWithOptionalDeviceId;
/*      */     }
/*  872 */     return sRelativePath;
/*      */   }
/*      */ 
/*      */   public String getFullBasePathForRelativePath(String a_sRelativePath)
/*      */     throws Bn2Exception
/*      */   {
/*  885 */     long lDeviceId = StorageDeviceUtil.getDeviceIdForRelativePath(a_sRelativePath);
/*      */ 
/*  887 */     StorageDevice device = getDevice(null, lDeviceId);
/*      */ 
/*  889 */     return device.getFullLocalBasePath();
/*      */   }
/*      */ 
/*      */   public StorageDevice getDeviceForRelativePath(String a_sRelativePath)
/*      */     throws Bn2Exception
/*      */   {
/*  900 */     long lDeviceId = StorageDeviceUtil.getDeviceIdForRelativePath(a_sRelativePath);
/*      */ 
/*  902 */     return getDevice(null, lDeviceId);
/*      */   }
/*      */ 
/*      */   public StorageDevice getDevice(DBTransaction a_dbTransaction, long a_lDeviceId)
/*      */     throws Bn2Exception
/*      */   {
/*  914 */     Vector vDevices = getAllDevices(a_dbTransaction);
/*      */ 
/*  916 */     for (int i = 0; i < vDevices.size(); i++)
/*      */     {
/*  918 */       StorageDevice device = (StorageDevice)vDevices.get(i);
/*      */ 
/*  920 */       if (device.getId() == a_lDeviceId)
/*      */       {
/*  922 */         return device;
/*      */       }
/*      */     }
/*  925 */     throw new IllegalArgumentException(c_ksClassName + ".getDevice() : No storage device found for id " + a_lDeviceId);
/*      */   }
/*      */ 
/*      */   private void refreshDeviceUsageData()
/*      */     throws Bn2Exception
/*      */   {
/*  935 */     long lStart = System.currentTimeMillis();
/*      */ 
/*  937 */     Vector<StorageDevice> vDevices = getAllDevices(null);
/*      */ 
/*  939 */     for (StorageDevice device : vDevices)
/*      */     {
/*  941 */       device.runUsageScan();
/*  942 */       updateDeviceUsageData(null, device.getId(), device.getUsedSpaceInBytes(), device.getUsedLocalSpaceInBytes(), device.getLastUsageScan());
/*      */     }
/*      */ 
/*  945 */     invalidateCache();
/*      */ 
/*  947 */     this.m_logger.debug(c_ksClassName + ".refreshDeviceUsageData() : Refreshed all devices in " + (System.currentTimeMillis() - lStart) / 1000L + " seconds");
/*      */   }
/*      */ 
/*      */   public void cleanAllDevices(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  953 */     Vector<StorageDevice> devices = getAllDevices(a_dbTransaction);
/*      */ 
/*  955 */     for (StorageDevice device : devices)
/*      */     {
/*  957 */       device.cleanFileStore();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void refreshDeviceUsageDataAsynchronously()
/*      */   {
/*  966 */     new Thread()
/*      */     {
/*      */       public void run()
/*      */       {
/*  972 */         if (StorageDeviceManager.this.m_boolRunningUpdateTask.compareAndSet(false, true))
/*      */         {
/*      */           try
/*      */           {
/*  976 */             StorageDeviceManager.this.refreshDeviceUsageData();
/*      */           }
/*      */           catch (Bn2Exception e)
/*      */           {
/*  980 */             StorageDeviceManager.this.m_logger.error("Could not update storage device usage data asynchronously due to exception: " + e.getLocalizedMessage(), e);
/*      */           }
/*      */           finally
/*      */           {
/*  984 */             StorageDeviceManager.this.m_boolRunningUpdateTask.set(false);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  966 */     .start();
/*      */   }
/*      */ 
/*      */   public String getAvailableExportDirectory()
/*      */   {
/*      */     try
/*      */     {
/*  998 */       StorageDevice device = getDeviceForNewFile(null, StorageDeviceType.getTypeFor(StoredFileType.EXPORT));
/*  999 */       String sPath = device.getLocalBasePath() + '/' + StoredFileType.EXPORT.getDirectoryName();
/* 1000 */       return FilenameUtils.separatorsToSystem(sPath);
/*      */     }
/*      */     catch (NoAvailableStorageException e)
/*      */     {
/* 1004 */       return null;
/*      */     }
/*      */     catch (Bn2Exception e) {
/*      */     }
/* 1008 */     return null;
/*      */   }
/*      */ 
/*      */   private void invalidateCache()
/*      */   {
/* 1014 */     this.m_bCacheInvalidated = true;
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_manager)
/*      */   {
/* 1019 */     this.m_transactionManager = a_manager;
/*      */   }
/*      */ 
/*      */   public void setScheduleManager(ScheduleManager a_sScheduleManager)
/*      */   {
/* 1024 */     this.m_scheduleManager = a_sScheduleManager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.service.StorageDeviceManager
 * JD-Core Version:    0.6.0
 */