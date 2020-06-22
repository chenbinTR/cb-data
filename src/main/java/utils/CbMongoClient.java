package utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-04-18
 * @see
 * @since
 */
public class CbMongoClient {


   public enum MongoServer{
        ALPHA("rzjjNPe9NCW58FdMKKYyRQ==", "27018", "admin", "admin", "in69M6XrKTzegAel"),
        DEV("r1PXSYlxTBUCf3B/pS6bnA==", "27023", "admin", "admin", "password"),
        PROD("icLZPgfn4zP5d72die7d8A==", "27025", "admin", "admin", "password"),
       PLATFORM_PROD("3FpSYqIJ5LV/tPTTVctjTA==","27017","admin", "admin", "password"),
       Data_PROD("3FpSYqIJ5LV/tPTTVctjTA==","27018","admin", "admin", "password"),
       ;
        private String host;
        private String port;
        private String user;
        private String db;
        private String password;

        MongoServer(String host, String port, String user, String db, String password) {
            this.host = host;
            this.port = port;
            this.user = user;
            this.db = db;
            this.password = password;
        }
    }
    public static MongoClient getMongoClientInstance(MongoServer mongoServer){
        ServerAddress serverAddress = new ServerAddress(SymmetricEncoder.AESDncode(mongoServer.host), Integer.valueOf(mongoServer.port));
        List<ServerAddress> addres = new ArrayList<ServerAddress>();
        addres.add(serverAddress);
        MongoCredential credential = MongoCredential.createScramSha1Credential(mongoServer.user, mongoServer.db, mongoServer.password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        return new MongoClient(addres, credentials);
    }
}
