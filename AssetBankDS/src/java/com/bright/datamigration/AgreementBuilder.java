/*     */ package com.bright.datamigration;
/*     */ 
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class AgreementBuilder
/*     */ {
/*     */   private static final long AGREEMENT_TYPE_ID_UNRESTRICTED = 1L;
/*     */   private static final long AGREEMENT_TYPE_ID_HAS_AGREEMENT = 2L;
/*     */   private static final long AGREEMENT_TYPE_ID_RESTRICTED = 3L;
/*     */ 
/*     */   public static Agreement buildAgreementFromXml(String sXml, String sGUC, boolean bIsTMB)
/*     */     throws Exception
/*     */   {
/*  58 */     Agreement a = new Agreement();
/*  59 */     Document dom = null;
/*  60 */     Element root = null;
/*  61 */     SAXBuilder saxBuilder = new SAXBuilder();
/*     */ 
/*  63 */     if (sXml != null)
/*     */     {
/*  65 */       dom = saxBuilder.build(new StringReader(sXml));
/*  66 */       root = dom.getRootElement();
/*     */     }
/*     */ 
/*  69 */     a.id = 2L;
/*     */ 
/*  71 */     String guc = sGUC;
/*     */ 
/*  73 */     if (guc.equalsIgnoreCase("AI_USE_WITH_RESTRICTIONS"))
/*     */     {
/*  75 */       a.title = "Amnesty International use with restrictions";
/*  76 */       a.content = "There are usage restrictions for this image, please read the following terms of use and confirm your use is permitted:\n\n";
/*     */ 
/*  78 */       buildContent(root, a, bIsTMB);
/*     */     }
/*  80 */     else if (guc.equalsIgnoreCase("NO_RESTRICTIONS_FOR_USE_BY_AI"))
/*     */     {
/*  82 */       a.id = 1L;
/*  83 */       a.title = "No restrictions on AI use";
/*  84 */       a.content = "For use in printed AI internal and campaigning materials\nFor use on AI internal and campaigning web-sites\nFor use in AI membership recruitment, fund-raising &/or advertising printed materials\nFor use in AI membership recruitment, fund-raising &/or advertising web-sites\nCan be distributed to external organisations by AI\nFor indefinite use by AI\n";
/*     */     }
/*  92 */     else if (guc.equalsIgnoreCase("REFERENCE_ONLY"))
/*     */     {
/*  94 */       a.id = 3L;
/*  95 */       a.title = "Reference only";
/*     */ 
/*  97 */       if (bIsTMB)
/*     */       {
/*  99 */         a.content = "This record is for reference only, this material is not available for use by Amnesty International.";
/*     */       }
/*     */       else
/*     */       {
/* 103 */         a.content = "This image is for reference only, please contact the Audiovisual Resources team for more information";
/*     */       }
/*     */     }
/* 106 */     else if (guc.equalsIgnoreCase("BROADCAST_USE_NO_RESTRICTIONS"))
/*     */     {
/* 108 */       a.id = 1L;
/* 109 */       a.title = "Broadcast use no restrictions";
/* 110 */       a.content = "This material is available for broadcast and non broadcast use in connection with Amnesty International's work on human rights.\n";
/*     */     }
/* 112 */     else if (guc.equalsIgnoreCase("BROADCAST_USE_WITH_RESTRICTIONS"))
/*     */     {
/* 114 */       a.title = "Broadcast use with restrictions";
/* 115 */       a.content = "This material is available for broadcast and non broadcast use in connection with Amnesty International's work on human rights, but there are usage restrictions, please read the following terms of use and confirm your use is permitted:\n\n";
/*     */ 
/* 117 */       buildContent(root, a, bIsTMB);
/*     */     }
/* 119 */     else if (guc.equalsIgnoreCase("AI_NON_BROADCAST_USE_NO_RESTRICTIONS"))
/*     */     {
/* 121 */       a.title = "Non broadcast use with no restrictions";
/* 122 */       a.content = "This material is available for non broadcast use in connection with Amnesty International's work on human rights.\n";
/*     */     }
/* 124 */     else if (guc.equalsIgnoreCase("AI_NON_BROADCAST_USE_WITH_RESTRICTIONS"))
/*     */     {
/* 126 */       a.title = "AI non broadcast use with restrictions";
/* 127 */       a.content = "There are usage restrictions for this material, please read the following terms of use and confirm your use is permitted:\n\n";
/*     */ 
/* 129 */       buildContent(root, a, bIsTMB);
/*     */     }
/*     */     else
/*     */     {
/* 133 */       throw new Exception("general-use-category not one of expected values: " + root.getChild("general-use-category").getValue());
/*     */     }
/*     */ 
/* 136 */     return a;
/*     */   }
/*     */ 
/*     */   public static Agreement buildContent(Element root, Agreement a, boolean bIsTBM) throws Exception
/*     */   {
/* 141 */     if (root.getChild("specific-usage") != null)
/*     */     {
/*     */       Agreement tmp17_16 = a; tmp17_16.content = (tmp17_16.content + root.getChild("specific-usage").getValue() + "\n\n");
/*     */     }
/*     */ 
/* 146 */     if (root.getChild("external-distribution") != null)
/*     */     {
/* 148 */       if (root.getChild("external-distribution").getValue().equalsIgnoreCase("PROHIBITED"))
/*     */       {
/* 150 */         a.content += "Cannot be distributed to external organisations by AI.\n";
/*     */       }
/* 152 */       else if (root.getChild("external-distribution").getValue().equalsIgnoreCase("ALLOWED"))
/*     */       {
/* 154 */         a.content += "Can be distributed to external organisations by AI.\n";
/*     */       }
/*     */     }
/*     */ 
/* 158 */     if ((root.getChild("print-use") != null) && (root.getChild("print-use").getValue().equalsIgnoreCase("NAMED_DOCUMENT")) && (root.getChild("ai-document") != null) && (root.getChild("ai-document").getChild("ai-index") != null))
/*     */     {
/*     */       Agreement tmp202_201 = a; tmp202_201.content = (tmp202_201.content + "\nFor one time printed use in " + root.getChild("ai-document").getChild("ai-index").getValue() + " only.\n\n");
/*     */     }
/*     */ 
/* 166 */     if (root.getChild("campaign-use") != null)
/*     */     {
/* 168 */       if (root.getChild("campaign-use").getValue().equalsIgnoreCase("ALL"))
/*     */       {
/* 170 */         a.content += "For use in printed AI internal and campaigning materials.\n";
/*     */       }
/* 172 */       else if (root.getChild("campaign-use").getValue().equalsIgnoreCase("NONE"))
/*     */       {
/* 174 */         a.content += "Not for use in printed AI internal and campaigning materials.\n";
/*     */       }
/* 176 */       else if (!root.getChild("campaign-use").getValue().equalsIgnoreCase("SPECIFIC"));
/*     */     }
/* 181 */     else if (root.getChild("print-use") != null)
/*     */     {
/* 183 */       if (root.getChild("print-use").getValue().equalsIgnoreCase("ALL"))
/*     */       {
/* 185 */         a.content += "For use in printed AI internal and campaigning materials.\n";
/*     */       }
/* 187 */       else if (root.getChild("print-use").getValue().equalsIgnoreCase("NONE"))
/*     */       {
/* 189 */         a.content += "Not for use in printed AI internal and campaigning materials.\n";
/*     */       }
/* 191 */       else if (!root.getChild("print-use").getValue().equalsIgnoreCase("SPECIFIC_USE"));
/*     */     }
/*     */ 
/* 199 */     if (root.getChild("web-use") != null)
/*     */     {
/* 201 */       if (root.getChild("web-use").getValue().equalsIgnoreCase("ALL"))
/*     */       {
/* 203 */         a.content += "For use on AI internal and campaigning web-sites.\n";
/*     */       }
/* 205 */       else if (root.getChild("web-use").getValue().equalsIgnoreCase("NONE"))
/*     */       {
/* 207 */         a.content += "Not for use on AI internal and campaigning web-sites.\n";
/*     */       }
/* 209 */       else if (root.getChild("web-use").getValue().equalsIgnoreCase("DEFINED_WEBSITE"))
/*     */       {
/* 211 */         if (root.getChild("specified-website") != null)
/*     */         {
/* 213 */           if (root.getChild("specified-website").getValue().equalsIgnoreCase("For use on all Amnesty International websites"))
/*     */           {
/* 215 */             a.content += "For use on all Amnesty International websites";
/*     */           }
/* 217 */           else if (root.getChild("specified-website").getValue().endsWith("only"))
/*     */           {
/*     */             Agreement tmp679_678 = a; tmp679_678.content = (tmp679_678.content + "For use on " + root.getChild("specified-website").getValue());
/*     */           }
/*     */           else
/*     */           {
/*     */             Agreement tmp720_719 = a; tmp720_719.content = (tmp720_719.content + "For use on " + root.getChild("specified-website").getValue() + " only");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 231 */     if (root.getChild("other-ai-use") != null)
/*     */     {
/* 233 */       if (root.getChild("other-ai-use").getValue().equalsIgnoreCase("NONE"))
/*     */       {
/* 235 */         if (!bIsTBM)
/*     */         {
/* 237 */           a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising printed materials.\n";
/* 238 */           a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising web-sites.\n";
/*     */         }
/*     */         else
/*     */         {
/* 242 */           a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising materials.\n";
/* 243 */           a.content += "Not for presentation at internal AI meetings or external meetings organised by AI where no entrance fee is charged.\n";
/* 244 */           a.content += "Not for resale by AI.\n";
/*     */         }
/*     */       }
/* 247 */       else if (root.getChild("other-ai-use").getValue().equalsIgnoreCase("OTHER_USE_ALLOWED"))
/*     */       {
/* 249 */         if (!bIsTBM)
/*     */         {
/* 251 */           a.content += "For use in AI membership recruitment, fund-raising &/or advertising printed materials.\n";
/* 252 */           a.content += "For use in AI membership recruitment, fund-raising &/or advertising web-sites.\n";
/*     */         }
/*     */       }
/* 255 */       else if (root.getChild("other-ai-use").getValue().equalsIgnoreCase("SPECIFIC_USE"))
/*     */       {
/* 257 */         a.content += "Before using this material for AI membership recruitment, fund-raising &/or advertising materials please refer to 'Specific use notes'.\n";
/* 258 */         a.content += "Before presenting this material at internal AI meetings or external meetings organised by AI, where no entrance fee is charged, please refer to 'Specific use notes'.\n";
/* 259 */         a.content += "Before selling this material please refer to 'Specific use notes'.\n";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 264 */     if ((root.getChild("other-ai-use") == null) || ((bIsTBM) && (root.getChild("other-ai-use").getValue().equalsIgnoreCase("OTHER_USE_ALLOWED"))))
/*     */     {
/* 266 */       if ((root.getChild("for-advertising") != null) && (root.getChild("for-fundraising") != null) && (root.getChild("for-recruitment") != null))
/*     */       {
/* 268 */         if ((root.getChild("for-advertising").getValue().equalsIgnoreCase("true")) && (root.getChild("for-fundraising").getValue().equalsIgnoreCase("true")) && (root.getChild("for-recruitment").getValue().equalsIgnoreCase("true")))
/*     */         {
/* 272 */           if (!bIsTBM)
/*     */           {
/* 274 */             a.content += "For use in AI membership recruitment, fund-raising &/or advertising printed materials.\n";
/* 275 */             a.content += "For use in AI membership recruitment, fund-raising &/or advertising web-sites.\n";
/*     */           }
/*     */           else
/*     */           {
/* 279 */             a.content += "For use in AI membership recruitment, fund-raising &/or advertising materials.\n";
/*     */           }
/*     */         }
/* 282 */         else if ((root.getChild("for-advertising").getValue().equalsIgnoreCase("false")) && (root.getChild("for-fundraising").getValue().equalsIgnoreCase("false")) && (root.getChild("for-recruitment").getValue().equalsIgnoreCase("false")))
/*     */         {
/* 286 */           if (!bIsTBM)
/*     */           {
/* 288 */             a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising printed materials.\n";
/* 289 */             a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising web-sites.\n";
/*     */           }
/*     */           else
/*     */           {
/* 293 */             a.content += "Not for use in AI membership recruitment, fund-raising &/or advertising materials.\n";
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 298 */           throw new Exception("Unexpected combination of use restrictions!");
/*     */         }
/*     */       }
/*     */ 
/* 302 */       if (root.getChild("for-sale") != null)
/*     */       {
/* 304 */         if (root.getChild("for-sale").getValue().equalsIgnoreCase("false"))
/*     */         {
/* 306 */           if (!bIsTBM)
/*     */           {
/* 308 */             a.workNotes += "\n\nImage resale field from ADAM 2 terms of use: <for-sale>false</for-sale> NOT FOR RESALE BY AI";
/*     */           }
/*     */           else
/*     */           {
/* 312 */             a.content += "Not for resale by AI.\n";
/*     */           }
/*     */         }
/* 315 */         else if (root.getChild("for-sale").getValue().equalsIgnoreCase("true"))
/*     */         {
/* 317 */           if (!bIsTBM)
/*     */           {
/* 319 */             a.workNotes += "\n\nImage resale field from ADAM 2 terms of use: <for-sale>true</for-sale> FOR RESALE BY AI";
/*     */           }
/*     */           else
/*     */           {
/* 323 */             a.content += "For resale by AI.\n";
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 328 */       if ((root.getChild("for-meetings") != null) && (bIsTBM))
/*     */       {
/* 330 */         if (root.getChild("for-meetings").getValue().equalsIgnoreCase("true"))
/*     */         {
/* 332 */           a.content += "For presentation at internal AI meetings, and external meetings organised by AI where no entrance fee is charged.\n";
/*     */         }
/* 334 */         else if (root.getChild("for-meetings").getValue().equalsIgnoreCase("false"))
/*     */         {
/* 336 */           a.content += "Not for presentation at internal AI meetings or external meetings organised by AI where no entrance fee is charged.\n";
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 342 */     if (root.getChild("use-period-type") != null)
/*     */     {
/* 344 */       if (root.getChild("use-period-type").getValue().equalsIgnoreCase("INDEFINITE"))
/*     */       {
/* 346 */         a.content += "\nFor indefinite use by AI.\n";
/*     */       }
/* 348 */       else if ((!root.getChild("use-period-type").getValue().equalsIgnoreCase("SPECIFIC")) && (!root.getChild("use-period-type").getValue().equalsIgnoreCase("SPECIFIC_USE")))
/*     */       {
/* 353 */         if (root.getChild("use-period-type").getValue().equalsIgnoreCase("PERIOD_SPECIFIED"))
/*     */         {
/* 355 */           String start = root.getChild("use-start-date").getValue();
/* 356 */           String end = root.getChild("use-expiry-date").getValue();
/*     */ 
/* 358 */           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
/*     */ 
/* 360 */           Date dtStart = format.parse(start);
/* 361 */           Date dtEnd = format.parse(end);
/*     */ 
/* 363 */           a.expiryDate = dtEnd;
/*     */ 
/* 365 */           format = new SimpleDateFormat("d MMMM yyyy");
/*     */           Agreement tmp1886_1885 = a; tmp1886_1885.content = (tmp1886_1885.content + "\nFor use by AI from " + format.format(dtStart) + " until " + format.format(dtEnd) + "\n");
/*     */         }
/*     */       }
/*     */     }
/* 371 */     if ((root.getChild("size-restrictions") != null) && (root.getChild("size-restrictions").getValue().equalsIgnoreCase("ALL")))
/*     */     {
/* 373 */       a.workNotes += "\n\nSize restrictions field from ADAM 2 terms of use: <size-restrictions>ALL</size-restrictions> ALL REPRO SIZES ALLOWED.";
/*     */     }
/*     */ 
/* 376 */     return a;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 381 */     String sFilename = args[0];
/* 382 */     String sOutputDir = args[1];
/* 383 */     String sMetadataFile = args[2];
/* 384 */     String sAgreement = "";
/* 385 */     FileWriter writer = null;
/* 386 */     Vector vecExtras = new Vector();
/*     */     try
/*     */     {
/* 390 */       writer = new FileWriter(sOutputDir + "/" + sMetadataFile);
/*     */ 
/* 392 */       InputStreamReader reader = new InputStreamReader(new FileInputStream(sFilename), "UTF-8");
/* 393 */       BufferedReader bReader = new BufferedReader(reader);
/*     */ 
/* 395 */       String sLine = bReader.readLine();
/*     */ 
/* 397 */       String sAdamId = "";
/* 398 */       String sLastAdamId = null;
/* 399 */       String sGUC = null;
/* 400 */       Agreement lastAgreement = null;
/* 401 */       long lAgreementId = 0L;
/* 402 */       boolean bFoundAgreement = false;
/* 403 */       boolean bUpdateAgreement = true;
/* 404 */       boolean bIsVideo = false;
/* 405 */       boolean bIsAudio = false;
/* 406 */       int iLines = 0;
/* 407 */       long lAgreementTypeId = 0L;
/* 408 */       long lNewAgreementId = 0L;
/* 409 */       long lEntityId = 0L;
/* 410 */       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
/* 411 */       HashSet agreements = new HashSet();
/* 412 */       HashSet assetsWithAgreements = new HashSet();
/*     */ 
/* 415 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('Amnesty International use with restrictions','There are usage restrictions for this image, please read the following terms of use and confirm your use is permitted:\n\n');\n");
/* 416 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('No restrictions on AI use','For use in printed AI internal and campaigning materials\nFor use on AI internal and campaigning web-sites\nFor use in AI membership recruitment, fund-raising &/or advertising printed materials\nFor use in AI membership recruitment, fund-raising &/or advertising web-sites\nCan be distributed to external organisations by AI\nFor indefinite use by AI\n');\n");
/*     */ 
/* 422 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('Reference only','This record is for reference only, this material is not available for use by Amnesty International.');\n");
/* 423 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('Broadcast use no restrictions','This material is available for broadcast and non broadcast use in connection with Amnesty Internationals work on human rights.\n');\n");
/* 424 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('Broadcast use with restrictions','This material is available for broadcast and non broadcast use in connection with Amnesty Internationals work on human rights, but there are usage restrictions, please read the following terms of use and confirm your use is permitted:\n\n');\n");
/* 425 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('Non broadcast use with no restrictions','This material is available for non broadcast use in connection with Amnesty Internationals work on human rights.\n');\n");
/* 426 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('AI non broadcast use with restrictions','There are usage restrictions for this material, please read the following terms of use and confirm your use is permitted:\n\n');\n");
/*     */ 
/* 428 */       while (sLine != null)
/*     */       {
/* 430 */         if (sLine.matches("[0-9]+\\t<.*"))
/*     */         {
/* 432 */           System.out.println("ADAM ID: " + sAdamId + " UPDATE AGREEMENT: " + bUpdateAgreement);
/* 433 */           if ((StringUtil.stringIsPopulated(sAdamId)) && (bUpdateAgreement))
/*     */           {
/* 435 */             if (lAgreementTypeId == 2L)
/*     */             {
/* 438 */               String sTitle = null;
/*     */ 
/* 440 */               if (sGUC.equalsIgnoreCase("BROADCAST_USE_NO_RESTRICTIONS"))
/*     */               {
/* 442 */                 sTitle = "Broadcast use no restrictions";
/*     */               }
/* 444 */               else if (sGUC.equalsIgnoreCase("BROADCAST_USE_WITH_RESTRICTIONS"))
/*     */               {
/* 446 */                 sTitle = "Broadcast use with restrictions";
/*     */               }
/* 448 */               else if (sGUC.equalsIgnoreCase("AI_NON_BROADCAST_USE_NO_RESTRICTIONS"))
/*     */               {
/* 450 */                 sTitle = "Non broadcast use with no restrictions";
/*     */               }
/* 452 */               else if (sGUC.equalsIgnoreCase("AI_NON_BROADCAST_USE_WITH_RESTRICTIONS"))
/*     */               {
/* 454 */                 sTitle = "AI non broadcast use with restrictions";
/*     */               }
/*     */ 
/* 457 */               if (sTitle != null)
/*     */               {
/* 459 */                 vecExtras.add("DELETE FROM AssetAgreement WHERE AssetId IN (" + getABIdSQL(sAdamId, lEntityId) + ");\n");
/*     */ 
/* 461 */                 vecExtras.add("INSERT INTO AssetAgreement (AgreementId,AssetId,DateActivated,IsCurrent) VALUES ((SELECT MIN(Id) FROM Agreement WHERE Title='" + sTitle + "' GROUP BY Title)," + "(" + getABIdSQL(sAdamId, lEntityId) + ")," + "'" + format.format(new Date()) + "'," + "1);\n");
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 470 */           bUpdateAgreement = true;
/* 471 */           sAdamId = "";
/*     */ 
/* 473 */           bIsVideo = sLine.indexOf("video") > 0;
/* 474 */           bIsAudio = sLine.indexOf("audio-asset") > 0;
/*     */ 
/* 476 */           if (bIsVideo)
/*     */           {
/* 478 */             lEntityId = 3L;
/*     */           }
/* 480 */           else if (bIsAudio)
/*     */           {
/* 482 */             lEntityId = 7L;
/*     */           }
/*     */           else
/*     */           {
/* 486 */             lEntityId = 1L;
/*     */           }
/*     */ 
/* 489 */           lAgreementTypeId = 0L;
/*     */         }
/* 491 */         else if (sLine.matches("[ \\t]+<general-use-category>.*"))
/*     */         {
/* 493 */           lAgreementTypeId = sLine.indexOf(">REFERENCE_ONLY<") > 0 ? 3L : sLine.indexOf(">BROADCAST_USE_NO_RESTRICTIONS<") > 0 ? 1L : sLine.indexOf(">NO_RESTRICTIONS_FOR_USE_BY_AI<") > 0 ? 1L : 2L;
/*     */ 
/* 499 */           sGUC = sLine.replaceAll("[ \\t]+<general-use-category>", "");
/* 500 */           sGUC = sGUC.replaceAll("</general-use-category>", "");
/* 501 */           sGUC = sGUC.trim();
/*     */ 
/* 503 */           writer.write("UPDATE Asset SET AgreementTypeId=" + lAgreementTypeId + " WHERE Author='" + sAdamId + "' AND AssetEntityId=" + lEntityId + ";\n");
/*     */ 
/* 505 */           if ((lAgreementTypeId == 1L) || (lAgreementTypeId == 3L))
/*     */           {
/* 507 */             Agreement a = buildAgreementFromXml(null, sGUC, (bIsVideo) || (bIsAudio));
/* 508 */             if (!agreements.contains(a.title))
/*     */             {
/* 510 */               agreements.add(a.title);
/* 511 */               writeAgreement(writer, format, a);
/*     */             }
/*     */ 
/* 514 */             writer.write("DELETE FROM AssetAgreement WHERE AssetId IN (" + getABIdSQL(sAdamId, lEntityId) + ");\n");
/*     */ 
/* 516 */             writer.write("INSERT INTO AssetAgreement (AgreementId,AssetId,DateActivated,IsCurrent) VALUES ((SELECT MIN(Id) FROM Agreement WHERE Title='" + a.title + "' GROUP BY Title)," + "(" + getABIdSQL(sAdamId, lEntityId) + ")," + "'" + format.format(new Date()) + "'," + "1);\n");
/*     */           }
/*     */ 
/*     */         }
/* 524 */         else if ((sAdamId.length() == 0) && (sLine.matches("[ \\t]+<id>[0-9]+</id>.*")))
/*     */         {
/* 526 */           sAdamId = sLine.replaceAll("([ \\t]+<id>)|(</id>.*)", "");
/*     */ 
/* 530 */           if (assetsWithAgreements.contains(sAdamId + "-" + lEntityId))
/*     */           {
/* 532 */             writer.write("DELETE FROM AssetAgreement WHERE AssetId IN (" + getABIdSQL(sAdamId, lEntityId) + ");\n");
/*     */           }
/*     */         }
/* 535 */         else if (sLine.matches("[ \\t]+<adam-agreements?>.*"))
/*     */         {
/* 537 */           bFoundAgreement = true;
/* 538 */           bUpdateAgreement = false;
/*     */         }
/* 540 */         else if (sLine.matches("[ \\t]+</adam-agreements?>.*"))
/*     */         {
/* 542 */           sAgreement = sAgreement + sLine + "\n";
/*     */ 
/* 544 */           if (iLines > 1)
/*     */           {
/* 546 */             Agreement a = buildAgreementFromXml(sAgreement, sGUC, (bIsVideo) || (bIsAudio));
/*     */ 
/* 548 */             if ((sLastAdamId == null) || (!sLastAdamId.equals(sAdamId)) || (!lastAgreement.equals(a)))
/*     */             {
/* 550 */               if ((sLastAdamId != null) && (sLastAdamId.equals(sAdamId)))
/*     */               {
/* 552 */                 System.out.println("Multiple agreements found: " + sAdamId);
/*     */               }
/*     */ 
/* 555 */               if ((lAgreementTypeId == 0L) && (a.id > 0L))
/*     */               {
/* 557 */                 lAgreementTypeId = a.id;
/*     */               }
/*     */ 
/* 560 */               if ((lAgreementTypeId != 1L) && (lAgreementTypeId != 3L))
/*     */               {
/* 562 */                 writer.write("UPDATE AssetAgreement SET IsCurrent=0 WHERE AssetId IN (" + getABIdSQL(sAdamId, lEntityId) + ");\n");
/*     */               }
/*     */ 
/* 565 */               writeAgreement(writer, format, a);
/*     */ 
/* 567 */               lNewAgreementId += 1L;
/*     */ 
/* 569 */               writer.write("INSERT INTO AssetAgreement (AgreementId,AssetId,DateActivated,IsCurrent) VALUES ((SELECT LAST_INSERT_ID()),(" + getABIdSQL(sAdamId, lEntityId) + ")," + "'" + format.format(new Date()) + "'," + ((lAgreementTypeId == 1L) || (lAgreementTypeId == 3L) ? "0" : "1") + ");\n");
/*     */ 
/* 576 */               assetsWithAgreements.add(sAdamId + "-" + lEntityId);
/*     */             }
/*     */             else
/*     */             {
/* 580 */               System.out.println("Non-updated agreement version found! " + sAdamId);
/*     */             }
/*     */ 
/* 583 */             lastAgreement = a;
/*     */           }
/*     */ 
/* 586 */           sAgreement = "";
/* 587 */           lAgreementId = 0L;
/* 588 */           iLines = 0;
/*     */ 
/* 590 */           sLastAdamId = sAdamId;
/* 591 */           bFoundAgreement = false;
/*     */         }
/*     */ 
/* 594 */         if (bFoundAgreement)
/*     */         {
/* 596 */           iLines++;
/* 597 */           sAgreement = sAgreement + sLine + "\n";
/*     */ 
/* 599 */           if ((lAgreementId == 0L) && (sLine.matches("[ \\t]+<id>[0-9]+</id>.*")))
/*     */           {
/* 601 */             lAgreementId = Long.parseLong(sLine.replaceAll("([ \\t]+<id>)|(</id>.*)", ""));
/*     */           }
/*     */         }
/*     */ 
/* 605 */         sLine = bReader.readLine();
/*     */       }
/*     */ 
/* 608 */       for (int i = 0; i < vecExtras.size(); i++)
/*     */       {
/* 610 */         writer.write((String)vecExtras.elementAt(i));
/*     */       }
/*     */ 
/* 613 */       writer.write("UPDATE Asset SET IsPreviewRestricted=1 WHERE AgreementTypeId=3;\n");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 635 */       e.printStackTrace();
/* 636 */       System.err.println(sAgreement);
/* 637 */       System.exit(1);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 643 */         writer.close();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 647 */         e.printStackTrace();
/* 648 */         System.err.println(sAgreement);
/* 649 */         System.exit(1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeAgreement(FileWriter writer, SimpleDateFormat format, Agreement a) throws IOException
/*     */   {
/* 656 */     a.content = a.content.replaceAll("'", "''");
/*     */ 
/* 658 */     if (a.expiryDate != null)
/*     */     {
/* 660 */       writer.write("INSERT INTO Agreement (Title,Body,Expiry) VALUES ('" + a.title + "','" + a.content + "','" + format.format(a.expiryDate) + "');\n");
/*     */     }
/*     */     else
/*     */     {
/* 664 */       writer.write("INSERT INTO Agreement (Title,Body) VALUES ('" + a.title + "','" + a.content + "');\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getABIdSQL(String a_sAdamId, long a_lEntityId)
/*     */   {
/* 726 */     return "SELECT a.Id FROM Asset a WHERE a.Author='" + a_sAdamId + "' AND a.AssetEntityId=" + a_lEntityId;
/*     */   }
/*     */ 
/*     */   private static class Agreement
/*     */   {
/* 670 */     long id = 0L;
/* 671 */     String title = null;
/* 672 */     String content = null;
/* 673 */     String workNotes = "";
/* 674 */     Date expiryDate = null;
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 678 */       int PRIME = 31;
/* 679 */       int result = 1;
/* 680 */       result = 31 * result + (this.content == null ? 0 : this.content.hashCode());
/* 681 */       result = 31 * result + (int)(this.id ^ this.id >>> 32);
/* 682 */       result = 31 * result + (this.title == null ? 0 : this.title.hashCode());
/* 683 */       result = 31 * result + (this.workNotes == null ? 0 : this.workNotes.hashCode());
/* 684 */       return result;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj) {
/* 688 */       if (this == obj)
/* 689 */         return true;
/* 690 */       if (obj == null)
/* 691 */         return false;
/* 692 */       if (getClass() != obj.getClass())
/* 693 */         return false;
/* 694 */       Agreement other = (Agreement)obj;
/* 695 */       if (this.content == null)
/*     */       {
/* 697 */         if (other.content != null)
/* 698 */           return false;
/*     */       }
/* 700 */       else if (!this.content.equals(other.content))
/* 701 */         return false;
/* 702 */       if (this.id != other.id)
/* 703 */         return false;
/* 704 */       if (this.title == null)
/*     */       {
/* 706 */         if (other.title != null)
/* 707 */           return false;
/*     */       }
/* 709 */       else if (!this.title.equals(other.title))
/* 710 */         return false;
/* 711 */       if (this.workNotes == null)
/*     */       {
/* 713 */         if (other.workNotes != null)
/* 714 */           return false;
/*     */       }
/* 716 */       else if (!this.workNotes.equals(other.workNotes))
/* 717 */         return false;
/* 718 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.AgreementBuilder
 * JD-Core Version:    0.6.0
 */