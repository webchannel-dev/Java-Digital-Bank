/*     */ package com.bright.framework.image.util;
/*     */ 
/*     */ import com.bright.framework.image.bean.Colour;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ImageMagickOptionList
/*     */   implements Cloneable
/*     */ {
/*     */   private Vector<String> m_options;
/*  44 */   private int m_iInputFilenameIndex = -1;
/*  45 */   private int m_iOutputFilenameIndex = -1;
/*  46 */   private String m_sInputFileIdentifier = null;
/*  47 */   private boolean m_bGetColourProfile = false;
/*  48 */   private boolean m_bDoNotCache = false;
/*     */ 
/*     */   public ImageMagickOptionList()
/*     */   {
/*  52 */     this.m_options = new Vector();
/*     */   }
/*     */ 
/*     */   List<String> getOptions()
/*     */   {
/*  60 */     return this.m_options;
/*     */   }
/*     */ 
/*     */   public void addInputFilename(String a_sFilename)
/*     */   {
/*  65 */     this.m_options.add(a_sFilename);
/*     */ 
/*  68 */     if (this.m_iInputFilenameIndex < 0)
/*     */     {
/*  70 */       this.m_iInputFilenameIndex = (this.m_options.size() - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAuxiliaryInputFilename(String a_sFilename)
/*     */   {
/*  79 */     this.m_options.add(a_sFilename);
/*     */   }
/*     */ 
/*     */   public void addOutputFilename(String a_sFilename)
/*     */   {
/*  84 */     this.m_options.add(a_sFilename);
/*     */ 
/*  87 */     if (this.m_iOutputFilenameIndex < 0)
/*     */     {
/*  89 */       this.m_iOutputFilenameIndex = (this.m_options.size() - 1);
/*     */     }
/*     */ 
/*  92 */     if (a_sFilename.toLowerCase().endsWith(".icc"))
/*     */     {
/*  94 */       this.m_bGetColourProfile = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void specifyInputLayer(int a_iLayer)
/*     */   {
/* 100 */     if ((this.m_iInputFilenameIndex >= 0) && (a_iLayer >= 0))
/*     */     {
/* 102 */       String sFilename = (String)this.m_options.get(this.m_iInputFilenameIndex);
/* 103 */       sFilename.replaceAll("\\[[0-9]+\\]", "");
/* 104 */       this.m_options.setElementAt(sFilename + "[" + a_iLayer + "]", this.m_iInputFilenameIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addResize(int a_iWidth, int a_iHeight, String a_sOption)
/*     */   {
/* 121 */     String sVal = "";
/*     */ 
/* 123 */     if (a_iWidth >= 0)
/*     */     {
/* 125 */       sVal = sVal + a_iWidth;
/*     */     }
/*     */ 
/* 128 */     if (a_iHeight >= 0)
/*     */     {
/* 130 */       sVal = sVal + "x" + a_iHeight;
/*     */     }
/*     */ 
/* 133 */     if (a_sOption != null)
/*     */     {
/* 135 */       sVal = sVal + a_sOption;
/*     */     }
/*     */ 
/* 138 */     add("-resize", sVal);
/*     */   }
/*     */ 
/*     */   public int[] addResize(int a_iWidth, int a_iHeight)
/*     */   {
/* 148 */     addResize(a_iWidth, a_iHeight, "!");
/* 149 */     return new int[] { a_iWidth, a_iHeight };
/*     */   }
/*     */ 
/*     */   public void addFormat(String a_sValue)
/*     */   {
/* 154 */     add("-format", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addWatermark(float a_fBrightness)
/*     */   {
/* 159 */     add("-dissolve", "%" + (int)(a_fBrightness * 100.0F));
/*     */   }
/*     */ 
/*     */   public void addCredit(String a_sBackgroundColour, String a_sTextColour, String a_sText, String a_sImageText, String a_sGravity)
/*     */   {
/* 164 */     addGravity(a_sGravity);
/* 165 */     addBackground(a_sBackgroundColour);
/* 166 */     addSplice("0x20");
/* 167 */     addFont("Candice");
/* 168 */     addPointSize("18");
/* 169 */     addDraw("text 0,0 '" + a_sText + " " + a_sImageText + "'");
/* 170 */     addFill(a_sTextColour);
/*     */   }
/*     */ 
/*     */   public void addPointSize(String a_sValue)
/*     */   {
/* 175 */     add("-pointsize", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addFont(String a_sValue)
/*     */   {
/* 180 */     add("-font", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addFill(String a_sValue)
/*     */   {
/* 185 */     add("-fill", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addTint(String a_sValue)
/*     */   {
/* 190 */     add("-tint", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addDraw(String a_sValue)
/*     */   {
/* 195 */     add("-draw", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addSplice(String a_sValue)
/*     */   {
/* 200 */     add("-splice", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addGravity(String a_sValue)
/*     */   {
/* 205 */     add("-gravity", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addBackground(String a_sValue)
/*     */   {
/* 210 */     add("-background", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addCompression(String a_sValue)
/*     */   {
/* 215 */     add("-compress", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addCrop(int a_iWidth, int a_iHeight, int a_iXOffset, int a_iYOffset)
/*     */   {
/* 220 */     add("-crop", a_iWidth + "x" + a_iHeight + "+" + a_iXOffset + "+" + a_iYOffset + "!");
/*     */   }
/*     */ 
/*     */   public void addQuality(float a_fValue)
/*     */   {
/* 229 */     if (a_fValue > 0.0F)
/*     */     {
/* 231 */       add("-quality", Integer.toString((int)(a_fValue * 100.0F)));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addStrip()
/*     */   {
/* 244 */     add("-strip");
/*     */   }
/*     */ 
/*     */   public void addFlatten()
/*     */   {
/* 249 */     add("-flatten");
/*     */   }
/*     */ 
/*     */   public void addDensity(int a_iVal)
/*     */   {
/* 254 */     String sVal = Integer.toString(a_iVal);
/* 255 */     add("-units", "PixelsPerInch");
/* 256 */     add("-density", sVal);
/*     */   }
/*     */ 
/*     */   public void addDensity(int a_iWidth, int a_iHeight)
/*     */   {
/* 261 */     String sVal = a_iWidth + "x" + a_iHeight;
/* 262 */     add("-density", sVal);
/*     */   }
/*     */ 
/*     */   public void addUnits(String a_sValue)
/*     */   {
/* 267 */     add("-units", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addType(String a_sValue)
/*     */   {
/* 272 */     add("-type", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addDepth(String a_sValue)
/*     */   {
/* 277 */     add("-depth", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addAlpha(String a_sValue)
/*     */   {
/* 283 */     addImmediatelyAfterInputFilename("-alpha", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addInputDensity(int a_iVal)
/*     */   {
/* 289 */     String sVal = Integer.toString(a_iVal);
/* 290 */     addBeforeInputFilename("-units");
/* 291 */     addBeforeInputFilename("PixelsPerInch");
/* 292 */     addBeforeInputFilename("-density");
/* 293 */     addBeforeInputFilename(sVal);
/*     */   }
/*     */ 
/*     */   public void addDefine(String a_sKeyVal)
/*     */   {
/* 299 */     addBeforeInputFilename("-define");
/* 300 */     addBeforeInputFilename(a_sKeyVal);
/*     */   }
/*     */ 
/*     */   public void addUseCropbox()
/*     */   {
/* 305 */     addDefine("pdf:use-cropbox=true");
/*     */   }
/*     */ 
/*     */   public void addRotateClockwise(int a_iAngle)
/*     */   {
/* 313 */     add("-rotate", "" + a_iAngle);
/*     */   }
/*     */ 
/*     */   public void addCompress(String a_sValue)
/*     */   {
/* 318 */     add("-compress", a_sValue);
/*     */   }
/*     */ 
/*     */   public void addProfile(String a_sProfile)
/*     */   {
/* 323 */     add("-profile", a_sProfile);
/*     */   }
/*     */ 
/*     */   protected void addToImageStack(ImageMagickOptionList a_options, String a_sFilename)
/*     */   {
/* 328 */     add("(");
/* 329 */     this.m_options.addAll(a_options.toList());
/* 330 */     add(a_sFilename);
/* 331 */     add(")");
/*     */   }
/*     */ 
/*     */   public List toList()
/*     */   {
/* 336 */     return (List)this.m_options.clone();
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 341 */     ImageMagickOptionList clone = new ImageMagickOptionList();
/* 342 */     clone.m_options = ((Vector)this.m_options.clone());
/* 343 */     return clone;
/*     */   }
/*     */ 
/*     */   protected void add(String a_flag, String a_sVal)
/*     */   {
/* 348 */     add(a_flag);
/*     */ 
/* 350 */     if (a_sVal != null)
/*     */     {
/* 352 */       add(a_sVal);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void add(String a_flag)
/*     */   {
/* 358 */     this.m_options.add(a_flag);
/*     */   }
/*     */ 
/*     */   protected void addImmediatelyAfterInputFilename(String a_flag, String a_sValue)
/*     */   {
/* 364 */     this.m_options.add(this.m_iInputFilenameIndex + 1, a_sValue);
/* 365 */     this.m_options.add(this.m_iInputFilenameIndex + 1, a_flag);
/*     */ 
/* 368 */     if (this.m_iOutputFilenameIndex >= 0)
/*     */     {
/* 370 */       this.m_iOutputFilenameIndex += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addBeforeInputFilename(String a_flag)
/*     */   {
/* 377 */     if (this.m_iInputFilenameIndex >= 0)
/*     */     {
/* 380 */       this.m_options.add(this.m_iInputFilenameIndex, a_flag);
/* 381 */       this.m_iInputFilenameIndex += 1;
/*     */ 
/* 384 */       if (this.m_iOutputFilenameIndex >= 0)
/*     */       {
/* 386 */         this.m_iOutputFilenameIndex += 1;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 392 */       add(a_flag);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addCompose(String a_sVal)
/*     */   {
/* 398 */     add("-compose", a_sVal);
/*     */   }
/*     */ 
/*     */   public void addComposite()
/*     */   {
/* 403 */     add("-composite");
/*     */   }
/*     */ 
/*     */   void addExecCommand(String a_sCommand)
/*     */   {
/* 408 */     this.m_options.add(0, a_sCommand);
/*     */ 
/* 410 */     this.m_iInputFilenameIndex += 1;
/* 411 */     this.m_iOutputFilenameIndex += 1;
/*     */   }
/*     */ 
/*     */   public void addSetColour(Colour a_colour)
/*     */   {
/* 420 */     addSetColour("#" + ColourUtil.hexStringFromColour(a_colour));
/*     */   }
/*     */ 
/*     */   public void addSetColour(String a_colour)
/*     */   {
/* 431 */     addFill(a_colour);
/* 432 */     this.m_options.add("-colorize");
/* 433 */     this.m_options.add("100%");
/*     */   }
/*     */ 
/*     */   public void addImageStack(ImageMagickOptionList a_stackOptions)
/*     */   {
/* 444 */     this.m_options.add("(");
/* 445 */     if ((this.m_iInputFilenameIndex < 0) && (a_stackOptions.m_iInputFilenameIndex >= 0))
/*     */     {
/* 447 */       this.m_iInputFilenameIndex = (this.m_options.size() + a_stackOptions.m_iInputFilenameIndex);
/*     */     }
/* 449 */     this.m_options.addAll(a_stackOptions.m_options);
/* 450 */     this.m_options.add(")");
/*     */   }
/*     */ 
/*     */   public String getOutputFile()
/*     */   {
/* 455 */     if ((this.m_iOutputFilenameIndex > 0) && (this.m_options.size() > this.m_iOutputFilenameIndex))
/*     */     {
/* 457 */       return (String)this.m_options.get(this.m_iOutputFilenameIndex);
/*     */     }
/* 459 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInputFileIdentifier(String originalFilePath)
/*     */   {
/* 472 */     this.m_sInputFileIdentifier = originalFilePath;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 480 */     int iPrime = 31;
/* 481 */     int result = 1;
/*     */ 
/* 483 */     if (this.m_options == null)
/*     */     {
/* 485 */       result = 31 * result;
/*     */     }
/*     */     else
/*     */     {
/* 489 */       for (int i = 0; i < this.m_options.size(); i++)
/*     */       {
/* 492 */         if ((i == this.m_iOutputFilenameIndex) || ((this.m_sInputFileIdentifier != null) && (i == this.m_iInputFilenameIndex)))
/*     */           continue;
/* 494 */         Object obj = this.m_options.get(i);
/* 495 */         result = 31 * result + (obj == null ? 0 : obj.hashCode());
/*     */       }
/*     */ 
/* 499 */       if (this.m_sInputFileIdentifier != null)
/*     */       {
/* 501 */         result = 31 * result + this.m_sInputFileIdentifier.hashCode();
/*     */       }
/*     */ 
/* 504 */       if (this.m_bGetColourProfile)
/*     */       {
/* 506 */         result = 31 * result;
/*     */       }
/*     */     }
/*     */ 
/* 510 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 518 */     if (this == obj)
/*     */     {
/* 520 */       return true;
/*     */     }
/* 522 */     if (obj == null)
/*     */     {
/* 524 */       return false;
/*     */     }
/* 526 */     if (getClass() != obj.getClass())
/*     */     {
/* 528 */       return false;
/*     */     }
/* 530 */     ImageMagickOptionList other = (ImageMagickOptionList)obj;
/* 531 */     if (this.m_options == null)
/*     */     {
/* 533 */       if (other.m_options != null)
/*     */       {
/* 535 */         return false;
/*     */       }
/*     */     } else {
/* 538 */       if (this.m_options.size() != other.m_options.size())
/*     */       {
/* 540 */         return false;
/*     */       }
/*     */ 
/* 544 */       if (this.m_sInputFileIdentifier == null)
/*     */       {
/* 546 */         if (other.m_sInputFileIdentifier != null)
/*     */         {
/* 548 */           return false;
/*     */         }
/*     */       } else {
/* 551 */         if (!this.m_sInputFileIdentifier.equals(other.m_sInputFileIdentifier))
/*     */         {
/* 553 */           return false;
/*     */         }
/* 555 */         if (this.m_bGetColourProfile != other.m_bGetColourProfile)
/*     */         {
/* 557 */           return false;
/*     */         }
/*     */       }
/* 560 */       for (int i = 0; i < this.m_options.size(); i++)
/*     */       {
/* 563 */         if ((i != this.m_iOutputFilenameIndex) && ((i != this.m_iInputFilenameIndex) || (this.m_sInputFileIdentifier == null)) && (!((String)this.m_options.get(i)).equals(other.m_options.get(i))))
/*     */         {
/* 567 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 571 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 576 */     return CollectionUtil.join(this.m_options, " ");
/*     */   }
/*     */ 
/*     */   public String[] toStringArray()
/*     */   {
/* 581 */     String[] aCmd = new String[this.m_options.size()];
/* 582 */     this.m_options.copyInto(aCmd);
/* 583 */     return aCmd;
/*     */   }
/*     */ 
/*     */   public boolean getDoNotCache()
/*     */   {
/* 588 */     return this.m_bDoNotCache;
/*     */   }
/*     */ 
/*     */   public void setDoNotCache(boolean a_bDoNotCache) {
/* 592 */     this.m_bDoNotCache = a_bDoNotCache;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.ImageMagickOptionList
 * JD-Core Version:    0.6.0
 */