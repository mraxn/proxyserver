package com.masa.proxyserver;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args)
    {
        ProxyServerData psData = new ProxyServerData();
        ProxyHttpClient proxyHttpClient = new ProxyHttpClient(psData);
        ProxyHttpServer proxyHttpServer = new ProxyHttpServer(psData);
        proxyHttpServer.run();
        proxyHttpClient.run();
    }
}
