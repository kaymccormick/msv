/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.datatype;

import junit.framework.*;

/**
 * tests FinalComponent.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class FinalComponentTest extends TestCase
{
	public FinalComponentTest( String name ) { super(name); }
	
	public static void main(java.lang.String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		return new TestSuite(FinalComponentTest.class);
	}
	
	public void testDerivationByRestriction() throws BadTypeException
	{
		TypeIncubator inc = new TypeIncubator(
			new FinalComponent(
				StringType.theInstance,
				DataType.DERIVATION_BY_RESTRICTION ) );
		
		try
		{
			inc.derive("test");
			fail("should throw Exception here");
		}
		catch( BadTypeException bte ) {;}
	}
	
	public void testDerivationByList() throws BadTypeException
	{
		try
		{
			DataTypeFactory.deriveByList( "test",
				new FinalComponent(
					StringType.theInstance,
					DataType.DERIVATION_BY_LIST )
						);
			fail("should throw Exception here");
		}
		catch( BadTypeException bte ) {;}
	}

	public void testDerivationByUnion() throws BadTypeException
	{
		try
		{
			DataTypeFactory.deriveByUnion("intermediate",
			  new DataTypeImpl[]{
					new FinalComponent(
						StringType.theInstance,
						DataType.DERIVATION_BY_UNION ),
					NumberType.theInstance } );
			fail("should throw Exception here");
		}
		catch( BadTypeException bte ) {;}
	}
}
