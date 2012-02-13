package com.bright.assetbank.priceband.constant;

import com.bright.framework.constant.FrameworkConstants;

public abstract interface PriceBandConstants extends FrameworkConstants
{
  public static final long k_lPriceBandType_Download = 1L;
  public static final long k_lPriceBandType_Print = 2L;
  public static final long k_lOrderPriceBandSearchType_WithDownload = 1L;
  public static final long k_lOrderPriceBandSearchType_WithPrint = 2L;
  public static final String k_sOrderPriceBandSearchTypeText_WithDownload = "Contains Downloads";
  public static final String k_sOrderPriceBandSearchTypeText_WithPrint = "Contains Prints";
  public static final String k_sParamNamePrefix_IncludedUsage = "includedUsage_";
  public static final String k_sParamNamePrefix_Region = "region_";
  public static final String k_sParamNamePrefix_QuantityRange = "quantity_";
  public static final String k_sParamNamePrefix_DownloadPriceBand = "downloadpriceband_";
  public static final String k_sParamNamePrefix_PrintPriceBand = "printpriceband_";
  public static final String k_sParamNamePrefix_PrintPriceBandQuantity = "printpricebandquantity_";
  public static final String k_sParamNamePrefix_ShowAdd = "showadd";
  public static final long k_lPriceFree = -1L;
  public static final long k_lPriceUsePriceBands = 0L;
}

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.constant.PriceBandConstants
 * JD-Core Version:    0.6.0
 */