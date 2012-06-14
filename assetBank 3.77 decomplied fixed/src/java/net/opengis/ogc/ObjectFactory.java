/*     */ package net.opengis.ogc;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  34 */   private static final QName _Function_QNAME = new QName("http://www.opengis.net/ogc", "Function");
/*  35 */   private static final QName _Disjoint_QNAME = new QName("http://www.opengis.net/ogc", "Disjoint");
/*  36 */   private static final QName _Crosses_QNAME = new QName("http://www.opengis.net/ogc", "Crosses");
/*  37 */   public static final QName _PropertyIsLessThan_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsLessThan");
/*  38 */   private static final QName _FeatureId_QNAME = new QName("http://www.opengis.net/ogc", "FeatureId");
/*  39 */   public static final QName _PropertyIsGreaterThanOrEqualTo_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsGreaterThanOrEqualTo");
/*  40 */   public static final QName _Intersects_QNAME = new QName("http://www.opengis.net/ogc", "Intersects");
/*  41 */   private static final QName _Filter_QNAME = new QName("http://www.opengis.net/ogc", "Filter");
/*  42 */   private static final QName _Sub_QNAME = new QName("http://www.opengis.net/ogc", "Sub");
/*  43 */   private static final QName _SpatialOps_QNAME = new QName("http://www.opengis.net/ogc", "spatialOps");
/*  44 */   public static final QName _Contains_QNAME = new QName("http://www.opengis.net/ogc", "Contains");
/*  45 */   public static final QName _PropertyIsEqualTo_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsEqualTo");
/*  46 */   private static final QName _Add_QNAME = new QName("http://www.opengis.net/ogc", "Add");
/*  47 */   private static final QName _Beyond_QNAME = new QName("http://www.opengis.net/ogc", "Beyond");
/*  48 */   private static final QName _Expression_QNAME = new QName("http://www.opengis.net/ogc", "expression");
/*  49 */   public static final QName _Or_QNAME = new QName("http://www.opengis.net/ogc", "Or");
/*  50 */   private static final QName _Div_QNAME = new QName("http://www.opengis.net/ogc", "Div");
/*  51 */   public static final QName _Touches_QNAME = new QName("http://www.opengis.net/ogc", "Touches");
/*  52 */   private static final QName _Literal_QNAME = new QName("http://www.opengis.net/ogc", "Literal");
/*  53 */   private static final QName _ComparisonOps_QNAME = new QName("http://www.opengis.net/ogc", "comparisonOps");
/*  54 */   private static final QName _Equals_QNAME = new QName("http://www.opengis.net/ogc", "Equals");
/*  55 */   private static final QName _Mul_QNAME = new QName("http://www.opengis.net/ogc", "Mul");
/*  56 */   public static final QName _Overlaps_QNAME = new QName("http://www.opengis.net/ogc", "Overlaps");
/*  57 */   public static final QName _PropertyIsGreaterThan_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsGreaterThan");
/*  58 */   private static final QName _PropertyIsNotEqualTo_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsNotEqualTo");
/*  59 */   public static final QName _PropertyIsLessThanOrEqualTo_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsLessThanOrEqualTo");
/*  60 */   private static final QName __Id_QNAME = new QName("http://www.opengis.net/ogc", "_Id");
/*  61 */   private static final QName _LogicOps_QNAME = new QName("http://www.opengis.net/ogc", "logicOps");
/*  62 */   private static final QName _PropertyIsLike_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsLike");
/*  63 */   private static final QName _BBOX_QNAME = new QName("http://www.opengis.net/ogc", "BBOX");
/*  64 */   private static final QName _SortBy_QNAME = new QName("http://www.opengis.net/ogc", "SortBy");
/*  65 */   private static final QName _Not_QNAME = new QName("http://www.opengis.net/ogc", "Not");
/*  66 */   private static final QName _DWithin_QNAME = new QName("http://www.opengis.net/ogc", "DWithin");
/*  67 */   private static final QName _GmlObjectId_QNAME = new QName("http://www.opengis.net/ogc", "GmlObjectId");
/*  68 */   public static final QName _Within_QNAME = new QName("http://www.opengis.net/ogc", "Within");
/*  69 */   private static final QName _PropertyIsBetween_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsBetween");
/*  70 */   public static final QName _And_QNAME = new QName("http://www.opengis.net/ogc", "And");
/*  71 */   private static final QName _PropertyIsNull_QNAME = new QName("http://www.opengis.net/ogc", "PropertyIsNull");
/*  72 */   private static final QName _PropertyName_QNAME = new QName("http://www.opengis.net/ogc", "PropertyName");
/*     */ 
/*     */   public DistanceType createDistanceType()
/*     */   {
/*  86 */     return new DistanceType();
/*     */   }
/*     */ 
/*     */   public FeatureIdType createFeatureIdType()
/*     */   {
/*  94 */     return new FeatureIdType();
/*     */   }
/*     */ 
/*     */   public FilterType createFilterType()
/*     */   {
/* 102 */     return new FilterType();
/*     */   }
/*     */ 
/*     */   public FID createFID()
/*     */   {
/* 110 */     return new FID();
/*     */   }
/*     */ 
/*     */   public BBOXType createBBOXType()
/*     */   {
/* 118 */     return new BBOXType();
/*     */   }
/*     */ 
/*     */   public BinarySpatialOpType createBinarySpatialOpType()
/*     */   {
/* 126 */     return new BinarySpatialOpType();
/*     */   }
/*     */ 
/*     */   public UnaryLogicOpType createUnaryLogicOpType()
/*     */   {
/* 134 */     return new UnaryLogicOpType();
/*     */   }
/*     */ 
/*     */   public BinaryOperatorType createBinaryOperatorType()
/*     */   {
/* 142 */     return new BinaryOperatorType();
/*     */   }
/*     */ 
/*     */   public SortByType createSortByType()
/*     */   {
/* 150 */     return new SortByType();
/*     */   }
/*     */ 
/*     */   public PropertyIsNullType createPropertyIsNullType()
/*     */   {
/* 158 */     return new PropertyIsNullType();
/*     */   }
/*     */ 
/*     */   public ArithmeticOperatorsType createArithmeticOperatorsType()
/*     */   {
/* 166 */     return new ArithmeticOperatorsType();
/*     */   }
/*     */ 
/*     */   public ComparisonOperatorsType createComparisonOperatorsType()
/*     */   {
/* 174 */     return new ComparisonOperatorsType();
/*     */   }
/*     */ 
/*     */   public GmlObjectIdType createGmlObjectIdType()
/*     */   {
/* 182 */     return new GmlObjectIdType();
/*     */   }
/*     */ 
/*     */   public Id_CapabilitiesType createId_CapabilitiesType()
/*     */   {
/* 190 */     return new Id_CapabilitiesType();
/*     */   }
/*     */ 
/*     */   public DistanceBufferType createDistanceBufferType()
/*     */   {
/* 198 */     return new DistanceBufferType();
/*     */   }
/*     */ 
/*     */   public FunctionType createFunctionType()
/*     */   {
/* 206 */     return new FunctionType();
/*     */   }
/*     */ 
/*     */   public Scalar_CapabilitiesType createScalar_CapabilitiesType()
/*     */   {
/* 214 */     return new Scalar_CapabilitiesType();
/*     */   }
/*     */ 
/*     */   public SpatialOperatorType createSpatialOperatorType()
/*     */   {
/* 222 */     return new SpatialOperatorType();
/*     */   }
/*     */ 
/*     */   public Spatial_CapabilitiesType createSpatial_CapabilitiesType()
/*     */   {
/* 230 */     return new Spatial_CapabilitiesType();
/*     */   }
/*     */ 
/*     */   public BinaryLogicOpType createBinaryLogicOpType()
/*     */   {
/* 238 */     return new BinaryLogicOpType();
/*     */   }
/*     */ 
/*     */   public SimpleArithmetic createSimpleArithmetic()
/*     */   {
/* 246 */     return new SimpleArithmetic();
/*     */   }
/*     */ 
/*     */   public EID createEID()
/*     */   {
/* 254 */     return new EID();
/*     */   }
/*     */ 
/*     */   public PropertyIsBetweenType createPropertyIsBetweenType()
/*     */   {
/* 262 */     return new PropertyIsBetweenType();
/*     */   }
/*     */ 
/*     */   public SortPropertyType createSortPropertyType()
/*     */   {
/* 270 */     return new SortPropertyType();
/*     */   }
/*     */ 
/*     */   public PropertyNameType createPropertyNameType()
/*     */   {
/* 278 */     return new PropertyNameType();
/*     */   }
/*     */ 
/*     */   public FunctionNamesType createFunctionNamesType()
/*     */   {
/* 286 */     return new FunctionNamesType();
/*     */   }
/*     */ 
/*     */   public LiteralType createLiteralType()
/*     */   {
/* 294 */     return new LiteralType();
/*     */   }
/*     */ 
/*     */   public FunctionNameType createFunctionNameType()
/*     */   {
/* 302 */     return new FunctionNameType();
/*     */   }
/*     */ 
/*     */   public SpatialOperatorsType createSpatialOperatorsType()
/*     */   {
/* 310 */     return new SpatialOperatorsType();
/*     */   }
/*     */ 
/*     */   public PropertyIsLikeType createPropertyIsLikeType()
/*     */   {
/* 318 */     return new PropertyIsLikeType();
/*     */   }
/*     */ 
/*     */   public LogicalOperators createLogicalOperators()
/*     */   {
/* 326 */     return new LogicalOperators();
/*     */   }
/*     */ 
/*     */   public UpperBoundaryType createUpperBoundaryType()
/*     */   {
/* 334 */     return new UpperBoundaryType();
/*     */   }
/*     */ 
/*     */   public BinaryComparisonOpType createBinaryComparisonOpType()
/*     */   {
/* 342 */     return new BinaryComparisonOpType();
/*     */   }
/*     */ 
/*     */   public FunctionsType createFunctionsType()
/*     */   {
/* 350 */     return new FunctionsType();
/*     */   }
/*     */ 
/*     */   public GeometryOperandsType createGeometryOperandsType()
/*     */   {
/* 358 */     return new GeometryOperandsType();
/*     */   }
/*     */ 
/*     */   public Filter_Capabilities createFilter_Capabilities()
/*     */   {
/* 366 */     return new Filter_Capabilities();
/*     */   }
/*     */ 
/*     */   public LowerBoundaryType createLowerBoundaryType()
/*     */   {
/* 374 */     return new LowerBoundaryType();
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Function", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<FunctionType> createFunction(FunctionType value)
/*     */   {
/* 383 */     return new JAXBElement(_Function_QNAME, FunctionType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Disjoint", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createDisjoint(BinarySpatialOpType value)
/*     */   {
/* 392 */     return new JAXBElement(_Disjoint_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Crosses", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createCrosses(BinarySpatialOpType value)
/*     */   {
/* 401 */     return new JAXBElement(_Crosses_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsLessThan", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsLessThan(BinaryComparisonOpType value)
/*     */   {
/* 410 */     return new JAXBElement(_PropertyIsLessThan_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="FeatureId", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="_Id")
/*     */   public JAXBElement<FeatureIdType> createFeatureId(FeatureIdType value)
/*     */   {
/* 419 */     return new JAXBElement(_FeatureId_QNAME, FeatureIdType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsGreaterThanOrEqualTo", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsGreaterThanOrEqualTo(BinaryComparisonOpType value)
/*     */   {
/* 428 */     return new JAXBElement(_PropertyIsGreaterThanOrEqualTo_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Intersects", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createIntersects(BinarySpatialOpType value)
/*     */   {
/* 437 */     return new JAXBElement(_Intersects_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Filter")
/*     */   public JAXBElement<FilterType> createFilter(FilterType value)
/*     */   {
/* 446 */     return new JAXBElement(_Filter_QNAME, FilterType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Sub", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<BinaryOperatorType> createSub(BinaryOperatorType value)
/*     */   {
/* 455 */     return new JAXBElement(_Sub_QNAME, BinaryOperatorType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="spatialOps")
/*     */   public JAXBElement<SpatialOpsType> createSpatialOps(SpatialOpsType value)
/*     */   {
/* 464 */     return new JAXBElement(_SpatialOps_QNAME, SpatialOpsType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Contains", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createContains(BinarySpatialOpType value)
/*     */   {
/* 473 */     return new JAXBElement(_Contains_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsEqualTo", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsEqualTo(BinaryComparisonOpType value)
/*     */   {
/* 482 */     return new JAXBElement(_PropertyIsEqualTo_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Add", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<BinaryOperatorType> createAdd(BinaryOperatorType value)
/*     */   {
/* 491 */     return new JAXBElement(_Add_QNAME, BinaryOperatorType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Beyond", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<DistanceBufferType> createBeyond(DistanceBufferType value)
/*     */   {
/* 500 */     return new JAXBElement(_Beyond_QNAME, DistanceBufferType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="expression")
/*     */   public JAXBElement<ExpressionType> createExpression(ExpressionType value)
/*     */   {
/* 509 */     return new JAXBElement(_Expression_QNAME, ExpressionType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Or", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="logicOps")
/*     */   public JAXBElement<BinaryLogicOpType> createOr(BinaryLogicOpType value)
/*     */   {
/* 518 */     return new JAXBElement(_Or_QNAME, BinaryLogicOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Div", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<BinaryOperatorType> createDiv(BinaryOperatorType value)
/*     */   {
/* 527 */     return new JAXBElement(_Div_QNAME, BinaryOperatorType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Touches", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createTouches(BinarySpatialOpType value)
/*     */   {
/* 536 */     return new JAXBElement(_Touches_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Literal", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<LiteralType> createLiteral(LiteralType value)
/*     */   {
/* 545 */     return new JAXBElement(_Literal_QNAME, LiteralType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="comparisonOps")
/*     */   public JAXBElement<ComparisonOpsType> createComparisonOps(ComparisonOpsType value)
/*     */   {
/* 554 */     return new JAXBElement(_ComparisonOps_QNAME, ComparisonOpsType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Equals", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createEquals(BinarySpatialOpType value)
/*     */   {
/* 563 */     return new JAXBElement(_Equals_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Mul", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<BinaryOperatorType> createMul(BinaryOperatorType value)
/*     */   {
/* 572 */     return new JAXBElement(_Mul_QNAME, BinaryOperatorType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Overlaps", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createOverlaps(BinarySpatialOpType value)
/*     */   {
/* 581 */     return new JAXBElement(_Overlaps_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsGreaterThan", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsGreaterThan(BinaryComparisonOpType value)
/*     */   {
/* 590 */     return new JAXBElement(_PropertyIsGreaterThan_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsNotEqualTo", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsNotEqualTo(BinaryComparisonOpType value)
/*     */   {
/* 599 */     return new JAXBElement(_PropertyIsNotEqualTo_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsLessThanOrEqualTo", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<BinaryComparisonOpType> createPropertyIsLessThanOrEqualTo(BinaryComparisonOpType value)
/*     */   {
/* 608 */     return new JAXBElement(_PropertyIsLessThanOrEqualTo_QNAME, BinaryComparisonOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="_Id")
/*     */   public JAXBElement<AbstractIdType> create_Id(AbstractIdType value)
/*     */   {
/* 617 */     return new JAXBElement(__Id_QNAME, AbstractIdType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="logicOps")
/*     */   public JAXBElement<LogicOpsType> createLogicOps(LogicOpsType value)
/*     */   {
/* 626 */     return new JAXBElement(_LogicOps_QNAME, LogicOpsType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsLike", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<PropertyIsLikeType> createPropertyIsLike(PropertyIsLikeType value)
/*     */   {
/* 635 */     return new JAXBElement(_PropertyIsLike_QNAME, PropertyIsLikeType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="BBOX", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BBOXType> createBBOX(BBOXType value)
/*     */   {
/* 644 */     return new JAXBElement(_BBOX_QNAME, BBOXType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="SortBy")
/*     */   public JAXBElement<SortByType> createSortBy(SortByType value)
/*     */   {
/* 653 */     return new JAXBElement(_SortBy_QNAME, SortByType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Not", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="logicOps")
/*     */   public JAXBElement<UnaryLogicOpType> createNot(UnaryLogicOpType value)
/*     */   {
/* 662 */     return new JAXBElement(_Not_QNAME, UnaryLogicOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="DWithin", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<DistanceBufferType> createDWithin(DistanceBufferType value)
/*     */   {
/* 671 */     return new JAXBElement(_DWithin_QNAME, DistanceBufferType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="GmlObjectId", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="_Id")
/*     */   public JAXBElement<GmlObjectIdType> createGmlObjectId(GmlObjectIdType value)
/*     */   {
/* 680 */     return new JAXBElement(_GmlObjectId_QNAME, GmlObjectIdType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="Within", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="spatialOps")
/*     */   public JAXBElement<BinarySpatialOpType> createWithin(BinarySpatialOpType value)
/*     */   {
/* 689 */     return new JAXBElement(_Within_QNAME, BinarySpatialOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsBetween", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<PropertyIsBetweenType> createPropertyIsBetween(PropertyIsBetweenType value)
/*     */   {
/* 698 */     return new JAXBElement(_PropertyIsBetween_QNAME, PropertyIsBetweenType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="And", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="logicOps")
/*     */   public JAXBElement<BinaryLogicOpType> createAnd(BinaryLogicOpType value)
/*     */   {
/* 707 */     return new JAXBElement(_And_QNAME, BinaryLogicOpType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyIsNull", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="comparisonOps")
/*     */   public JAXBElement<PropertyIsNullType> createPropertyIsNull(PropertyIsNullType value)
/*     */   {
/* 716 */     return new JAXBElement(_PropertyIsNull_QNAME, PropertyIsNullType.class, null, value);
/*     */   }
/*     */ 
/*     */   @XmlElementDecl(namespace="http://www.opengis.net/ogc", name="PropertyName", substitutionHeadNamespace="http://www.opengis.net/ogc", substitutionHeadName="expression")
/*     */   public JAXBElement<PropertyNameType> createPropertyName(PropertyNameType value)
/*     */   {
/* 725 */     return new JAXBElement(_PropertyName_QNAME, PropertyNameType.class, null, value);
/*     */   }
/*     */ }

/* Location:           D:\Project - Photint Asset-bank\net\opengis\ogc.zip
 * Qualified Name:     ogc.ObjectFactory
 * JD-Core Version:    0.6.0
 */