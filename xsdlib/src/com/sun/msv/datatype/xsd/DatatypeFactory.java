/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.datatype.xsd;

import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import org.relaxng.datatype.DatatypeException;

/**
 * Datatype object factory.
 *
 * <p>
 * Applications should use this class to get and derive DataType objects.
 * All methods are static.
 * 
 * <p>
 * Derivation by restriction should be done by using {@link TypeIncubator}.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class DatatypeFactory {
	
	private DatatypeFactory(){}
	
	/**
	 * derives a new type by list.
	 *
	 * See http://www.w3.org/TR/xmlschema-2#derivation-by-list for
	 * what "derivation by list" means.
	 *
	 * @return
	 *		always return non-null value. If error occurs,
	 *		then an exception will be thrown.
	 * 
	 * @param newTypeName
	 *		name of the new type. it can be set to null for indicating an anonymous type.
	 * @param itemType
	 *		Type of the list item. It must be an atom type which is implemented
	 *		in this package or derived from types implemented in this package.
	 *		You cannot use your own DataType implementation here.
	 *
	 * @exception BadTypeException
	 *		this exception is thrown when the derivation is illegal.
	 *		For example, when you try to derive a type from non-atom type.
	 */
	public static XSDatatype deriveByList( String newTypeName, XSDatatype itemType )
		throws DatatypeException {
		return new ListType(newTypeName,(XSDatatypeImpl)itemType);
	}
	
	/**
	 * derives a new type by union.
	 *
	 * See http://www.w3.org/TR/xmlschema-2#derivation-by-union for
	 * what "derivation by union" means.
	 * 
	 * @param newTypeName
	 *		name of the new type. it can be set to null to
	 *		indicate an anonymous type.
	 * @param memberTypes
	 *		Types of the union member. It can be any type that implements DataType.
	 *
	 * @exception BadTypeException
	 *		this exception is thrown when the derivation is illegal.
	 */
	public static XSDatatype deriveByUnion( String newTypeName, XSDatatype[] memberTypes )
		throws DatatypeException {
		
		return new UnionType(newTypeName,memberTypes);
	}
	
	public static XSDatatype deriveByUnion( String newTypeName, Collection memberTypes )
		throws DatatypeException {
		XSDatatypeImpl[] m = new XSDatatypeImpl[memberTypes.size()];
		int n=0;
		for( Iterator itr=memberTypes.iterator(); itr.hasNext(); n++ )
		for( int i=0; i<m.length; i++ )
			m[i] = (XSDatatypeImpl)itr.next();
		
		return new UnionType(newTypeName,m);
	}
	
	
	private static void add( Map m, XSDatatypeImpl type ) {
		final String name = type.getName();
		if( name==null )
			throw new IllegalArgumentException("anonymous type");
		
		if( m.containsKey(name) )
			// this error is considered as an assertion,
			// since this object doesn't allow external programs to
			// add types to the object.
			throw new IllegalArgumentException("multiple definition");
		
		m.put( name, type );
	}

	
	/**
	 * obtain a built-in DataType object by its name.
	 * For example, you can pass somethings like "token", "gYear", etc.
	 * 
	 * @return
	 *		If the datatype was not found, <code>null</code> is returned.
	 */
	public static XSDatatype getTypeByName( String dataTypeName ) {
		XSDatatype dt = (XSDatatype)builtinType.get(dataTypeName);
		if(dt!=null)	return dt;
		
		try {
			// types may be not added to the map.
			if( dataTypeName.equals("float") )
				add( builtinType, FloatType.theInstance );
			else
			if( dataTypeName.equals("double") )
				add( builtinType, DoubleType.theInstance );
			else
			if( dataTypeName.equals("duration") )
				add( builtinType, DurationType.theInstance );
			else
			if( dataTypeName.equals("dateTime") )
				add( builtinType, DateTimeType.theInstance );
			else
			if( dataTypeName.equals("time") )
				add( builtinType, TimeType.theInstance );
			else
			if( dataTypeName.equals("date") )
				add( builtinType, DateType.theInstance );
			else
			if( dataTypeName.equals("gYearMonth") )
				add( builtinType, GYearMonthType.theInstance );
			else
			if( dataTypeName.equals("gYear") )
				add( builtinType, GYearType.theInstance );
			else
			if( dataTypeName.equals("gMonthDay") )
				add( builtinType, GMonthDayType.theInstance );
			else
			if( dataTypeName.equals("gDay") )
				add( builtinType, GDayType.theInstance );
			else
			if( dataTypeName.equals("gMonth") )
				add( builtinType, GMonthType.theInstance );
			else
			if( dataTypeName.equals("hexBinary") )
				add( builtinType, HexBinaryType.theInstance );
			else
			if( dataTypeName.equals("base64Binary") )
				add( builtinType, Base64BinaryType.theInstance );
			else
			if( dataTypeName.equals("anyURI") )
				add( builtinType, AnyURIType.theInstance );
			else
			if( dataTypeName.equals("entity") )
				add( builtinType, EntityType.theInstance );
			else
			if( dataTypeName.equals("language") )
				add( builtinType, LanguageType.theInstance );
			else
			if( dataTypeName.equals("ENTITIES") )
				add( builtinType, new ListType("ENTITIES",EntityType.theInstance) );
			else
			if( dataTypeName.equals("NMTOKENS") )
				add( builtinType, new ListType("NMTOKENS",NmtokenType.theInstance) );
			else
			if( dataTypeName.equals("NOTATION") )
				add( builtinType, new StringType("NOTATION", WhiteSpaceProcessor.theCollapse) );
			else
			if( dataTypeName.equals("nonPositiveInteger") )
				add( builtinType, NonPositiveIntegerType.theInstance );
			else
			if( dataTypeName.equals("unsignedLong") )
				add( builtinType, UnsignedLongType.theInstance );
			else
			if( dataTypeName.equals("unsignedInt") )
				add( builtinType, UnsignedIntType.theInstance );
			else
			if( dataTypeName.equals("unsignedShort") )
				add( builtinType, UnsignedShortType.theInstance );
			else
			if( dataTypeName.equals("unsignedByte") )
				add( builtinType, UnsignedByteType.theInstance );
		} catch( DatatypeException dte )	{
			// assertion failed
			throw new Error();
		}
		
		return (XSDatatype)builtinType.get(dataTypeName);
	}
	
	/**
	 * a map that contains built in types.
	 * 
	 * To speed up the boot process, only a handful types are added
	 * at the first time.
	 */
	private static final Map builtinType = createInitialBuiltinTypesMap();
	
	/** creates a map that contains frequently-used built-in types */
	private static Map createInitialBuiltinTypesMap() {
		Map m = new java.util.HashMap();

		// missing types are noted inline.

		add( m, StringType.theInstance );
		add( m, BooleanType.theInstance );
		add( m, NumberType.theInstance );
//		ID, IDREF
		add( m, QnameType.theInstance );
		add( m, NormalizedStringType.theInstance );
		add( m, TokenType.theInstance );
//		IDREFS
		add( m, NmtokenType.theInstance );
		add( m, NameType.theInstance );
		add( m, NcnameType.theInstance );
			
		add( m, IntegerType.theInstance );
		add( m, NegativeIntegerType.theInstance );
		add( m, LongType.theInstance );
		add( m, IntType.theInstance );
		add( m, ShortType.theInstance );
		add( m, ByteType.theInstance );
		add( m, NonNegativeIntegerType.theInstance );
		add( m, PositiveIntegerType.theInstance );

			
		return m;
	}
}
