/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bright.assetbank.application.action;
import com.bn2web.common.exception.Bn2Exception;
import com.bn2web.common.service.GlobalApplication;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import com.bright.assetbank.application.constant.AssetBankSettings;
import com.bright.assetbank.application.constant.HandysawDSConstants;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.bright.assetbank.application.form.DetectVideoSceneForm;
import com.bright.assetbank.user.bean.ABUserProfile;
import com.bright.framework.common.action.BTransactionAction;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.database.service.DBTransactionManager;
import com.bright.framework.service.FileStoreManager;
import com.bright.framework.user.bean.UserProfile;
import com.bright.framework.util.FileUtil;
import com.bright.framework.util.commandline.CommandLineExecHandysaw;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import org.apache.avalon.framework.component.ComponentException;
/**
 *
 * @author mamatha
 */
public class DetectVideoScenes extends Action{
    public static long MAX_WAIT_TIME = 300000L;
    long assetid;
    long duration=0;
    int fps=0;
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest a_request, HttpServletResponse response) throws Bn2Exception {
    String sFile = a_request.getParameter("file");
    String fileLocation= a_request.getParameter("fileLocation");
    String id = a_request.getParameter("id");
    this.assetid = Long.parseLong(id);
    DBTransactionManager transactionManager;
    DBTransaction dbt=null;
    DBTransaction dbtinit =null;
    Connection  con=null;
    String isProcessed=null;
    FileStoreManager fileStoreManager=null;
    try{
        transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
        dbtinit = transactionManager.getNewTransaction();
        isProcessed = this.getVideoStatus(dbtinit);
        fileStoreManager = (FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager");
    }
    catch(SQLException e){
        System.out.println(e.getMessage());
        try{
         if(con!=null && !con.isClosed()){
             con.close();
          }
          if(dbtinit!=null)
             dbtinit.rollback();
        }catch(SQLException ex){ System.out.println(ex);}
        return mapping.findForward("failure"); //error
    }
    catch(ComponentException e){
        System.out.println(e.getMessage());
        return mapping.findForward("failure"); //error
    }
    if(isProcessed.equals("2")){
        try{
         if(con!=null && !con.isClosed()){
             con.close();
         }
         if(dbtinit!=null)
             dbtinit.commit();
        }catch(SQLException ex){System.out.println(ex);}
        return mapping.findForward("processed");
    }
    else if(isProcessed.equals("1")){
            try{
             if(con!=null && !con.isClosed()){
             con.close();
             }
             if(dbtinit!=null)
                dbtinit.rollback();
            }catch(SQLException ex){return mapping.findForward("failure");}
        return mapping.findForward("processing");
    }
    else if(isProcessed.equals("0")){
    String filePath = fileStoreManager.getAbsolutePath(fileLocation);
    String outputPath = filePath+".txt"; 
    
     String handySawInstallPath = HandysawDSConstants.getHandysawDSInstallPath();
    String[] detectSceneCommand = {handySawInstallPath, "/q","/x", "/s", "/r4", "/i2",filePath};
    try{
        CommandLineExecHandysaw.execute(detectSceneCommand);
     }
      catch (Bn2Exception be)
      {
          System.out.println(be.getMessage());
          DBTransaction rdbt = transactionManager.getNewTransaction();
          this.rollbackStatus(rdbt);
          return mapping.findForward("failure"); //error
      }
      int noScenes=0;
      Scanner scanner=null;
      File  fFile = new File(outputPath);
      if(fFile != null && fFile.exists()){
      try{
             TreeMap  scenes = new TreeMap<Integer,Integer>();
             scanner = new Scanner(new FileReader(fFile));
             Integer endpt=null;
             while ( scanner.hasNextLine() ){
                 String aLine = scanner.nextLine();
                 Scanner lineScanner = new Scanner(aLine);
                 lineScanner.useDelimiter(",");
                  if ( lineScanner.hasNext() ){
                  String sStart = lineScanner.next();
                  String sEnd = lineScanner.next();
     
                  Integer startpt = Integer.valueOf(sStart.trim());
                  endpt = Integer.valueOf(sEnd.trim());
                  scenes.put(startpt, endpt);
                  noScenes++;   
                }  
             }
           // System.out.println("Last frame :"+endpt+" Duration :"+this.duration);
            if(endpt!=null)
                this.fps = (int) (endpt/this.duration);
           // System.out.println("FPS :"+this.fps); 
            dbt = transactionManager.getNewTransaction();
            Connection conn = dbt.getConnection();
            Integer endp=0;
            Integer startp=0;
            long tspan = 0;
            long calculatedEnd=-1;
            long calculatedstart=1;
            for(Object key : scenes.keySet()) {
                endp = (Integer) scenes.get(key);
                startp = (Integer)key;
                double dTspan = 1.0 *(endp - startp) /this.fps ;
                tspan = Math.round(dTspan);
                    tspan-=1;
              
               addscenedetails(calculatedstart,tspan, conn);
                calculatedEnd = calculatedstart + tspan; 
                calculatedstart = calculatedEnd+1;
            }
            if(scenes.size() > 0){
             updateVideoSceneDetectedStatus(conn);
             dbt.commit();
            }
          }
          catch(FileNotFoundException fnfe){
              System.out.println(fnfe);
              DBTransaction rdbt = transactionManager.getNewTransaction();
              this.rollbackStatus(rdbt);
             /* deleteTask(taskName);
              if(batchFile.exists())
                batchFile.delete();*/
              return mapping.findForward("failure"); //error
          }
          catch(SQLException es){
               System.out.println(es);
                    try {
                        dbt.rollback();
                    } catch (SQLException ex) {
                        Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
                    }
              DBTransaction rdbt = transactionManager.getNewTransaction();
              this.rollbackStatus(rdbt);
              /*deleteTask(taskName);
              if(batchFile.exists())
                batchFile.delete();*/
              try {
                        dbt.rollback();
              } catch (SQLException ex) {
                        Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
              }
               return mapping.findForward("failure"); //error
          }
          
          finally {
              if(scanner!=null)
                    scanner.close();
              if(fFile.exists())
                  fFile.delete();
              String hsqStr = filePath+".hsq";
              File hsq = new File(hsqStr);
              if(hsq!=null && hsq.exists())
                  hsq.delete();
             }
      }
      else{
          System.out.println("Could not find the temp file:");
          DBTransaction rdbt = transactionManager.getNewTransaction();
              this.rollbackStatus(rdbt);
             /* deleteTask(taskName);
              if(batchFile.exists())
                batchFile.delete();*/
          return mapping.findForward("failure");
      }
    return mapping.findForward("success");
    }
    else{
      return mapping.findForward("failure");  
    }
   }
    
  protected void updateVideoSceneDetectedStatus(Connection con) throws SQLException{

      PreparedStatement pusql=null;
     // System.out.println("about to update DS status");
      String uSql = "update VIDEOASSET set DETECTEDSCENES='2' where ASSETID=?";
      pusql = con.prepareStatement(uSql);
      pusql.setLong(1,this.assetid);
      pusql.executeUpdate();
      pusql.close();
       
  }
  
  protected void addscenedetails(long start,long timespan, Connection con)throws SQLException{
     if(con==null){
        System.out.println("Error in opening DBConnection");
        throw new SQLException("No valid connection");
     }
    PreparedStatement psql = null;
    String sSQL = "INSERT INTO VIDEOKEYWORD VALUES(SCENESEQUENCE.nextval,?,?,?)";
   // System.out.println("About to update videokeyword with assetid: "+ this.assetid);   
    psql = con.prepareStatement(sSQL);
    psql.setLong(1, this.assetid);
    psql.setLong(2, start);
    psql.setLong(3, timespan);
    psql.executeUpdate();
    psql.close();
    PreparedStatement keywordstmt = null;
    String keywordsql = "INSERT INTO VIDEOKEYWORDTEXT VALUES(SCENESEQUENCE.currval,?,?)";
    keywordstmt=con.prepareStatement(keywordsql);
    keywordstmt.setInt(1, 1);
    keywordstmt.setString(2,"scene");
    keywordstmt.executeUpdate();
    keywordstmt.close();
  }

  protected String getVideoStatus(DBTransaction dbt) throws SQLException{  
        try {
            Connection con =  dbt.getConnection();
            PreparedStatement psql = null;
            String sSQL ="SELECT DETECTEDSCENES,DURATION FROM VIDEOASSET WHERE ASSETID=?";
            psql = con.prepareStatement(sSQL);
            psql.setLong(1, this.assetid);
            ResultSet rs = psql.executeQuery();
            if(rs.next()){
                String status = rs.getString(1);
               // System.out.println(status);
                if(status.equals("0")){
                    this.duration = rs.getLong(2);
                    this.duration /= 1000;
                //    System.out.println("Info from table: Duration :"+this.duration);
                        String uSql = "update VIDEOASSET set DETECTEDSCENES='1' where ASSETID=?";
                        PreparedStatement pusql = con.prepareStatement(uSql);
                        pusql.setLong(1,this.assetid);
                        pusql.executeUpdate();
                        pusql.close();
                }
                psql.close();
                dbt.commit();
                return status;
            }
            else{
                 psql.close();
                return null;  //inconsistant
            }
        } catch (Bn2Exception ex) {
          
            Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
  }
  
  protected void rollbackStatus(DBTransaction dbt){
        try {
            Connection con =  dbt.getConnection();
            String uSql = "update VIDEOASSET set DETECTEDSCENES='0' where ASSETID=?";
            PreparedStatement pusql = con.prepareStatement(uSql);
            pusql.setLong(1,this.assetid);
            pusql.executeUpdate();
            pusql.close();
            dbt.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Bn2Exception ex) {
            Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
 protected File createTaskBatchFile(String batchfile,String fileForHS) {
  
        
        try{
        File f = new File(batchfile);
        FileWriter fstream = new FileWriter(f);
        BufferedWriter out = new BufferedWriter(fstream);
        String handySawInstallPath = HandysawDSConstants.getHandysawDSInstallPath();
        handySawInstallPath=handySawInstallPath+" /q /x /s /r4 /i2 "+fileForHS;
        out.write(handySawInstallPath);
        out.close();
        return f;
        
        }catch(IOException e){
            return null;
        }

}
protected boolean isTaskCompleted(String taskName) throws Bn2Exception{
    
    String[] queryTaskCmd = {"c:\\Windows\\System32\\schtasks.exe", "/query","/fo", "LIST","/TN",taskName };
    try{
         String outputForCmdLine = CommandLineExecHandysaw.execute(queryTaskCmd);
         Scanner lineScanner = new Scanner(outputForCmdLine);
         lineScanner.useDelimiter("\n");
         boolean noMoreRun = false;
         boolean isReady = false;
         while(lineScanner.hasNext()){
             String line=lineScanner.next();
             String[] lineSegs =  line.split(":");
             if(lineSegs.length<2) continue;
             
             if(lineSegs[0].trim().equalsIgnoreCase("Next Run Time") && lineSegs[1].trim().equalsIgnoreCase("N/A")){
                noMoreRun = true;
                continue;
             }
             if(lineSegs[0].trim().equalsIgnoreCase("Status") && lineSegs[1].trim().equalsIgnoreCase("Ready")){
                isReady = true;
             }
         }
         if(noMoreRun && isReady)
             return true;
         else 
             return false;
      }
       catch (Bn2Exception be){
          System.out.println(be);
          throw be;
      }

} 
protected boolean deleteTask(String taskName){
        try {
            String[] delTaskCmd = {"c:\\Windows\\System32\\schtasks.exe", "/delete","/f","/TN",taskName };
            String outputForCmdLine = CommandLineExecHandysaw.execute(delTaskCmd);
            return true;
        } catch (Bn2Exception ex) {
            Logger.getLogger(DetectVideoScenes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
 }

}
