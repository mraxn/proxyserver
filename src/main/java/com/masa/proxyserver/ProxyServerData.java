package com.masa.proxyserver;

import java.net.URI;
import java.net.http.HttpHeaders;

import com.sun.net.httpserver.Headers;

public class ProxyServerData
{
  public URI remoteUri = null;
  public Headers reqHdrs = null;
  public HttpHeaders resHdrs = null;
  // public Boolean isRcvdFromSrvTx = false;
  // public Boolean isRcvdFromClientTx = false;
  public Boolean isTxSrvReady = false;
  public Boolean isRcvdFromClientRx = false;
  public String resBody;
  public int resStatusCode = 0;
}
