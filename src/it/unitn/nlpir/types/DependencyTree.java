

/* First created by JCasGen Wed Nov 23 17:36:58 CET 2016 */
package it.unitn.nlpir.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Nov 23 17:36:58 CET 2016
 * XML source: /Users/kateryna/Documents/workspace/RelationalTextRanking/desc/PipelineTypeSystem.xml
 * @generated */
public class DependencyTree extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DependencyTree.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DependencyTree() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DependencyTree(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DependencyTree(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DependencyTree(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: tree

  /** getter for tree - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTree() {
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_tree == null)
      jcasType.jcas.throwFeatMissing("tree", "it.unitn.nlpir.types.DependencyTree");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_tree);}
    
  /** setter for tree - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTree(String v) {
    if (DependencyTree_Type.featOkTst && ((DependencyTree_Type)jcasType).casFeat_tree == null)
      jcasType.jcas.throwFeatMissing("tree", "it.unitn.nlpir.types.DependencyTree");
    jcasType.ll_cas.ll_setStringValue(addr, ((DependencyTree_Type)jcasType).casFeatCode_tree, v);}    
  }

    