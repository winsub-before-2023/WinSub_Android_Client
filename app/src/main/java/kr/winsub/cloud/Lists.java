package kr.winsub.cloud;

public class Lists {
    public String name;
    public String size;
    public String url;
    public boolean isFIle;

    public Lists(String name, String size, String url, boolean isFIle) {
        this.name = name;
        this.size = size;
        this.url = url;
        this.isFIle = isFIle;
    }

    public String getUrl() {
        return this.url;
    }

    public Boolean getIsFIle() {
        return this.isFIle;
    }
}
