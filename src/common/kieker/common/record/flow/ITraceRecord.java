/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
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

package kieker.common.record.flow;

/**
 * Interface for all flow records that describe information in traces.
 * 
 * All trace records have a <code>traceId</code> field of type <code>long</code> and an <code>orderIndex</code> field of type <code>int</code>.
 * 
 * @author Jan Waller
 * 
 * @since 1.6
 */
public interface ITraceRecord extends IFlowRecord {

	/**
	 * @since 1.6
	 */
	public abstract long getTraceId();

	/**
	 * @since 1.6
	 */
	public abstract int getOrderIndex();
}
