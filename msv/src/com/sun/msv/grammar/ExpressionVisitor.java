/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.grammar;

/**
 * Visitor interface for Expression and its derived types.
 * 
 * <p>
 * You may want to use ExpressionVisitorXXXX class if you want to
 * return boolean, void, or {@link Expression}.
 * 
 * <p>
 * It is the callee's responsibility to traverse child expression.
 * Expression and its derived classes do not provide any traversal.
 * See {@link ExpressionCloner} for example.
 * 
 * <p>
 * ExpressionVisitorXXX classes (including this class) is only capable
 * of handling AGM without any TREX extension. If you visit AGM which contains
 * TREX exntension primitives with a visitor that implements ExpressionVisitorXXX,
 * some exception will be thrown.
 * 
 * <p>
 * To support TREX exntension primitives, implement
 * {@link com.sun.msv.grammar.trex.TREXPatternVisitor} or its family.
 * 
 * <p>
 * onRef method is called for all subclass of ReferenceExp. So you can safely use this
 * interface to visit AGMs from RELAX grammar.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public interface ExpressionVisitor
{
	Object onAttribute( AttributeExp exp );
	Object onChoice( ChoiceExp exp );
	Object onElement( ElementExp exp );
	Object onOneOrMore( OneOrMoreExp exp );
	Object onMixed( MixedExp exp );
	Object onRef( ReferenceExp exp );
	Object onEpsilon();
	Object onNullSet();
	Object onAnyString();
	Object onSequence( SequenceExp exp );
	Object onTypedString( TypedStringExp exp );
}
