/*      */ package net.opengis.gml;
/*      */ 
/*      */ import java.math.BigDecimal;
/*      */ import java.util.List;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.bind.annotation.XmlElementDecl;
/*      */ import javax.xml.bind.annotation.XmlRegistry;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ @XmlRegistry
/*      */ public class ObjectFactory
/*      */ {
/*   36 */   private static final QName _UnitDefinition_QNAME = new QName("http://www.opengis.net/gml", "UnitDefinition");
/*   37 */   private static final QName _OuterBoundaryIs_QNAME = new QName("http://www.opengis.net/gml", "outerBoundaryIs");
/*   38 */   private static final QName _MultiLineString_QNAME = new QName("http://www.opengis.net/gml", "MultiLineString");
/*   39 */   private static final QName __GeometricAggregate_QNAME = new QName("http://www.opengis.net/gml", "_GeometricAggregate");
/*   40 */   private static final QName _BSpline_QNAME = new QName("http://www.opengis.net/gml", "BSpline");
/*   41 */   private static final QName _SolidProperty_QNAME = new QName("http://www.opengis.net/gml", "solidProperty");
/*   42 */   private static final QName _LinearRing_QNAME = new QName("http://www.opengis.net/gml", "LinearRing");
/*   43 */   private static final QName _TriangulatedSurface_QNAME = new QName("http://www.opengis.net/gml", "TriangulatedSurface");
/*   44 */   private static final QName _LineStringSegment_QNAME = new QName("http://www.opengis.net/gml", "LineStringSegment");
/*   45 */   private static final QName _DefinitionCollection_QNAME = new QName("http://www.opengis.net/gml", "DefinitionCollection");
/*   46 */   private static final QName _InnerBoundaryIs_QNAME = new QName("http://www.opengis.net/gml", "innerBoundaryIs");
/*   47 */   private static final QName __Reference_QNAME = new QName("http://www.opengis.net/gml", "_reference");
/*   48 */   private static final QName _MultiSolidProperty_QNAME = new QName("http://www.opengis.net/gml", "multiSolidProperty");
/*   49 */   private static final QName __GriddedSurface_QNAME = new QName("http://www.opengis.net/gml", "_GriddedSurface");
/*   50 */   private static final QName _UnitOfMeasure_QNAME = new QName("http://www.opengis.net/gml", "unitOfMeasure");
/*   51 */   private static final QName _SolidMembers_QNAME = new QName("http://www.opengis.net/gml", "solidMembers");
/*   52 */   private static final QName _Circle_QNAME = new QName("http://www.opengis.net/gml", "Circle");
/*   53 */   private static final QName _CurveProperty_QNAME = new QName("http://www.opengis.net/gml", "curveProperty");
/*   54 */   private static final QName _ConventionalUnit_QNAME = new QName("http://www.opengis.net/gml", "ConventionalUnit");
/*   55 */   private static final QName _Description_QNAME = new QName("http://www.opengis.net/gml", "description");
/*   56 */   private static final QName _SurfaceMembers_QNAME = new QName("http://www.opengis.net/gml", "surfaceMembers");
/*   57 */   private static final QName _PolygonPatch_QNAME = new QName("http://www.opengis.net/gml", "PolygonPatch");
/*   58 */   private static final QName _AffinePlacement_QNAME = new QName("http://www.opengis.net/gml", "AffinePlacement");
/*   59 */   private static final QName _ArcByCenterPoint_QNAME = new QName("http://www.opengis.net/gml", "ArcByCenterPoint");
/*   60 */   private static final QName _PosList_QNAME = new QName("http://www.opengis.net/gml", "posList");
/*   61 */   private static final QName _MetaDataProperty_QNAME = new QName("http://www.opengis.net/gml", "metaDataProperty");
/*   62 */   private static final QName _SurfaceProperty_QNAME = new QName("http://www.opengis.net/gml", "surfaceProperty");
/*   63 */   private static final QName __Solid_QNAME = new QName("http://www.opengis.net/gml", "_Solid");
/*   64 */   private static final QName _MultiSolid_QNAME = new QName("http://www.opengis.net/gml", "MultiSolid");
/*   65 */   private static final QName __Ring_QNAME = new QName("http://www.opengis.net/gml", "_Ring");
/*   66 */   private static final QName _OrientableCurve_QNAME = new QName("http://www.opengis.net/gml", "OrientableCurve");
/*   67 */   private static final QName _MultiPosition_QNAME = new QName("http://www.opengis.net/gml", "multiPosition");
/*   68 */   private static final QName _DefinitionRef_QNAME = new QName("http://www.opengis.net/gml", "definitionRef");
/*   69 */   private static final QName _SurfaceMember_QNAME = new QName("http://www.opengis.net/gml", "surfaceMember");
/*   70 */   private static final QName _MultiPolygon_QNAME = new QName("http://www.opengis.net/gml", "MultiPolygon");
/*   71 */   private static final QName _Solid_QNAME = new QName("http://www.opengis.net/gml", "Solid");
/*   72 */   private static final QName _Measure_QNAME = new QName("http://www.opengis.net/gml", "measure");
/*   73 */   private static final QName _PolygonMember_QNAME = new QName("http://www.opengis.net/gml", "polygonMember");
/*   74 */   private static final QName _MultiGeometry_QNAME = new QName("http://www.opengis.net/gml", "MultiGeometry");
/*   75 */   private static final QName _Members_QNAME = new QName("http://www.opengis.net/gml", "members");
/*   76 */   private static final QName __Association_QNAME = new QName("http://www.opengis.net/gml", "_association");
/*   77 */   private static final QName _MultiCurveProperty_QNAME = new QName("http://www.opengis.net/gml", "multiCurveProperty");
/*   78 */   private static final QName _PolyhedralSurface_QNAME = new QName("http://www.opengis.net/gml", "PolyhedralSurface");
/*   79 */   private static final QName _PolygonPatches_QNAME = new QName("http://www.opengis.net/gml", "polygonPatches");
/*   80 */   private static final QName _Name_QNAME = new QName("http://www.opengis.net/gml", "name");
/*   81 */   private static final QName __Curve_QNAME = new QName("http://www.opengis.net/gml", "_Curve");
/*   82 */   private static final QName _RoughConversionToPreferredUnit_QNAME = new QName("http://www.opengis.net/gml", "roughConversionToPreferredUnit");
/*   83 */   private static final QName _Clothoid_QNAME = new QName("http://www.opengis.net/gml", "Clothoid");
/*   84 */   private static final QName _GeometryMember_QNAME = new QName("http://www.opengis.net/gml", "geometryMember");
/*   85 */   private static final QName _PointProperty_QNAME = new QName("http://www.opengis.net/gml", "pointProperty");
/*   86 */   private static final QName _Null_QNAME = new QName("http://www.opengis.net/gml", "Null");
/*   87 */   private static final QName __GeometricPrimitive_QNAME = new QName("http://www.opengis.net/gml", "_GeometricPrimitive");
/*   88 */   private static final QName _MultiEdgeOf_QNAME = new QName("http://www.opengis.net/gml", "multiEdgeOf");
/*   89 */   private static final QName _Curve_QNAME = new QName("http://www.opengis.net/gml", "Curve");
/*   90 */   private static final QName _CurveMembers_QNAME = new QName("http://www.opengis.net/gml", "curveMembers");
/*   91 */   private static final QName _LineStringMember_QNAME = new QName("http://www.opengis.net/gml", "lineStringMember");
/*   92 */   private static final QName _Array_QNAME = new QName("http://www.opengis.net/gml", "Array");
/*   93 */   private static final QName _Pos_QNAME = new QName("http://www.opengis.net/gml", "pos");
/*   94 */   private static final QName _Arc_QNAME = new QName("http://www.opengis.net/gml", "Arc");
/*   95 */   private static final QName _Exterior_QNAME = new QName("http://www.opengis.net/gml", "exterior");
/*   96 */   private static final QName _GenericMetaData_QNAME = new QName("http://www.opengis.net/gml", "GenericMetaData");
/*   97 */   private static final QName _Surface_QNAME = new QName("http://www.opengis.net/gml", "Surface");
/*   98 */   private static final QName _Point_QNAME = new QName("http://www.opengis.net/gml", "Point");
/*   99 */   private static final QName _Coord_QNAME = new QName("http://www.opengis.net/gml", "coord");
/*  100 */   private static final QName _MultiCoverage_QNAME = new QName("http://www.opengis.net/gml", "multiCoverage");
/*  101 */   private static final QName _PolygonProperty_QNAME = new QName("http://www.opengis.net/gml", "polygonProperty");
/*  102 */   private static final QName _MultiSurface_QNAME = new QName("http://www.opengis.net/gml", "MultiSurface");
/*  103 */   private static final QName _Angle_QNAME = new QName("http://www.opengis.net/gml", "angle");
/*  104 */   private static final QName _Geodesic_QNAME = new QName("http://www.opengis.net/gml", "Geodesic");
/*  105 */   private static final QName _SolidMember_QNAME = new QName("http://www.opengis.net/gml", "solidMember");
/*  106 */   private static final QName _OrientableSurface_QNAME = new QName("http://www.opengis.net/gml", "OrientableSurface");
/*  107 */   private static final QName __Object_QNAME = new QName("http://www.opengis.net/gml", "_Object");
/*  108 */   private static final QName _Member_QNAME = new QName("http://www.opengis.net/gml", "member");
/*  109 */   private static final QName _CurveMember_QNAME = new QName("http://www.opengis.net/gml", "curveMember");
/*  110 */   private static final QName _DecimalMinutes_QNAME = new QName("http://www.opengis.net/gml", "decimalMinutes");
/*  111 */   private static final QName _Rectangle_QNAME = new QName("http://www.opengis.net/gml", "Rectangle");
/*  112 */   private static final QName _Tin_QNAME = new QName("http://www.opengis.net/gml", "Tin");
/*  113 */   private static final QName _DerivationUnitTerm_QNAME = new QName("http://www.opengis.net/gml", "derivationUnitTerm");
/*  114 */   private static final QName _Polygon_QNAME = new QName("http://www.opengis.net/gml", "Polygon");
/*  115 */   private static final QName _DefinitionProxy_QNAME = new QName("http://www.opengis.net/gml", "DefinitionProxy");
/*  116 */   private static final QName _Seconds_QNAME = new QName("http://www.opengis.net/gml", "seconds");
/*  117 */   private static final QName _CubicSpline_QNAME = new QName("http://www.opengis.net/gml", "CubicSpline");
/*  118 */   private static final QName _Ring_QNAME = new QName("http://www.opengis.net/gml", "Ring");
/*  119 */   private static final QName _CatalogSymbol_QNAME = new QName("http://www.opengis.net/gml", "catalogSymbol");
/*  120 */   private static final QName _DerivedUnit_QNAME = new QName("http://www.opengis.net/gml", "DerivedUnit");
/*  121 */   private static final QName _ArcString_QNAME = new QName("http://www.opengis.net/gml", "ArcString");
/*  122 */   private static final QName __SurfacePatch_QNAME = new QName("http://www.opengis.net/gml", "_SurfacePatch");
/*  123 */   private static final QName _MultiExtentOf_QNAME = new QName("http://www.opengis.net/gml", "multiExtentOf");
/*  124 */   private static final QName _MultiGeometryProperty_QNAME = new QName("http://www.opengis.net/gml", "multiGeometryProperty");
/*  125 */   private static final QName _MultiCenterOf_QNAME = new QName("http://www.opengis.net/gml", "multiCenterOf");
/*  126 */   private static final QName __CurveSegment_QNAME = new QName("http://www.opengis.net/gml", "_CurveSegment");
/*  127 */   private static final QName _Interior_QNAME = new QName("http://www.opengis.net/gml", "interior");
/*  128 */   private static final QName _CircleByCenterPoint_QNAME = new QName("http://www.opengis.net/gml", "CircleByCenterPoint");
/*  129 */   private static final QName _Sphere_QNAME = new QName("http://www.opengis.net/gml", "Sphere");
/*  130 */   private static final QName _SolidArrayProperty_QNAME = new QName("http://www.opengis.net/gml", "solidArrayProperty");
/*  131 */   private static final QName _PointMembers_QNAME = new QName("http://www.opengis.net/gml", "pointMembers");
/*  132 */   private static final QName _Degrees_QNAME = new QName("http://www.opengis.net/gml", "degrees");
/*  133 */   private static final QName _Minutes_QNAME = new QName("http://www.opengis.net/gml", "minutes");
/*  134 */   private static final QName __MetaData_QNAME = new QName("http://www.opengis.net/gml", "_MetaData");
/*  135 */   private static final QName __StrictAssociation_QNAME = new QName("http://www.opengis.net/gml", "_strictAssociation");
/*  136 */   private static final QName _ArcByBulge_QNAME = new QName("http://www.opengis.net/gml", "ArcByBulge");
/*  137 */   private static final QName _CurveArrayProperty_QNAME = new QName("http://www.opengis.net/gml", "curveArrayProperty");
/*  138 */   private static final QName _PointRep_QNAME = new QName("http://www.opengis.net/gml", "pointRep");
/*  139 */   private static final QName _PointArrayProperty_QNAME = new QName("http://www.opengis.net/gml", "pointArrayProperty");
/*  140 */   private static final QName __Geometry_QNAME = new QName("http://www.opengis.net/gml", "_Geometry");
/*  141 */   private static final QName _DmsAngle_QNAME = new QName("http://www.opengis.net/gml", "dmsAngle");
/*  142 */   private static final QName _Bag_QNAME = new QName("http://www.opengis.net/gml", "Bag");
/*  143 */   private static final QName _OffsetCurve_QNAME = new QName("http://www.opengis.net/gml", "OffsetCurve");
/*  144 */   private static final QName _ConversionToPreferredUnit_QNAME = new QName("http://www.opengis.net/gml", "conversionToPreferredUnit");
/*  145 */   private static final QName _Coordinates_QNAME = new QName("http://www.opengis.net/gml", "coordinates");
/*  146 */   private static final QName _MultiPoint_QNAME = new QName("http://www.opengis.net/gml", "MultiPoint");
/*  147 */   private static final QName _MultiCurve_QNAME = new QName("http://www.opengis.net/gml", "MultiCurve");
/*  148 */   private static final QName _Vector_QNAME = new QName("http://www.opengis.net/gml", "vector");
/*  149 */   private static final QName _MultiCenterLineOf_QNAME = new QName("http://www.opengis.net/gml", "multiCenterLineOf");
/*  150 */   private static final QName _GeometryMembers_QNAME = new QName("http://www.opengis.net/gml", "geometryMembers");
/*  151 */   private static final QName _DictionaryEntry_QNAME = new QName("http://www.opengis.net/gml", "dictionaryEntry");
/*  152 */   private static final QName __GML_QNAME = new QName("http://www.opengis.net/gml", "_GML");
/*  153 */   private static final QName _IndirectEntry_QNAME = new QName("http://www.opengis.net/gml", "indirectEntry");
/*  154 */   private static final QName _Bezier_QNAME = new QName("http://www.opengis.net/gml", "Bezier");
/*  155 */   private static final QName _MultiPointProperty_QNAME = new QName("http://www.opengis.net/gml", "multiPointProperty");
/*  156 */   private static final QName _Patches_QNAME = new QName("http://www.opengis.net/gml", "patches");
/*  157 */   private static final QName _ArcStringByBulge_QNAME = new QName("http://www.opengis.net/gml", "ArcStringByBulge");
/*  158 */   private static final QName _MultiLocation_QNAME = new QName("http://www.opengis.net/gml", "multiLocation");
/*  159 */   private static final QName _LineStringProperty_QNAME = new QName("http://www.opengis.net/gml", "lineStringProperty");
/*  160 */   private static final QName _TrianglePatches_QNAME = new QName("http://www.opengis.net/gml", "trianglePatches");
/*  161 */   private static final QName _MultiSurfaceProperty_QNAME = new QName("http://www.opengis.net/gml", "multiSurfaceProperty");
/*  162 */   private static final QName _DefinitionMember_QNAME = new QName("http://www.opengis.net/gml", "definitionMember");
/*  163 */   private static final QName _SurfaceArrayProperty_QNAME = new QName("http://www.opengis.net/gml", "surfaceArrayProperty");
/*  164 */   private static final QName _Triangle_QNAME = new QName("http://www.opengis.net/gml", "Triangle");
/*  165 */   private static final QName _Cone_QNAME = new QName("http://www.opengis.net/gml", "Cone");
/*  166 */   private static final QName _QuantityType_QNAME = new QName("http://www.opengis.net/gml", "quantityType");
/*  167 */   private static final QName _LineString_QNAME = new QName("http://www.opengis.net/gml", "LineString");
/*  168 */   private static final QName _BaseUnit_QNAME = new QName("http://www.opengis.net/gml", "BaseUnit");
/*  169 */   public static final QName _Envelope_QNAME = new QName("http://www.opengis.net/gml", "Envelope");
/*  170 */   private static final QName _Definition_QNAME = new QName("http://www.opengis.net/gml", "Definition");
/*  171 */   private static final QName _GeodesicString_QNAME = new QName("http://www.opengis.net/gml", "GeodesicString");
/*  172 */   private static final QName __Surface_QNAME = new QName("http://www.opengis.net/gml", "_Surface");
/*  173 */   private static final QName _BaseSurface_QNAME = new QName("http://www.opengis.net/gml", "baseSurface");
/*  174 */   private static final QName _PointMember_QNAME = new QName("http://www.opengis.net/gml", "pointMember");
/*  175 */   private static final QName _Cylinder_QNAME = new QName("http://www.opengis.net/gml", "Cylinder");
/*  176 */   private static final QName _Segments_QNAME = new QName("http://www.opengis.net/gml", "segments");
/*  177 */   private static final QName __ParametricCurveSurface_QNAME = new QName("http://www.opengis.net/gml", "_ParametricCurveSurface");
/*  178 */   private static final QName _Dictionary_QNAME = new QName("http://www.opengis.net/gml", "Dictionary");
/*  179 */   private static final QName _BaseCurve_QNAME = new QName("http://www.opengis.net/gml", "baseCurve");
/*      */ 
/*      */   public TinType createTinType()
/*      */   {
/*  193 */     return new TinType();
/*      */   }
/*      */ 
/*      */   public AreaType createAreaType()
/*      */   {
/*  201 */     return new AreaType();
/*      */   }
/*      */ 
/*      */   public MultiSolidPropertyType createMultiSolidPropertyType()
/*      */   {
/*  209 */     return new MultiSolidPropertyType();
/*      */   }
/*      */ 
/*      */   public FormulaType createFormulaType()
/*      */   {
/*  217 */     return new FormulaType();
/*      */   }
/*      */ 
/*      */   public AssociationType createAssociationType()
/*      */   {
/*  225 */     return new AssociationType();
/*      */   }
/*      */ 
/*      */   public ClothoidType createClothoidType()
/*      */   {
/*  233 */     return new ClothoidType();
/*      */   }
/*      */ 
/*      */   public UnitOfMeasureType createUnitOfMeasureType()
/*      */   {
/*  241 */     return new UnitOfMeasureType();
/*      */   }
/*      */ 
/*      */   public ArcByBulgeType createArcByBulgeType()
/*      */   {
/*  249 */     return new ArcByBulgeType();
/*      */   }
/*      */ 
/*      */   public MultiPolygonPropertyType createMultiPolygonPropertyType()
/*      */   {
/*  257 */     return new MultiPolygonPropertyType();
/*      */   }
/*      */ 
/*      */   public ArrayType createArrayType()
/*      */   {
/*  265 */     return new ArrayType();
/*      */   }
/*      */ 
/*      */   public TriangulatedSurfaceType createTriangulatedSurfaceType()
/*      */   {
/*  273 */     return new TriangulatedSurfaceType();
/*      */   }
/*      */ 
/*      */   public LinearRingPropertyType createLinearRingPropertyType()
/*      */   {
/*  281 */     return new LinearRingPropertyType();
/*      */   }
/*      */ 
/*      */   public PointPropertyType createPointPropertyType()
/*      */   {
/*  289 */     return new PointPropertyType();
/*      */   }
/*      */ 
/*      */   public ArcStringByBulgeType createArcStringByBulgeType()
/*      */   {
/*  297 */     return new ArcStringByBulgeType();
/*      */   }
/*      */ 
/*      */   public MeasureListType createMeasureListType()
/*      */   {
/*  305 */     return new MeasureListType();
/*      */   }
/*      */ 
/*      */   public AbstractGriddedSurfaceType createAbstractGriddedSurfaceType()
/*      */   {
/*  313 */     return new AbstractGriddedSurfaceType();
/*      */   }
/*      */ 
/*      */   public DerivationUnitTermType createDerivationUnitTermType()
/*      */   {
/*  321 */     return new DerivationUnitTermType();
/*      */   }
/*      */ 
/*      */   public VolumeType createVolumeType()
/*      */   {
/*  329 */     return new VolumeType();
/*      */   }
/*      */ 
/*      */   public CodeListType createCodeListType()
/*      */   {
/*  337 */     return new CodeListType();
/*      */   }
/*      */ 
/*      */   public UnitDefinitionType createUnitDefinitionType()
/*      */   {
/*  345 */     return new UnitDefinitionType();
/*      */   }
/*      */ 
/*      */   public GridLengthType createGridLengthType()
/*      */   {
/*  353 */     return new GridLengthType();
/*      */   }
/*      */ 
/*      */   public BezierType createBezierType()
/*      */   {
/*  361 */     return new BezierType();
/*      */   }
/*      */ 
/*      */   public AbstractSurfaceType createAbstractSurfaceType()
/*      */   {
/*  369 */     return new AbstractSurfaceType();
/*      */   }
/*      */ 
/*      */   public CoordType createCoordType()
/*      */   {
/*  377 */     return new CoordType();
/*      */   }
/*      */ 
/*      */   public MetaDataPropertyType createMetaDataPropertyType()
/*      */   {
/*  385 */     return new MetaDataPropertyType();
/*      */   }
/*      */ 
/*      */   public MeasureOrNullListType createMeasureOrNullListType()
/*      */   {
/*  393 */     return new MeasureOrNullListType();
/*      */   }
/*      */ 
/*      */   public DerivedUnitType createDerivedUnitType()
/*      */   {
/*  401 */     return new DerivedUnitType();
/*      */   }
/*      */ 
/*      */   public IndirectEntryType createIndirectEntryType()
/*      */   {
/*  409 */     return new IndirectEntryType();
/*      */   }
/*      */ 
/*      */   public RectangleType createRectangleType()
/*      */   {
/*  417 */     return new RectangleType();
/*      */   }
/*      */ 
/*      */   public CoordinatesType createCoordinatesType()
/*      */   {
/*  425 */     return new CoordinatesType();
/*      */   }
/*      */ 
/*      */   public OffsetCurveType createOffsetCurveType()
/*      */   {
/*  433 */     return new OffsetCurveType();
/*      */   }
/*      */ 
/*      */   public MultiLineStringPropertyType createMultiLineStringPropertyType()
/*      */   {
/*  441 */     return new MultiLineStringPropertyType();
/*      */   }
/*      */ 
/*      */   public PointArrayPropertyType createPointArrayPropertyType()
/*      */   {
/*  449 */     return new PointArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public CylinderType createCylinderType()
/*      */   {
/*  457 */     return new CylinderType();
/*      */   }
/*      */ 
/*      */   public SolidArrayPropertyType createSolidArrayPropertyType()
/*      */   {
/*  465 */     return new SolidArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public MultiGeometryType createMultiGeometryType()
/*      */   {
/*  473 */     return new MultiGeometryType();
/*      */   }
/*      */ 
/*      */   public RingType createRingType()
/*      */   {
/*  481 */     return new RingType();
/*      */   }
/*      */ 
/*      */   public PolygonPatchType createPolygonPatchType()
/*      */   {
/*  489 */     return new PolygonPatchType();
/*      */   }
/*      */ 
/*      */   public RefLocation createClothoidTypeRefLocation()
/*      */   {
/*  497 */     return new RefLocation();
/*      */   }
/*      */ 
/*      */   public AffinePlacementType createAffinePlacementType()
/*      */   {
/*  505 */     return new AffinePlacementType();
/*      */   }
/*      */ 
/*      */   public PolygonPatchArrayPropertyType createPolygonPatchArrayPropertyType()
/*      */   {
/*  513 */     return new PolygonPatchArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public DirectPositionType createDirectPositionType()
/*      */   {
/*  521 */     return new DirectPositionType();
/*      */   }
/*      */ 
/*      */   public ControlPoint createTinTypeControlPoint()
/*      */   {
/*  529 */     return new ControlPoint();
/*      */   }
/*      */ 
/*      */   public RingPropertyType createRingPropertyType()
/*      */   {
/*  537 */     return new RingPropertyType();
/*      */   }
/*      */ 
/*      */   public DegreesType createDegreesType()
/*      */   {
/*  545 */     return new DegreesType();
/*      */   }
/*      */ 
/*      */   public LineStringType createLineStringType()
/*      */   {
/*  553 */     return new LineStringType();
/*      */   }
/*      */ 
/*      */   public CircleType createCircleType()
/*      */   {
/*  561 */     return new CircleType();
/*      */   }
/*      */ 
/*      */   public SurfacePropertyType createSurfacePropertyType()
/*      */   {
/*  569 */     return new SurfacePropertyType();
/*      */   }
/*      */ 
/*      */   public BSplineType createBSplineType()
/*      */   {
/*  577 */     return new BSplineType();
/*      */   }
/*      */ 
/*      */   public ConventionalUnitType createConventionalUnitType()
/*      */   {
/*  585 */     return new ConventionalUnitType();
/*      */   }
/*      */ 
/*      */   public BagType createBagType()
/*      */   {
/*  593 */     return new BagType();
/*      */   }
/*      */ 
/*      */   public AbstractSolidType createAbstractSolidType()
/*      */   {
/*  601 */     return new AbstractSolidType();
/*      */   }
/*      */ 
/*      */   public KnotPropertyType createKnotPropertyType()
/*      */   {
/*  609 */     return new KnotPropertyType();
/*      */   }
/*      */ 
/*      */   public SphereType createSphereType()
/*      */   {
/*  617 */     return new SphereType();
/*      */   }
/*      */ 
/*      */   public TimeType createTimeType()
/*      */   {
/*  625 */     return new TimeType();
/*      */   }
/*      */ 
/*      */   public PolygonType createPolygonType()
/*      */   {
/*  633 */     return new PolygonType();
/*      */   }
/*      */ 
/*      */   public DefinitionType createDefinitionType()
/*      */   {
/*  641 */     return new DefinitionType();
/*      */   }
/*      */ 
/*      */   public ArcStringType createArcStringType()
/*      */   {
/*  649 */     return new ArcStringType();
/*      */   }
/*      */ 
/*      */   public OrientableCurveType createOrientableCurveType()
/*      */   {
/*  657 */     return new OrientableCurveType();
/*      */   }
/*      */ 
/*      */   public ConversionToPreferredUnitType createConversionToPreferredUnitType()
/*      */   {
/*  665 */     return new ConversionToPreferredUnitType();
/*      */   }
/*      */ 
/*      */   public DMSAngleType createDMSAngleType()
/*      */   {
/*  673 */     return new DMSAngleType();
/*      */   }
/*      */ 
/*      */   public EnvelopeType createEnvelopeType()
/*      */   {
/*  681 */     return new EnvelopeType();
/*      */   }
/*      */ 
/*      */   public DirectPositionListType createDirectPositionListType()
/*      */   {
/*  689 */     return new DirectPositionListType();
/*      */   }
/*      */ 
/*      */   public GeodesicStringType createGeodesicStringType()
/*      */   {
/*  697 */     return new GeodesicStringType();
/*      */   }
/*      */ 
/*      */   public CodeType createCodeType()
/*      */   {
/*  705 */     return new CodeType();
/*      */   }
/*      */ 
/*      */   public MultiSolidType createMultiSolidType()
/*      */   {
/*  713 */     return new MultiSolidType();
/*      */   }
/*      */ 
/*      */   public CurveArrayPropertyType createCurveArrayPropertyType()
/*      */   {
/*  721 */     return new CurveArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public AbstractParametricCurveSurfaceType createAbstractParametricCurveSurfaceType()
/*      */   {
/*  729 */     return new AbstractParametricCurveSurfaceType();
/*      */   }
/*      */ 
/*      */   public GeodesicType createGeodesicType()
/*      */   {
/*  737 */     return new GeodesicType();
/*      */   }
/*      */ 
/*      */   public PolygonPropertyType createPolygonPropertyType()
/*      */   {
/*  745 */     return new PolygonPropertyType();
/*      */   }
/*      */ 
/*      */   public SolidType createSolidType()
/*      */   {
/*  753 */     return new SolidType();
/*      */   }
/*      */ 
/*      */   public TrianglePatchArrayPropertyType createTrianglePatchArrayPropertyType()
/*      */   {
/*  761 */     return new TrianglePatchArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public GeometryArrayPropertyType createGeometryArrayPropertyType()
/*      */   {
/*  769 */     return new GeometryArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public DictionaryType createDictionaryType()
/*      */   {
/*  777 */     return new DictionaryType();
/*      */   }
/*      */ 
/*      */   public LengthType createLengthType()
/*      */   {
/*  785 */     return new LengthType();
/*      */   }
/*      */ 
/*      */   public LineStringPropertyType createLineStringPropertyType()
/*      */   {
/*  793 */     return new LineStringPropertyType();
/*      */   }
/*      */ 
/*      */   public MultiSurfacePropertyType createMultiSurfacePropertyType()
/*      */   {
/*  801 */     return new MultiSurfacePropertyType();
/*      */   }
/*      */ 
/*      */   public CurveSegmentArrayPropertyType createCurveSegmentArrayPropertyType()
/*      */   {
/*  809 */     return new CurveSegmentArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public CurvePropertyType createCurvePropertyType()
/*      */   {
/*  817 */     return new CurvePropertyType();
/*      */   }
/*      */ 
/*      */   public LinearRingType createLinearRingType()
/*      */   {
/*  825 */     return new LinearRingType();
/*      */   }
/*      */ 
/*      */   public SolidPropertyType createSolidPropertyType()
/*      */   {
/*  833 */     return new SolidPropertyType();
/*      */   }
/*      */ 
/*      */   public KnotType createKnotType()
/*      */   {
/*  841 */     return new KnotType();
/*      */   }
/*      */ 
/*      */   public DictionaryEntryType createDictionaryEntryType()
/*      */   {
/*  849 */     return new DictionaryEntryType();
/*      */   }
/*      */ 
/*      */   public OrientableSurfaceType createOrientableSurfaceType()
/*      */   {
/*  857 */     return new OrientableSurfaceType();
/*      */   }
/*      */ 
/*      */   public AngleChoiceType createAngleChoiceType()
/*      */   {
/*  865 */     return new AngleChoiceType();
/*      */   }
/*      */ 
/*      */   public VectorType createVectorType()
/*      */   {
/*  873 */     return new VectorType();
/*      */   }
/*      */ 
/*      */   public MultiGeometryPropertyType createMultiGeometryPropertyType()
/*      */   {
/*  881 */     return new MultiGeometryPropertyType();
/*      */   }
/*      */ 
/*      */   public CurveType createCurveType()
/*      */   {
/*  889 */     return new CurveType();
/*      */   }
/*      */ 
/*      */   public ArcByCenterPointType createArcByCenterPointType()
/*      */   {
/*  897 */     return new ArcByCenterPointType();
/*      */   }
/*      */ 
/*      */   public ConeType createConeType()
/*      */   {
/*  905 */     return new ConeType();
/*      */   }
/*      */ 
/*      */   public DefinitionProxyType createDefinitionProxyType()
/*      */   {
/*  913 */     return new DefinitionProxyType();
/*      */   }
/*      */ 
/*      */   public LineStringSegmentArrayPropertyType createLineStringSegmentArrayPropertyType()
/*      */   {
/*  921 */     return new LineStringSegmentArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public GenericMetaDataType createGenericMetaDataType()
/*      */   {
/*  929 */     return new GenericMetaDataType();
/*      */   }
/*      */ 
/*      */   public SurfaceType createSurfaceType()
/*      */   {
/*  937 */     return new SurfaceType();
/*      */   }
/*      */ 
/*      */   public MultiLineStringType createMultiLineStringType()
/*      */   {
/*  945 */     return new MultiLineStringType();
/*      */   }
/*      */ 
/*      */   public MultiCurvePropertyType createMultiCurvePropertyType()
/*      */   {
/*  953 */     return new MultiCurvePropertyType();
/*      */   }
/*      */ 
/*      */   public ArrayAssociationType createArrayAssociationType()
/*      */   {
/*  961 */     return new ArrayAssociationType();
/*      */   }
/*      */ 
/*      */   public PolyhedralSurfaceType createPolyhedralSurfaceType()
/*      */   {
/*  969 */     return new PolyhedralSurfaceType();
/*      */   }
/*      */ 
/*      */   public BaseUnitType createBaseUnitType()
/*      */   {
/*  977 */     return new BaseUnitType();
/*      */   }
/*      */ 
/*      */   public CodeOrNullListType createCodeOrNullListType()
/*      */   {
/*  985 */     return new CodeOrNullListType();
/*      */   }
/*      */ 
/*      */   public MultiSurfaceType createMultiSurfaceType()
/*      */   {
/*  993 */     return new MultiSurfaceType();
/*      */   }
/*      */ 
/*      */   public MeasureType createMeasureType()
/*      */   {
/* 1001 */     return new MeasureType();
/*      */   }
/*      */ 
/*      */   public SpeedType createSpeedType()
/*      */   {
/* 1009 */     return new SpeedType();
/*      */   }
/*      */ 
/*      */   public MultiPointPropertyType createMultiPointPropertyType()
/*      */   {
/* 1017 */     return new MultiPointPropertyType();
/*      */   }
/*      */ 
/*      */   public CircleByCenterPointType createCircleByCenterPointType()
/*      */   {
/* 1025 */     return new CircleByCenterPointType();
/*      */   }
/*      */ 
/*      */   public AbstractRingPropertyType createAbstractRingPropertyType()
/*      */   {
/* 1033 */     return new AbstractRingPropertyType();
/*      */   }
/*      */ 
/*      */   public SurfacePatchArrayPropertyType createSurfacePatchArrayPropertyType()
/*      */   {
/* 1041 */     return new SurfacePatchArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public LineStringSegmentType createLineStringSegmentType()
/*      */   {
/* 1049 */     return new LineStringSegmentType();
/*      */   }
/*      */ 
/*      */   public MultiPointType createMultiPointType()
/*      */   {
/* 1057 */     return new MultiPointType();
/*      */   }
/*      */ 
/*      */   public MultiPolygonType createMultiPolygonType()
/*      */   {
/* 1065 */     return new MultiPolygonType();
/*      */   }
/*      */ 
/*      */   public GeometryPropertyType createGeometryPropertyType()
/*      */   {
/* 1073 */     return new GeometryPropertyType();
/*      */   }
/*      */ 
/*      */   public CubicSplineType createCubicSplineType()
/*      */   {
/* 1081 */     return new CubicSplineType();
/*      */   }
/*      */ 
/*      */   public GeometricPrimitivePropertyType createGeometricPrimitivePropertyType()
/*      */   {
/* 1089 */     return new GeometricPrimitivePropertyType();
/*      */   }
/*      */ 
/*      */   public PointType createPointType()
/*      */   {
/* 1097 */     return new PointType();
/*      */   }
/*      */ 
/*      */   public AngleType createAngleType()
/*      */   {
/* 1105 */     return new AngleType();
/*      */   }
/*      */ 
/*      */   public MultiCurveType createMultiCurveType()
/*      */   {
/* 1113 */     return new MultiCurveType();
/*      */   }
/*      */ 
/*      */   public Row createAbstractGriddedSurfaceTypeRow()
/*      */   {
/* 1121 */     return new Row();
/*      */   }
/*      */ 
/*      */   public StringOrRefType createStringOrRefType()
/*      */   {
/* 1129 */     return new StringOrRefType();
/*      */   }
/*      */ 
/*      */   public SurfaceArrayPropertyType createSurfaceArrayPropertyType()
/*      */   {
/* 1137 */     return new SurfaceArrayPropertyType();
/*      */   }
/*      */ 
/*      */   public ScaleType createScaleType()
/*      */   {
/* 1145 */     return new ScaleType();
/*      */   }
/*      */ 
/*      */   public ArcType createArcType()
/*      */   {
/* 1153 */     return new ArcType();
/*      */   }
/*      */ 
/*      */   public TriangleType createTriangleType()
/*      */   {
/* 1161 */     return new TriangleType();
/*      */   }
/*      */ 
/*      */   public ReferenceType createReferenceType()
/*      */   {
/* 1169 */     return new ReferenceType();
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="UnitDefinition", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Definition")
/*      */   public JAXBElement<UnitDefinitionType> createUnitDefinition(UnitDefinitionType value)
/*      */   {
/* 1178 */     return new JAXBElement(_UnitDefinition_QNAME, UnitDefinitionType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="outerBoundaryIs", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="exterior")
/*      */   public JAXBElement<AbstractRingPropertyType> createOuterBoundaryIs(AbstractRingPropertyType value)
/*      */   {
/* 1187 */     return new JAXBElement(_OuterBoundaryIs_QNAME, AbstractRingPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiLineString", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiLineStringType> createMultiLineString(MultiLineStringType value)
/*      */   {
/* 1196 */     return new JAXBElement(_MultiLineString_QNAME, MultiLineStringType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_GeometricAggregate", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Geometry")
/*      */   public JAXBElement<AbstractGeometricAggregateType> create_GeometricAggregate(AbstractGeometricAggregateType value)
/*      */   {
/* 1205 */     return new JAXBElement(__GeometricAggregate_QNAME, AbstractGeometricAggregateType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="BSpline", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<BSplineType> createBSpline(BSplineType value)
/*      */   {
/* 1214 */     return new JAXBElement(_BSpline_QNAME, BSplineType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="solidProperty")
/*      */   public JAXBElement<SolidPropertyType> createSolidProperty(SolidPropertyType value)
/*      */   {
/* 1223 */     return new JAXBElement(_SolidProperty_QNAME, SolidPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="LinearRing", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Ring")
/*      */   public JAXBElement<LinearRingType> createLinearRing(LinearRingType value)
/*      */   {
/* 1232 */     return new JAXBElement(_LinearRing_QNAME, LinearRingType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="TriangulatedSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Surface")
/*      */   public JAXBElement<TriangulatedSurfaceType> createTriangulatedSurface(TriangulatedSurfaceType value)
/*      */   {
/* 1241 */     return new JAXBElement(_TriangulatedSurface_QNAME, TriangulatedSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="LineStringSegment", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<LineStringSegmentType> createLineStringSegment(LineStringSegmentType value)
/*      */   {
/* 1250 */     return new JAXBElement(_LineStringSegment_QNAME, LineStringSegmentType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="DefinitionCollection", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Definition")
/*      */   public JAXBElement<DictionaryType> createDefinitionCollection(DictionaryType value)
/*      */   {
/* 1259 */     return new JAXBElement(_DefinitionCollection_QNAME, DictionaryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="innerBoundaryIs", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="interior")
/*      */   public JAXBElement<AbstractRingPropertyType> createInnerBoundaryIs(AbstractRingPropertyType value)
/*      */   {
/* 1268 */     return new JAXBElement(_InnerBoundaryIs_QNAME, AbstractRingPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_reference")
/*      */   public JAXBElement<ReferenceType> create_Reference(ReferenceType value)
/*      */   {
/* 1277 */     return new JAXBElement(__Reference_QNAME, ReferenceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiSolidProperty")
/*      */   public JAXBElement<MultiSolidPropertyType> createMultiSolidProperty(MultiSolidPropertyType value)
/*      */   {
/* 1286 */     return new JAXBElement(_MultiSolidProperty_QNAME, MultiSolidPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_GriddedSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_ParametricCurveSurface")
/*      */   public JAXBElement<AbstractGriddedSurfaceType> create_GriddedSurface(AbstractGriddedSurfaceType value)
/*      */   {
/* 1295 */     return new JAXBElement(__GriddedSurface_QNAME, AbstractGriddedSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="unitOfMeasure")
/*      */   public JAXBElement<UnitOfMeasureType> createUnitOfMeasure(UnitOfMeasureType value)
/*      */   {
/* 1304 */     return new JAXBElement(_UnitOfMeasure_QNAME, UnitOfMeasureType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="solidMembers")
/*      */   public JAXBElement<SolidArrayPropertyType> createSolidMembers(SolidArrayPropertyType value)
/*      */   {
/* 1313 */     return new JAXBElement(_SolidMembers_QNAME, SolidArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Circle", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Arc")
/*      */   public JAXBElement<CircleType> createCircle(CircleType value)
/*      */   {
/* 1322 */     return new JAXBElement(_Circle_QNAME, CircleType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="curveProperty")
/*      */   public JAXBElement<CurvePropertyType> createCurveProperty(CurvePropertyType value)
/*      */   {
/* 1331 */     return new JAXBElement(_CurveProperty_QNAME, CurvePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="ConventionalUnit", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="UnitDefinition")
/*      */   public JAXBElement<ConventionalUnitType> createConventionalUnit(ConventionalUnitType value)
/*      */   {
/* 1340 */     return new JAXBElement(_ConventionalUnit_QNAME, ConventionalUnitType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="description")
/*      */   public JAXBElement<StringOrRefType> createDescription(StringOrRefType value)
/*      */   {
/* 1349 */     return new JAXBElement(_Description_QNAME, StringOrRefType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="surfaceMembers")
/*      */   public JAXBElement<SurfaceArrayPropertyType> createSurfaceMembers(SurfaceArrayPropertyType value)
/*      */   {
/* 1358 */     return new JAXBElement(_SurfaceMembers_QNAME, SurfaceArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="PolygonPatch", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_SurfacePatch")
/*      */   public JAXBElement<PolygonPatchType> createPolygonPatch(PolygonPatchType value)
/*      */   {
/* 1367 */     return new JAXBElement(_PolygonPatch_QNAME, PolygonPatchType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="AffinePlacement")
/*      */   public JAXBElement<AffinePlacementType> createAffinePlacement(AffinePlacementType value)
/*      */   {
/* 1376 */     return new JAXBElement(_AffinePlacement_QNAME, AffinePlacementType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="ArcByCenterPoint", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<ArcByCenterPointType> createArcByCenterPoint(ArcByCenterPointType value)
/*      */   {
/* 1385 */     return new JAXBElement(_ArcByCenterPoint_QNAME, ArcByCenterPointType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="posList")
/*      */   public JAXBElement<DirectPositionListType> createPosList(DirectPositionListType value)
/*      */   {
/* 1394 */     return new JAXBElement(_PosList_QNAME, DirectPositionListType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="metaDataProperty")
/*      */   public JAXBElement<MetaDataPropertyType> createMetaDataProperty(MetaDataPropertyType value)
/*      */   {
/* 1403 */     return new JAXBElement(_MetaDataProperty_QNAME, MetaDataPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="surfaceProperty")
/*      */   public JAXBElement<SurfacePropertyType> createSurfaceProperty(SurfacePropertyType value)
/*      */   {
/* 1412 */     return new JAXBElement(_SurfaceProperty_QNAME, SurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Solid", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricPrimitive")
/*      */   public JAXBElement<AbstractSolidType> create_Solid(AbstractSolidType value)
/*      */   {
/* 1421 */     return new JAXBElement(__Solid_QNAME, AbstractSolidType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiSolid", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiSolidType> createMultiSolid(MultiSolidType value)
/*      */   {
/* 1430 */     return new JAXBElement(_MultiSolid_QNAME, MultiSolidType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Ring", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Geometry")
/*      */   public JAXBElement<AbstractRingType> create_Ring(AbstractRingType value)
/*      */   {
/* 1439 */     return new JAXBElement(__Ring_QNAME, AbstractRingType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="OrientableCurve", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Curve")
/*      */   public JAXBElement<OrientableCurveType> createOrientableCurve(OrientableCurveType value)
/*      */   {
/* 1448 */     return new JAXBElement(_OrientableCurve_QNAME, OrientableCurveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiPosition")
/*      */   public JAXBElement<MultiPointPropertyType> createMultiPosition(MultiPointPropertyType value)
/*      */   {
/* 1457 */     return new JAXBElement(_MultiPosition_QNAME, MultiPointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="definitionRef")
/*      */   public JAXBElement<ReferenceType> createDefinitionRef(ReferenceType value)
/*      */   {
/* 1466 */     return new JAXBElement(_DefinitionRef_QNAME, ReferenceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="surfaceMember")
/*      */   public JAXBElement<SurfacePropertyType> createSurfaceMember(SurfacePropertyType value)
/*      */   {
/* 1475 */     return new JAXBElement(_SurfaceMember_QNAME, SurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiPolygon", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiPolygonType> createMultiPolygon(MultiPolygonType value)
/*      */   {
/* 1484 */     return new JAXBElement(_MultiPolygon_QNAME, MultiPolygonType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Solid", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Solid")
/*      */   public JAXBElement<SolidType> createSolid(SolidType value)
/*      */   {
/* 1493 */     return new JAXBElement(_Solid_QNAME, SolidType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="measure")
/*      */   public JAXBElement<MeasureType> createMeasure(MeasureType value)
/*      */   {
/* 1502 */     return new JAXBElement(_Measure_QNAME, MeasureType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="polygonMember")
/*      */   public JAXBElement<PolygonPropertyType> createPolygonMember(PolygonPropertyType value)
/*      */   {
/* 1511 */     return new JAXBElement(_PolygonMember_QNAME, PolygonPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiGeometry", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiGeometryType> createMultiGeometry(MultiGeometryType value)
/*      */   {
/* 1520 */     return new JAXBElement(_MultiGeometry_QNAME, MultiGeometryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="members")
/*      */   public JAXBElement<ArrayAssociationType> createMembers(ArrayAssociationType value)
/*      */   {
/* 1529 */     return new JAXBElement(_Members_QNAME, ArrayAssociationType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_association")
/*      */   public JAXBElement<AssociationType> create_Association(AssociationType value)
/*      */   {
/* 1538 */     return new JAXBElement(__Association_QNAME, AssociationType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiCurveProperty")
/*      */   public JAXBElement<MultiCurvePropertyType> createMultiCurveProperty(MultiCurvePropertyType value)
/*      */   {
/* 1547 */     return new JAXBElement(_MultiCurveProperty_QNAME, MultiCurvePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="PolyhedralSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Surface")
/*      */   public JAXBElement<PolyhedralSurfaceType> createPolyhedralSurface(PolyhedralSurfaceType value)
/*      */   {
/* 1556 */     return new JAXBElement(_PolyhedralSurface_QNAME, PolyhedralSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="polygonPatches", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="patches")
/*      */   public JAXBElement<PolygonPatchArrayPropertyType> createPolygonPatches(PolygonPatchArrayPropertyType value)
/*      */   {
/* 1565 */     return new JAXBElement(_PolygonPatches_QNAME, PolygonPatchArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="name")
/*      */   public JAXBElement<CodeType> createName(CodeType value)
/*      */   {
/* 1574 */     return new JAXBElement(_Name_QNAME, CodeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Curve", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricPrimitive")
/*      */   public JAXBElement<AbstractCurveType> create_Curve(AbstractCurveType value)
/*      */   {
/* 1583 */     return new JAXBElement(__Curve_QNAME, AbstractCurveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="roughConversionToPreferredUnit")
/*      */   public JAXBElement<ConversionToPreferredUnitType> createRoughConversionToPreferredUnit(ConversionToPreferredUnitType value)
/*      */   {
/* 1592 */     return new JAXBElement(_RoughConversionToPreferredUnit_QNAME, ConversionToPreferredUnitType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Clothoid", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<ClothoidType> createClothoid(ClothoidType value)
/*      */   {
/* 1601 */     return new JAXBElement(_Clothoid_QNAME, ClothoidType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="geometryMember")
/*      */   public JAXBElement<GeometryPropertyType> createGeometryMember(GeometryPropertyType value)
/*      */   {
/* 1610 */     return new JAXBElement(_GeometryMember_QNAME, GeometryPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pointProperty")
/*      */   public JAXBElement<PointPropertyType> createPointProperty(PointPropertyType value)
/*      */   {
/* 1619 */     return new JAXBElement(_PointProperty_QNAME, PointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Null")
/*      */   public JAXBElement<List<String>> createNull(List<String> value)
/*      */   {
/* 1628 */     return new JAXBElement(_Null_QNAME, List.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_GeometricPrimitive", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Geometry")
/*      */   public JAXBElement<AbstractGeometricPrimitiveType> create_GeometricPrimitive(AbstractGeometricPrimitiveType value)
/*      */   {
/* 1637 */     return new JAXBElement(__GeometricPrimitive_QNAME, AbstractGeometricPrimitiveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiEdgeOf")
/*      */   public JAXBElement<MultiCurvePropertyType> createMultiEdgeOf(MultiCurvePropertyType value)
/*      */   {
/* 1646 */     return new JAXBElement(_MultiEdgeOf_QNAME, MultiCurvePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Curve", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Curve")
/*      */   public JAXBElement<CurveType> createCurve(CurveType value)
/*      */   {
/* 1655 */     return new JAXBElement(_Curve_QNAME, CurveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="curveMembers")
/*      */   public JAXBElement<CurveArrayPropertyType> createCurveMembers(CurveArrayPropertyType value)
/*      */   {
/* 1664 */     return new JAXBElement(_CurveMembers_QNAME, CurveArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="lineStringMember")
/*      */   public JAXBElement<LineStringPropertyType> createLineStringMember(LineStringPropertyType value)
/*      */   {
/* 1673 */     return new JAXBElement(_LineStringMember_QNAME, LineStringPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Array", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GML")
/*      */   public JAXBElement<ArrayType> createArray(ArrayType value)
/*      */   {
/* 1682 */     return new JAXBElement(_Array_QNAME, ArrayType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pos")
/*      */   public JAXBElement<DirectPositionType> createPos(DirectPositionType value)
/*      */   {
/* 1691 */     return new JAXBElement(_Pos_QNAME, DirectPositionType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Arc", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="ArcString")
/*      */   public JAXBElement<ArcType> createArc(ArcType value)
/*      */   {
/* 1700 */     return new JAXBElement(_Arc_QNAME, ArcType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="exterior")
/*      */   public JAXBElement<AbstractRingPropertyType> createExterior(AbstractRingPropertyType value)
/*      */   {
/* 1709 */     return new JAXBElement(_Exterior_QNAME, AbstractRingPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="GenericMetaData", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_MetaData")
/*      */   public JAXBElement<GenericMetaDataType> createGenericMetaData(GenericMetaDataType value)
/*      */   {
/* 1718 */     return new JAXBElement(_GenericMetaData_QNAME, GenericMetaDataType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Surface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Surface")
/*      */   public JAXBElement<SurfaceType> createSurface(SurfaceType value)
/*      */   {
/* 1727 */     return new JAXBElement(_Surface_QNAME, SurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Point", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricPrimitive")
/*      */   public JAXBElement<PointType> createPoint(PointType value)
/*      */   {
/* 1736 */     return new JAXBElement(_Point_QNAME, PointType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="coord")
/*      */   public JAXBElement<CoordType> createCoord(CoordType value)
/*      */   {
/* 1745 */     return new JAXBElement(_Coord_QNAME, CoordType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiCoverage")
/*      */   public JAXBElement<MultiSurfacePropertyType> createMultiCoverage(MultiSurfacePropertyType value)
/*      */   {
/* 1754 */     return new JAXBElement(_MultiCoverage_QNAME, MultiSurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="polygonProperty")
/*      */   public JAXBElement<PolygonPropertyType> createPolygonProperty(PolygonPropertyType value)
/*      */   {
/* 1763 */     return new JAXBElement(_PolygonProperty_QNAME, PolygonPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiSurfaceType> createMultiSurface(MultiSurfaceType value)
/*      */   {
/* 1772 */     return new JAXBElement(_MultiSurface_QNAME, MultiSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="angle")
/*      */   public JAXBElement<MeasureType> createAngle(MeasureType value)
/*      */   {
/* 1781 */     return new JAXBElement(_Angle_QNAME, MeasureType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Geodesic", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="GeodesicString")
/*      */   public JAXBElement<GeodesicType> createGeodesic(GeodesicType value)
/*      */   {
/* 1790 */     return new JAXBElement(_Geodesic_QNAME, GeodesicType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="solidMember")
/*      */   public JAXBElement<SolidPropertyType> createSolidMember(SolidPropertyType value)
/*      */   {
/* 1799 */     return new JAXBElement(_SolidMember_QNAME, SolidPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="OrientableSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Surface")
/*      */   public JAXBElement<OrientableSurfaceType> createOrientableSurface(OrientableSurfaceType value)
/*      */   {
/* 1808 */     return new JAXBElement(_OrientableSurface_QNAME, OrientableSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Object")
/*      */   public JAXBElement<Object> create_Object(Object value)
/*      */   {
/* 1817 */     return new JAXBElement(__Object_QNAME, Object.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="member")
/*      */   public JAXBElement<AssociationType> createMember(AssociationType value)
/*      */   {
/* 1826 */     return new JAXBElement(_Member_QNAME, AssociationType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="curveMember")
/*      */   public JAXBElement<CurvePropertyType> createCurveMember(CurvePropertyType value)
/*      */   {
/* 1835 */     return new JAXBElement(_CurveMember_QNAME, CurvePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="decimalMinutes")
/*      */   public JAXBElement<BigDecimal> createDecimalMinutes(BigDecimal value)
/*      */   {
/* 1844 */     return new JAXBElement(_DecimalMinutes_QNAME, BigDecimal.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Rectangle", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_SurfacePatch")
/*      */   public JAXBElement<RectangleType> createRectangle(RectangleType value)
/*      */   {
/* 1853 */     return new JAXBElement(_Rectangle_QNAME, RectangleType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Tin", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="TriangulatedSurface")
/*      */   public JAXBElement<TinType> createTin(TinType value)
/*      */   {
/* 1862 */     return new JAXBElement(_Tin_QNAME, TinType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="derivationUnitTerm")
/*      */   public JAXBElement<DerivationUnitTermType> createDerivationUnitTerm(DerivationUnitTermType value)
/*      */   {
/* 1871 */     return new JAXBElement(_DerivationUnitTerm_QNAME, DerivationUnitTermType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Polygon", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Surface")
/*      */   public JAXBElement<PolygonType> createPolygon(PolygonType value)
/*      */   {
/* 1880 */     return new JAXBElement(_Polygon_QNAME, PolygonType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="DefinitionProxy", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Definition")
/*      */   public JAXBElement<DefinitionProxyType> createDefinitionProxy(DefinitionProxyType value)
/*      */   {
/* 1889 */     return new JAXBElement(_DefinitionProxy_QNAME, DefinitionProxyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="seconds")
/*      */   public JAXBElement<BigDecimal> createSeconds(BigDecimal value)
/*      */   {
/* 1898 */     return new JAXBElement(_Seconds_QNAME, BigDecimal.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="CubicSpline", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<CubicSplineType> createCubicSpline(CubicSplineType value)
/*      */   {
/* 1907 */     return new JAXBElement(_CubicSpline_QNAME, CubicSplineType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Ring", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Ring")
/*      */   public JAXBElement<RingType> createRing(RingType value)
/*      */   {
/* 1916 */     return new JAXBElement(_Ring_QNAME, RingType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="catalogSymbol")
/*      */   public JAXBElement<CodeType> createCatalogSymbol(CodeType value)
/*      */   {
/* 1925 */     return new JAXBElement(_CatalogSymbol_QNAME, CodeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="DerivedUnit", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="UnitDefinition")
/*      */   public JAXBElement<DerivedUnitType> createDerivedUnit(DerivedUnitType value)
/*      */   {
/* 1934 */     return new JAXBElement(_DerivedUnit_QNAME, DerivedUnitType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="ArcString", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<ArcStringType> createArcString(ArcStringType value)
/*      */   {
/* 1943 */     return new JAXBElement(_ArcString_QNAME, ArcStringType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_SurfacePatch")
/*      */   public JAXBElement<AbstractSurfacePatchType> create_SurfacePatch(AbstractSurfacePatchType value)
/*      */   {
/* 1952 */     return new JAXBElement(__SurfacePatch_QNAME, AbstractSurfacePatchType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiExtentOf")
/*      */   public JAXBElement<MultiSurfacePropertyType> createMultiExtentOf(MultiSurfacePropertyType value)
/*      */   {
/* 1961 */     return new JAXBElement(_MultiExtentOf_QNAME, MultiSurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiGeometryProperty")
/*      */   public JAXBElement<MultiGeometryPropertyType> createMultiGeometryProperty(MultiGeometryPropertyType value)
/*      */   {
/* 1970 */     return new JAXBElement(_MultiGeometryProperty_QNAME, MultiGeometryPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiCenterOf")
/*      */   public JAXBElement<MultiPointPropertyType> createMultiCenterOf(MultiPointPropertyType value)
/*      */   {
/* 1979 */     return new JAXBElement(_MultiCenterOf_QNAME, MultiPointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_CurveSegment")
/*      */   public JAXBElement<AbstractCurveSegmentType> create_CurveSegment(AbstractCurveSegmentType value)
/*      */   {
/* 1988 */     return new JAXBElement(__CurveSegment_QNAME, AbstractCurveSegmentType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="interior")
/*      */   public JAXBElement<AbstractRingPropertyType> createInterior(AbstractRingPropertyType value)
/*      */   {
/* 1997 */     return new JAXBElement(_Interior_QNAME, AbstractRingPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="CircleByCenterPoint", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="ArcByCenterPoint")
/*      */   public JAXBElement<CircleByCenterPointType> createCircleByCenterPoint(CircleByCenterPointType value)
/*      */   {
/* 2006 */     return new JAXBElement(_CircleByCenterPoint_QNAME, CircleByCenterPointType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Sphere", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GriddedSurface")
/*      */   public JAXBElement<SphereType> createSphere(SphereType value)
/*      */   {
/* 2015 */     return new JAXBElement(_Sphere_QNAME, SphereType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="solidArrayProperty")
/*      */   public JAXBElement<SolidArrayPropertyType> createSolidArrayProperty(SolidArrayPropertyType value)
/*      */   {
/* 2024 */     return new JAXBElement(_SolidArrayProperty_QNAME, SolidArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pointMembers")
/*      */   public JAXBElement<PointArrayPropertyType> createPointMembers(PointArrayPropertyType value)
/*      */   {
/* 2033 */     return new JAXBElement(_PointMembers_QNAME, PointArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="degrees")
/*      */   public JAXBElement<DegreesType> createDegrees(DegreesType value)
/*      */   {
/* 2042 */     return new JAXBElement(_Degrees_QNAME, DegreesType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="minutes")
/*      */   public JAXBElement<Integer> createMinutes(Integer value)
/*      */   {
/* 2051 */     return new JAXBElement(_Minutes_QNAME, Integer.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_MetaData", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Object")
/*      */   public JAXBElement<AbstractMetaDataType> create_MetaData(AbstractMetaDataType value)
/*      */   {
/* 2060 */     return new JAXBElement(__MetaData_QNAME, AbstractMetaDataType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_strictAssociation")
/*      */   public JAXBElement<AssociationType> create_StrictAssociation(AssociationType value)
/*      */   {
/* 2069 */     return new JAXBElement(__StrictAssociation_QNAME, AssociationType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="ArcByBulge", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="ArcStringByBulge")
/*      */   public JAXBElement<ArcByBulgeType> createArcByBulge(ArcByBulgeType value)
/*      */   {
/* 2078 */     return new JAXBElement(_ArcByBulge_QNAME, ArcByBulgeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="curveArrayProperty")
/*      */   public JAXBElement<CurveArrayPropertyType> createCurveArrayProperty(CurveArrayPropertyType value)
/*      */   {
/* 2087 */     return new JAXBElement(_CurveArrayProperty_QNAME, CurveArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pointRep")
/*      */   public JAXBElement<PointPropertyType> createPointRep(PointPropertyType value)
/*      */   {
/* 2096 */     return new JAXBElement(_PointRep_QNAME, PointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pointArrayProperty")
/*      */   public JAXBElement<PointArrayPropertyType> createPointArrayProperty(PointArrayPropertyType value)
/*      */   {
/* 2105 */     return new JAXBElement(_PointArrayProperty_QNAME, PointArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Geometry", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GML")
/*      */   public JAXBElement<AbstractGeometryType> create_Geometry(AbstractGeometryType value)
/*      */   {
/* 2114 */     return new JAXBElement(__Geometry_QNAME, AbstractGeometryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="dmsAngle")
/*      */   public JAXBElement<DMSAngleType> createDmsAngle(DMSAngleType value)
/*      */   {
/* 2123 */     return new JAXBElement(_DmsAngle_QNAME, DMSAngleType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Bag", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GML")
/*      */   public JAXBElement<BagType> createBag(BagType value)
/*      */   {
/* 2132 */     return new JAXBElement(_Bag_QNAME, BagType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="OffsetCurve", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<OffsetCurveType> createOffsetCurve(OffsetCurveType value)
/*      */   {
/* 2141 */     return new JAXBElement(_OffsetCurve_QNAME, OffsetCurveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="conversionToPreferredUnit")
/*      */   public JAXBElement<ConversionToPreferredUnitType> createConversionToPreferredUnit(ConversionToPreferredUnitType value)
/*      */   {
/* 2150 */     return new JAXBElement(_ConversionToPreferredUnit_QNAME, ConversionToPreferredUnitType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="coordinates")
/*      */   public JAXBElement<CoordinatesType> createCoordinates(CoordinatesType value)
/*      */   {
/* 2159 */     return new JAXBElement(_Coordinates_QNAME, CoordinatesType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiPoint", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiPointType> createMultiPoint(MultiPointType value)
/*      */   {
/* 2168 */     return new JAXBElement(_MultiPoint_QNAME, MultiPointType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="MultiCurve", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricAggregate")
/*      */   public JAXBElement<MultiCurveType> createMultiCurve(MultiCurveType value)
/*      */   {
/* 2177 */     return new JAXBElement(_MultiCurve_QNAME, MultiCurveType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="vector")
/*      */   public JAXBElement<VectorType> createVector(VectorType value)
/*      */   {
/* 2186 */     return new JAXBElement(_Vector_QNAME, VectorType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiCenterLineOf")
/*      */   public JAXBElement<MultiCurvePropertyType> createMultiCenterLineOf(MultiCurvePropertyType value)
/*      */   {
/* 2195 */     return new JAXBElement(_MultiCenterLineOf_QNAME, MultiCurvePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="geometryMembers")
/*      */   public JAXBElement<GeometryArrayPropertyType> createGeometryMembers(GeometryArrayPropertyType value)
/*      */   {
/* 2204 */     return new JAXBElement(_GeometryMembers_QNAME, GeometryArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="dictionaryEntry")
/*      */   public JAXBElement<DictionaryEntryType> createDictionaryEntry(DictionaryEntryType value)
/*      */   {
/* 2213 */     return new JAXBElement(_DictionaryEntry_QNAME, DictionaryEntryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_GML", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Object")
/*      */   public JAXBElement<AbstractGMLType> create_GML(AbstractGMLType value)
/*      */   {
/* 2222 */     return new JAXBElement(__GML_QNAME, AbstractGMLType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="indirectEntry")
/*      */   public JAXBElement<IndirectEntryType> createIndirectEntry(IndirectEntryType value)
/*      */   {
/* 2231 */     return new JAXBElement(_IndirectEntry_QNAME, IndirectEntryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Bezier", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="BSpline")
/*      */   public JAXBElement<BezierType> createBezier(BezierType value)
/*      */   {
/* 2240 */     return new JAXBElement(_Bezier_QNAME, BezierType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiPointProperty")
/*      */   public JAXBElement<MultiPointPropertyType> createMultiPointProperty(MultiPointPropertyType value)
/*      */   {
/* 2249 */     return new JAXBElement(_MultiPointProperty_QNAME, MultiPointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="patches")
/*      */   public JAXBElement<SurfacePatchArrayPropertyType> createPatches(SurfacePatchArrayPropertyType value)
/*      */   {
/* 2258 */     return new JAXBElement(_Patches_QNAME, SurfacePatchArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="ArcStringByBulge", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<ArcStringByBulgeType> createArcStringByBulge(ArcStringByBulgeType value)
/*      */   {
/* 2267 */     return new JAXBElement(_ArcStringByBulge_QNAME, ArcStringByBulgeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiLocation")
/*      */   public JAXBElement<MultiPointPropertyType> createMultiLocation(MultiPointPropertyType value)
/*      */   {
/* 2276 */     return new JAXBElement(_MultiLocation_QNAME, MultiPointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="lineStringProperty")
/*      */   public JAXBElement<LineStringPropertyType> createLineStringProperty(LineStringPropertyType value)
/*      */   {
/* 2285 */     return new JAXBElement(_LineStringProperty_QNAME, LineStringPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="trianglePatches", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="patches")
/*      */   public JAXBElement<TrianglePatchArrayPropertyType> createTrianglePatches(TrianglePatchArrayPropertyType value)
/*      */   {
/* 2294 */     return new JAXBElement(_TrianglePatches_QNAME, TrianglePatchArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="multiSurfaceProperty")
/*      */   public JAXBElement<MultiSurfacePropertyType> createMultiSurfaceProperty(MultiSurfacePropertyType value)
/*      */   {
/* 2303 */     return new JAXBElement(_MultiSurfaceProperty_QNAME, MultiSurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="definitionMember", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="dictionaryEntry")
/*      */   public JAXBElement<DictionaryEntryType> createDefinitionMember(DictionaryEntryType value)
/*      */   {
/* 2312 */     return new JAXBElement(_DefinitionMember_QNAME, DictionaryEntryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="surfaceArrayProperty")
/*      */   public JAXBElement<SurfaceArrayPropertyType> createSurfaceArrayProperty(SurfaceArrayPropertyType value)
/*      */   {
/* 2321 */     return new JAXBElement(_SurfaceArrayProperty_QNAME, SurfaceArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Triangle", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_SurfacePatch")
/*      */   public JAXBElement<TriangleType> createTriangle(TriangleType value)
/*      */   {
/* 2330 */     return new JAXBElement(_Triangle_QNAME, TriangleType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Cone", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GriddedSurface")
/*      */   public JAXBElement<ConeType> createCone(ConeType value)
/*      */   {
/* 2339 */     return new JAXBElement(_Cone_QNAME, ConeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="quantityType")
/*      */   public JAXBElement<StringOrRefType> createQuantityType(StringOrRefType value)
/*      */   {
/* 2348 */     return new JAXBElement(_QuantityType_QNAME, StringOrRefType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="LineString", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_Curve")
/*      */   public JAXBElement<LineStringType> createLineString(LineStringType value)
/*      */   {
/* 2357 */     return new JAXBElement(_LineString_QNAME, LineStringType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="BaseUnit", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="UnitDefinition")
/*      */   public JAXBElement<BaseUnitType> createBaseUnit(BaseUnitType value)
/*      */   {
/* 2366 */     return new JAXBElement(_BaseUnit_QNAME, BaseUnitType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Envelope")
/*      */   public JAXBElement<EnvelopeType> createEnvelope(EnvelopeType value)
/*      */   {
/* 2375 */     return new JAXBElement(_Envelope_QNAME, EnvelopeType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Definition", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GML")
/*      */   public JAXBElement<DefinitionType> createDefinition(DefinitionType value)
/*      */   {
/* 2384 */     return new JAXBElement(_Definition_QNAME, DefinitionType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="GeodesicString", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_CurveSegment")
/*      */   public JAXBElement<GeodesicStringType> createGeodesicString(GeodesicStringType value)
/*      */   {
/* 2393 */     return new JAXBElement(_GeodesicString_QNAME, GeodesicStringType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_Surface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GeometricPrimitive")
/*      */   public JAXBElement<AbstractSurfaceType> create_Surface(AbstractSurfaceType value)
/*      */   {
/* 2402 */     return new JAXBElement(__Surface_QNAME, AbstractSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="baseSurface")
/*      */   public JAXBElement<SurfacePropertyType> createBaseSurface(SurfacePropertyType value)
/*      */   {
/* 2411 */     return new JAXBElement(_BaseSurface_QNAME, SurfacePropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="pointMember")
/*      */   public JAXBElement<PointPropertyType> createPointMember(PointPropertyType value)
/*      */   {
/* 2420 */     return new JAXBElement(_PointMember_QNAME, PointPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Cylinder", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_GriddedSurface")
/*      */   public JAXBElement<CylinderType> createCylinder(CylinderType value)
/*      */   {
/* 2429 */     return new JAXBElement(_Cylinder_QNAME, CylinderType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="segments")
/*      */   public JAXBElement<CurveSegmentArrayPropertyType> createSegments(CurveSegmentArrayPropertyType value)
/*      */   {
/* 2438 */     return new JAXBElement(_Segments_QNAME, CurveSegmentArrayPropertyType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="_ParametricCurveSurface", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="_SurfacePatch")
/*      */   public JAXBElement<AbstractParametricCurveSurfaceType> create_ParametricCurveSurface(AbstractParametricCurveSurfaceType value)
/*      */   {
/* 2447 */     return new JAXBElement(__ParametricCurveSurface_QNAME, AbstractParametricCurveSurfaceType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="Dictionary", substitutionHeadNamespace="http://www.opengis.net/gml", substitutionHeadName="Definition")
/*      */   public JAXBElement<DictionaryType> createDictionary(DictionaryType value)
/*      */   {
/* 2456 */     return new JAXBElement(_Dictionary_QNAME, DictionaryType.class, null, value);
/*      */   }
/*      */ 
/*      */   @XmlElementDecl(namespace="http://www.opengis.net/gml", name="baseCurve")
/*      */   public JAXBElement<CurvePropertyType> createBaseCurve(CurvePropertyType value)
/*      */   {
/* 2465 */     return new JAXBElement(_BaseCurve_QNAME, CurvePropertyType.class, null, value);
/*      */   }
/*      */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\gml.zip
 * Qualified Name:     gml.ObjectFactory
 * JD-Core Version:    0.6.0
 */