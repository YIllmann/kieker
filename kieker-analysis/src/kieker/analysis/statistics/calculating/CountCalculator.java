/***************************************************************************
 * Copyright 2022 Kieker Project (http://kieker-monitoring.net)
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

package kieker.analysis.statistics.calculating;

import org.eclipse.emf.ecore.EObject;

import kieker.model.analysismodel.statistics.EPropertyType;
import kieker.model.analysismodel.statistics.StatisticRecord;

/**
 * Computes an incrementable value.
 *
 * @param <T>
 *            Type of elements
 *
 * @author Sören Henning
 *
 * @since 1.14
 */
public class CountCalculator<T> implements ICalculator<T> {

	public CountCalculator() {
		// Create Calculator
	}

	@Override
	public void calculate(final StatisticRecord statistic, final T input, final EObject modelObject) {
		final Long oldCount = (Long) statistic.getProperties().get(EPropertyType.COUNT);
		final long newCount = oldCount != null ? oldCount + 1 : 1; // NOCS (declarative)
		statistic.getProperties().put(EPropertyType.COUNT, newCount);
	}

}
