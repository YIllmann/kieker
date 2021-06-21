/***************************************************************************
 * Copyright 2020 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.analysis;

import java.io.File;
import java.nio.file.Path;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;

import kieker.analysis.graph.dependency.DependencyGraphCreatorStage;
import kieker.analysis.graph.dependency.DeploymentLevelOperationDependencyGraphBuilderFactory;
import kieker.analysis.graph.dependency.dot.DotExportConfigurationFactory;
import kieker.analysis.graph.dependency.vertextypes.IVertexTypeMapper;
import kieker.analysis.graph.export.dot.DotExportConfiguration;
import kieker.analysis.graph.export.dot.DotFileWriterStage;
import kieker.analysis.model.ExecutionModelAssembler;
import kieker.analysis.model.ExecutionModelAssemblerStage;
import kieker.analysis.model.ModelObjectFromOperationCallAccessors;
import kieker.analysis.model.ModelRepository;
import kieker.analysis.model.StaticModelsAssemblerStage;
import kieker.analysis.signature.NameBuilder;
import kieker.analysis.signature.SignatureExtractor;
import kieker.analysis.source.file.DirectoryReaderStage;
import kieker.analysis.source.file.DirectoryScannerStage;
import kieker.analysis.statistics.CallStatisticsStage;
import kieker.analysis.statistics.FullReponseTimeStatisticsStage;
import kieker.analysis.trace.graph.TraceToGraphTransformerStage;
import kieker.analysis.trace.graph.dot.DotTraceGraphFileWriterStage;
import kieker.analysis.trace.reconstruction.FlowRecordTraceReconstructionStage;
import kieker.analysis.trace.reconstruction.TraceStatisticsDecoratorStage;
import kieker.analysis.util.stage.AllowedRecordsFilter;
import kieker.analysis.util.stage.trigger.TriggerOnTerminationStage;
import kieker.model.analysismodel.assembly.AssemblyFactory;
import kieker.model.analysismodel.assembly.AssemblyModel;
import kieker.model.analysismodel.deployment.DeploymentFactory;
import kieker.model.analysismodel.deployment.DeploymentModel;
import kieker.model.analysismodel.execution.ExecutionFactory;
import kieker.model.analysismodel.execution.ExecutionModel;
import kieker.model.analysismodel.sources.SourceModel;
import kieker.model.analysismodel.sources.SourcesFactory;
import kieker.model.analysismodel.statistics.StatisticsFactory;
import kieker.model.analysismodel.statistics.StatisticsModel;
import kieker.model.analysismodel.trace.OperationCall;
import kieker.model.analysismodel.trace.Trace;
import kieker.model.analysismodel.type.TypeFactory;
import kieker.model.analysismodel.type.TypeModel;

import teetime.framework.Configuration;
import teetime.stage.basic.distributor.Distributor;
import teetime.stage.basic.distributor.strategy.CopyByReferenceStrategy;

/**
 *
 * Example configuration for testing the current development.
 *
 * @author Sören Henning
 *
 * @since 1.14
 */
public class ExampleConfiguration extends Configuration {

	private static final String DYNAMIC_SOURCE = "dynamic-source";

	private final TypeModel typeModel = TypeFactory.eINSTANCE.createTypeModel();
	private final AssemblyModel assemblyModel = AssemblyFactory.eINSTANCE.createAssemblyModel();
	private final DeploymentModel deploymentModel = DeploymentFactory.eINSTANCE.createDeploymentModel();
	private final ExecutionModel executionModel = ExecutionFactory.eINSTANCE.createExecutionModel();
	private final StatisticsModel statisticsModel = StatisticsFactory.eINSTANCE.createStatisticsModel();
	private final SourceModel sourceModel = SourcesFactory.eINSTANCE.createSourceModel();
	private final SignatureExtractor signatureExtractor = SignatureExtractor.forJava();

