package object;

public class commentArea {
    public int id;
    private int newsId;
    private String ip;
    private String userName;
    private String comment;
    private String date;

    public commentArea(){

    }

    public commentArea (int id,int newsId,String ip,String userName,String comment,String date) {
        this.id = id;
        this.newsId = newsId;
        this.ip = ip;
        this.userName = userName;
        this.comment = comment;
        this.date = date;
    }

    public int getNewsId () {
        return newsId;
    }

    public void setNewsId (int newsId) {
        this.newsId = newsId;
    }

    public String getDate () {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getIp () {
        return ip;
    }

    public void setIp (String ip) {
        this.ip = ip;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }
}
