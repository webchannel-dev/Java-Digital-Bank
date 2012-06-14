package net.opengis.ogc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SpatialOpsType")
@XmlSeeAlso({BinarySpatialOpType.class, DistanceBufferType.class, BBOXType.class})
public abstract class SpatialOpsType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.SpatialOpsType
 * JD-Core Version:    0.6.0
 */