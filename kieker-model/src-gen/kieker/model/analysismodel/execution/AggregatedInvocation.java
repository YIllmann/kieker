/**
 */
package kieker.model.analysismodel.execution;

import kieker.model.analysismodel.deployment.DeployedOperation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Aggregated Invocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kieker.model.analysismodel.execution.AggregatedInvocation#getSource <em>Source</em>}</li>
 *   <li>{@link kieker.model.analysismodel.execution.AggregatedInvocation#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see kieker.model.analysismodel.execution.ExecutionPackage#getAggregatedInvocation()
 * @model
 * @generated
 */
public interface AggregatedInvocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(DeployedOperation)
	 * @see kieker.model.analysismodel.execution.ExecutionPackage#getAggregatedInvocation_Source()
	 * @model
	 * @generated
	 */
	DeployedOperation getSource();

	/**
	 * Sets the value of the '{@link kieker.model.analysismodel.execution.AggregatedInvocation#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(DeployedOperation value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(DeployedOperation)
	 * @see kieker.model.analysismodel.execution.ExecutionPackage#getAggregatedInvocation_Target()
	 * @model
	 * @generated
	 */
	DeployedOperation getTarget();

	/**
	 * Sets the value of the '{@link kieker.model.analysismodel.execution.AggregatedInvocation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(DeployedOperation value);

} // AggregatedInvocation
