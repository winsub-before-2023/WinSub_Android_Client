package kr.winsub.cloud;

public class Profile {
    public String name;
    public String desc;
    public String homepage;

    public Profile (String name, String desc, String homepage) {
        this.name = name;
        this.desc = desc;
        this.homepage = homepage;
    }

    public String getHomepage() {
        return this.homepage;
    }
}
