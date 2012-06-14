package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AbstractCurveType")
@XmlSeeAlso({CurveType.class, LineStringType.class, OrientableCurveType.class})
public abstract class AbstractCurveType extends AbstractGeometricPrimitiveType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractCurveType
 * JD-Core Version:    0.6.0
 */