package com.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
	* Created with IntelliJ IDEA.
	* User: MacieSerenity
	* Date: 2020/05/11 15:24
	* Description:将数据库中存储的文章转换为HTML
	* Version: V1.0
	*/
public class MarkDownUtils {

	
	/**
	* @Description: markDown格式转换为HTML格式
	* @Method: markdownToHtml
	* @Param: [markdown]
	* @return: java.lang.String
	* @Author: MacieSerenity
	* @Date: 2020/5/11
	*/ 
	public static String markdownToHtml(String markdown){
		Parser parser= Parser.builder().build();
		Node document=parser.parse(markdown);
		HtmlRenderer renderer=HtmlRenderer.builder().build();
		return renderer.render(document);
	}

	public static String markdownToHtmlExtensions(String markdown){
		/**
		* @Description: 将table转换为html中的table，并加入样式
		* @Method: markdownToHtmlExtensions
		* @Param: [markdown]
		* @return: java.lang.String
		* @Author: MacieSerenity
		* @Date: 2020/5/11
		*/ 
		//H1 h2 h3标题生成ID
		Set<Extension> headingAnchorExtensions= Collections.singleton(HeadingAnchorExtension.create());
//						转换table的Html
		List<Extension> tableExtension= Arrays.asList(TablesExtension.create());
		Parser parser=Parser.builder().extensions(tableExtension).build();
		Node document=parser.parse(markdown);
		HtmlRenderer renderer=HtmlRenderer.builder().extensions(headingAnchorExtensions)
						.extensions(tableExtension)
						.attributeProviderFactory(new AttributeProviderFactory() {
							@Override
							public AttributeProvider create(AttributeProviderContext context) {
								return new CustomAttributeProvider();
							}
						}).build();
		return renderer.render(document);
	}

	
	static class CustomAttributeProvider implements AttributeProvider{
	/**
	* @Description: 如果是表格，添加一个semantic UI的样式
	* @Method: setAttributes
	* @Param: [node, tagName, attr]
	* @return: void
	* @Author: MacieSerenity
	* @Date: 2020/5/11
	*/ 
		@Override
		public void setAttributes(Node node,String tagName,Map<String,String> attr){
			if (node instanceof Link){
				attr.put("target","_blank");
			}
			if (node instanceof TableBlock){
				attr.put("class","ui celled table");
			}
		}
	}

}
