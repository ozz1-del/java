package oz1.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @author: ykk
 * @date: 2023/4/21 21:14
 * @version: 1.0
 */

public class MarkdownUtils {

  /**
   * markdown格式转换成HTML格式
   * 
   * @param markdown
   * @return
   */
  public static String markdownToHtml(String markdown) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(markdown);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(document);
  }

  /**
   * 增加扩展[标题锚点，表格生成]
   * Markdown转换成HTML
   * 
   * @param markdown
   * @return
   */
  public static String markdownToHtmlExtensions(String markdown) {
    // 转换table的HTML
    List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
    Parser parser = Parser.builder()
        .extensions(tableExtension)
        .build();
    Node document = parser.parse(markdown);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(document);
  }

  /**
   * 处理标签的属性
   */
  static class CustomAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
      // 改变a标签的target属性为_blank
      if (node instanceof Link) {
        attributes.put("target", "_blank");
      }
      if (node instanceof TableBlock) {
        attributes.put("class", "ui celled table");
      }
    }
  }

}
