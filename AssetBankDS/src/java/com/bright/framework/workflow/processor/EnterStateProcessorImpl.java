package com.bright.framework.workflow.processor;

import com.bn2web.common.exception.Bn2Exception;
import com.bn2web.common.service.Bn2Manager;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.workflow.bean.WorkflowInfo;

public abstract class EnterStateProcessorImpl extends Bn2Manager
  implements EnterStateProcessor
{
  public abstract boolean process(DBTransaction paramDBTransaction, WorkflowInfo paramWorkflowInfo)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.processor.EnterStateProcessorImpl
 * JD-Core Version:    0.6.0
 */