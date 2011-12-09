/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package setup;

import java.io.IOException;

import org.apache.cassandra.thrift.CassandraDaemon;
import org.apache.thrift.transport.TTransportException;

class EmbeddedCassandra implements Runnable {
    private CassandraDaemon cassandraDaemon;

    public void init() throws TTransportException, IOException {
        cassandraDaemon = new CassandraDaemon();
        cassandraDaemon.init(null);
    }

    @Override
    public void run() {
        cassandraDaemon.start();
    }
}
