package ng.edu.unn.unninfo.News_Object;

public class News_Object {
    private String postID;
    private String title, PostURL, body, date;

    public String getpostID() {
        return postID;
    }

    public void setpostID(String id) {
        this.postID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostURL() {
        return PostURL;
    }

    public void setPostURL(String postURL) {
        this.PostURL = postURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

