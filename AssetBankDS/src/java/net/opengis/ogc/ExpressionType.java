package net.opengis.ogc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExpressionType")
@XmlSeeAlso({BinaryOperatorType.class, FunctionType.class, PropertyNameType.class})
public abstract class ExpressionType
{
}

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ExpressionType
 * JD-Core Version:    0.6.0
 */