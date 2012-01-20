/*
 * Copyright 2011 Shunsuke Nakamura, and contributors.
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
 */

package org.apache.cassandra.db.engine;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.EngineInfo;
import org.apache.cassandra.io.util.DataInputBuffer;
import org.apache.cassandra.db.ColumnFamily;
import org.apache.cassandra.db.ColumnFamilySerializer;
import org.apache.cassandra.db.DecoratedKey;

public abstract class RangeDBInstance implements StorageEngine
{
    private static final Logger logger = LoggerFactory.getLogger(RangeDBInstance.class);
    static final int SUCCESS = 1;
    static final int FAILURE = -1;

    String engineName;
    String ksName;
    String cfName;

    // default configuration
    String host = "localhost";
    int port;
    String user;
    String pass;

    public int put(DecoratedKey key, ColumnFamily cf)
    {
        String rowKey = key.getTxtKey();
        if (cf.isMarkedForDelete())
        {
            return delete(rowKey);
        }
        else
        {
            Set<ByteBuffer> cNames = cf.getRemovedColumnNames();
            ColumnFamily cfOld = get(rowKey);
            byte[] token = key.getTokenBytes();
            if (cNames != null && !cNames.isEmpty())
            {
                for (Object cName : cNames.toArray())
                {
                    cfOld.remove((ByteBuffer) cName);
                }
                return insert(rowKey, token, cfOld);
            }
            else
            {
                return cfOld != null ? update(rowKey, mergeColumnFamily(cfOld, cf)) : insert(rowKey, token, cf);
            }
        }
    }

    public ColumnFamily get(DecoratedKey key)
    {
        return get(key.getTxtKey());
    }

    public ColumnFamily get(String key)
    {
        try
        {
            return bytes2ColumnFamily(select(key));
        }
        catch (IOException e)
        {
            errorMsg("db get error", e);
            return null;
        }
    }

    public abstract Map<ByteBuffer, ColumnFamily> getRangeSlice(DecoratedKey startWith, DecoratedKey stopAt, int maxResults);
    public abstract int truncate();
    public abstract int dropTable();
    public abstract int dropDB();

    public abstract int delete(String rowKey);
    public abstract int insert(String rowKey, byte[] token, ColumnFamily cf);
    public abstract int update(String rowKey, ColumnFamily newcf);
    public abstract byte[] select(String rowKey);

    public ColumnFamily mergeColumnFamily(ColumnFamily cf, ColumnFamily newcf)
    {
        cf.addAll(newcf);
        return cf;
    }

    public ColumnFamily bytes2ColumnFamily(byte[] b) throws IOException
    {
        return b != null ? new ColumnFamilySerializer().deserialize(new DataInputBuffer(b, 0, b.length)) : null;
    }

    public void setConfiguration()
    {
        int storageType = DatabaseDescriptor.getStorageType(engineName);
        EngineInfo einfo = DatabaseDescriptor.getEngineInfo(storageType);
        if (einfo.host != null)
            host = einfo.host;
        if (einfo.port > 0)
            port = einfo.port;
        if (einfo.user != null)
            user = einfo.user;
        if (einfo.pass != null)
            pass = einfo.pass;
    }

    public int errorMsg(String msg, Exception e)
    {
        logger.info("[MyCassandra (" + " Keyspace:" + ksName + "/ CF: " + cfName + ")] " + msg + ": " + e);
        return FAILURE;
    }
}
