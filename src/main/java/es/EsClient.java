package es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import utils.SymmetricEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ChenOT
 * @date 2019-09-18
 * @see
 * @since
 */
public class EsClient {
    /**
     * @param address
     * @return
     */
    public static TransportClient getClient(EsAddress address) {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .build();
        TransportClient client  = new PreBuiltTransportClient(settings);
        TransportAddress transportAddress = null;
        try {
            transportAddress = new TransportAddress(
                    InetAddress.getByName(SymmetricEncoder.AESDncode(address.getHost())), address.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.addTransportAddress(transportAddress);
        return client;
    }
}
