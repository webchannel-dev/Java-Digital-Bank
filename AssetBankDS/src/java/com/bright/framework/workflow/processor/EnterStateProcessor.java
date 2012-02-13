package com.bright.framework.workflow.processor;

import com.bn2web.common.exception.Bn2Exception;
import com.bright.framework.database.bean.DBTransaction;
import com.bright.framework.workflow.bean.WorkflowInfo;

public abstract interface EnterStateProcessor
{
  public abstract boolean process(DBTransaction paramDBTransaction, WorkflowInfo paramWorkflowInfo)
    throws Bn2Exception;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.processor.EnterStateProcessor
 * JD-Core Version:    0.6.0
 */