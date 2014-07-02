package pt.up.med.mim.fh.quality.domain.inference.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.EliminationHeuristic;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.InferenceEngine;
import edu.ucla.belief.StateNotFoundException;
import edu.ucla.belief.Table;
import edu.ucla.belief.io.NetworkIO;
import edu.ucla.belief.io.PropertySuperintendent;

public class ZCHUGINQueryTutorial
{
  /** Test. */
  public static void main(String[] args){
    ZCHUGINQueryTutorial T = new ZCHUGINQueryTutorial();
    T.doProbabilityQuery( T.readNetworkFile() );
  }

  /**
    Demonstrates a probability query.
  */
  public void doProbabilityQuery( BeliefNetwork bn )
  {
    /* Define evidence, by id. */
    Map<FiniteVariable, Object> evidence = new HashMap(10);
    FiniteVariable var = null;
    var = (FiniteVariable) bn.forID( "Described" );
    evidence.put( var, var.instance( "No" ) );
    var = (FiniteVariable) bn.forID( "Reintroduced" );
    evidence.put( var, var.instance( "No" ) );
    var = (FiniteVariable) bn.forID( "Notifier" );
    evidence.put( var, var.instance( "Nurse" ) );
    var = (FiniteVariable) bn.forID( "Administration" );
    evidence.put( var, var.instance( "Injectable" ) );
    var = (FiniteVariable) bn.forID( "Reappeared" );
    evidence.put( var, var.instance( "NA" ) );
    var = (FiniteVariable) bn.forID( "Concomitant" );
    evidence.put( var, var.instance( "No" ) );
    var = (FiniteVariable) bn.forID( "Suspended" );
    evidence.put( var, var.instance( "No" ) );
    var = (FiniteVariable) bn.forID( "ImprovedAfterSuspension" );
    evidence.put( var, var.instance( "NA" ) );
    var = (FiniteVariable) bn.forID( "SuspectedInteraction" );
    evidence.put( var, var.instance( "No" ) );
    var = (FiniteVariable) bn.forID( "PharmaGroup" );
    evidence.put( var, var.instance( "GastrointestinalSystem" ) );

    /* Create the Dynamator(edu.ucla.belief.inference.SynchronizedInferenceEngine$SynchronizedPartialDerivativeEngine). */
    edu.ucla.belief.inference.ZCEngineGenerator dynamator = new edu.ucla.belief.inference.ZCEngineGenerator();

    /* Edit settings. */
    edu.ucla.belief.inference.JoinTreeSettings settings = dynamator.getSettings( (PropertySuperintendent)bn, true );
    /*
      Define the elimination order heuristic used to create the join tree, one of:
        MIN_FILL (Min Fill), MIN_SIZE (Min Size)
    */
    settings.setEliminationHeuristic( EliminationHeuristic.MIN_FILL );

    /* Create the InferenceEngine. */
    InferenceEngine engine = dynamator.manufactureInferenceEngine( bn );

    /* Set evidence. */
    try{
      bn.getEvidenceController().setObservations( evidence );
    }catch( StateNotFoundException e ){
      System.err.println( "Error, failed to set evidence: " + e );
      return;
    };

    /* Calculate Pr(e). */
    double pE = engine.probability();
    System.out.println( "Pr(e): " + pE );
    System.out.println();

    /* Define the set of 14 variables for which we want marginal probabilities, by id. */
    Set setMarginalVariables = new HashSet();
    String[] arrayMarginalVariableIDs = new String[] { "Conditional", "Described", "Definite", "Notifier", "Administration", "Concomitant", "Suspended", "SuspectedInteraction", "PharmaGroup", "Reintroduced", "Probable", "Reappeared", "Possible", "ImprovedAfterSuspension" };
    for( int i=0; i<arrayMarginalVariableIDs.length; i++ ){
      setMarginalVariables.add( bn.forID( arrayMarginalVariableIDs[i] ) );
    }

    /* Calculate marginals. */
    System.out.println( "Marginal probability tables:" );
    FiniteVariable varMarginal = null;
    Table answer = null;
    for( Iterator iterator = setMarginalVariables.iterator(); iterator.hasNext(); ){
      varMarginal = (FiniteVariable)iterator.next();
      answer = engine.conditional( varMarginal );
      System.out.println( answer.tableString() );
      System.out.println();
    }

    /* Clean up to avoid memory leaks. */
    engine.die();

    return;
  }

  /**
    Open the network file used to create this tutorial.
  */
  public BeliefNetwork readNetworkFile()
  {
    String path = "/home/willoliveira/Programs/samiam/network_samples/custom/adverse_reaction.net";

    BeliefNetwork ret = null;
    try{
      /* Use NetworkIO static method to read network file. */
      ret = NetworkIO.read( path );
    }catch( Exception e ){
      System.err.println( "Error, failed to read \"" + path + "\": " + e );
      return (BeliefNetwork)null;
    }
    return ret;
  }
}