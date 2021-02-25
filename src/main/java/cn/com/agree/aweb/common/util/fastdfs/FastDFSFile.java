package cn.com.agree.aweb.common.util.fastdfs;

import lombok.Data;

@Data
public class FastDFSFile {

  private String name;

  private byte[] content;

  private String ext;

  private String author;

  public FastDFSFile(String name, byte[] content, String ext) {
    super();
    this.name = name;
    this.content = content;
    this.ext = ext;

  }
}
