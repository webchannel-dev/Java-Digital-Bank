/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bright.assetbank.application.form;
import org.apache.struts.action.ActionForm;
/**
 *
 * @author mamatha
 */
public class DetectVideoSceneForm extends ActionForm{
    private String message=null;
    private int noScenes=0;
    private String m_fileLocation = null;
    private String m_sTempDirName;

public String getMessage() {
        return message;
    }
 
public void setMessage(String message) {
        this.message = message;
    }
public void setNoScenes(int noScenes){
    this.noScenes = noScenes;
}

public int getNoScenes(){
    return this.noScenes;
}

public String getTempFileLocation()
{
     return this.m_fileLocation;
} 

public void setTempFileLocation(String a_sTempFileLocation)
{
     this.m_fileLocation = a_sTempFileLocation;
} 
public String getTempDirName()
{
    return this.m_sTempDirName;
}
public void setTempDirName(String a_sTempDirName)
   {
    this.m_sTempDirName = a_sTempDirName;
 }
}
