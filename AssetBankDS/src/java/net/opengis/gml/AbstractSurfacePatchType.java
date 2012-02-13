package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AbstractSurfacePatchType")
@XmlSeeAlso({TriangleType.class, RectangleType.class, AbstractParametricCurveSurfaceType.class, PolygonPatchType.class})
public abstract class AbstractSurfacePatchType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.AbstractSurfacePatchType
 * JD-Core Version:    0.6.0
 */