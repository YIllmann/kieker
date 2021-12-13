/***************************************************************************
 * Copyright 2021 Kieker Project (http://kieker-monitoring.net)
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
package kieker.analysis.stage.adaptation;

import kieker.analysis.stage.adaptation.events.AbstractTcpControlEvent;

/**
 * @author Reiner Jung
 *
 */
public interface IProbeController {

	/**
	 * Control probe with given event.
	 *
	 * @param event
	 *            control information
	 * @throws RemoteControlFailedException
	 *             on errors to control the probe
	 */
	void controlProbe(AbstractTcpControlEvent event) throws RemoteControlFailedException;

}
