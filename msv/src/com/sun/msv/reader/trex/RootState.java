/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.reader.trex;

import com.sun.msv.util.StartTagInfo;
import com.sun.msv.reader.State;
import com.sun.msv.reader.SimpleState;
import com.sun.msv.reader.ExpressionOwner;
import com.sun.msv.grammar.Expression;
import com.sun.msv.grammar.trex.TREXGrammar;

/**
 * invokes State object that parses the document element.
 * 
 * This class accepts grammar element only.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class RootState extends SimpleState implements ExpressionOwner
{
	protected State createChildState( StartTagInfo tag )
	{
		// grammar has to be treated separately so as not to
		// create unnecessary TREXGrammar object.
		if(tag.localName.equals("grammar"))
			return new GrammarState();
		
		State s = reader.createExpressionChildState(this,tag);
		if(s!=null)
		{// other pattern element is specified.
			// create wrapper grammar
			final TREXGrammarReader reader = (TREXGrammarReader)this.reader;
			reader.grammar = new TREXGrammar( reader.getPool(), null );
			simple = true;
		}
		
		return s;
	}
	
	/**
	 * a flag that indicates 'grammar' element was not used.
	 * In that case, this object is responsible to set start pattern.
	 */
	private boolean simple = false;
	
	// GrammarState implements ExpressionState,
	// so RootState has to implement ExpressionOwner.
	public void onEndChild(Expression exp)
	{
		if( simple )
		{
			((TREXGrammarReader)reader).grammar.start = exp;
			// run-away expression check is not necessary,
			// because there can be no Ref element.
			
			// make sure that there is no sequenced string.
			exp.visit( new TREXSequencedStringChecker((TREXGrammarReader)reader) );
		}
	}
}
