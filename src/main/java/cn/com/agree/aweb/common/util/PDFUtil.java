package cn.com.agree.aweb.common.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * PDF处理类 用于word和excel文件转pdf文件
 *
 * @author fanzhendong
 * @version 1.0
 * @date 2018年11月27日 下午7:20:44
 */
public class PDFUtil {

  private PDFUtil() {

  }

  public static OutputStream excel2PDF(InputStream excelIs, boolean isWithStyle) {
    String htmlStr = null;
    try {
      Workbook wb = WorkbookFactory.create(excelIs);// 此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
      if (wb instanceof XSSFWorkbook) {
        XSSFWorkbook xWb = (XSSFWorkbook) wb;
        htmlStr = getExcelInfo(xWb, isWithStyle);
      } else if (wb instanceof HSSFWorkbook) {
        HSSFWorkbook hWb = (HSSFWorkbook) wb;
        htmlStr = getExcelInfo(hWb, isWithStyle);
      }
      if (htmlStr != null) {
        htmlStr = parseExcelHtml(htmlStr);
        return html2PDF(htmlStr);
      }
    } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * * 把excel文件转换为pdf文件 转换后样式可能有点不完整， 转换后样式不能看的话，把isWithStyle设为false； 同一行不要有两个表，不然会挤到一起非常难看
   *
   * @param fileName 需要转换文件全路径名称
   * @param targetName 生成文件全路径名称
   * @param isWithStyle 是否需要样式
   */
  public static boolean excel2PDF(String fileName, String targetName, boolean isWithStyle) {
    if (fileName == null || fileName.trim().isEmpty()) {
      throw new NullPointerException("fileName参数不能为null或者空字符串");
    }
    if (targetName == null || targetName.trim().isEmpty()) {
      targetName = fileName.substring(0, fileName.lastIndexOf('.')) + ".pdf";
    }
    if (!targetName.endsWith(".pdf")) {
      targetName = targetName + ".pdf";
    }
    String htmlName = fileName.substring(0, fileName.lastIndexOf('.')) + ".html";
    boolean flag = false;

    FileInputStream fis = null;
    String htmlStr = null;
    File file = new File(fileName);
    try {
      fis = new FileInputStream(file);
      Workbook wb = WorkbookFactory.create(fis);// 此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
      if (wb instanceof XSSFWorkbook) {
        XSSFWorkbook xWb = (XSSFWorkbook) wb;
        htmlStr = getExcelInfo(xWb, isWithStyle);
      } else if (wb instanceof HSSFWorkbook) {
        HSSFWorkbook hWb = (HSSFWorkbook) wb;
        htmlStr = getExcelInfo(hWb, isWithStyle);
      }
      if (htmlStr != null) {
        if (parseExcelHtml(htmlStr, htmlName)) {
          if (html2PDF(htmlName, targetName)) {
            flag = true;
          }
        }
      }
    } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
      e.printStackTrace();
    }

    File htmlFile = new File(htmlName);
    if (htmlFile.exists() && htmlFile.isFile()) {
      htmlFile.delete();
    }
    return flag;
  }

