/*
 *   Copyright 2009 Joubin Houshyar
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *    
 *   http://www.apache.org/licenses/LICENSE-2.0
 *    
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.apache.cassandra.db;

import java.io.UnsupportedEncodingException;
import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.protocol.Command;
import org.jredis.ri.alphazero.JRedisClient;
import static org.jredis.ri.alphazero.support.DefaultCodec.*;

/**
 * [TODO: document me!]
 *
 * @author  Joubin Houshyar (alphazero@sensesay.net)
 * @version alpha.0, Apr 15, 2009
 * @since   alpha.0
 * 
 */

public class TestJredis {
	public static final String key = "jredis::examples::HelloAgain::message";
	public static void main(String[] args) {
		String password = "";
		if(args.length > 0) password  = args[0];
		new TestJredis().run(password);
	}

	private void run(String password) {
		try {
			JRedis	jredis = new JRedisClient("localhost", 6379, "jredis", 0);
			jredis.ping();
			
			int objcnt = 100;
            System.out.format ("Creating and saving %d Java objects to redis ...", objcnt);

            for(int i=1; i<objcnt; i++){
                    // instance it
                    String str = "bean #" + i;
                    byte[] obj = null;
					try {
						obj = str.getBytes("EUC_JP");
					} catch (UnsupportedEncodingException e) {
						
					}
                    // we can bind it a unique key using map (Redis "String") semantics now
                    String key = "objects::SimpleBean::" + i;

                    // voila: java object db
                    jredis.set(key, obj);
                    
                    // and lets add it to this set too since this is so much fun
                    jredis.sadd("object_set", obj);
            }
			jredis.quit();
		}
		catch (RedisException e){
			if (e.getCommand()==Command.PING){
				System.out.format("I'll need that password!  Try again with password as command line arg for this program.\n");
			}
		}
		catch (ProviderException e){
			System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", e.getMessage());
		}
		catch (ClientRuntimeException e){
			System.out.format("%s\n", e.getMessage());
		}
	}
}
