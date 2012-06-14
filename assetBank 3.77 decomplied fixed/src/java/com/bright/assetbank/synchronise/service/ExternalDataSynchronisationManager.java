/*    */ package com.bright.assetbank.synchronise.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.synchronise.bean.ExternalDataSynchronisationTask;
/*    */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*    */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*    */ import com.bright.assetbank.synchronise.constant.SynchronisationSettings;
/*    */ import com.bright.framework.common.service.ScheduleManager;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.lang.reflect.Constructor;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class ExternalDataSynchronisationManager extends ScheduleManager
/*    */   implements SynchronisationConstants
/*    */ {
/*    */   private static final String c_ksClassName = "ExternalDataSynchronisationManager";
/*    */ 
/*    */   public void startup()
/*    */     throws Bn2Exception
/*    */   {
/* 44 */     super.startup();
/* 45 */     prepareSynch(false);
/*    */   }
/*    */ 
/*    */   public void runSynchronisationTasks()
/*    */   {
/* 51 */     prepareSynch(true);
/*    */   }
/*    */ 
/*    */   private void prepareSynch(boolean bRunNow)
/*    */   {
/* 58 */     String sNames = SynchronisationSettings.getSynchTaskNames();
/*    */ 
/* 60 */     if (StringUtil.stringIsPopulated(sNames))
/*    */     {
/* 63 */       String[] aTasks = sNames.split(",");
/* 64 */       String sName = null;
/* 65 */       String sSettings = null;
/* 66 */       String sTask = null;
/*    */ 
/* 68 */       for (int i = 0; i < aTasks.length; i++)
/*    */       {
/* 70 */         sTask = aTasks[i];
/* 71 */         sSettings = sTask + "Settings";
/* 72 */         sName = sTask + "Task";
/*    */         try
/*    */         {
/* 76 */           this.m_logger.debug("ExternalDataSynchronisationManager : Starting up : Scheduling External Datasource Sychronisation tasks");
/*    */ 
/* 79 */           ExternalDataSynchronisationTask task = (ExternalDataSynchronisationTask)(ExternalDataSynchronisationTask)Class.forName("com.bright.assetbank.synchronise.bean.impl." + sName).newInstance();
/*    */ 
/* 82 */           Class settingsClass = Class.forName("com.bright.assetbank.synchronise.constant.impl." + sSettings);
/* 83 */           Constructor con = settingsClass.getConstructors()[0];
/* 84 */           Object[] objArgs = { sTask };
/*    */ 
/* 87 */           ExternalDataSynchronisationSettings settings = null;
/*    */           try
/*    */           {
/* 90 */             settings = (ExternalDataSynchronisationSettings)con.newInstance(objArgs);
/*    */           }
/*    */           catch (Exception e)
/*    */           {
/*    */           }
/*    */ 
/* 97 */           if (settings == null)
/*    */           {
/* 99 */             settings = new ExternalDataSynchronisationSettings(sTask);
/*    */           }
/*    */ 
/* 102 */           task.setSettings(settings);
/*    */ 
/* 105 */           if (bRunNow)
/*    */           {
/* 107 */             task.run();
/*    */           }
/*    */           else
/*    */           {
/* 111 */             int iHourOfDay = settings.getSynchHourOfDay();
/* 112 */             scheduleDailyTask(task, iHourOfDay, false);
/*    */           }
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/* 117 */           this.m_logger.error("ExternalDataSynchronisationManager.startup: Unable to schedule " + sName + " synchronisation task: ", e);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.service.ExternalDataSynchronisationManager
 * JD-Core Version:    0.6.0
 */