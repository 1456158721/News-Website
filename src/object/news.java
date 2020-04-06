package object;

public class news {
    int id;
    int subjectId;
    String title;
    String date;
    String author;
    String thisAbstract;
    String content;
    String image;
    
    public news(){
        
    }
    public news (int id,int subjectId,String title,String date,String author,String thisAbstract,String content,String image){
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.date = date;
        this.author = author;
        this.thisAbstract = thisAbstract;
        this.content = content;
        this.image = image;
    }

    public int getId () {
        return id;
    }

    public int getSubjectId () {
        return subjectId;
    }

    public String getTitle () {
        return title;
    }

    public String getDate () {
        return date;
    }

    public String getAuthor () {
        return author;
    }

    public String getThisAbstract () {
        return thisAbstract;
    }

    public String getContent () {
        return content;
    }

    public String getImage () {
        return image;
    }
}
