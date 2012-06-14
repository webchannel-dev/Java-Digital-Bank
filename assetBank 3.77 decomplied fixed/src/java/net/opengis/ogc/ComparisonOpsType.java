package net.opengis.ogc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ComparisonOpsType")
@XmlSeeAlso({BinaryComparisonOpType.class, PropertyIsLikeType.class, PropertyIsBetweenType.class, PropertyIsNullType.class})
public abstract class ComparisonOpsType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ComparisonOpsType
 * JD-Core Version:    0.6.0
 */