package com.job.core.rpc.jetty.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.core.thread.ExecutRegistThread;
import com.job.core.thread.TriggerCallBackThread;
/**
 * 
 * @Title: JettyServer.java
 * @Package: com.job.core.rpc.jetty.server
 * @Description: jetty服务端
 * @author: sunwei
 * @date: 2017年5月15日 上午11:11:43
 * @version: V1.0
 */
public class JettyServer {
    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

	private Server server;
	private Thread thread;
	public void start(final int port, final String ip, final String appName) throws Exception {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {

				// The Server
				server = new Server(new ExecutorThreadPool());  // 非阻塞

				// HTTP connector
				ServerConnector connector = new ServerConnector(server);
				connector.setPort(port);
				server.setConnectors(new Connector[]{connector});

				// Set a handler
				HandlerCollection handlerc =new HandlerCollection();
				handlerc.setHandlers(new Handler[]{new JettyServerHandler()});
				server.setHandler(handlerc);

				try {
					// Start server
					server.start();
					logger.info(">>>>>>>>>>>> xxl-job jetty server start success at port:{}.", port);

					// Start Registry-Server
					ExecutRegistThread.getExecutRegistThread().start(port, ip, appName);
					// Start Callback-Server
					TriggerCallBackThread.getInstance().start();

					server.join();	// block until thread stopped
					logger.info(">>>>>>>>>>> xxl-rpc server join success, netcon={}, port={}", JettyServer.class.getName(), port);
				} catch (Exception e) {
					logger.error("", e);
				} finally {
					destroy();
				}
			}
		});
		thread.setDaemon(true);	// daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
		thread.start();
	}

	public void destroy() {

		// destroy server
		if (server != null) {
			try {
				server.stop();
				server.destroy();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		if (thread.isAlive()) {
			thread.interrupt();
		}

		// destroy Registry-Server
		ExecutRegistThread.getExecutRegistThread().toStop();

		// destroy Callback-Server
		TriggerCallBackThread.getInstance().toStop();

		logger.info(">>>>>>>>>>> xxl-rpc server destroy success, netcon={}", JettyServer.class.getName());
	}

}
