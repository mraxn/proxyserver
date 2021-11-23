package com.masa.proxyserver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

public class ProxyHttpClient extends Thread
{
  HttpClient httpClient = null;
  // Use the client to send the request:
  HttpResponse<String> httpRes = null;
  ProxyServerData psData = null;

  public ProxyHttpClient(ProxyServerData locPsData)
  {
    this.psData = locPsData;
    httpClient = HttpClient.newHttpClient();
  }

  public void run()
  {
    while (true)
    {
      // Create a client:
      this.Send();

      psData.resBody = httpRes.body();
      psData.resHdrs = httpRes.headers();
      psData.resStatusCode = httpRes.statusCode();
      psData.isRcvdFromClientRx = true;
    }
  }

  public void Send()
  {
    // Create a request:

    while (!this.psData.isTxSrvReady)
    {
      // try {
      // wait();
      // } catch (Exception e) {
      // System.out.println(e);
      // }
      try
      {
        TimeUnit.SECONDS.sleep(1);
      }
      catch (Exception e)
      {
      }
    }

    this.psData.isTxSrvReady = false;

    HttpRequest httpReq = HttpRequest.newBuilder(psData.remoteUri).header("accept", "*/*").build();
    try
    {
      httpRes = httpClient.send(httpReq, BodyHandlers.ofString());

    }
    catch (InterruptedException interruptedException)
    {
      System.out.println("InterruptedException!");
    }
    catch (IOException ioException)
    {
      System.out.println("IOException!");
    }
  }

}
