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

/**
 * "float" type.
 * 
 * See http://www.w3.org/TR/xmlschema-2/#float for the spec
 * 
 * @author	Kohsuke Kawaguchi
 */
public class FloatType extends FloatingNumberType
{
	public static final FloatType theInstance = new FloatType();
	private FloatType() { super("float"); }
	
	public Object convertToValue( String lexicalValue, ValidationContextProvider context )
	{// TODO : quick hack. Spec doesn't allow me directly to use FloatValueType.valueOf method
		
		/* Incompatibilities of XML Schema's float "xfloat" and Java's float "jfloat"
		
			* jfloat.valueOf ignores leading and trailing whitespaces,
			  whereas this is not allowed in xfloat.
			* jfloat.valueOf allows "float type suffix" (f, F) to be
			  appended after float literal (e.g., 1.52e-2f), whereare
			  this is not the case of xfloat.
		
			gray zone
			---------
			* jfloat allows ".523". And there is no clear statement that mentions
			  this case in xfloat. Although probably this is allowed.
			* 
		*/
		
		try
		{
			if(lexicalValue.equals("NaN"))	return new Float(Float.NaN);
			if(lexicalValue.equals("INF"))	return new Float(Float.POSITIVE_INFINITY);
			if(lexicalValue.equals("-INF"))	return new Float(Float.NEGATIVE_INFINITY);
			
			if(lexicalValue.length()==0
			|| !isDigitOrPeriodOrSign(lexicalValue.charAt(0))
			|| !isDigitOrPeriodOrSign(lexicalValue.charAt(lexicalValue.length()-1)) )
				return null;
			
			// these screening process is necessary due to the wobble of Float.valueOf method
			return Float.valueOf(lexicalValue);
		}
		catch( NumberFormatException e )
		{
			return null;
		}
	}
	
}
