package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AbstractGeometricPrimitiveType")
@XmlSeeAlso({PointType.class, AbstractSolidType.class, AbstractCurveType.class, AbstractSurfaceType.class})
public abstract class AbstractGeometricPrimitiveType extends AbstractGeometryType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractGeometricPrimitiveType
 * JD-Core Version:    0.6.0
 */