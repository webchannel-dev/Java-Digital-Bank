/*      */ package com.bright.framework.util;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.framework.constant.FrameworkSettings;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.util.Collection;
/*      */ import java.util.zip.ZipEntry;
/*      */ import java.util.zip.ZipOutputStream;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.io.FilenameUtils;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class FileUtil
/*      */ {
/*   65 */   private static final Log c_logger = LogUtil.getLog(ClassUtil.currentClassName());
/*      */ 
/*   69 */   public static char k_cDirSep = System.getProperty("file.separator").charAt(0);
/*      */   private static final int k_iBlocksize = 1024;
/*      */   private static final int k_iBytesAtATime = 8192;
/*      */   private static final String k_sPathSepRE = "/|\\\\";
/*      */ 
/*      */   public static String getStandardisedPath(String a_sOriginalPath)
/*      */   {
/*   87 */     if (a_sOriginalPath == null)
/*      */     {
/*   89 */       return null;
/*      */     }
/*      */ 
/*   92 */     return a_sOriginalPath.replace(k_cDirSep, '/');
/*      */   }
/*      */ 
/*      */   public static String getFilename(String a_sFullPath)
/*      */   {
/*  109 */     if (a_sFullPath == null)
/*      */     {
/*  111 */       return null;
/*      */     }
/*      */ 
/*  114 */     String sFilename = a_sFullPath;
/*      */ 
/*  117 */     int iLastIndex = a_sFullPath.lastIndexOf('/');
/*      */ 
/*  119 */     if ((iLastIndex > 0) && (iLastIndex < a_sFullPath.length() - 1))
/*      */     {
/*  121 */       sFilename = a_sFullPath.substring(iLastIndex + 1);
/*      */     }
/*      */ 
/*  124 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public static String getParentDirectoryPath(String a_sFullPath)
/*      */   {
/*  142 */     if (a_sFullPath == null)
/*      */     {
/*  144 */       return null;
/*      */     }
/*      */ 
/*  147 */     String sDir = a_sFullPath;
/*      */ 
/*  149 */     int iLastIndex = a_sFullPath.lastIndexOf('/');
/*      */ 
/*  152 */     if ((iLastIndex > 0) && (iLastIndex == a_sFullPath.length() - 1))
/*      */     {
/*  154 */       iLastIndex = a_sFullPath.substring(0, a_sFullPath.length() - 1).lastIndexOf('/');
/*      */     }
/*      */ 
/*  157 */     if (iLastIndex <= 0)
/*      */     {
/*  159 */       sDir = "";
/*      */     }
/*      */     else
/*      */     {
/*  163 */       sDir = a_sFullPath.substring(0, iLastIndex);
/*      */     }
/*      */ 
/*  166 */     return sDir;
/*      */   }
/*      */ 
/*      */   public static String getSuffix(String a_sFilename)
/*      */   {
/*  182 */     if (a_sFilename == null)
/*      */     {
/*  184 */       return null;
/*      */     }
/*      */ 
/*  187 */     String sSuffix = null;
/*      */ 
/*  190 */     int iLastIndex = a_sFilename.lastIndexOf('.');
/*      */ 
/*  192 */     if ((iLastIndex > 0) && (iLastIndex < a_sFilename.length() - 1))
/*      */     {
/*  194 */       sSuffix = a_sFilename.substring(iLastIndex + 1);
/*      */     }
/*      */ 
/*  197 */     return sSuffix;
/*      */   }
/*      */ 
/*      */   public static boolean hasSuffix(String a_sFilename, String a_sSuffix)
/*      */   {
/*  208 */     String sSuffix = getSuffix(a_sFilename);
/*  209 */     return (sSuffix != null) && (sSuffix.toLowerCase().equals(a_sSuffix));
/*      */   }
/*      */ 
/*      */   public static String getFilepathWithoutSuffix(String a_sFilename)
/*      */   {
/*  224 */     return getFilenameWithoutSuffix(a_sFilename, false);
/*      */   }
/*      */ 
/*      */   public static String getFilenameWithoutSuffix(String a_sFilename)
/*      */   {
/*  235 */     return getFilenameWithoutSuffix(a_sFilename, true);
/*      */   }
/*      */ 
/*      */   public static String getFilenameWithoutSuffix(String a_sFilename, boolean a_bRemoveDirInfo)
/*      */   {
/*  254 */     if (a_sFilename == null)
/*      */     {
/*  256 */       return null;
/*      */     }
/*      */ 
/*  259 */     if (a_bRemoveDirInfo)
/*      */     {
/*  262 */       a_sFilename = getFilename(a_sFilename);
/*      */     }
/*      */ 
/*  265 */     String sStartOfFilename = null;
/*      */ 
/*  268 */     int iLastIndex = a_sFilename.lastIndexOf('.');
/*      */ 
/*  270 */     if ((iLastIndex > 0) && (iLastIndex < a_sFilename.length() - 1))
/*      */     {
/*  272 */       sStartOfFilename = a_sFilename.substring(0, iLastIndex);
/*      */     }
/*      */     else
/*      */     {
/*  277 */       sStartOfFilename = a_sFilename;
/*      */     }
/*      */ 
/*  280 */     return sStartOfFilename;
/*      */   }
/*      */ 
/*      */   public static String lowercaseSuffix(String a_sFilename)
/*      */   {
/*  285 */     String sSuff = getSuffix(a_sFilename);
/*      */ 
/*  287 */     if (StringUtils.isNotEmpty(sSuff))
/*      */     {
/*  289 */       String sStart = getFilepathWithoutSuffix(a_sFilename);
/*  290 */       return sStart + "." + sSuff.toLowerCase();
/*      */     }
/*  292 */     return a_sFilename;
/*      */   }
/*      */ 
/*      */   public static String getSafeFilename(String a_sFilenameCandidate, boolean a_bReplaceSpaces)
/*      */   {
/*  309 */     if (a_sFilenameCandidate != null)
/*      */     {
/*  313 */       a_sFilenameCandidate = a_sFilenameCandidate.replaceAll("[^\\w\\.\\s-]", "_");
/*      */ 
/*  315 */       if (a_bReplaceSpaces)
/*      */       {
/*  317 */         a_sFilenameCandidate = a_sFilenameCandidate.replace(" ", "_");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  322 */     return a_sFilenameCandidate;
/*      */   }
/*      */ 
/*      */   public static void copyStream(InputStream a_is, OutputStream a_os)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  339 */       byte[] buffer = new byte[8192];
/*      */       int nbytes;
/*  343 */       while ((nbytes = a_is.read(buffer)) != -1)
/*      */       {
/*  345 */         a_os.write(buffer, 0, nbytes);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  351 */       throw new Bn2Exception("Exception in FileUtil.copyStream:", ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyFile(String a_sSrc, String a_sDest)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  370 */       InputStream is = new FileInputStream(a_sSrc);
/*  371 */       OutputStream os = new FileOutputStream(a_sDest);
/*      */ 
/*  373 */       copyStream(is, os);
/*      */ 
/*  375 */       is.close();
/*  376 */       os.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  380 */       throw new Bn2Exception("Exception in FileUtil.copyFile:", ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyFile(File inputFile, File outputFile)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  392 */       FileInputStream is = new FileInputStream(inputFile);
/*  393 */       FileOutputStream os = new FileOutputStream(outputFile);
/*      */ 
/*  395 */       copyStream(is, os);
/*      */ 
/*  397 */       is.close();
/*  398 */       os.close();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  402 */       throw new Bn2Exception("Exception in FileUtil.copyFile:", ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void copyDir(String a_sSrc, String a_sDest)
/*      */     throws Bn2Exception
/*      */   {
/*  424 */     copyDir(a_sSrc, a_sDest, null);
/*      */   }
/*      */ 
/*      */   public static void copyDir(String a_sSrc, String a_sDest, String[] a_exceptions)
/*      */     throws Bn2Exception
/*      */   {
/*  449 */     if ((a_exceptions == null) || (!StringUtil.matchesRegexs(a_sSrc, a_exceptions)))
/*      */     {
/*  452 */       File toCopy = new File(a_sSrc);
/*      */ 
/*  454 */       if (toCopy.isDirectory())
/*      */       {
/*  456 */         File file = new File(a_sDest);
/*  457 */         file.mkdirs();
/*  458 */         if (!file.exists())
/*      */         {
/*  460 */           throw new Bn2Exception("Error creating directory/file in FileUtil.copyDir: " + a_sDest);
/*      */         }
/*      */ 
/*  463 */         File[] children = toCopy.listFiles();
/*  464 */         for (int i = 0; (children != null) && (i < children.length); i++)
/*      */         {
/*  466 */           copyDir(children[i].getAbsolutePath(), a_sDest.concat(File.separator).concat(children[i].getName()), a_exceptions);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  473 */         copyFile(a_sSrc, a_sDest);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void deleteFile(File a_file)
/*      */     throws IOException
/*      */   {
/*  486 */     if (!a_file.delete())
/*      */     {
/*  488 */       throw new IOException("Could not delete " + a_file);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void mkdir(File a_dir)
/*      */     throws IOException
/*      */   {
/*  500 */     if (!a_dir.mkdir())
/*      */     {
/*  502 */       throw new IOException("Couldn't create directory " + a_dir);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void deleteDir(String a_sDir)
/*      */   {
/*  522 */     File toDelete = new File(a_sDir);
/*      */ 
/*  524 */     if (toDelete.isDirectory())
/*      */     {
/*  526 */       File[] children = toDelete.listFiles();
/*  527 */       for (int i = 0; i < children.length; i++)
/*      */       {
/*  529 */         deleteDir(children[i].getAbsolutePath());
/*      */       }
/*      */ 
/*  533 */       toDelete.delete();
/*  534 */       logFileDeletion(toDelete);
/*      */     }
/*      */     else
/*      */     {
/*  538 */       toDelete.delete();
/*  539 */       logFileDeletion(toDelete);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean filesChanged(File a_fDirectory, long a_lLastChecked)
/*      */   {
/*  559 */     boolean bSomethingChanged = false;
/*      */ 
/*  562 */     File[] aFiles = a_fDirectory.listFiles();
/*      */ 
/*  564 */     if (aFiles == null)
/*      */     {
/*  566 */       return false;
/*      */     }
/*      */ 
/*  570 */     for (int iFilenamePos = 0; (iFilenamePos < aFiles.length) && (!bSomethingChanged); iFilenamePos++)
/*      */     {
/*  572 */       File currentFile = aFiles[iFilenamePos];
/*      */ 
/*  575 */       if (currentFile.isDirectory())
/*      */       {
/*  577 */         bSomethingChanged = filesChanged(currentFile, a_lLastChecked);
/*      */       }
/*      */       else
/*      */       {
/*  581 */         if (currentFile.lastModified() <= a_lLastChecked)
/*      */           continue;
/*  583 */         bSomethingChanged = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  588 */     return bSomethingChanged;
/*      */   }
/*      */ 
/*      */   public static StringBuffer readIntoStringBuffer(String a_sFileLocation, String a_sCharEncoding)
/*      */     throws IOException
/*      */   {
/*  607 */     Reader rFile = null;
/*  608 */     StringBuffer sbFile = new StringBuffer();
/*  609 */     int iCharsRead = 0;
/*  610 */     char[] cbuf = new char[1024];
/*      */ 
/*  612 */     if (a_sCharEncoding == null)
/*      */     {
/*  614 */       rFile = new FileReader(a_sFileLocation);
/*      */     }
/*      */     else
/*      */     {
/*  618 */       rFile = new InputStreamReader(new FileInputStream(a_sFileLocation), a_sCharEncoding);
/*      */     }
/*      */ 
/*  622 */     while ((iCharsRead = rFile.read(cbuf, 0, 1024)) != -1)
/*      */     {
/*  624 */       sbFile.append(cbuf, 0, iCharsRead);
/*      */     }
/*      */ 
/*  627 */     rFile.close();
/*      */ 
/*  629 */     return sbFile;
/*      */   }
/*      */ 
/*      */   public static StringBuffer readIntoStringBuffer(String a_sFileLocation)
/*      */     throws IOException
/*      */   {
/*  648 */     return readIntoStringBuffer(a_sFileLocation, null);
/*      */   }
/*      */ 
/*      */   public static void createFileAndItsDirectories(String a_sStartingDir, String a_sFilePath)
/*      */     throws Bn2Exception
/*      */   {
/*      */     try
/*      */     {
/*  671 */       String[] aTokens = a_sFilePath.split("/|\\\\");
/*      */ 
/*  674 */       String sCurrentDir = a_sStartingDir;
/*      */ 
/*  678 */       for (int i = 0; i < aTokens.length - 1; i++)
/*      */       {
/*  681 */         if (!sCurrentDir.endsWith("/"))
/*      */         {
/*  683 */           sCurrentDir = sCurrentDir + "/";
/*      */         }
/*      */ 
/*  686 */         sCurrentDir = sCurrentDir + aTokens[i];
/*      */ 
/*  689 */         File fDir = new File(sCurrentDir);
/*      */ 
/*  691 */         if (fDir.exists())
/*      */           continue;
/*  693 */         fDir.mkdir();
/*      */       }
/*      */ 
/*  698 */       String sFullFilePath = a_sStartingDir + "/" + a_sFilePath;
/*      */ 
/*  701 */       File fNewFile = new File(sFullFilePath);
/*  702 */       fNewFile.createNewFile();
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  706 */       throw new Bn2Exception("IOException in FileUtil.createFileAndItsDirectories:", ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void replaceStringInFile(File a_file, String a_sStringToReplace, String a_sReplacement)
/*      */     throws IOException, FileNotFoundException
/*      */   {
/*  716 */     StringBuffer fileData = new StringBuffer(1000);
/*  717 */     BufferedReader reader = new BufferedReader(new FileReader(a_file));
/*      */ 
/*  719 */     char[] buf = new char[1024];
/*  720 */     int numRead = 0;
/*  721 */     while ((numRead = reader.read(buf)) != -1)
/*      */     {
/*  723 */       String readData = String.valueOf(buf, 0, numRead);
/*  724 */       fileData.append(readData);
/*  725 */       buf = new char[1024];
/*      */     }
/*  727 */     reader.close();
/*  728 */     String sFileAsString = fileData.toString();
/*      */ 
/*  731 */     sFileAsString = sFileAsString.replaceAll(a_sStringToReplace, a_sReplacement);
/*      */ 
/*  734 */     Writer output = new BufferedWriter(new FileWriter(a_file));
/*  735 */     output.write(sFileAsString);
/*  736 */     output.close();
/*      */   }
/*      */ 
/*      */   public static String encryptFilepath(String sFilepath)
/*      */   {
/*  754 */     String sResult = null;
/*  755 */     String sFilepathNoSuffix = null;
/*  756 */     String sSuffix = null;
/*      */ 
/*  758 */     if (sFilepath != null)
/*      */     {
/*  760 */       sFilepathNoSuffix = getFilepathWithoutSuffix(sFilepath);
/*  761 */       sSuffix = getSuffix(sFilepath);
/*      */ 
/*  763 */       sResult = RC4CipherUtil.encryptToHex(sFilepathNoSuffix);
/*      */ 
/*  765 */       if (sSuffix != null)
/*      */       {
/*  767 */         sResult = sResult + "." + sSuffix;
/*      */       }
/*      */     }
/*      */ 
/*  771 */     return sResult;
/*      */   }
/*      */ 
/*      */   public static String decryptFilepath(String a_sFilepath)
/*      */   {
/*  788 */     String sResult = null;
/*  789 */     String sFilepathNoSuffix = null;
/*  790 */     String sSuffix = null;
/*      */ 
/*  792 */     if (a_sFilepath != null)
/*      */     {
/*  794 */       sFilepathNoSuffix = getFilepathWithoutSuffix(a_sFilepath);
/*  795 */       sSuffix = getSuffix(a_sFilepath);
/*      */       try
/*      */       {
/*  799 */         sResult = RC4CipherUtil.decryptFromHex(sFilepathNoSuffix);
/*      */ 
/*  801 */         if (sSuffix != null)
/*      */         {
/*  803 */           sResult = sResult + "." + sSuffix;
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  809 */         sResult = a_sFilepath;
/*      */       }
/*      */     }
/*      */ 
/*  813 */     return sResult;
/*      */   }
/*      */ 
/*      */   public static String getUniqueFilename(String a_sDirectory, String a_sCandidateFilename)
/*      */   {
/*  833 */     boolean bFoundUniqueFilename = false;
/*  834 */     int iFileCount = 0;
/*  835 */     String sFilename = a_sCandidateFilename;
/*      */ 
/*  837 */     while (!bFoundUniqueFilename)
/*      */     {
/*  839 */       String sFilePath = a_sDirectory + "/" + sFilename;
/*      */ 
/*  842 */       File fTemp = new File(sFilePath);
/*      */ 
/*  844 */       if (fTemp.exists())
/*      */       {
/*  848 */         iFileCount++;
/*      */ 
/*  851 */         String sStartOfFilename = getFilepathWithoutSuffix(a_sCandidateFilename);
/*  852 */         String sSuffix = getSuffix(a_sCandidateFilename);
/*      */ 
/*  855 */         if (sStartOfFilename != null)
/*      */         {
/*  857 */           sFilename = sStartOfFilename;
/*      */         }
/*      */         else
/*      */         {
/*  861 */           sFilename = "";
/*      */         }
/*      */ 
/*  865 */         sFilename = sFilename + iFileCount;
/*      */ 
/*  868 */         if (sSuffix != null)
/*      */         {
/*  870 */           sFilename = sFilename + "." + sSuffix.toLowerCase();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  875 */         bFoundUniqueFilename = true;
/*      */       }
/*      */     }
/*      */ 
/*  879 */     return sFilename;
/*      */   }
/*      */ 
/*      */   public static void ensureDirectoryExists(File f)
/*      */     throws Bn2Exception
/*      */   {
/*  885 */     if (f.isFile())
/*      */     {
/*      */       try
/*      */       {
/*  889 */         FileUtils.forceDelete(f);
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/*  893 */         throw new Bn2Exception("Could not delete file " + f.getPath() + " to make way for directory", e);
/*      */       }
/*      */     }
/*      */ 
/*  897 */     if (!f.exists())
/*      */     {
/*  899 */       f.mkdir();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void clearDirectory(String a_sPath, FileFilter a_fileFilter)
/*      */   {
/*  913 */     File fDownloadDirectory = new File(a_sPath);
/*  914 */     File[] files = fDownloadDirectory.listFiles();
/*      */ 
/*  916 */     if (files != null)
/*      */     {
/*  918 */       for (int i = 0; i < files.length; i++)
/*      */       {
/*  920 */         if (files[i].isDirectory())
/*      */         {
/*  923 */           clearDirectory(files[i].getAbsolutePath(), a_fileFilter);
/*      */         }
/*      */ 
/*  926 */         if ((a_fileFilter == null) || (!a_fileFilter.accept(files[i])))
/*      */           continue;
/*  928 */         if (!files[i].delete())
/*      */         {
/*  930 */           GlobalApplication.getInstance().getLogger().error("Could not delete file " + files[i]);
/*      */         }
/*  932 */         logFileDeletion(files[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void clearDirectory(String a_sPath, final long a_lOlderThan)
/*      */   {
/*  948 */     if (StringUtils.isEmpty(a_sPath))
/*      */     {
/*  950 */       throw new IllegalArgumentException("FileUtil.clearDirectory(String,long) called with an empty directory path.");
/*      */     }
/*      */ 
/*  954 */     FileFilter filter = new FileFilter()
/*      */     {
/*      */       public boolean accept(File pathname)
/*      */       {
/*  958 */         return pathname.lastModified() < a_lOlderThan;
/*      */       }
/*      */     };
/*  962 */     clearDirectory(a_sPath, filter);
/*      */   }
/*      */ 
/*      */   public static void logFileDeletion(File a_file)
/*      */   {
/*  968 */     if ((FrameworkSettings.getLogStackTraceWithDeletion()) && (a_file != null))
/*      */     {
/*  970 */       GlobalApplication.getInstance().getLogger().warn("FileDeletion: The following file has been deleted: " + a_file);
/*  971 */       StackTraceElement[] aStack = Thread.currentThread().getStackTrace();
/*  972 */       String sTrace = "";
/*  973 */       if (aStack != null)
/*      */       {
/*  975 */         for (int i = 0; i < aStack.length; i++)
/*      */         {
/*  977 */           if (aStack[i] == null)
/*      */             continue;
/*  979 */           sTrace = sTrace + aStack[i] + System.getProperty("line.separator");
/*      */         }
/*      */       }
/*      */ 
/*  983 */       GlobalApplication.getInstance().getLogger().warn("FileDeletion: StackTrace: " + sTrace);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean moveFile(String a_sFromPath, String a_sToPath)
/*      */   {
/*  989 */     return new File(a_sFromPath).renameTo(new File(a_sToPath));
/*      */   }
/*      */ 
/*      */   public static void createZipFile(File a_zipFile, File[] a_filesToAdd, Collection<String> a_exclusions)
/*      */     throws IOException
/*      */   {
/*  995 */     ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(a_zipFile));
/*  996 */     byte[] buff = new byte[8192];
/*  997 */     int iBytesWrittenToZip = 0;
/*      */ 
/*  999 */     for (File ssFile : a_filesToAdd)
/*      */     {
/* 1001 */       if (a_exclusions.contains(ssFile.getName()))
/*      */         continue;
/*      */       try
/*      */       {
/* 1005 */         InputStream in = new FileInputStream(ssFile);
/* 1006 */         ZipEntry entry = new ZipEntry(ssFile.getName());
/* 1007 */         zout.putNextEntry(entry);
/*      */ 
/* 1009 */         int iNumBytes = 0;
/* 1010 */         while ((iNumBytes = in.read(buff)) != -1)
/*      */         {
/* 1012 */           zout.write(buff, 0, iNumBytes);
/*      */ 
/* 1015 */           iBytesWrittenToZip += iNumBytes;
/*      */         }
/*      */ 
/* 1019 */         zout.closeEntry();
/*      */ 
/* 1022 */         in.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1027 */         GlobalApplication.getInstance().getLogger().error("FileUtil.createZipFile: Error added file to zip : " + ssFile.getName() + " : ", e);
/*      */       }
/*      */     }
/*      */ 
/* 1031 */     zout.close();
/*      */   }
/*      */ 
/*      */   public static String getFirstFilenameWithExtension(Collection<String> a_fileNames, String a_sExt)
/*      */   {
/* 1042 */     for (String sName : a_fileNames)
/*      */     {
/* 1044 */       String sExt = FilenameUtils.getExtension(sName);
/* 1045 */       if ((StringUtils.isNotEmpty(sExt)) && (sExt.equalsIgnoreCase(a_sExt)))
/*      */       {
/* 1047 */         return sName;
/*      */       }
/*      */     }
/* 1050 */     return null;
/*      */   }
/*      */ 
/*      */   public static File createTempDir(String a_suffix, String a_prefix, File a_parentDir)
/*      */     throws IOException
/*      */   {
/* 1064 */     File tempFile = null;
/*      */     try
/*      */     {
/* 1067 */       tempFile = File.createTempFile(a_prefix, a_suffix, a_parentDir);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1073 */       c_logger.error("Couldn't create temp file in directory " + a_parentDir);
/* 1074 */       throw e;
/*      */     }
/* 1076 */     deleteFile(tempFile);
/* 1077 */     mkdir(tempFile);
/* 1078 */     return tempFile;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.FileUtil
 * JD-Core Version:    0.6.0
 */