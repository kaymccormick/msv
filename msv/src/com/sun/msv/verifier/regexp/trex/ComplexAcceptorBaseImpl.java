/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.verifier.regexp.trex;

import com.sun.msv.datatype.ValidationContextProvider;
import com.sun.msv.grammar.Expression;
import com.sun.msv.grammar.ElementExp;
import com.sun.msv.verifier.Acceptor;
import com.sun.msv.verifier.regexp.StringToken;
import com.sun.msv.verifier.regexp.Token;
import com.sun.msv.verifier.regexp.AnyElementToken;
import com.sun.msv.verifier.regexp.ElementToken;
import com.sun.msv.verifier.regexp.ResidualCalculator;
import com.sun.msv.util.StringRef;
import com.sun.msv.util.DataTypeRef;

/**
 * base implementation of ComplexAcceptor.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class ComplexAcceptorBaseImpl extends ContentModelAcceptor
{
	protected final Expression[]	contents;
	
	public ComplexAcceptorBaseImpl(
		TREXDocumentDeclaration docDecl,
		Expression combined,
		Expression[] contents )
	{
		super( docDecl, combined );
		this.contents = contents;
	}

	/** eats string literal */
	public final boolean stepForward( String literal, ValidationContextProvider context, StringRef refErr, DataTypeRef refType )
	{
		if(!super.stepForward(literal,context,refErr,refType))	return false;

		final StringToken token = new StringToken(literal,context);
		final ResidualCalculator res = docDecl.getResidualCalculator();

		// some may become invalid, but at least one always remain valid
		for( int i=0; i<contents.length; i++ )
			contents[i] = res.calcResidual( contents[i], token );
		
		return true;
	}
	
	public final boolean stepForward( Acceptor child, StringRef errRef )
	{
		if(!super.stepForward(child,errRef))	return false;

		final ResidualCalculator res = docDecl.getResidualCalculator();
		Token token;
		
		if( child instanceof SimpleAcceptor ) {
			// this is possible although it is very rare.
			// continuation cannot be used here, because
			// some contents[i] may reject this owner.
			ElementExp cowner = ((SimpleAcceptor)child).owner;
			if( cowner==null )
				// cowner==null means we are currently recovering from an error.
				// so use AnyElementToken to make contents[i] happy.
				token = AnyElementToken.theInstance;
			else
				token = new ElementToken( new ElementExp[]{cowner} );
		} else {
			if( errRef!=null )
				// in error recovery mode
				// pretend that every candidate of child ComplexAcceptor is happy
				token = new ElementToken( ((ComplexAcceptor)child).owners );
			else
				// in normal mode, collect only those satisfied owners.
				token = new ElementToken( ((ComplexAcceptor)child).getSatisfiedOwners() );
		}
		
		for( int i=0; i<contents.length; i++ )
			contents[i] = res.calcResidual( contents[i], token );
		
		return true;
	}
}