	public ExampleConfiguration(final File importDirectory, final Path exportDirectory) {

		final TemporalUnit timeUnitOfRecods = ChronoUnit.NANOS;
		final Function<OperationCall, EObject> statisticsObjectAccesor = ModelObjectFromOperationCallAccessors.DEPLOYED_OPERATION;
		final DeploymentLevelOperationDependencyGraphBuilderFactory deploymentGraphBuilderFactory = new DeploymentLevelOperationDependencyGraphBuilderFactory();
		final DotExportConfiguration dependencyGraphDotExportConfiguration = new DotExportConfigurationFactory(
				NameBuilder.forJavaShortOperations(), IVertexTypeMapper.TO_STRING)
						.createForDeploymentLevelOperationDependencyGraph();

		final ModelRepository repository = new ModelRepository("Example");
		repository.register(TypeModel.class, this.typeModel);
		repository.register(AssemblyModel.class, this.assemblyModel);
		repository.register(DeploymentModel.class, this.deploymentModel);
		repository.register(ExecutionModel.class, this.executionModel);
		repository.register(StatisticsModel.class, this.statisticsModel);
		repository.register(SourceModel.class, this.sourceModel);

		// Create the stages
		final DirectoryScannerStage directoryScannerStage = new DirectoryScannerStage(importDirectory);
		final DirectoryReaderStage directoryReaderStage = new DirectoryReaderStage(false, 80860);
		// BETTER consider if KiekerMetadataRecord has to be processed
		// final DebugStage<IMonitoringRecord> debugRecordsStage = new
		// DebugStage<>();
		final AllowedRecordsFilter allowedRecordsFilter = new AllowedRecordsFilter();
		final StaticModelsAssemblerStage staticModelsAssembler = new StaticModelsAssemblerStage(this.typeModel,
				this.assemblyModel, this.deploymentModel, this.sourceModel, DYNAMIC_SOURCE, this.signatureExtractor);
		final FlowRecordTraceReconstructionStage traceReconstructor = new FlowRecordTraceReconstructionStage(this.deploymentModel,
				timeUnitOfRecods);
		final TraceStatisticsDecoratorStage traceStatisticsDecorator = new TraceStatisticsDecoratorStage();

		final OperationCallExtractorStage operationCallExtractor = new OperationCallExtractorStage();
		final ExecutionModelAssemblerStage executionModelAssembler = new ExecutionModelAssemblerStage(
				this.executionModel, new ExecutionModelAssembler(this.executionModel, this.sourceModel, DYNAMIC_SOURCE));
		final FullReponseTimeStatisticsStage fullStatisticsDecorator = new FullReponseTimeStatisticsStage(
				this.statisticsModel, statisticsObjectAccesor);
		final CallStatisticsStage callStatisticsDecorator = new CallStatisticsStage(this.statisticsModel,
				this.executionModel);

		final TraceToGraphTransformerStage traceToGraphTransformer = new TraceToGraphTransformerStage();
		final DotTraceGraphFileWriterStage dotTraceGraphFileWriter = DotTraceGraphFileWriterStage
				.create(exportDirectory);
		// final GraphMLFileWriterStage graphMLTraceGraphFileWriter = new
		// GraphMLFileWriterStage(exportDirectory.getPath());
		final Distributor<Trace> traceDistributor = new Distributor<>(new CopyByReferenceStrategy());
		final TriggerOnTerminationStage onTerminationTrigger = new TriggerOnTerminationStage();

		final DependencyGraphCreatorStage dependencyGraphCreator = new DependencyGraphCreatorStage(repository, deploymentGraphBuilderFactory);
		final DotFileWriterStage dotDepGraphFileWriter = new DotFileWriterStage(exportDirectory,
				dependencyGraphDotExportConfiguration);

		// final AbstractConsumerStage<Graph> debugStage = new
		// GraphPrinterStage();

		// Connect the stages
		super.connectPorts(directoryScannerStage.getOutputPort(), directoryReaderStage.getInputPort());
		super.connectPorts(directoryReaderStage.getOutputPort(), allowedRecordsFilter.getInputPort());
		super.connectPorts(allowedRecordsFilter.getOutputPort(), staticModelsAssembler.getInputPort());
		super.connectPorts(staticModelsAssembler.getOutputPort(), traceReconstructor.getInputPort());
		super.connectPorts(traceReconstructor.getOutputPort(), traceStatisticsDecorator.getInputPort());
		super.connectPorts(traceStatisticsDecorator.getOutputPort(), traceDistributor.getInputPort());
		super.connectPorts(traceDistributor.getNewOutputPort(), traceToGraphTransformer.getInputPort());
		super.connectPorts(traceToGraphTransformer.getOutputPort(), dotTraceGraphFileWriter.getInputPort());
		// super.connectPorts(traceToGraphTransformer.getOutputPort(),
		// graphMLTraceGraphFileWriter.getInputPort());
		super.connectPorts(traceDistributor.getNewOutputPort(), operationCallExtractor.getInputPort());
		super.connectPorts(operationCallExtractor.getOutputPort(), executionModelAssembler.getInputPort());
		super.connectPorts(executionModelAssembler.getOutputPort(), fullStatisticsDecorator.getInputPort());
		super.connectPorts(fullStatisticsDecorator.getOutputPort(), callStatisticsDecorator.getInputPort());
		super.connectPorts(callStatisticsDecorator.getOutputPort(), onTerminationTrigger.getInputPort());
		super.connectPorts(onTerminationTrigger.getOutputPort(), dependencyGraphCreator.getInputPort());
		// super.connectPorts(dependencyGraphCreator.getOutputPort(),
		// debugStage.getInputPort());
		super.connectPorts(dependencyGraphCreator.getOutputPort(), dotDepGraphFileWriter.getInputPort());

	}

	public DeploymentModel getDeploymentModel() {
		return this.deploymentModel;
	}

	public ExecutionModel getExecutionModel() {
		return this.executionModel;
	}

	public StatisticsModel getStatisticsModel() {
		return this.statisticsModel;
	}

}
