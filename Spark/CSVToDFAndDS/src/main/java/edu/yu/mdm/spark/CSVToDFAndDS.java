package edu.yu.mdm.spark;

import java.io.Serializable;
import java.util.Date;

public class CSVToDFAndDS implements Serializable {

  public static class Book {
    int id;
    int authorId;
    String title;
    Date releaseDate;
    String link;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getAuthorId() {
      return authorId;
    }

    public void setAuthorId(int authorId) {
      this.authorId = authorId;
    }

    public void setAuthorId(Integer authorId) {
      if (authorId == null) {
        this.authorId = 0;
      }
      else {
        this.authorId = authorId;
      }
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public Date getReleaseDate() {
      return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
      this.releaseDate = releaseDate;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    @Override
    public int hashCode() { return id; } // assumption: id is unique

    @Override
    public boolean equals(Object o) { 
      if (o == this) { 
        return true; 
      } 

      if (!(o instanceof Book)) { return false; }
      final Book that = (Book) o;
      return this.id == that.id;
    }

    @Override
    public String toString() {
      // @fixme needs more state and a StringBuilder
      return "Book: id="+id+",authorId="+authorId+", title="+title;
    }

  } // static inner class Book


  private static final long serialVersionUID = -1L;

  public static void main(String[] args) {
    if (args.length != 1) {
      final String msg = "Usage: "+CSVToDFAndDS.class.getName()+
        " nameOfInputFile";
      System.err.println(msg);
      throw new IllegalArgumentException(msg);
    }

    final String inputFileName = args[0];
  } // main

} // CSVToDFAndDS
