<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	
	<xsl:output method="text" encoding="us-ascii" />
	
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="grammar">
	
	
	<!--            header            -->
	<!--==============================-->
		<xsl:if test="./package">
			<xsl:text>package </xsl:text>
			<xsl:value-of select="./package"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
			<xsl:call-template name="CRLF"/>
		</xsl:if>
	
<xsl:text><![CDATA[
import org.relaxng.datatype.DatatypeBuilder;
import org.relaxng.datatype.ValidationContext;
import com.sun.msv.datatype.DatabindableDatatype;
import com.sun.msv.datatype.xsd.ngimpl.DataTypeLibraryImpl;
import com.sun.msv.grammar.*;
import com.sun.tahiti.runtime.ll.*;

/**
 * generated class.
 */
public class ]]></xsl:text>
		<xsl:value-of select="name"/>
		<xsl:text> {</xsl:text>
		<xsl:call-template name="CRLF"/>
	
	
	
	
	<!--      symbol definitions      -->
	<!--==============================-->
	
		<xsl:text>// symbols</xsl:text>
		<xsl:call-template name="CRLF"/>
		
	<!-- element symbols -->
		<xsl:for-each select="elementSymbol">
			<xsl:text>	private static final LLElementExp </xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		
	<!-- attribute symbols -->
		<xsl:for-each select="attributeSymbol">
			<xsl:text>	private static final LLAttributeExp </xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		
	<!-- intermediate symbols -->
		<xsl:for-each select="intermediateSymbol">
			<xsl:text>	private static final IntermediateSymbol </xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text> = new IntermediateSymbol("</xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text>");</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		
	<!-- named symbols -->
		<xsl:for-each select="namedSymbol">
			<xsl:text>	public static final NamedSymbol </xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text> = new NamedSymbol("</xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text>");</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
	
	<!-- datatype symbols -->
		<xsl:for-each select="dataSymbol">
			<xsl:text>	private static final DatabindableDatatype </xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
	
	<!-- primitive symbols -->
		<xsl:for-each select="primitiveSymbol">
			<xsl:text>	private static final NonTerminalSymbol </xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text> = new NonTerminalSymbol(){</xsl:text>
<xsl:text><![CDATA[
		public LLParser.Receiver createReceiver( final LLParser.Receiver parent ) {
			return new LLParser.CharacterReceiver(){
				public void action(DatabindableDatatype dt, String literal, ValidationContext context ) throws Exception {
					((LLParser.ObjectReceiver)parent).action(
						dt.createJavaObject(literal,context) );
				}
				public void start() throws Exception {}
				public void end() throws Exception {}
			};
		}
		public String toString() { return "]]></xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>"; }
	};
</xsl:text>
		</xsl:for-each>
		
		
		
	<!-- ignore symbols -->
		<xsl:for-each select="ignoreSymbol">
			<xsl:text>	private static final NonTerminalSymbol </xsl:text>
				<xsl:value-of select="@id"/>
			<xsl:text> = new NonTerminalSymbol(){</xsl:text>
<xsl:text><![CDATA[
		public LLParser.Receiver createReceiver( final LLParser.Receiver parent ) {
			return LLParser.ignoreReceiver;
		}
		public String toString() { return "]]></xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>"; }
	};
</xsl:text>
		</xsl:for-each>
		
		
		
	<!-- class symbols -->
		<xsl:for-each select="classSymbol">
			<xsl:text>	private static final NonTerminalSymbol </xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text> = new NonTerminalSymbol() {</xsl:text>
<xsl:text><![CDATA[
			public LLParser.Receiver createReceiver( final LLParser.Receiver parent ) {
				return new LLParser.FieldReceiver(){
					private ]]></xsl:text>
			<xsl:value-of select="@type"/>
			<xsl:text><![CDATA[ o;
					public void start() {
						o = new ]]></xsl:text>
			<xsl:value-of select="@type"/>
			<xsl:text><![CDATA[();
					}
					public void action(Object item, NamedSymbol name ) throws Exception {
						o.setField(name,item);
					}
					public void end() throws Exception {
						((LLParser.ObjectReceiver)parent).action( o );
					}
				};
			}
			public String toString() { return "C<]]></xsl:text>
			<xsl:value-of select="@type"/>
			<xsl:text>>"; }
		};</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>


	<!--  rule definitions -->
	<!--===================-->
		<xsl:for-each select="rules/rule">
			<!-- rule itself -->
			<xsl:text>	private static final Rule </xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		<xsl:call-template name="CRLF"/>
	
	
		
		
		
		
		<xsl:text><![CDATA[
	public static final BindableGrammar grammar;
	
	static {
		try {
			final ExpressionPool pool = new ExpressionPool();
			
]]></xsl:text>
	
	
	<!--   create datatype symbols    -->
	<!--==============================-->
		<xsl:text> // TODO: still leave a lot to be desired </xsl:text>
		<xsl:call-template name="CRLF"/>
		
		<xsl:for-each select="dataSymbol">
			<xsl:text>			</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text> = com.sun.msv.datatype.xsd.DatatypeFactory.getTypeByName("</xsl:text>
			<xsl:value-of select="@type"/>
			<xsl:text>");</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
	
	
	
	<!-- create empty element symbols -->
	<!--==============================-->
	
		<xsl:for-each select="elementSymbol">
			<xsl:text>	 		</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text> = new LLElementExp( </xsl:text>
			<xsl:apply-templates select="name"/>
			<xsl:text> ); </xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		
		
	<!-- construct grammar -->
	<!--===================-->
		<xsl:text>		// attributes and shared particles. In the same order as in the xml file </xsl:text>
		<xsl:call-template name="CRLF"/>
		
		<xsl:for-each select="particle|attributeSymbol">
			<xsl:choose>
				<xsl:when test="name()='particle'">
					<xsl:text>			Expression </xsl:text>
					<xsl:value-of select="@id"/>
					<xsl:text> = </xsl:text>
					<xsl:apply-templates select="*" mode="exp"/>
					<xsl:text>;</xsl:text>
				</xsl:when>
				<xsl:when test="name()='attributeSymbol'">
					<xsl:text>			</xsl:text>
					<xsl:value-of select="@id"/>
					<xsl:text> = new LLAttributeExp(</xsl:text>
					<xsl:apply-templates select="name"/>
					<xsl:text> , </xsl:text>
					<xsl:apply-templates select="content/*" mode="exp"/>
					<xsl:text>);</xsl:text>
				</xsl:when>
			</xsl:choose>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		

	<!--      create bare rules       -->
	<!--==============================-->
		<!-- this can be done only after we create attribute symbols -->
		<xsl:for-each select="rules/rule">
			<!-- rule itself -->
			<xsl:text>			</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text> = new Rule( </xsl:text>
			<xsl:value-of select="left/@symbolRef"/>
			<xsl:text>, new Object[]{ </xsl:text>
			<xsl:for-each select="right/item">
				<xsl:if test="position()!=1">
					<xsl:text>,</xsl:text>
				</xsl:if>
				<xsl:value-of select="@symbolRef"/>
			</xsl:for-each>
			<xsl:text> }</xsl:text>
			<xsl:if test="@interleave='true'">
				<xsl:text>,**********</xsl:text>
			</xsl:if>
			<xsl:text>);</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		<xsl:call-template name="CRLF"/>
		
		
	

	<!-- elements -->
		<xsl:text>		// element content models </xsl:text>
		<xsl:call-template name="CRLF"/>
		
		<xsl:for-each select="elementSymbol">
			<!-- create content model -->
			<xsl:text>			</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>.contentModel = </xsl:text>
			<xsl:apply-templates select="content/*" mode="exp"/>
			<xsl:text>;</xsl:text>
			<xsl:call-template name="CRLF"/>
			
			<!-- then assign a parser table -->
			<xsl:text>			</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>.parserTable = new Table_</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>();</xsl:text>
			<xsl:call-template name="CRLF"/>
			
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>

	<!-- elements -->
		<xsl:text>		// set parsing table for attributes </xsl:text>
		<xsl:call-template name="CRLF"/>
		<xsl:for-each select="attributeSymbol">
			<!-- create content model -->
			<xsl:text>			</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>.parserTable = new Table_</xsl:text>
			<xsl:value-of select="@id"/>
			<xsl:text>();</xsl:text>
			<xsl:call-template name="CRLF"/>
			
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
		<xsl:call-template name="CRLF"/>
		
	<!-- top level -->
		<xsl:text>		// top level</xsl:text>
		<xsl:call-template name="CRLF"/>
		<xsl:text>			final Expression topLevel = </xsl:text>
		<xsl:apply-templates select="topLevel/content/*" mode="exp"/>
		<xsl:text>;</xsl:text>
		<xsl:call-template name="CRLF"/>
	
	<!-- root parser table -->
		<xsl:text>			final LLParserTable rootTable = new Table_</xsl:text>
		<xsl:value-of select="topLevel/@id"/>
		<xsl:text>();</xsl:text>
		<xsl:call-template name="CRLF"/>
		<xsl:text>			final Object rootSymbol = </xsl:text>
		<xsl:value-of select="topLevel/@id"/>
		<xsl:text>;</xsl:text>
		<xsl:call-template name="CRLF"/>
	
	<!-- footer -->
		<xsl:text><![CDATA[
		// set the grammar
			grammar = new BindableGrammar() {
				public ExpressionPool getPool() { return pool; }
				public Expression getTopLevel() { return topLevel; }
				public LLParserTable getRootTable() { return rootTable; }
				public Object getRootSymbol() { return rootSymbol; }
			};
			
		} catch( Exception e ) {
			e.printStackTrace();
			throw new Error();
		}
	}
]]></xsl:text>
	
	
	<!--    parser table   -->
	<!--===================-->
		<xsl:for-each select="*/parserTable">
			<xsl:text>	private static final class Table_</xsl:text>
			<xsl:value-of select="parent::*/@id"/>
			<xsl:text> implements LLParserTable {</xsl:text>
			<xsl:call-template name="CRLF"/>
			
			<xsl:for-each select="action">
				<xsl:text>		private static final Rule[] a</xsl:text>
				<xsl:value-of select="@no"/>
				<xsl:choose>
					<xsl:when test="count(rule)=1">
						<!--
							if this action contains only one rule,
							then we don't need to create a new array
						-->
						<xsl:text> = </xsl:text>
						<xsl:value-of select="rule/@ref"/>
						<xsl:text>.selfArray</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<!--
							if this action has more than one rule,
							an explicit array creation is necessary
						-->
						<xsl:text> = new Rule[]{</xsl:text>
						<xsl:for-each select="rule">
							<xsl:if test="position()!=1">
								<xsl:text>,</xsl:text>
							</xsl:if>
							<xsl:value-of select="@ref"/>
						</xsl:for-each>
						<xsl:text>}</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:text>;</xsl:text>
				<xsl:call-template name="CRLF"/>
			</xsl:for-each>
			<xsl:call-template name="CRLF"/>
			
			
			<!-- the get method -->
			<xsl:text>		public Rule[] get( Object top, Object input ) {</xsl:text>
			<xsl:call-template name="CRLF"/>
			
			<xsl:for-each select="action">
				<xsl:text>			if( top==</xsl:text>
				<xsl:value-of select="@stackTop"/>
				<xsl:text> &amp;&amp; input==</xsl:text>
				<xsl:value-of select="@token"/>
				<xsl:text> ) return a</xsl:text>
				<xsl:value-of select="@no"/>
				<xsl:text>;</xsl:text>
				<xsl:call-template name="CRLF"/>
			</xsl:for-each>
			
			<xsl:text>			return null;</xsl:text>
			<xsl:call-template name="CRLF"/>
			<xsl:text>		}</xsl:text>
			<xsl:call-template name="CRLF"/>
			<!-- end of the get method -->
			
			<xsl:text>	};</xsl:text>
			<xsl:call-template name="CRLF"/>
		</xsl:for-each>
	
	<!--  footer           -->
	<!--===================-->
	<xsl:text>}</xsl:text>
	<xsl:call-template name="CRLF"/>
	
	</xsl:template>

<!--	
			<xsl:text></xsl:text>
-->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
<!-- generate name class -->
<!--=====================-->
<!--=====================-->
	<xsl:template match="name">
		<xsl:apply-templates select="*" mode="nc"/>
	</xsl:template>
	
		<xsl:template match="name" mode="nc">
			<xsl:text>new SimpleNameClass("</xsl:text>
			<xsl:value-of select="@ns"/>
			<xsl:text>","</xsl:text>
			<xsl:value-of select="@local"/>
			<xsl:text>")</xsl:text>
		</xsl:template>
		
		<xsl:template match="choice" mode="nc">
			<xsl:text>new ChoiceNameClass(</xsl:text>
			<xsl:apply-templates select="*[1]" mode="nc"/>
			<xsl:text>,</xsl:text>
			<xsl:apply-templates select="*[2]" mode="nc"/>
			<xsl:text>)</xsl:text>
		</xsl:template>
		
		<xsl:template match="difference" mode="nc">
			<xsl:text>new DifferenceNameClass(</xsl:text>
			<xsl:apply-templates select="*[1]" mode="nc"/>
			<xsl:text>,</xsl:text>
			<xsl:apply-templates select="*[2]" mode="nc"/>
			<xsl:text>)</xsl:text>
		</xsl:template>
		
		<xsl:template match="anyName" mode="nc">
			<xsl:text>AnyNameClass.theInstance</xsl:text>
		</xsl:template>	
		
		<xsl:template match="nsName" mode="nc">
			<xsl:text>new NamespaceNameClass("</xsl:text>
			<xsl:value-of select="@ns"/>
			<xsl:text>")</xsl:text>
		</xsl:template>
		
		<xsl:template match="not" mode="nc">
			<xsl:text>new NotNameClass(</xsl:text>
			<xsl:apply-templates select="*" mode="nc"/>
			<xsl:text>)</xsl:text>
		</xsl:template>




<!-- generate expression -->
<!--=====================-->
<!--=====================-->
	<xsl:template match="choice" mode="exp">
		<xsl:text>pool.createChoice(</xsl:text>
		<xsl:apply-templates select="*[1]" mode="exp"/>
		<xsl:text>,</xsl:text>
		<xsl:apply-templates select="*[2]" mode="exp"/>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
	<xsl:template match="interleave" mode="exp">
		<xsl:text>pool.createInterleave(</xsl:text>
		<xsl:apply-templates select="*[1]" mode="exp"/>
		<xsl:text>,</xsl:text>
		<xsl:apply-templates select="*[2]" mode="exp"/>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
	<xsl:template match="group" mode="exp">
		<xsl:text>pool.createSequence(</xsl:text>
		<xsl:apply-templates select="*[1]" mode="exp"/>
		<xsl:text>,</xsl:text>
		<xsl:apply-templates select="*[2]" mode="exp"/>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
	<xsl:template match="epsilon" mode="exp">
		<xsl:text>Expression.epsilon</xsl:text>
	</xsl:template>
	
	<xsl:template match="text" mode="exp">
		<xsl:text>Expression.anyString</xsl:text>
	</xsl:template>
	
	<xsl:template match="list" mode="exp">
		<xsl:text>pool.createList(</xsl:text>
		<xsl:apply-templates select="*" mode="exp"/>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
	<xsl:template match="oneOrMore" mode="exp">
		<xsl:text>pool.createOneOrMore(</xsl:text>
		<xsl:apply-templates select="*" mode="exp"/>
		<xsl:text>)</xsl:text>
	</xsl:template>
	
	<xsl:template match="typedString" mode="exp">
		<xsl:text>pool.createTypedString(</xsl:text>
		<xsl:value-of select="@dataSymbolRef"/>
		<xsl:text>,"")</xsl:text>
	</xsl:template>
	
	<xsl:template match="element" mode="exp">
		<xsl:value-of select="@symbolRef"/>
	</xsl:template>
	
	<xsl:template match="attribute" mode="exp">
		<xsl:value-of select="@symbolRef"/>
	</xsl:template>
	
	<xsl:template match="ref" mode="exp">
		<xsl:value-of select="@particle"/>
	</xsl:template>
	
	
	
	
	
	
<!-- utility methods -->
<!--=================-->
<!--=================-->
	<xsl:template name="CRLF">
<xsl:text>
</xsl:text>
	</xsl:template>
</xsl:stylesheet>