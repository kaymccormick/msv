/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.grammar.trex;

import com.sun.msv.grammar.ExpressionVisitorBoolean;

/**
 * TREX version of {@link ExpressionVisitorBoolean}.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public interface TREXPatternVisitorBoolean extends ExpressionVisitorBoolean
{
	boolean onConcur( ConcurPattern p );
	boolean onInterleave( InterleavePattern p );
}
