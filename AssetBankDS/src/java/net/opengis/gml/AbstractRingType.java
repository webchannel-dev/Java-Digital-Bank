package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AbstractRingType")
@XmlSeeAlso({RingType.class, LinearRingType.class})
public abstract class AbstractRingType extends AbstractGeometryType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractRingType
 * JD-Core Version:    0.6.0
 */