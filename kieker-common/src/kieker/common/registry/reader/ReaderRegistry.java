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
package kieker.common.registry.reader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents an unsynchronized registry for read-only purposes. It is used by the readers within the analysis component.
 *
 * @param <E>
 *            the type of the values in this registry
 *
 * @author Christian Wulf
 *
 * @since 1.13
 */
public class ReaderRegistry<E> {

	// NOCS TODO replace by a high performance map with primitive key type
	private final Map<Long, E> registryEntries = new HashMap<Long, E>(); // NOPMD (should be unsynchronized)

	/**
	 * Constructs an unsynchronized reader registry.
	 */
	public ReaderRegistry() {
		super();
	}

	/**
	 * Get registry entry matching the key.
	 *
	 * @param key
	 *            key
	 * @return returns matching entry.
	 */
	public E get(final long key) {
		return this.registryEntries.get(key);
	}

	/**
	 * Reverse lookup of key based on value.
	 *
	 * @param value
	 *            value
	 * @return returns first key
	 */
	public Long getKey(final E value) {
		for (final Entry<Long, E> entry : this.registryEntries.entrySet()) {
			if (entry.equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Add one entry to the registry.
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 *
	 * @return the previous associated value for the given <code>key</code>, or <code>null</code> otherwise.
	 */
	public E register(final long key, final E value) {
		return this.registryEntries.put(key, value);
	}
}
