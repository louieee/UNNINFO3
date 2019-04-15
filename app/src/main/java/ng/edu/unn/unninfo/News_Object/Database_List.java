package ng.edu.unn.unninfo.News_Object;

public class Database_List {
    private String id;
    private String [] img;
    private String Title,date,body;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    private String[] Attachment;
    private String PostUrl;
    private String Time;

    public String getPostUrl() {
        return PostUrl;
    }

    public void setPostUrl(String postUrl) {
        PostUrl = postUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getAttachment() {
        return Attachment;
    }

    public void setAttachment(String[] attachment) {
        Attachment = attachment;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