  /* Excel转html部分 */
  private static String getExcelInfo(Workbook wb, boolean isWithStyle) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < wb.getNumberOfSheets(); i++) {
      Sheet sheet = wb.getSheetAt(i);
      int lastRowNum = sheet.getLastRowNum();
      Map<String, String> map[] = getRowSpanColSpanMap(sheet);
      sb.append("<table style='border-collapse:collapse;' width='100%'>");
      Row row = null;
      Cell cell = null;

      for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
        row = sheet.getRow(rowNum);
        if (row == null) {
          sb.append("<tr><td > &nbsp;</td></tr>");
          continue;
        }
        sb.append("<tr>");
        int lastColNum = row.getLastCellNum();
        for (int colNum = 0; colNum < lastColNum; colNum++) {
          cell = row.getCell(colNum);
          if (cell == null) {// 特殊情况 空白的单元格会返回null
            sb.append("<td>&nbsp;</td>");
            continue;
          }

          String stringValue = getCellValue(cell);
          if (map[0].containsKey(rowNum + "," + colNum)) {
            String pointString = map[0].get(rowNum + "," + colNum);
            map[0].remove(rowNum + "," + colNum);
            int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
            int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
            int rowSpan = bottomeRow - rowNum + 1;
            int colSpan = bottomeCol - colNum + 1;
            sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan
                + "' ");
          } else if (map[1].containsKey(rowNum + "," + colNum)) {
            map[1].remove(rowNum + "," + colNum);
            continue;
          } else {
            sb.append("<td ");
          }

          // 判断是否需要样式
          if (isWithStyle) {
            dealExcelStyle(wb, sheet, cell, sb);
          }

          sb.append(">");
          if (stringValue == null || "".equals(stringValue.trim())) {
            sb.append(" &nbsp; ");
          } else {
            // 将ascii码为160的空格转换为html下的空格（&nbsp;）
            sb.append(stringValue.replace(String.valueOf((char) 160),
                "&nbsp;"));
          }
          sb.append("</td>");
        }
        sb.append("</tr>");
      }
      sb.append("</table>");
      sb.append("<br/>");
    }
    return sb.toString();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

    Map<String, String> map0 = new HashMap<String, String>();
    Map<String, String> map1 = new HashMap<String, String>();
    int mergedNum = sheet.getNumMergedRegions();
    CellRangeAddress range = null;
    for (int i = 0; i < mergedNum; i++) {
      range = sheet.getMergedRegion(i);
      int topRow = range.getFirstRow();
      int topCol = range.getFirstColumn();
      int bottomRow = range.getLastRow();
      int bottomCol = range.getLastColumn();
      map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
      int tempRow = topRow;
      while (tempRow <= bottomRow) {
        int tempCol = topCol;
        while (tempCol <= bottomCol) {
          map1.put(tempRow + "," + tempCol, "");
          tempCol++;
        }
        tempRow++;
      }
      map1.remove(topRow + "," + topCol);
    }
    Map[] map = {map0, map1};
    return map;
  }

  private static String getCellValue(Cell cell) {

    String result = new String();
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_NUMERIC:// 数字类型
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
          SimpleDateFormat sdf = null;
          if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
              .getBuiltinFormat("h:mm")) {
            sdf = new SimpleDateFormat("HH:mm");
          } else {// 日期
            sdf = new SimpleDateFormat("yyyy-MM-dd");
          }
          Date date = cell.getDateCellValue();
          result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
          // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          double value = cell.getNumericCellValue();
          Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
          result = sdf.format(date);
        } else {
          double value = cell.getNumericCellValue();
          CellStyle style = cell.getCellStyle();
          DecimalFormat format = new DecimalFormat();
          String temp = style.getDataFormatString();
          // 单元格设置成常规
          if (temp.equals("General")) {
            format.applyPattern("#");
          }
          result = format.format(value);
        }
        break;
      case Cell.CELL_TYPE_STRING:// String类型
        result = cell.getRichStringCellValue().toString();
        break;
      case Cell.CELL_TYPE_BLANK:
        result = "";
        break;
      default:
        result = "";
        break;
    }
    return result;
  }

  private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell,
      StringBuffer sb) {

    CellStyle cellStyle = cell.getCellStyle();
    if (cellStyle != null) {
      short alignment = cellStyle.getAlignment();
      sb.append("align='" + convertAlignToHtml(alignment) + "' ");// 单元格内容的水平对齐方式
      short verticalAlignment = cellStyle.getVerticalAlignment();
      sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");// 单元格中内容的垂直排列方式

      if (wb instanceof XSSFWorkbook) {

        XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
//        short boldWeight = xf.getBoldweight();
        sb.append("style='");
//        sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
        sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
        int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
        sb.append("width:" + columnWidth + "px;");

        XSSFColor xc = xf.getXSSFColor();
        if (xc != null && !"".equals(xc)) {
          sb.append("color:#" + xc.getARGBHex().substring(2) + ";"); // 字体颜色
        }

        XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
        if (bgColor != null && !"".equals(bgColor)) {
          sb.append("background-color:#" + bgColor.getARGBHex().substring(2)
              + ";"); // 背景颜色
        }
        sb.append(getBorderStyle(0, cellStyle.getBorderTop(),
            ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
        sb.append(getBorderStyle(1, cellStyle.getBorderRight(),
            ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
        sb.append(getBorderStyle(2, cellStyle.getBorderBottom(),
            ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
        sb.append(getBorderStyle(3, cellStyle.getBorderLeft(),
            ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

      } else if (wb instanceof HSSFWorkbook) {

        HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
//        short boldWeight = hf.getBoldweight();
        short fontColor = hf.getColor();
        sb.append("style='");
        HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
        HSSFColor hc = palette.getColor(fontColor);
//        sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
        sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
        String fontColorStr = convertToStardColor(hc);
        if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
          sb.append("color:" + fontColorStr + ";"); // 字体颜色
        }
        int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
        sb.append("width:" + columnWidth + "px;");
        short bgColor = cellStyle.getFillForegroundColor();
        hc = palette.getColor(bgColor);
        String bgColorStr = convertToStardColor(hc);
        if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
          sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
        }
        sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(),
            cellStyle.getTopBorderColor()));
        sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(),
            cellStyle.getRightBorderColor()));
        sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(),
            cellStyle.getLeftBorderColor()));
        sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(),
            cellStyle.getBottomBorderColor()));
      }

      sb.append("' ");
    }
  }

  private static String convertAlignToHtml(short alignment) {

    String align = "left";
//    switch (alignment) {
//      case CellStyle.ALIGN_LEFT:
//        align = "left";
//        break;
//      case CellStyle.ALIGN_CENTER:
//        align = "center";
//        break;
//      case CellStyle.ALIGN_RIGHT:
//        align = "right";
//        break;
//      default:
//        break;
//    }
    return align;
  }

  private static String convertVerticalAlignToHtml(short verticalAlignment) {

    String valign = "middle";
//    switch (verticalAlignment) {
//      case CellStyle.VERTICAL_BOTTOM:
//        valign = "bottom";
//        break;
//      case CellStyle.VERTICAL_CENTER:
//        valign = "center";
//        break;
//      case CellStyle.VERTICAL_TOP:
//        valign = "top";
//        break;
//      default:
//        break;
//    }
    return valign;
  }

  private static String convertToStardColor(HSSFColor hc) {

    StringBuffer sb = new StringBuffer("");
    if (hc != null) {
      if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
        return null;
      }
      sb.append("#");
      for (int i = 0; i < hc.getTriplet().length; i++) {
        sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
      }
    }

    return sb.toString();
  }

  private static String fillWithZero(String str) {
    if (str != null && str.length() < 2) {
      return "0" + str;
    }
    return str;
  }

  static String[] bordesr = {"border-top:", "border-right:", "border-bottom:",
      "border-left:"};
  static String[] borderStyles = {"solid ", "solid ", "solid ", "solid ", "solid ",
      "solid ", "solid ", "solid ", "solid ", "solid", "solid", "solid", "solid",
      "solid"};

  private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {

    if (s == 0) {
      return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
    }
    ;
    String borderColorStr = convertToStardColor(palette.getColor(t));
    borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000"
        : borderColorStr;
    return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";

  }

  private static String getBorderStyle(int b, short s, XSSFColor xc) {

    if (s == 0) {
      return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
    }
    ;
    if (xc != null && !"".equals(xc)) {
      String borderColorStr = xc.getARGBHex();
      borderColorStr = borderColorStr == null || borderColorStr.length() < 1
          ? "#000000"
          : borderColorStr.substring(2);
      return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
    }

    return "";
  }

  private static String parseExcelHtml(String content) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    org.jsoup.nodes.Document doc = Jsoup.parse(content);

    Element body = doc.body();
    String style = body.attr("style");
    if (style != null && !style.isEmpty() && style.indexOf("width") != -1) {
      body.attr("style", "");
    }
    Elements divs = doc.select("div");
    for (int i = 0; i < divs.size(); i++) {
      Element div = divs.get(i);
      style = div.attr("style");
      if (style != null && !style.isEmpty() && style.indexOf("width") != -1) {
        div.attr("style", "");
      }
    }

    Elements tables = doc.getAllElements().select("table");
    for (int i = 0; i < tables.size(); i++) {
      tables.get(i).after("<p><br/></p>");
    }

    content = doc.html();
    return content;
  }

  private static boolean parseExcelHtml(String content, String targetPath) {
    FileOutputStream fos = null;
    BufferedWriter bw = null;
    org.jsoup.nodes.Document doc = Jsoup.parse(content);
    boolean flag = false;

    Element body = doc.body();
    String style = body.attr("style");
    if (style != null && !style.isEmpty() && style.indexOf("width") != -1) {
      body.attr("style", "");
    }
    Elements divs = doc.select("div");
    for (int i = 0; i < divs.size(); i++) {
      Element div = divs.get(i);
      style = div.attr("style");
      if (style != null && !style.isEmpty() && style.indexOf("width") != -1) {
        div.attr("style", "");
      }
    }

    Elements tables = doc.getAllElements().select("table");
    for (int i = 0; i < tables.size(); i++) {
      tables.get(i).after("<p><br/></p>");
    }

    content = doc.html();
    File file = new File(targetPath);

    try {
      fos = new FileOutputStream(file);
      bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
      bw.write(content);
      flag = true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
        if (fos != null) {
          fos.close();
        }
      } catch (IOException e) {
      }
    }
    return flag;
  }

  /* html转pdf部分 */
  public static ByteArrayOutputStream html2PDF(String htmlStr) {
    Document document = new Document();
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    try {
      PdfWriter writer = PdfWriter.getInstance(document, result);
      document.open();

      XMLWorkerHelper.getInstance().parseXHtml(writer, document,
          new ByteArrayInputStream(htmlStr.getBytes()), Charset.forName("utf-8"),
          new SimsunFontProvider());
      document.close();

    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static boolean html2PDF(String fileName, String targetName) {
    Document document = new Document();
    boolean flag = false;
    try {
      PdfWriter writer = PdfWriter.getInstance(document,
          new FileOutputStream(targetName));
      document.open();

      XMLWorkerHelper.getInstance().parseXHtml(writer, document,
          new FileInputStream(fileName), Charset.forName("utf-8"),
          new SimsunFontProvider());
      document.close();
      flag = true;

    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }
    return flag;
  }

  private static boolean deleteDir(File file) {
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        deleteDir(f);
      }
    }
    file.delete();
    return false;
  }

}

class SimsunFontProvider extends XMLWorkerFontProvider {

  @Override
  public Font getFont(final String fontname, final String encoding, final boolean embedded,
      final float size, final int style, final BaseColor color) {
    BaseFont bf = null;

    try {
      bf = BaseFont.createFont("/font/SIMSUN.TTC,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    } catch (DocumentException | IOException e) {
      e.printStackTrace();
    }

    Font font = new Font(bf, size, style, color);
    font.setColor(color);
    return font;
  }
}
