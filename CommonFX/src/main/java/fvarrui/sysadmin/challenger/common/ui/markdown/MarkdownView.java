package fvarrui.sysadmin.challenger.common.ui.markdown;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MarkdownView extends BorderPane {

	// model

	private StringProperty markdown = new SimpleStringProperty();
	private ReadOnlyStringWrapper html = new ReadOnlyStringWrapper();

	private WebEngine engine;

	// view

	private WebView webView;

	public MarkdownView() {
		super();
		
		this.webView = new WebView();
		this.webView.setContextMenuEnabled(false);
		
		this.engine = webView.getEngine();

		this.markdown.addListener((o, ov, nv) -> {
			html.set(MarkdownUtils.render(nv));
		});

		this.html.addListener((o, ov, nv) -> {
			engine.loadContent(nv);
		});

		setCenter(webView);
	}

	public final StringProperty markdownProperty() {
		return this.markdown;
	}

	public final String getMarkdown() {
		return this.markdownProperty().get();
	}

	public final void setMarkdown(final String markdown) {
		this.markdownProperty().set(markdown);
	}

	public final javafx.beans.property.ReadOnlyStringProperty htmlProperty() {
		return this.html.getReadOnlyProperty();
	}

	public final String getHtml() {
		return this.htmlProperty().get();
	}

}
