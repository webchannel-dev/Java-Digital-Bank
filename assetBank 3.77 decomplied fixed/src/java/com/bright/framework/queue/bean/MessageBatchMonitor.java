/*     */ package com.bright.framework.queue.bean;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class MessageBatchMonitor
/*     */ {
/*  81 */   private static Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */   private static final int PROCESSES_LIMIT = 128;
/*  88 */   Map<Long, BatchProcess> m_mapBatchProcesses = Collections.synchronizedMap(new LinkedHashMap());
/*     */ 
/*     */   public BatchProcess startBatchProcess(long a_id)
/*     */   {
/*  99 */     BatchProcess process = new BatchProcess();
/* 100 */     this.m_mapBatchProcesses.put(Long.valueOf(a_id), process);
/*     */ 
/* 104 */     Iterator iter = this.m_mapBatchProcesses.entrySet().iterator();
/* 105 */     while ((this.m_mapBatchProcesses.size() > 128) && (iter.hasNext()))
/*     */     {
/* 107 */       Map.Entry entry = (Map.Entry)iter.next();
/* 108 */       Long id = (Long)entry.getKey();
/* 109 */       BatchProcess existingProcess = (BatchProcess)entry.getValue();
/* 110 */       if (existingProcess.getStatus() != Status.RUNNING)
/*     */       {
/* 112 */         this.m_mapBatchProcesses.remove(id);
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return process;
/*     */   }
/*     */ 
/*     */   public void endBatchProcess(long a_id)
/*     */   {
/* 126 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 127 */     if (process != null)
/*     */     {
/* 129 */       process.setStatus(Status.SUCCESS);
/*     */     }
/*     */     else
/*     */     {
/* 136 */       m_logger.error("endBatchProcess: no process found with ID " + a_id, new RuntimeException());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void failBatchProcess(long a_id)
/*     */   {
/* 147 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 148 */     if (process != null)
/*     */     {
/* 150 */       process.setStatus(Status.FAILURE);
/*     */     }
/*     */     else
/*     */     {
/* 157 */       m_logger.error("failBatchProcess: no process found with ID " + a_id, new RuntimeException());
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void addMessage(long a_id, String a_message)
/*     */   {
/* 169 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 170 */     if (process != null)
/*     */     {
/* 172 */       process.addMessage(a_message);
/*     */     }
/*     */ 
/* 179 */     m_logger.info("MessageBatchMonitor: " + a_id + ": " + a_message);
/*     */   }
/*     */ 
/*     */   public void clearMessages(long a_lId)
/*     */   {
/* 185 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_lId));
/* 186 */     if (process != null)
/*     */     {
/* 188 */       process.clearMessages();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector<String> getMessages(long a_id)
/*     */   {
/* 199 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 200 */     return process == null ? new Vector() : process.getMessages();
/*     */   }
/*     */ 
/*     */   public boolean isBatchInProgress(long a_id)
/*     */   {
/* 210 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 211 */     return (process != null) && (process.getStatus() == Status.RUNNING);
/*     */   }
/*     */ 
/*     */   public Status getStatus(long a_id)
/*     */   {
/* 221 */     BatchProcess process = (BatchProcess)this.m_mapBatchProcesses.get(Long.valueOf(a_id));
/* 222 */     return process == null ? null : process.getStatus();
/*     */   }
/*     */ 
/*     */   public int getInProgressCount()
/*     */   {
/* 232 */     int ret = 0;
/* 233 */     for (BatchProcess process : this.m_mapBatchProcesses.values())
/*     */     {
/* 235 */       ret += (process.getStatus() == Status.RUNNING ? 1 : 0);
/*     */     }
/* 237 */     return ret;
/*     */   }
/*     */ 
/*     */   private class BatchProcess
/*     */   {
/*     */     private MessageBatchMonitor.Status m_status;
/*     */     private Vector<String> m_messages;
/*     */ 
/*     */     public BatchProcess()
/*     */     {
/*  51 */       this.m_messages = new Vector();
/*  52 */       this.m_status = MessageBatchMonitor.Status.RUNNING;
/*     */     }
/*     */ 
/*     */     public MessageBatchMonitor.Status getStatus()
/*     */     {
/*  57 */       return this.m_status;
/*     */     }
/*     */ 
/*     */     public void setStatus(MessageBatchMonitor.Status a_status)
/*     */     {
/*  62 */       this.m_status = a_status;
/*     */     }
/*     */ 
/*     */     public Vector<String> getMessages()
/*     */     {
/*  67 */       return this.m_messages;
/*     */     }
/*     */ 
/*     */     public void addMessage(String a_message)
/*     */     {
/*  72 */       this.m_messages.add(a_message);
/*     */     }
/*     */ 
/*     */     public void clearMessages()
/*     */     {
/*  77 */       this.m_messages = new Vector();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Status
/*     */   {
/*  42 */     RUNNING, SUCCESS, FAILURE;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.queue.bean.MessageBatchMonitor
 * JD-Core Version:    0.6.0
 */