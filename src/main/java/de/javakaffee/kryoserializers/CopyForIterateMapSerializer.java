/*
 * Copyright 2010 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.javakaffee.kryoserializers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

/**
 * A kryo {@link Serializer} that creates a copy of the source map for writing object data.
 * <p>
 * This is useful for applications where objects/maps that are serialized
 * might be accessed by different threads. However, it only reduces the probability
 * of concurrent modification exceptions, as even during taking the copy the
 * map might be modified by another thread.
 * </p>
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class CopyForIterateMapSerializer extends MapSerializer {
    
    @SuppressWarnings("unchecked")
    @Override
    public void write( final Kryo kryo, final Output output, @SuppressWarnings("rawtypes") final Map object ) {
        final Map<?, ?> map;
        // we only need special support for linked hash map, as SortedMaps will
        // recreate correct sorting during deserialization...
        if ( object instanceof LinkedHashMap<?, ?> ) {
            map = new LinkedHashMap<Object, Object>( object );
        }
        else {
            map = new HashMap<Object, Object>( object );
        }
        super.write( kryo, output, map );
    }

}
