package fvarrui.sysadmin.challenger.common.ui.markdown;

import java.util.Arrays;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;

public class MarkdownUtils {

	private static final String TEMPLATE = 
			"<html>" + "\n" + 
				"<head>" + "\n" + 
					"<script type='text/javascript' src='" + MarkdownUtils.class.getResource("/markdown/js/markdown.js") + "'></script>" + "\n" + 
					"<script type='text/javascript' src='" + MarkdownUtils.class.getResource("/markdown/js/admonition.js") + "'></script>" + "\n" + 
					"<link rel='stylesheet' href='" + MarkdownUtils.class.getResource("/markdown/css/markdown.css") + "' type='text/css'>" + "\n" + 
					"<link rel='stylesheet' href='" + MarkdownUtils.class.getResource("/markdown/css/admonition.css") + "' type='text/css'>" + "\n" + 
				"</head>" + "\n" + 
				"<body>%s</body>" + "\n" + 
			"</html>";
	
	private static Parser parser;
	private static HtmlRenderer renderer;

	static {
		MutableDataSet options = new MutableDataSet();
		options.setFrom(ParserEmulationProfile.KRAMDOWN);
		options.set(Parser.EXTENSIONS,
				Arrays.asList(AbbreviationExtension.create(), DefinitionExtension.create(), FootnoteExtension.create(),
						TablesExtension.create(), TypographicExtension.create(), StrikethroughExtension.create(),
						AdmonitionExtension.create(), EmojiExtension.create()));
		options.set(EmojiExtension.ROOT_IMAGE_PATH, "" + MarkdownUtils.class.getResource("/markdown/emojis/"));
		parser = Parser.builder(options).build();
		renderer = HtmlRenderer.builder(options).build();
	}

	public static String render(String markdown) {
		Node document = parser.parse(markdown);
		String body = renderer.render(document);
		String html = String.format(TEMPLATE, body);
		return html;
	}

}
