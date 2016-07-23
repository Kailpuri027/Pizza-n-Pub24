package capstone.conestoga.javafiles;

/**
 * Created by Kailpuri on 7/11/2016.
 */
public class Album {
    private String name;
    private int num;
    private int thumbnail;

    public Album() {
    }

    public Album(String name, int num, int thumbnail) {
        this.name = name;
        this.num = num;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) { this.num = num; }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
