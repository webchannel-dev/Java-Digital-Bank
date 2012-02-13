/*     */ package com.bright.framework.user.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.user.bean.Permission;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.struts.util.LabelValueBean;
/*     */ 
/*     */ public class UserForm extends Bn2Form
/*     */ {
/*  37 */   protected User m_user = null;
/*  38 */   private String m_sPassword = null;
/*  39 */   private String m_sConfirmPassword = null;
/*  40 */   private Vector m_vecPermissions = null;
/*  41 */   private String[] m_asSelectedPermissions = null;
/*  42 */   private Vector m_vecRoles = null;
/*     */ 
/*     */   public LabelValueBean[] getPermissionOptions()
/*     */   {
/*  57 */     LabelValueBean[] alvbPermissionOptions = null;
/*  58 */     if (this.m_vecPermissions == null)
/*     */     {
/*  60 */       alvbPermissionOptions = new LabelValueBean[0];
/*     */     }
/*     */     else
/*     */     {
/*  64 */       alvbPermissionOptions = new LabelValueBean[this.m_vecPermissions.size()];
/*  65 */       for (int i = 0; i < this.m_vecPermissions.size(); i++)
/*     */       {
/*  67 */         Permission perm = (Permission)this.m_vecPermissions.get(i);
/*  68 */         alvbPermissionOptions[i] = new LabelValueBean(perm.getDescription(), Long.toString(perm.getId()));
/*     */       }
/*     */     }
/*  71 */     return alvbPermissionOptions;
/*     */   }
/*     */ 
/*     */   public void setPermissions(Vector a_vecPermissions) {
/*  75 */     this.m_vecPermissions = a_vecPermissions;
/*     */   }
/*     */ 
/*     */   public String[] getSelectedPermissions()
/*     */   {
/*  81 */     return this.m_asSelectedPermissions;
/*     */   }
/*     */ 
/*     */   public void setSelectedPermissions(String[] a_asSelectedPermissions) {
/*  85 */     this.m_asSelectedPermissions = a_asSelectedPermissions;
/*     */   }
/*     */ 
/*     */   public Vector getRoles()
/*     */   {
/*  90 */     return this.m_vecRoles;
/*     */   }
/*     */ 
/*     */   public void setRoles(Vector a_vecRoles)
/*     */   {
/*  95 */     this.m_vecRoles = a_vecRoles;
/*     */   }
/*     */ 
/*     */   public void setPassword(String a_sPassword)
/*     */   {
/* 100 */     this.m_sPassword = a_sPassword;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/* 104 */     return this.m_sPassword;
/*     */   }
/*     */ 
/*     */   public void setConfirmPassword(String a_sConfirmPassword) {
/* 108 */     this.m_sConfirmPassword = a_sConfirmPassword;
/*     */   }
/*     */ 
/*     */   public String getConfirmPassword() {
/* 112 */     return this.m_sConfirmPassword;
/*     */   }
/*     */ 
/*     */   public User getUser() {
/* 116 */     if (this.m_user == null)
/*     */     {
/* 118 */       this.m_user = new User();
/*     */     }
/* 120 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setUser(User a_user) {
/* 124 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public void setSelectedPermissions(Collection a_colPermissions)
/*     */   {
/* 138 */     if (a_colPermissions != null)
/*     */     {
/* 140 */       this.m_asSelectedPermissions = new String[a_colPermissions.size()];
/*     */ 
/* 142 */       Iterator itPermissions = a_colPermissions.iterator();
/*     */ 
/* 145 */       int i = 0;
/* 146 */       while (itPermissions.hasNext())
/*     */       {
/* 148 */         Permission permission = (Permission)itPermissions.next();
/* 149 */         this.m_asSelectedPermissions[(i++)] = Long.toString(permission.getId());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 154 */       this.m_asSelectedPermissions = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.form.UserForm
 * JD-Core Version:    0.6.0
 */