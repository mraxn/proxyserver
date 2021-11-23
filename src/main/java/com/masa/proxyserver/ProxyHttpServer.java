package com.masa.proxyserver;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ProxyHttpServer extends Thread
{

  HttpServer httpServer = null;
  String resStr;
  ProxyServerData psData = null;
  HttpServer server = null;

  public ProxyHttpServer(ProxyServerData locPsData)
  {
    this.psData = locPsData;
  }

  public void run()
  {
    try
    {
      server = HttpServer.create(new InetSocketAddress(3000), 0);
    }
    catch (Throwable tr)
    {
      tr.printStackTrace();
    }
    server.createContext("/", new Rcv());
    server.start();

    // }
  }

  private class Rcv implements HttpHandler
  {
    @Override
    public void handle(final HttpExchange httpExchange)
    {
      psData.remoteUri = httpExchange.getRequestURI();
      psData.reqHdrs = httpExchange.getRequestHeaders();

      psData.isTxSrvReady = true;
      // notify();

      while (!psData.isRcvdFromClientRx)
      {
        try
        {
          TimeUnit.SECONDS.sleep(1);
        }
        catch (Exception e)
        {
        }

      }

      psData.isRcvdFromClientRx = false;

      try
      {
        byte response[] = psData.resBody.getBytes("UTF-8");
        // httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        for (String resHdrKey : psData.resHdrs.map().keySet())
        {
          httpExchange.getResponseHeaders().add(resHdrKey, psData.resHdrs.map().get(resHdrKey).get(0));
        }

        httpExchange.sendResponseHeaders(psData.resStatusCode, response.length);

        OutputStream out = httpExchange.getResponseBody();

        out.write(response);
        out.close();
      }
      catch (Exception e)
      {
        System.out.println(e);
      }
    }
  }
}
