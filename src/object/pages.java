package object;

import java.util.ArrayList;

public class pages {
    //总页数
    int totalPage;
    //总条数
    int totalCount;
    //每页条数
    int everyPage;
    //页数
    int pages;
    //该页新闻
    ArrayList<news> news;

    public pages (int totalCount,int pages,ArrayList<news> news) {
        this.totalCount = totalCount;
        this.pages = pages;
        this.news = news;
        setPages();
    }

    private void setPages(){
        everyPage = 34;
        //获取总页数
        if (totalCount != 0 && totalCount % everyPage == 0) {
            totalPage = totalCount / everyPage;
        } else {
            totalPage = totalCount / everyPage + 1;
        }
    }

    public int getTotalPage () {
        return totalPage;
    }

    public void setTotalPage (int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount () {
        return totalCount;
    }

    public void setTotalCount (int totalCount) {
        this.totalCount = totalCount;
    }

    public int getEveryPage () {
        return everyPage;
    }

    public void setEveryPage (int everyPage) {
        this.everyPage = everyPage;
    }

    public int getPages () {
        return pages;
    }

    public void setPages (int pages) {
        this.pages = pages;
    }

    public ArrayList<news> getNews () {
        return news;
    }

    public void setNews (ArrayList<news> news) {
        this.news = news;
    }
}
