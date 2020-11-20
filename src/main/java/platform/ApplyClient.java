package platform;

public enum ApplyClient {
    OfficialAccounts("微信公众号"),
    WeixinPlugin("微信小程序"),
    WeixinGroup("微信群"),
    QQ("QQ群"),
    WeiBo("微博"),
    APP("APP"),
    Website("网站"),
    SmartHardware("硬件"),
    MediaPlatform("传媒平台"),
    VRAR("VR/AR"),
    Others("其他");

    private String name;

    private ApplyClient(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

  }