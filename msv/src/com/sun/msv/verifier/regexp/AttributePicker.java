package com.sun.tranquilo.verifier.regexp;

import com.sun.tranquilo.grammar.*;

/**
 * removes all unnecessary expressions and
 * creates an expression that consists of required attributes and choices only.
 * 
 * For example,
 * 
 * <choice>
 *   <element />
 *   <attribute />
 * </choice>
 * 
 * will be converted to
 * 
 * <empty />
 * 
 * because no attribute is required. But
 * 
 * <choice>
 *   <attribute />
 *   <attribute />
 * </choice>
 * 
 * will remain the same because one or the other is required.
 * 
 * this method also removes SequenceExp.
 * 
 * <sequence>
 *   <attribute name="A" />
 *   <attribute name="B" />
 * </sequence>
 * 
 * will be converted to
 * 
 * <attribute name="A" />
 * 
 * This function object is used only for error recovery.
 * Resulting expressions always consist only of <choice>s and <attribute>s.
 */
public class AttributePicker implements ExpressionVisitorExpression
{
	private final ExpressionPool pool;
	
	protected AttributePicker( ExpressionPool pool )
	{
		this.pool = pool;
	}
	
	public Expression onElement( ElementExp exp )
		{ return AttributeExp.epsilon; }
	
	public Expression onMixed( MixedExp exp )
		{ return exp.exp.visit(this); }
	
	public Expression onAnyString()
		{ return Expression.epsilon; }
	
	public Expression onEpsilon()
		{ return Expression.epsilon; }
	
	public Expression onNullSet()
		{ throw new Error(); }	// this method shall never be called
	
	public Expression onRef( ReferenceExp exp )
		{ return exp.exp.visit(this); }
	
	public Expression onTypedString( TypedStringExp exp )
		{ return Expression.epsilon; }
	
	public Expression onAttribute( AttributeExp exp )
	{
		return exp;
	}
	
	public Expression onOneOrMore( OneOrMoreExp exp )
	{// reduce A+ -> A
		return exp.exp.visit(this);
	}
	
	public Expression onSequence( SequenceExp exp )
	{
		Expression ex1 = exp.exp1.visit(this);
		Expression ex2 = exp.exp2.visit(this);
		
		if(ex1.isEpsilonReducible())
		{
			if(ex2.isEpsilonReducible())	return Expression.epsilon;
			else							return ex2;
		}
		else
			return ex1;
	}
	
	public Expression onChoice( ChoiceExp exp )
	{
		Expression ex1 = exp.exp1.visit(this);
		Expression ex2 = exp.exp2.visit(this);
		// if one of choice is epsilon-reducible,
		// the entire choice becomes optional.
		// optional attributes have to be removed from the result.
		if( ex1.isEpsilonReducible() || ex2.isEpsilonReducible() )
			return Expression.epsilon;
		return pool.createChoice(ex1,ex2);
	}
}