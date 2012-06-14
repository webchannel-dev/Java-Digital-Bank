/*     */ package com.bright.assetbank.workflow.bean;
/*     */ 
/*     */ import com.bright.framework.workflow.bean.State;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetsInState
/*     */   implements Comparable<AssetsInState>
/*     */ {
/*     */   private State m_state;
/*  35 */   private Vector m_assetList = new Vector();
/*     */   private int m_numberAssetsActual;
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  51 */     String sKey = this.m_state.getVariationName() + ":" + this.m_state.getWorkflowName() + ":" + this.m_state.getName();
/*     */ 
/*  53 */     return sKey;
/*     */   }
/*     */ 
/*     */   public int compareTo(AssetsInState a_ais)
/*     */   {
/*  67 */     boolean bVisToOwnerState1 = this.m_state.getVisibleToOwner();
/*  68 */     int iState1 = this.m_state.getStateNumber();
/*  69 */     String sVar1 = this.m_state.getVariationName();
/*  70 */     String sWorkflow1 = this.m_state.getWorkflowName();
/*     */ 
/*  72 */     boolean bVisToOwnerState2 = a_ais.getState().getVisibleToOwner();
/*  73 */     int iState2 = a_ais.getState().getStateNumber();
/*  74 */     String sVar2 = a_ais.getState().getVariationName();
/*  75 */     String sWorkflow2 = a_ais.getState().getWorkflowName();
/*     */ 
/*  78 */     int iRet = sWorkflow1.compareTo(sWorkflow2);
/*  79 */     if (iRet == 0)
/*     */     {
/*  81 */       iRet = sVar1.compareTo(sVar2);
/*     */ 
/*  83 */       if (iRet == 0)
/*     */       {
/*  85 */         if ((bVisToOwnerState1) && (!bVisToOwnerState2))
/*     */         {
/*  87 */           iRet = -1;
/*     */         }
/*  89 */         else if ((bVisToOwnerState2) && (!bVisToOwnerState1))
/*     */         {
/*  91 */           iRet = 1;
/*     */         }
/*     */         else
/*     */         {
/*  95 */           if (iState1 < iState2) iRet = -1;
/*  96 */           if (iState1 > iState2) iRet = 1;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 101 */     return iRet;
/*     */   }
/*     */ 
/*     */   public void incrementNumberAssetsActual()
/*     */   {
/* 111 */     this.m_numberAssetsActual += 1;
/*     */   }
/*     */ 
/*     */   public int getNumberAssets()
/*     */   {
/* 122 */     return this.m_assetList.size();
/*     */   }
/*     */ 
/*     */   public State getState()
/*     */   {
/* 133 */     return this.m_state;
/*     */   }
/*     */ 
/*     */   public void setState(State a_sState)
/*     */   {
/* 143 */     this.m_state = a_sState;
/*     */   }
/*     */ 
/*     */   public Vector getAssetList()
/*     */   {
/* 153 */     return this.m_assetList;
/*     */   }
/*     */ 
/*     */   public void setAssetList(Vector a_sAssetList)
/*     */   {
/* 163 */     this.m_assetList = a_sAssetList;
/*     */   }
/*     */ 
/*     */   public int getNumberAssetsActual()
/*     */   {
/* 174 */     return this.m_numberAssetsActual;
/*     */   }
/*     */ 
/*     */   public void setNumberAssetsActual(int a_sNumberAssetsActual)
/*     */   {
/* 184 */     this.m_numberAssetsActual = a_sNumberAssetsActual;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.bean.AssetsInState
 * JD-Core Version:    0.6.0
 */