/*     */ package com.bright.framework.workflow.bean;
/*     */ 
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class State
/*     */ {
/*  35 */   private String m_sName = null;
/*     */ 
/*  40 */   private String m_sDescription = null;
/*     */ 
/*  45 */   private String m_sHelpText = null;
/*     */ 
/*  50 */   private String m_sVisibleTo = null;
/*     */ 
/*  55 */   private Vector m_alertList = null;
/*     */ 
/*  60 */   private Vector m_transitionList = null;
/*     */   private Vector m_eventList;
/*  70 */   private String m_workflowName = "";
/*     */ 
/*  75 */   private String m_variationName = "";
/*     */   private int m_stateNumber;
/*     */ 
/*     */   public boolean getVisibleToOwner()
/*     */   {
/*  86 */     boolean b = this.m_sVisibleTo.equalsIgnoreCase("owner");
/*  87 */     return b;
/*     */   }
/*     */ 
/*     */   public int getNumTransitions()
/*     */   {
/*  97 */     int iNumber = 0;
/*     */ 
/*  99 */     if (this.m_transitionList != null)
/*     */     {
/* 101 */       iNumber = this.m_transitionList.size();
/*     */     }
/* 103 */     return iNumber;
/*     */   }
/*     */ 
/*     */   public int getTransitionNumberByName(String a_sName)
/*     */   {
/* 116 */     int iNumber = -1;
/*     */ 
/* 118 */     if (this.m_transitionList != null)
/*     */     {
/* 120 */       for (int i = 0; i < this.m_transitionList.size(); i++)
/*     */       {
/* 123 */         Transition trans = (Transition)this.m_transitionList.get(i);
/*     */ 
/* 126 */         if (!trans.getName().equals(a_sName))
/*     */           continue;
/* 128 */         iNumber = i;
/* 129 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     return iNumber;
/*     */   }
/*     */ 
/*     */   public Transition getTransition(int a_iNumber)
/*     */   {
/* 149 */     Transition trans = null;
/*     */ 
/* 151 */     if ((a_iNumber >= 0) && (a_iNumber < this.m_transitionList.size()))
/*     */     {
/* 153 */       trans = (Transition)this.m_transitionList.get(a_iNumber);
/*     */     }
/*     */ 
/* 156 */     return trans;
/*     */   }
/*     */ 
/*     */   public boolean getTransitionsHaveHelpText()
/*     */   {
/* 167 */     boolean bResult = false;
/*     */ 
/* 169 */     for (int i = 0; i < this.m_transitionList.size(); i++)
/*     */     {
/* 172 */       Transition trans = (Transition)this.m_transitionList.get(i);
/*     */ 
/* 175 */       if (!StringUtil.stringIsPopulated(trans.getHelpText()))
/*     */         continue;
/* 177 */       bResult = true;
/* 178 */       break;
/*     */     }
/*     */ 
/* 182 */     return bResult;
/*     */   }
/*     */ 
/*     */   public boolean getTransitionsHaveMessages()
/*     */   {
/* 192 */     boolean bResult = false;
/*     */ 
/* 194 */     for (int i = 0; i < this.m_transitionList.size(); i++)
/*     */     {
/* 197 */       Transition trans = (Transition)this.m_transitionList.get(i);
/*     */ 
/* 200 */       if (!trans.getHasMessage())
/*     */         continue;
/* 202 */       bResult = true;
/* 203 */       break;
/*     */     }
/*     */ 
/* 207 */     return bResult;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 218 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/* 226 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public Vector getTransitionList()
/*     */   {
/* 234 */     return this.m_transitionList;
/*     */   }
/*     */ 
/*     */   public void setTransitionList(Vector a_vecTransitions)
/*     */   {
/* 242 */     this.m_transitionList = a_vecTransitions;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 250 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 258 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getHelpText()
/*     */   {
/* 266 */     return this.m_sHelpText;
/*     */   }
/*     */ 
/*     */   public void setHelpText(String a_sHelpText)
/*     */   {
/* 274 */     this.m_sHelpText = a_sHelpText;
/*     */   }
/*     */ 
/*     */   public Vector getAlertList()
/*     */   {
/* 282 */     return this.m_alertList;
/*     */   }
/*     */ 
/*     */   public void setAlertList(Vector a_sAlertList)
/*     */   {
/* 290 */     this.m_alertList = a_sAlertList;
/*     */   }
/*     */ 
/*     */   public String getVisibleTo()
/*     */   {
/* 298 */     return this.m_sVisibleTo;
/*     */   }
/*     */ 
/*     */   public void setVisibleTo(String a_sVisibleTo)
/*     */   {
/* 306 */     this.m_sVisibleTo = a_sVisibleTo;
/*     */   }
/*     */ 
/*     */   public Vector getEventList()
/*     */   {
/* 315 */     return this.m_eventList;
/*     */   }
/*     */ 
/*     */   public void setEventList(Vector a_sEventList)
/*     */   {
/* 323 */     this.m_eventList = a_sEventList;
/*     */   }
/*     */ 
/*     */   public String getWorkflowName()
/*     */   {
/* 332 */     return this.m_workflowName;
/*     */   }
/*     */ 
/*     */   public void setWorkflowName(String a_sWorkflowName)
/*     */   {
/* 340 */     this.m_workflowName = a_sWorkflowName;
/*     */   }
/*     */ 
/*     */   public String getVariationName()
/*     */   {
/* 348 */     return this.m_variationName;
/*     */   }
/*     */ 
/*     */   public void setVariationName(String a_sVariationName)
/*     */   {
/* 356 */     this.m_variationName = a_sVariationName;
/*     */   }
/*     */ 
/*     */   public int getStateNumber()
/*     */   {
/* 375 */     return this.m_stateNumber;
/*     */   }
/*     */ 
/*     */   public void setStateNumber(int a_sStateNumber)
/*     */   {
/* 385 */     this.m_stateNumber = a_sStateNumber;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.bean.State
 * JD-Core Version:    0.6.0
 */