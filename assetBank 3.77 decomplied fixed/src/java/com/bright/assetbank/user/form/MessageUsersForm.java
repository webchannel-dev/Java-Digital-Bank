/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class MessageUsersForm extends Bn2Form
/*     */ {
/*  35 */   private Vector m_vGroups = null;
/*     */ 
/*  37 */   private Vector m_vecGroupIds = null;
/*     */ 
/*  39 */   private String m_sMessageSubject = null;
/*     */ 
/*  41 */   private String m_sMessageBody = null;
/*  42 */   private String m_sCompleteBody = null;
/*     */ 
/*  44 */   private String m_sToUser = null;
/*     */ 
/*  46 */   private int m_iUserCount = -1;
/*     */ 
/*     */   public String getMessageBody()
/*     */   {
/*  53 */     return this.m_sMessageBody;
/*     */   }
/*     */ 
/*     */   public void setMessageBody(String a_sMessageBody)
/*     */   {
/*  61 */     this.m_sMessageBody = a_sMessageBody;
/*     */   }
/*     */ 
/*     */   public String getMessageSubject()
/*     */   {
/*  69 */     return this.m_sMessageSubject;
/*     */   }
/*     */ 
/*     */   public void setMessageSubject(String a_sMessageSubject)
/*     */   {
/*  77 */     this.m_sMessageSubject = a_sMessageSubject;
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/*  85 */     return this.m_vGroups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_vGroups)
/*     */   {
/*  93 */     this.m_vGroups = a_vGroups;
/*     */   }
/*     */ 
/*     */   public boolean getSelectedGroup(int a_iGroupId)
/*     */   {
/* 101 */     Long longVal = null;
/*     */ 
/* 103 */     if (getGroupIds() != null)
/*     */     {
/* 105 */       for (int i = 0; i < getGroupIds().size(); i++)
/*     */       {
/* 107 */         longVal = (Long)(Long)getGroupIds().elementAt(i);
/*     */ 
/* 109 */         if (a_iGroupId == longVal.longValue())
/*     */         {
/* 111 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   public Vector getGroupIds()
/*     */   {
/* 124 */     return this.m_vecGroupIds;
/*     */   }
/*     */ 
/*     */   public void setGroupIds(Vector a_GroupIds)
/*     */   {
/* 132 */     this.m_vecGroupIds = a_GroupIds;
/*     */   }
/*     */ 
/*     */   public String getToUser()
/*     */   {
/* 137 */     return this.m_sToUser;
/*     */   }
/*     */ 
/*     */   public void setToUser(String a_sToUser)
/*     */   {
/* 142 */     this.m_sToUser = a_sToUser;
/*     */   }
/*     */ 
/*     */   public void setCompleteBody(String a_sCompleteBody)
/*     */   {
/* 147 */     this.m_sCompleteBody = a_sCompleteBody;
/*     */   }
/*     */ 
/*     */   public String getCompleteBody()
/*     */   {
/* 152 */     return this.m_sCompleteBody;
/*     */   }
/*     */ 
/*     */   public void setUserCount(int a_iUserCount)
/*     */   {
/* 157 */     this.m_iUserCount = a_iUserCount;
/*     */   }
/*     */ 
/*     */   public int getUserCount()
/*     */   {
/* 162 */     return this.m_iUserCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.MessageUsersForm
 * JD-Core Version:    0.6.0
 */