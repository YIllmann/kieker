/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kieker.analysis.model.analysisMetaModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link kieker.analysis.model.analysisMetaModel.Connector#getDstInputPort <em>Dst Input Port</em>}</li>
 *   <li>{@link kieker.analysis.model.analysisMetaModel.Connector#getSicOutputPort <em>Sic Output Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see kieker.analysis.model.analysisMetaModel.AnalysisMetaModelPackage#getConnector()
 * @model
 * @generated
 */
public interface Connector extends EObject {
	/**
	 * Returns the value of the '<em><b>Dst Input Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dst Input Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dst Input Port</em>' reference.
	 * @see #setDstInputPort(InputPort)
	 * @see kieker.analysis.model.analysisMetaModel.AnalysisMetaModelPackage#getConnector_DstInputPort()
	 * @model required="true"
	 * @generated
	 */
	InputPort getDstInputPort();

	/**
	 * Sets the value of the '{@link kieker.analysis.model.analysisMetaModel.Connector#getDstInputPort <em>Dst Input Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dst Input Port</em>' reference.
	 * @see #getDstInputPort()
	 * @generated
	 */
	void setDstInputPort(InputPort value);

	/**
	 * Returns the value of the '<em><b>Sic Output Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sic Output Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sic Output Port</em>' reference.
	 * @see #setSicOutputPort(OutputPort)
	 * @see kieker.analysis.model.analysisMetaModel.AnalysisMetaModelPackage#getConnector_SicOutputPort()
	 * @model required="true"
	 * @generated
	 */
	OutputPort getSicOutputPort();

	/**
	 * Sets the value of the '{@link kieker.analysis.model.analysisMetaModel.Connector#getSicOutputPort <em>Sic Output Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sic Output Port</em>' reference.
	 * @see #getSicOutputPort()
	 * @generated
	 */
	void setSicOutputPort(OutputPort value);

} // Connector
