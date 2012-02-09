package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AbstractGeometricAggregateType")
@XmlSeeAlso({MultiSurfaceType.class, MultiLineStringType.class, MultiPolygonType.class, MultiGeometryType.class, MultiCurveType.class, MultiPointType.class, MultiSolidType.class})
public abstract class AbstractGeometricAggregateType extends AbstractGeometryType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractGeometricAggregateType
 * JD-Core Version:    0.6.0
 */