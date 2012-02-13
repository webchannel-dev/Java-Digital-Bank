/*     */ package com.bright.framework.common.service;
/*     */ 
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.common.bean.AsynchronousTimerTask;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ScheduleManager extends Bn2Manager
/*     */ {
/*  39 */   private Vector m_vecTasks = new Vector();
/*  40 */   private Timer m_timer = new Timer();
/*     */ 
/*     */   public void schedule(TimerTask a_task, long a_lInitialDelay, long a_lPeriod, boolean a_bRunAsynchronously)
/*     */   {
/*  60 */     if (a_task != null)
/*     */     {
/*  62 */       if (a_bRunAsynchronously)
/*     */       {
/*  64 */         a_task = new AsynchronousTimerTask(a_task);
/*     */       }
/*     */ 
/*  67 */       this.m_timer.schedule(a_task, a_lInitialDelay, a_lPeriod);
/*  68 */       this.m_vecTasks.add(a_task);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scheduleDailyTask(TimerTask a_task, int a_iHourOfDay, boolean a_bRunAsynchronously)
/*     */   {
/*  88 */     long lNow = 0L;
/*  89 */     Calendar cal = Calendar.getInstance();
/*     */ 
/*  92 */     cal.setTime(new Date());
/*  93 */     lNow = cal.getTime().getTime();
/*     */ 
/*  96 */     if (cal.get(11) >= a_iHourOfDay)
/*     */     {
/*  99 */       cal.add(5, 1);
/*     */     }
/*     */ 
/* 103 */     cal.set(11, a_iHourOfDay);
/* 104 */     cal.set(12, 0);
/* 105 */     cal.set(13, 0);
/* 106 */     cal.set(14, 0);
/*     */ 
/* 109 */     long lInitialDelay = cal.getTime().getTime() - lNow;
/*     */ 
/* 112 */     schedule(a_task, lInitialDelay, 86400000L, a_bRunAsynchronously);
/*     */   }
/*     */ 
/*     */   public void schedule(TimerTask a_task, Date a_dtDate, boolean a_bRunAsynchronously)
/*     */   {
/* 131 */     if (a_task != null)
/*     */     {
/* 133 */       if (a_bRunAsynchronously)
/*     */       {
/* 135 */         a_task = new AsynchronousTimerTask(a_task);
/*     */       }
/*     */ 
/* 138 */       this.m_timer.schedule(a_task, a_dtDate);
/* 139 */       this.m_vecTasks.add(a_task);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 155 */     for (int i = 0; i < this.m_vecTasks.size(); i++)
/*     */     {
/* 157 */       TimerTask task = (TimerTask)(TimerTask)this.m_vecTasks.get(i);
/*     */ 
/* 159 */       if (task == null)
/*     */         continue;
/* 161 */       task.cancel();
/*     */     }
/*     */ 
/* 165 */     this.m_timer.cancel();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.service.ScheduleManager
 * JD-Core Version:    0.6.0
 */